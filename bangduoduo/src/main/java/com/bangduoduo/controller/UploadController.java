package com.bangduoduo.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.bangduoduo.service.UploadService;
import com.bangduoduo.upload.UploadMessage;

@Controller
@RequestMapping("/upload")
public class UploadController {
	
	@Autowired
	private UploadService uploadService;
	
	@RequestMapping(value="/image",method=RequestMethod.POST)
	@ResponseBody
	public UploadMessage uploadImage(HttpServletRequest request,@RequestParam() MultipartFile imgFile){
		try {
			return uploadService.uploadFile(request, imgFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value="/list",method=RequestMethod.GET)
	@ResponseBody
	public Object listedField(HttpServletRequest request){
		try {
			return uploadService.getFieldList(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
