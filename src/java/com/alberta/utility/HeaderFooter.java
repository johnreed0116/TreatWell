/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alberta.utility;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.text.SimpleDateFormat;

/**
 *
 * @author Faraz
 */
public class HeaderFooter extends PdfPageEventHelper {

    String header;
    PdfTemplate total;

    public void setHeader(String header) {
        this.header = header;
    }

    @Override
    public void onOpenDocument(PdfWriter writer, Document document) {
        total = writer.getDirectContent().createTemplate(30, 16);
    }

    @Override
    public void onChapter(PdfWriter writer, Document document,
            float paragraphPosition, Paragraph title) {
    }

    @Override
    public void onStartPage(PdfWriter writer, Document document) {
    }

    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        /*try {
         Rectangle rect = writer.getBoxSize("art");
         String pageNo = Integer.toString(writer.getPageNumber());
         String temp = String.format("Page %d of", writer.getPageNumber());
         Image.getInstance(total);
         Font font = new Font(Font.FontFamily.TIMES_ROMAN, 7);
         ColumnText.showTextAligned(writer.getDirectContent(),
         Element.ALIGN_CENTER, new Phrase(temp, font),
         (rect.getLeft() + rect.getRight()) / 2, rect.getBottom() - 3, 0);
            

         String newstring = new SimpleDateFormat("dd/MM/yyyy hh:mm a").format(new java.util.Date());
         ColumnText.showTextAligned(writer.getDirectContent(),
         Element.ALIGN_RIGHT, new Phrase(newstring, font),
         rect.getRight() - 3,
         rect.getBottom() - 3, 0);
         } catch (Exception ex) {
         }*/
        PdfPTable table = new PdfPTable(2);
        try {
            Rectangle rect = writer.getBoxSize("art");
            Font font = new Font(Font.FontFamily.TIMES_ROMAN, 8);
            table.setWidths(new int[]{48, 2});
            table.setTotalWidth(527);
            table.setLockedWidth(true);
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
            PdfPCell c1 = new PdfPCell(new Phrase(String.format("Page %d of", writer.getPageNumber()), font));
            c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
            c1.setBorder(0);
            table.addCell(c1);
            c1 = new PdfPCell(Image.getInstance(total));
            c1.setBorder(0);
            table.addCell(c1);
            //(rect.getLeft() + rect.getRight()) / 2, rect.getBottom() - 3
            table.writeSelectedRows(0, -1, 10, 35, writer.getDirectContent());
        } catch (DocumentException de) {
            throw new ExceptionConverter(de);
        }
       
    }

    @Override
    public void onCloseDocument(PdfWriter writer, Document document) {
        ColumnText.showTextAligned(total, Element.ALIGN_LEFT,
                new Phrase(String.valueOf(writer.getPageNumber() - 1)),
                2, 2, 0);
    }
}
