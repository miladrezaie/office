package com.javasampleapproach.jqueryboostraptable.Service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.javasampleapproach.jqueryboostraptable.model.Tajhizat;
import com.javasampleapproach.jqueryboostraptable.model.User;
import com.javasampleapproach.jqueryboostraptable.model.officeForm;
import javafx.scene.control.Cell;


import javax.servlet.http.HttpServletResponse;


import java.io.IOException;


public class OfficePdfGenerator {

    private officeForm officeForms;

    public OfficePdfGenerator(officeForm officeForms) {
        this.officeForms = officeForms;
    }

    private void writeTableHeader(PdfPTable table) throws IOException, com.itextpdf.text.DocumentException {
        table.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
        PdfPCell cell = new PdfPCell();
        cell.setPadding(8);

        BaseFont farsiFont = BaseFont.createFont("D:/project/office/src/main/resources/static/fonts/Vazir.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font paraFont = new Font(farsiFont, 8);


        cell.setPhrase(new Phrase("نام برنامه : " + officeForms.getProgram().getName(), paraFont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);

        cell.setPhrase(new Phrase("مکان برنامه : " + officeForms.getLocation().getName(), paraFont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);

        cell.setPhrase(new Phrase("تاریخ شروع آفیش : " + officeForms.getDate_begin() + "تاریخ پایان ضبط : " + officeForms.getDate_end(), paraFont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);

        cell.setPhrase(new Phrase("شروع  و پایان ضبط : " + officeForms.getSaat_zabt() + " - " + officeForms.getSaat_zabt_end(), paraFont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);


        cell.setPhrase(new Phrase("عوامل : ", paraFont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cell.setPhrase(new Phrase("نام و نام خانوادگی : ", paraFont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cell.setPhrase(new Phrase("تجهیزات همراه : ", paraFont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cell.setPhrase(new Phrase("امضا : ", paraFont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);


//        nestedtable5.addCell(getCell("asdasdasd", PdfPCell.ALIGN_CENTER));
//        nestedtable5.addCell(getCell("asdasdasdasdddd", PdfPCell.ALIGN_RIGHT));


    }

    private void writeTableData(PdfPTable table) throws IOException, com.itextpdf.text.DocumentException {
        table.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
        BaseFont farsiFont = BaseFont.createFont("D:/project/office/src/main/resources/static/fonts/Vazir.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font paraFont = new Font(farsiFont, 6);
        PdfPCell cell = new PdfPCell();
        cell.setPaddingBottom(5);
        cell.setPhrase(new Phrase(officeForms.getProgram().getName(), paraFont));
        table.addCell(cell);
        cell.setPhrase(new Phrase(officeForms.getLocation().getName(), paraFont));
        table.addCell(cell);

    }

    public void export(HttpServletResponse response) throws IOException, DocumentException {
//        Document document = new Document(PageSize.A5, 50, 50, 50, 50);
        Document document = new Document(PageSize.A5);

        PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());
        writer.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);

        document.open();
        document.addTitle("نسخه چاپی آفیش");

        BaseFont farsiFont = BaseFont.createFont("D:/project/office/src/main/resources/static/fonts/Vazir.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font paraFont = new Font(farsiFont, 8);


        PdfPTable tableHeader = new PdfPTable(1);
        tableHeader.setWidthPercentage(100f);
        tableHeader.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
        PdfPCell cell1 = new PdfPCell();
        cell1.setPadding(10);
        cell1.setPhrase(new Phrase(officeForms.getType().getDisplayName(), paraFont));
        cell1.setVerticalAlignment(Element.ALIGN_CENTER);
        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell1.setBorder(Rectangle.NO_BORDER);

        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        tableHeader.addCell(cell1);


        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100f);
        table.setWidths(new float[]{4.0f, 4.0f, 4.0f, 4.0f});


        PdfPTable table_1 = new PdfPTable(4);
        table_1.setWidthPercentage(100f);
        table_1.setWidths(new float[]{4.0f, 4.0f, 4.0f, 4.0f});
        table_1.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);


        PdfPCell cell1_1 = new PdfPCell(new Phrase("عوامل  ", paraFont));
        cell1_1.setPadding(8);
        cell1_1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        PdfPCell cell2_2 = new PdfPCell(new Phrase("نام و نام خانوادگی ", paraFont));
        cell2_2.setPadding(8);
        cell2_2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        PdfPCell cell3_3 = new PdfPCell(new Phrase("تجهیزات همراه ", paraFont));
        cell3_3.setPadding(8);
        cell3_3.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        PdfPCell cell4_4 = new PdfPCell(new Phrase("امضا  ", paraFont));
        cell4_4.setPadding(8);
        cell4_4.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);



        PdfPTable nestedTableUserAvamel = new PdfPTable(1);
        nestedTableUserAvamel.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
        for (User user : officeForms.getUsers()) {
            nestedTableUserAvamel.addCell(getCell(user.getJob().getName(), PdfPCell.ALIGN_CENTER));
        }

        PdfPTable nestedTableUser = new PdfPTable(1);
        nestedTableUser.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
        for (User user : officeForms.getUsers()) {
            nestedTableUser.addCell(getCell(user.getFullname(), PdfPCell.ALIGN_CENTER));
        }

        PdfPTable nestedTableTajhizat = new PdfPTable(1);
        nestedTableTajhizat.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
        for (Tajhizat tajhizat : officeForms.getTajhizatss()) {
            nestedTableTajhizat.addCell(getCell(tajhizat.getName(), PdfPCell.ALIGN_CENTER));
        }
        cell1_1.addElement(nestedTableUserAvamel);
        cell2_2.addElement(nestedTableUser);
        cell3_3.addElement(nestedTableTajhizat);

        table_1.addCell(cell1_1);
        table_1.addCell(cell2_2);
        table_1.addCell(cell3_3);
        table_1.addCell(cell4_4);


        addEmptyLine(new Paragraph(), 1);
        PdfPTable emzaTable = new PdfPTable(2);
        emzaTable.setWidthPercentage(100f);
        emzaTable.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
        emzaTable.setWidths(new float[]{4.0f, 4.0f});

        PdfPCell cellEmza = new PdfPCell();
        cellEmza.setPhrase(new Phrase("امضای تصویر بردار : ", paraFont));
        cellEmza.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellEmza.setBorder(Rectangle.NO_BORDER);
        cellEmza.setVerticalAlignment(Element.ALIGN_MIDDLE);
        emzaTable.addCell(cellEmza);

        cellEmza.setPhrase(new Phrase("امضای صدابردار : ", paraFont));
        cellEmza.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellEmza.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cellEmza.setBorder(Rectangle.NO_BORDER);
        emzaTable.addCell(cellEmza);

        writeTableHeader(table);
        writeTableData(table);

        document.add(tableHeader);
        document.add(table);
        document.add(table_1);
        document.add(emzaTable);
        document.close();
    }

    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }

    public PdfPCell getCell(String text, int alignment) throws IOException, DocumentException {
        BaseFont farsiFont = BaseFont.createFont("D:/project/office/src/main/resources/static/fonts/Vazir.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font paraFont = new Font(farsiFont, 8);
        PdfPCell cell = new PdfPCell(new Phrase(text, paraFont));
        cell.setPadding(4);
        cell.setHorizontalAlignment(alignment);
        cell.setBorder(PdfPCell.NO_BORDER);
        return cell;
    }
}
