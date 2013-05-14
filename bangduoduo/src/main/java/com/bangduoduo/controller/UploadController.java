package com.bangduoduo.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.bangduoduo.service.UploadService;

@Controller
@RequestMapping("/upload")
public class UploadController {
	
	@Autowired
	private UploadService uploadService;
	
	@RequestMapping("/image")
	@ResponseBody
	public String uploadImage(HttpServletRequest request,@RequestParam() MultipartFile imgFile){
		try {
			uploadService.uploadFile(request, imgFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
