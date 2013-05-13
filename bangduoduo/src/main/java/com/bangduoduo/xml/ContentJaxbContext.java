package com.bangduoduo.xml;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import com.bangduoduo.web.form.Content;

public class ContentJaxbContext {
	private final static ContentJaxbContext contentJaxbContext = new ContentJaxbContext();
	
	private JAXBContext jaxbContext;
	
	private ContentJaxbContext(){
		try {
			jaxbContext = JAXBContext.newInstance(Content.class);
		} catch (JAXBException e) {
			e.printStackTrace();
		} 
	}
	
	public static JAXBContext getJAXBContextInstance(){
		return contentJaxbContext.jaxbContext;
	}
}
