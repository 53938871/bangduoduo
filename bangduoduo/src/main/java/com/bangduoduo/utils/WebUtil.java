package com.bangduoduo.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.util.StringUtils;
import org.springframework.web.util.HtmlUtils;

public class WebUtil {
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
	
	public static String getRootPath(HttpServletRequest request){
		String dirName = request.getParameter("dir");
		String rootPath = request.getSession().getServletContext().getRealPath("/") 
		+ "attach\\" + dirName + "\\";
		return rootPath;
	}
	
	public static String getCurrentUploadPath(HttpServletRequest request){
		
		String rootPath = getRootPath(request) + formatDate("yyyy-MM-dd") + "\\";
		System.out.println(rootPath);
		File file = new File(rootPath);
		if(!file.exists()){
			file.mkdirs();
		}
		return rootPath;
	}
	
	public static String getRootUrl(HttpServletRequest request){
		String dirName = request.getParameter("dir");
		return request.getContextPath() + "/attach/" + dirName +"/";
	}
	
	public static String getCurrentFileUrl(HttpServletRequest request){
		return getRootUrl(request) +  formatDate("yyyy-MM-dd") + "/";
	}
	
	public static String formatDate(String format){
		if (StringUtils.isEmpty(format)){
			format = "yyyy-MM-dd";
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		return simpleDateFormat.format(new java.util.Date());
	}
	
}
