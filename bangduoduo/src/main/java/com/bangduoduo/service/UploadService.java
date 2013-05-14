package com.bangduoduo.service;

import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bangduoduo.utils.WebUtil;



@Service
public class UploadService {
	public void uploadFile(HttpServletRequest request,MultipartFile imgFile)  throws Exception {
		String rootPath = WebUtil.getCurrentUploadPath(request);
		String urlPaht = WebUtil.getCurrentFileUrl(request);
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(rootPath + imgFile.getOriginalFilename()); 
			byte[] image = imgFile.getBytes();
			fos.write(image);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
}
