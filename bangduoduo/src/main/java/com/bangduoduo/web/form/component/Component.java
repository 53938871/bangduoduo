package com.bangduoduo.web.form.component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.bangduoduo.utils.FreemarkerUtil;
import com.bangduoduo.utils.WebUtil;
import com.bangduoduo.web.form.DataSource;
import com.bangduoduo.web.form.Field;

import freemarker.template.TemplateException;

public class Component {
	protected String label;
	
	protected String value;
	
	protected String html;
	
	private DataSource dataSource;
	
	private Field field;
	
	protected final static Map<String,Object> propertyMap = new HashMap<String,Object>();
	
	public Component(){}
	
	public Component(DataSource dataSource,Field field){
		this.dataSource = dataSource;
		this.field = field;
		this.label = field.getLabel();
	}

	public String getHtml() {
		String className = getComponentName();
		String html = "";
		loadDatasourceValue();
		try {
			html = FreemarkerUtil.transfer(className+".ftl", this, propertyMap);
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return html;
	}
	
	private void loadDatasourceValue(){
		if(field.getDataSource() == null){
			return;
		}
		if ("static".equals(dataSource.getType())){
			Map<String,String> options = WebUtil.json2Options(dataSource.getValue());
			propertyMap.put("options", options);
			return;
		} 
		if ("db".equals(dataSource.getType())){
			
		}
	}
	

	private String getComponentName() {
		String componentName = this.getClass().getName();
		String className = componentName.substring(componentName.lastIndexOf(".")+1);
		return className;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}
	
	public static void main(String[] args){
		Component c = new Component();
		c.getHtml();
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}
