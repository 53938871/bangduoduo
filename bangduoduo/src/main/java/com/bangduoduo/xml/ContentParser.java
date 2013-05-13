package com.bangduoduo.xml;

import java.io.ByteArrayInputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.bangduoduo.web.form.Content;

public class ContentParser {
	public static Content parseContent(String contentXml){
		JAXBContext jaxbContext = ContentJaxbContext.getJAXBContextInstance();
		try {
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			Content content = (Content)unmarshaller.unmarshal(new ByteArrayInputStream(contentXml.getBytes()));
			return content;
			
		} catch (JAXBException e) {
			e.printStackTrace();
			return null;
		}
	}
}
