package com.bangduoduo.xml;

import java.io.ByteArrayInputStream;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.junit.Test;

import com.bangduoduo.web.form.Field;
import com.bangduoduo.web.form.Form;

public class FormJaxbContextTest {
	/**
	   <form>
	     <dataSources>
	     <dataSource id="dbNames" type="db" > 
	         select id,name from User
	     </dataSource>
	     <dataSource id="fileNames" type="file" >
	         names.properties
	     </dataSource> 
	     <dataSource id="staticNames" type="static" default="1">
	         <![CDATA[
	         <option value="1">1</option>
	         <option value="2">1</option>
		 ]]>
	     </dataSource> 
	     <dataSource id="constNames" type="const" default="10">
	        1..10,10..20
	     </dataSource> 
	      </dataSources>
	      <fields>
	         <field name="name" type="text" label="UserName" required="true" size="12" initValue=""/>
	         <field name="birth" type="select" label="Birth" required="true" size="12" initValue="" dataSource="dbNames"/>
	      </fields>
	 </form>
	 */
	
	@Test
	public void should_return_java_bean_by_jaxb(){
		StringBuilder sb = new StringBuilder();
		sb.append("<form><resources>");
		sb.append("<dataSource id=\"dbNames\" type=\"db\">");
		sb.append("select id,name from User");
		sb.append("</dataSource>");
		sb.append("<dataSource id=\"staticNames\" type=\"static\" >");
		sb.append("<![CDATA[<option value=\"1\">1</option><option valule=\"2\">2</option>]]>");
		sb.append("</dataSource>");
		sb.append("</resources>");
		
		sb.append("<htmlItems><field name=\"name\" type=\"text\" label=\"UserName\" required=\"true\" size=\"12\" initValue=\"\"/>");
		sb.append("</htmlItems></form>");
		JAXBContext jaxbContext = FormJaxbContext.getJAXBContextInstance();
		try {
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			Form form = (Form)unmarshaller.unmarshal(new ByteArrayInputStream(sb.toString().getBytes()));
			List<Field> list = form.getHtmlItems().getFields();
			for(Field field: list){
				System.out.println(field.getName()+" "+field.getLabel()+" "+field.getType());
			}
			
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
