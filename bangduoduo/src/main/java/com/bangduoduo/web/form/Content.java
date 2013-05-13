package com.bangduoduo.web.form;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name="content")
public class Content {
	
	@XmlElement(name="node")
	private List<Node> nodes;

	@XmlTransient
	public List<Node> getNodes() {
		return nodes;
	}

	public void setNodes(List<Node> nodes) {
		this.nodes = nodes;
	}
	
	public String getValueByName(String name){
		for(Node node : nodes){
			if(name.equals(node.getName())){
				return node.getValue();
			}
		}
		return "";
	}
	
}
