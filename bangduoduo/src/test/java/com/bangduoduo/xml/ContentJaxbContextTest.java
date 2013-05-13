package com.bangduoduo.xml;

import java.io.ByteArrayInputStream;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.junit.Test;

import com.bangduoduo.web.form.Content;
import com.bangduoduo.web.form.Node;

public class ContentJaxbContextTest {
	private ContentJaxbContext context;
	
	@Test
	public void should_return_java_bean_by_jaxb(){
		String xml = "<content><node name=\"name\">pxc</node></content>";
		JAXBContext jaxbContext = ContentJaxbContext.getJAXBContextInstance();
		try {
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			Content content = (Content)unmarshaller.unmarshal(new ByteArrayInputStream(xml.getBytes()));
			List<Node> list = content.getNodes();
			for(Node node : list){
				System.out.println(node.getName()+" "+ node.getValue());
			}
			
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		
	}
}
