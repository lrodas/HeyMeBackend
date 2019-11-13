package com.cycsystems.heymebackend.util;

import com.cycsystems.heymebackend.models.entity.Notificacion;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

public class GeneradorExcel {
	
	public static ByteArrayInputStream reporteNotificaciones(List<Notificacion> notificaciones) throws IOException {
	    
		String[] COLUMNs = {"#", "Titulo", "Fecha de Envio", "Fecha de programacion", "Estado", "Usuario"};
	    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		
		try(
	        Workbook workbook = new XSSFWorkbook();
	        ByteArrayOutputStream out = new ByteArrayOutputStream();
	    ){
	      CreationHelper createHelper = workbook.getCreationHelper();
	   
	      Sheet sheet = workbook.createSheet("notificaciones");
	   
	      Font headerFont = workbook.createFont();
	      headerFont.setBold(true);
	      headerFont.setColor(IndexedColors.BLUE.getIndex());
	   
	      CellStyle headerCellStyle = workbook.createCellStyle();
	      headerCellStyle.setFont(headerFont);
	   
	      // Row for Header
	      Row headerRow = sheet.createRow(0);
	   
	      // Header
	      for (int col = 0; col < COLUMNs.length; col++) {
	        Cell cell = headerRow.createCell(col);
	        cell.setCellValue(COLUMNs[col]);
	        cell.setCellStyle(headerCellStyle);
	      }
	   
	      // CellStyle for Age
	      CellStyle ageCellStyle = workbook.createCellStyle();
	      ageCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("#"));
	   
	      int rowIdx = 1;
	      for (Notificacion notificacion : notificaciones) {
	        Row row = sheet.createRow(rowIdx++);
	   
	        row.createCell(0).setCellValue(notificacion.getIdNotificaciones());
	        row.createCell(1).setCellValue(notificacion.getTitulo());
	        row.createCell(2).setCellValue(sdf.format(notificacion.getFechaEnvio()));
	        row.createCell(3).setCellValue(sdf.format(notificacion.getFechaProgramacion()));
	        row.createCell(4).setCellValue(notificacion.getEstado().getDescripcion());
	   
	        Cell ageCell = row.createCell(5);
	        ageCell.setCellValue(notificacion.getUsuario().getNombres() + " " + notificacion.getUsuario().getApellidos());
	        ageCell.setCellStyle(ageCellStyle);
	      }
	   
	      workbook.write(out);
	      return new ByteArrayInputStream(out.toByteArray());
	    }
	  }
	
}
