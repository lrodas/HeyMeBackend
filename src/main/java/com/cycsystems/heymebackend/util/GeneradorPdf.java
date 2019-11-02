package com.cycsystems.heymebackend.util;

import com.cycsystems.heymebackend.models.entity.Notificacion;
import com.itextpdf.text.*;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.List;

public class GeneradorPdf {

	private static final Logger logger = LogManager.getLogger(GeneradorPdf.class);

    public static ByteArrayInputStream ReporteNotificaciones(List<Notificacion> notificaciones) throws DocumentException {

    	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4.rotate(), 36, 36, 90, 36);
        PdfWriter writer = PdfWriter.getInstance(document, out);
        HeaderFooterPageEvent event = new HeaderFooterPageEvent();
        writer.setPageEvent(event);

        try {

            PdfPTable table = new PdfPTable(6);
            table.setWidthPercentage(100);
            table.setWidths(new int[]{1, 5, 3, 3, 3, 5});

            Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, Font.BOLD, BaseColor.WHITE);

            PdfPCell hcell;
            hcell = new PdfPCell(new Phrase("#", headFont));
            Style.headerCellStyle(hcell);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("TITULO", headFont));
            Style.headerCellStyle(hcell);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("FECHA DE ENVIO", headFont));
            Style.headerCellStyle(hcell);
            table.addCell(hcell);
            
            hcell = new PdfPCell(new Phrase("FECHA DE PROGRAMACION", headFont));
            Style.headerCellStyle(hcell);
            table.addCell(hcell);
            
            hcell = new PdfPCell(new Phrase("ESTADO", headFont));
            Style.headerCellStyle(hcell);
            table.addCell(hcell);
            
            hcell = new PdfPCell(new Phrase("USUARIO", headFont));
            Style.headerCellStyle(hcell);
            table.addCell(hcell);

            
            Font font1 = new Font(FontFamily.HELVETICA, 8, Font.NORMAL, BaseColor.DARK_GRAY);
            Font font2 = new Font(FontFamily.HELVETICA, 8, Font.NORMAL, BaseColor.BLACK);
            for (Notificacion notificacion : notificaciones) {

                PdfPCell cell;

                cell = new PdfPCell(new Phrase(notificacion.getIdNotificaciones()+"", font1));
                Style.labelCellStyle(cell);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(notificacion.getTitulo(), font2));
                Style.valueCellStyle(cell);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(sdf.format(notificacion.getFechaEnvio()), font1));
                Style.labelCellStyle(cell);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
                
                cell = new PdfPCell(new Phrase(sdf.format(notificacion.getFechaProgramacion()), font2));
                Style.valueCellStyle(cell);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
                
                cell = new PdfPCell(new Phrase(notificacion.getEstado().getDescripcion(), font1));
                Style.labelCellStyle(cell);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
                
                cell = new PdfPCell(new Phrase(notificacion.getUsuario().getNombres() + " " + notificacion.getUsuario().getApellidos(), font2));
                Style.valueCellStyle(cell);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(cell);
            }

            document.open();
            document.addTitle("Reporte de notificaciones");
            document.add(table);
            document.close();

        } catch (DocumentException ex) {

            logger.error("Error occurred: {0}", ex);
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
}
