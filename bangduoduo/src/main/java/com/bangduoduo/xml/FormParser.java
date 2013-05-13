package com.bangduoduo.xml;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.bangduoduo.domain.Document;
import com.bangduoduo.utils.FreemarkerUtil;
import com.bangduoduo.web.form.Content;
import com.bangduoduo.web.form.DataSource;
import com.bangduoduo.web.form.Field;
import com.bangduoduo.web.form.Form;
import com.bangduoduo.web.form.component.Component;

import freemarker.template.TemplateException;

public class FormParser {
	public static Form getForm(String formXml){
		JAXBContext jaxbContext = FormJaxbContext.getJAXBContextInstance();
		try {
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			Form form = (Form)unmarshaller.unmarshal(new ByteArrayInputStream(formXml.getBytes()));
			return form;
			
		} catch (JAXBException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void renderFormHtml(Document document,String formXml){
		Form form = getForm(formXml);
		List<Field> list = form.getHtmlItems().getFields();
		List<Component> components = new ArrayList<Component>();
		
		Map<String,DataSource> dataSourceMap = form.getDataSourceMap();
		
		Content content = getContent(document);
		for(Field field : list){
			String componentType = field.getType();
			try {
				@SuppressWarnings("unchecked")
				Class<? extends Component> clazz = 
					(Class<? extends Component>)Class.forName("com.bangduoduo.web.form.component."+componentType);
				Constructor<? extends Component> constructor = clazz.getConstructor(new Class[]{DataSource.class,Field.class});
				constructor.setAccessible(true);
				Component component = (Component)constructor.newInstance(dataSourceMap.get(field.getDataSource()),field);
				if(content != null){
					component.setValue(content.getValueByName(field.getName()));
				}
				components.add(component);
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		form.setComponents(components);
		
		try {
			String formHtml = FreemarkerUtil.transfer("Form.ftl", form, null);
			System.out.println(formHtml);
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static Content getContent(Document document){
		if(document == null){
			return null;
		}
		
		Content content = ContentParser.parseContent(document.getContent());
		return content;
	}
	
	
	public static void main(String[] args){
		StringBuilder sb = new StringBuilder();
		sb.append("<form action=\"aaa.action\" name=\"test\"><resources>");
		sb.append("<dataSource id=\"dbNames\" type=\"db\">");
		sb.append("select id,name from User");
		sb.append("</dataSource>");
		sb.append("<dataSource id=\"staticNames\" type=\"static\" >");
		sb.append("<![CDATA[{'':'请选择','a':'a','b':'b','c':'3'}]]>");
		sb.append("</dataSource>");
		sb.append("</resources>");
		
		sb.append("<htmlItems>");
		sb.append("<field name=\"name\" type=\"Select\" label=\"UserName\" required=\"true\" css=\"input\" size=\"12\" initValue=\"\" dataSource=\"staticNames\"/>");
	    sb.append("<field name=\"age\" type=\"Text\" label=\"Age\" hint=\"please input your name\" css=\"input\" />");
	    sb.append("<field name=\"hobby\" type=\"CheckBox\" label=\"hobby\" dataSource=\"staticNames\" />");
	    sb.append("<field name=\"radio\" type=\"Radio\" label=\"radio\" dataSource=\"staticNames\"/>");
	    sb.append("<field name=\"des\" type=\"TextArea\" label=\"des\" id=\"des\" css=\"input\"/>");
		sb.append("</htmlItems></form>");
		
		StringBuilder content = new StringBuilder();
		content.append("<content><node name=\"age\">20</node><node name=\"name\">c</node></content>");
		
		Document document = new Document();
		document.setContent(content.toString());
		
		renderFormHtml(document,sb.toString());
	}
	
}
