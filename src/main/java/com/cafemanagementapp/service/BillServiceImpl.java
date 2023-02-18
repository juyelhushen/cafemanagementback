package com.cafemanagementapp.service;

import com.cafemanagementapp.constant.CafeConstants;
import com.cafemanagementapp.dao.BillDao;
import com.cafemanagementapp.entity.Bill;
import com.cafemanagementapp.jwt.JwtAuthFilter;
import com.cafemanagementapp.utils.CafeUtils;
import com.cafemanagementapp.wrapper.BillWrapper;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.io.IOUtils;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class BillServiceImpl implements BillService {

    @Autowired
    private final BillDao billDao;
    private final JwtAuthFilter filter;


    @Override
    public ResponseEntity<String> generateReport(Map<String, Object> request) {
        log.info("Inside report Generator");
        try {
            String fileName;
            if (filter.isAdmin()) {
                if (validateRequest(request)) {
                    if (request.containsKey("isGenerate") && !(Boolean) request.get("isGenerate")) {
                        fileName = request.get("uuid").toString();
                    } else {
                        fileName = CafeUtils.getUUID();
                        request.put("uuid", fileName);
                        insertBill(request);
                    }

                    String data = "Name : " + request.get("name")
                            + "\n" + "Contact Number : " + request.get("contactNumber") + "\n"
                            + "\n" + "Email : " + request.get("email") + "\n"
                            + "PaymentMethod : " + request.get("paymentMethod");
                    Document doc = new Document();
                    PdfWriter.getInstance(doc, new FileOutputStream(CafeConstants.STORE_LOCATION + "\\" + fileName + ".pdf"));

                    doc.open();
                    setRectanglePdf(doc);


                    Paragraph header = new Paragraph("Payment Details", getFont("Header"));
                    header.setAlignment(Element.ALIGN_CENTER);
                    doc.add(header);


                    Paragraph body = new Paragraph(data + "\n \n", getFont("Data"));
                    doc.add(body);


                    PdfPTable table = new PdfPTable(5);
                    table.setWidthPercentage(100);
                    addTableHeaders(table);


                    JSONArray jsonArray = CafeUtils.getJsonArrayFromString(request.get("productDetails").toString());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        addRow(table, CafeUtils.getMapFromJson(jsonArray.getString(i)));
                    }
                    doc.add(table);

                    Paragraph footer = new Paragraph("Total : " + request.get("totalAmount") +
                            "\n" + "Thank you for visiting. Please do visit again!.", getFont("Data"));
                    doc.add(footer);
                    doc.close();
                    return new ResponseEntity<>("{\"uuid\":\"" + fileName + "\"}", HttpStatus.OK);

                } else {
                    return CafeUtils.getResponse(CafeConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
                }
            } else {
                return CafeUtils.getResponse(CafeConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CafeUtils.getResponse(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    //mapping the data to the table
    private void addRow(PdfPTable table, Map<String, Object> data) {
        log.info("inside addRow");
        table.addCell(data.get("name").toString());
        table.addCell(data.get("category").toString());
        table.addCell(data.get("quantity").toString());
        table.addCell(data.get("price").toString());
        table.addCell(data.get("total").toString());

    }

    //    pdf design
    private void addTableHeaders(PdfPTable table) {
        log.info("Inside table headers");
        Stream.of("Name", "Category", "Quantity", "Price", "Sub Total")
                .forEach(CT -> {
                    PdfPCell cell = new PdfPCell();
                    cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    cell.setBorderWidth(2);
                    cell.setPhrase(new Phrase(CT));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    table.addCell(cell);
                });
    }


    //pdf file font
    private Font getFont(String type) {
        log.info("inside font factory");
        switch (type) {
            case "Header":
                Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLDOBLIQUE, 18, BaseColor.BLUE);
                headerFont.setStyle(Font.BOLD);
                return headerFont;
            case "Data":
                Font dataFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, BaseColor.BLACK);
                dataFont.setStyle(Font.BOLD);
                return dataFont;
            default:
                return new Font();
        }
    }

    //Pdf Border Template
    private void setRectanglePdf(Document doc) throws DocumentException {
        log.info("inside document Pdf.");
        Rectangle rectangle = new Rectangle(566, 800, 18, 15);
        rectangle.enableBorderSide(1);
        rectangle.enableBorderSide(2);
        rectangle.enableBorderSide(4);
        rectangle.enableBorderSide(8);
        rectangle.setBorderColor(BaseColor.DARK_GRAY);
        rectangle.setBorderWidth(1);
        doc.add(rectangle);
    }

    private boolean validateRequest(Map<String, Object> request) {
        return request.containsKey("name") && request.containsKey("email")
                && request.containsKey("contactNumber") && request.containsKey("paymentMethod")
                && request.containsKey("productDetails");
    }

    private void insertBill(Map<String, Object> request) {
        try {
            Bill bill = new Bill();
            bill.setUuid(request.get("uuid").toString());
            bill.setName(request.get("name").toString());
            bill.setEmail(request.get("email").toString());
            bill.setContactNumber(request.get("contactNumber").toString());
            bill.setPaymentMethod(request.get("paymentMethod").toString());
            bill.setProductDetails(request.get("productDetails").toString());
            bill.setCreatedBy(filter.getCurrentUser());
            bill.setTotal(Integer.parseInt(request.get("totalAmount").toString()));
            billDao.save(bill);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ResponseEntity<List<BillWrapper>> getBill() {
        try {
            List<BillWrapper> bill = new ArrayList<>();
            if (filter.isAdmin()) {
                List<BillWrapper> billAll = billDao.getAllBill();
                return new ResponseEntity<>(billAll, HttpStatus.OK);
            } else {
                List<BillWrapper> userBill = billDao.getUserBill(filter.getCurrentUser());
                return new ResponseEntity<>(userBill, HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<List<BillWrapper>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @Override
    public ResponseEntity<byte[]> getPDF(Map<String, Object> request) {
        log.info("inside getPDF method");
        try {
            byte[] bytesArray = new byte[0];
            if (!request.containsKey("uuid") && validateRequest(request)) {
                return new ResponseEntity<>(bytesArray, HttpStatus.BAD_REQUEST);
            }
            System.out.println(request.get("uuid"));
            String filePath = CafeConstants.STORE_LOCATION + "\\" +(String) request.get("uuid") + ".pdf";
            if (CafeUtils.isFileExist(filePath)) {
                bytesArray = getByteArray(filePath);
                return new ResponseEntity<>(bytesArray, HttpStatus.OK);
            } else {
                request.put("isGenerate", false);
                generateReport(request);
                bytesArray = getByteArray(filePath);
                return new ResponseEntity<>(bytesArray, HttpStatus.OK);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private byte[] getByteArray(String filePath) throws Exception {
        File initialFile = new File(filePath);
        InputStream targetStream = new FileInputStream(initialFile);
        byte[] bytes = IOUtils.toByteArray(targetStream);
        targetStream.close();
        return bytes;
    }

    @Override
    public ResponseEntity<String> deleteBill(Integer id) {
        try {
            Optional<Bill> optionalBill = billDao.findById(id);
            if (optionalBill.isPresent()) {
                billDao.deleteById(id);
                return CafeUtils.getResponse("Bill deleted successfully",HttpStatus.OK);
            } else {
                return CafeUtils.getResponse("Bill id does not exist",HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CafeUtils.getResponse(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
