package com.shopme.admin.user;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.shopme.common.entity.User;

public class UserCSVExporter extends AbstractExporter {
	public void export(List<User> listUsers, HttpServletResponse httpServletResponse) throws IOException {
		super.setResponseHeader(httpServletResponse, "text/csv", ".csv");
		ICsvBeanWriter csvWriter=new CsvBeanWriter(httpServletResponse.getWriter(),CsvPreference.STANDARD_PREFERENCE);
		String[] csvHeader= {"User_id","Email","First Name","Last Name","Roles","Enabled"};
		String[] fieldMapping= {"id","email","firstName","lastName","roles","enabled"};
	
		csvWriter.writeHeader(csvHeader);
		for(User user:listUsers) {
			csvWriter.write(user,fieldMapping);
		}
		csvWriter.close();
		
	}

}
