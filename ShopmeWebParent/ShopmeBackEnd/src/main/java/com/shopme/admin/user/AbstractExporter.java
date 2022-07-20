package com.shopme.admin.user;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.shopme.common.entity.User;

public class AbstractExporter {
	public void setResponseHeader( HttpServletResponse httpServletResponse,String contentType,String extention) throws IOException {
		DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		String timestamp=dateFormat.format(new Date());
		String fileName="users_"+ timestamp+ extention;
		httpServletResponse.setContentType(contentType);
		String headerKey="Content-Disposition";
		String headerValue="attachment;filename="+fileName;
		httpServletResponse.setHeader(headerKey, headerValue);
	}

}
