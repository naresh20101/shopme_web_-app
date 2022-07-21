package com.shopme.admin.user;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.Document;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfCell;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfTable;
import com.lowagie.text.pdf.PdfWriter;
import com.microsoft.schemas.office.visio.x2012.main.impl.DocumentSettingsTypeImpl;
import com.shopme.common.entity.User;

public class UserPDFExporter extends AbstractExporter {
	public void export(List<User> listUsers, HttpServletResponse httpServletResponse) throws IOException{
		super.setResponseHeader(httpServletResponse, "application/pdf", ".pdf");
		
		Document doucuments=new Document(PageSize.A4);
	PdfWriter.getInstance(doucuments, httpServletResponse.getOutputStream());
	doucuments.open();
	com.lowagie.text.Font font=FontFactory.getFont(FontFactory.HELVETICA_BOLD);
	font.setSize(18);
	font.setColor(Color.blue);
	Paragraph paragraph=new Paragraph("List Of Users",font);
	paragraph.setAlignment(Paragraph.ALIGN_CENTER);
	doucuments.add(paragraph);
	PdfPTable table=new PdfPTable(6);
	table.setWidthPercentage(100f);
	table.setSpacingBefore(10);
	table.setWidths(new float[] {1.5f,3.5f,3.8f,3.0f,3.0f,1.6f});
	writeTableHeader(table);
	writeTableData(table,listUsers);
	doucuments.add(table);
	doucuments.close();
	}

	private void writeTableData(PdfPTable table, List<User> listUsers) {
		// TODO Auto-generated method stub
		for(User user:listUsers) {
			table.addCell(String.valueOf(user.getId()));
			table.addCell(String.valueOf(user.getEmail()));
			table.addCell(String.valueOf(user.getFirstName()));
			table.addCell(String.valueOf(user.getLastName()));
			table.addCell(String.valueOf(user.getRoles().toString()));
			table.addCell(String.valueOf(user.isEnabled()));
		}
		
	}

	private void writeTableHeader(PdfPTable table) {
		// TODO Auto-generated method stub
		PdfPCell cell=new PdfPCell();
		cell.setBackgroundColor(Color.blue);
		cell.setPadding(5);
		
		com.lowagie.text.Font font=FontFactory.getFont(FontFactory.HELVETICA);
		
		font.setColor(Color.WHITE);
		cell.setPhrase(new Phrase("UserId",font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("E-mail",font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("First Name",font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Last Name",font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Roles",font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Enabled",font));
		table.addCell(cell);
		
	}

}
