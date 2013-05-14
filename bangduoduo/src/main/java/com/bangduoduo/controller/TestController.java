package com.bangduoduo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bangduoduo.service.UploadService;

@Controller
@RequestMapping("/test")
public class TestController {
	
	@Autowired
	private UploadService uploadService;
	
	@RequestMapping("go")
	public void go(){
		System.out.println("test ...");
	}
	
	@RequestMapping("/json")
	@ResponseBody
	public Object json(){
		return "{\"name\":\"kkcheng\",\"age\":\"5\"}";
	}
	
	
	
	
}
