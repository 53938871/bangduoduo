package com.bangduoduo.xml;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import com.bangduoduo.web.form.Form;

public class FormJaxbContext {
	private final static FormJaxbContext contentJaxbContext = new FormJaxbContext();
	
	private JAXBContext jaxbContext;
	
	private FormJaxbContext(){
		try {
			jaxbContext = JAXBContext.newInstance(Form.class);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
	
	public static JAXBContext getJAXBContextInstance(){
		return contentJaxbContext.jaxbContext;
	}
}
