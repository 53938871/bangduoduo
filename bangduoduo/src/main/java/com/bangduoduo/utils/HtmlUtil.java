package com.bangduoduo.utils;

import java.util.LinkedHashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.util.StringUtils;
import org.springframework.web.util.HtmlUtils;

public class HtmlUtil {
	public static String encodeHtml(String html){
		if (StringUtils.isEmpty(html)){
			return "";
		}
		return HtmlUtils.htmlEscape(html);
	}
	
	public static String decodeHtml(String encodeHtml){
		if (StringUtils.isEmpty(encodeHtml)){
			return "";
		}
		return HtmlUtils.htmlUnescape(encodeHtml);
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String,String> json2Options(String json){
		JSONObject jsonObject = JSONObject.fromObject(json);
		Map<String,String> map = new LinkedHashMap<String,String>();
		map.putAll(jsonObject);
		return 	map;
	}
}
