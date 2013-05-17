package com.bangduoduo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bangduoduo.service.UploadService;

@Controller
@RequestMapping("/upload")
public class UploadController {
	
	@Autowired
	private UploadService uploadService;
	
	@RequestMapping(value="/upload",method=RequestMethod.POST)
	public void uploadImage(HttpServletRequest request,HttpServletResponse response,
			MultipartHttpServletRequest multiRequest){
		try {
			uploadService.uploadFile(request, response, multiRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
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
