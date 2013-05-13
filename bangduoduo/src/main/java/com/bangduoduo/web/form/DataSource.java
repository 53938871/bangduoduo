package com.bangduoduo.web.form;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;


@XmlAccessorType(XmlAccessType.FIELD)
public class DataSource {
	@XmlAttribute
	private String id;
	
	@XmlAttribute
	private String type;
	
	
	@XmlValue
	private String value;
	
	public DataSource(){}
	
	public DataSource(String id,String type,String value){
		this.id = id;
		this.type = type;
		this.value = value;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}
