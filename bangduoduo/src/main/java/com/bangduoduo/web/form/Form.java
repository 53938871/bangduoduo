package com.bangduoduo.web.form;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.bangduoduo.web.form.component.Component;

@XmlRootElement(name="form")
@XmlAccessorType(XmlAccessType.FIELD)
public class Form {
	
	@XmlAttribute
	private String action;
	
	@XmlAttribute
	private String name;
	
	@XmlElement(name="htmlItems")
	private HtmlItems htmlItems;
	
	@XmlElement(name="resources")
	private Resources resources;
	
	private List<Component> components;

	public HtmlItems getHtmlItems() {
		return htmlItems;
	}

	public void setHtmlItems(HtmlItems htmlItems) {
		this.htmlItems = htmlItems;
	}

	public Resources getResources() {
		return resources;
	}

	public void setResources(Resources resources) {
		this.resources = resources;
	}
	
	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Map<String,DataSource> getDataSourceMap(){
		Map<String,DataSource> dataSourceMap = new HashMap<String,DataSource>();
		List<DataSource> list = resources.getDataSource();
		for(DataSource dataSource : list){
			dataSourceMap.put(dataSource.getId(), dataSource);
		}
		return dataSourceMap;
	}

	public List<Component> getComponents() {
		return components;
	}

	public void setComponents(List<Component> components) {
		this.components = components;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
}
