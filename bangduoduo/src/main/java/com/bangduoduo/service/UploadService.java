package com.bangduoduo.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bangduoduo.upload.NameComparator;
import com.bangduoduo.upload.SizeComparator;
import com.bangduoduo.upload.TypeComparator;
import com.bangduoduo.upload.UploadMessage;
import com.bangduoduo.utils.WebUtil;



@Service
public class UploadService {
	private static final String[] IMG_TYPE = {"gif","jpg","png","bmp","jpeg"};
	
	public UploadMessage uploadFile(HttpServletRequest request,MultipartFile imgFile)  throws Exception {
		UploadMessage message = new UploadMessage();
		if(!isImage(imgFile)){
			message.setMessage("Please choose image!");
			message.setError(1);
			return message;
		}
		String rootPath = WebUtil.getCurrentUploadPath(request);
		String urlPath = WebUtil.getCurrentFileUrl(request);
		
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
		System.out.println(urlPath  + imgFile.getOriginalFilename());
		message.setUrl(urlPath  + imgFile.getOriginalFilename());
		message.setError(0);
		return message;
	}
	
	private boolean isImage(MultipartFile imgFile){
		String imgName = imgFile.getOriginalFilename();
		String ext = imgName.substring(imgName.lastIndexOf(".")+1);
		boolean isImage = false;
		for(String type : IMG_TYPE){
			if(type.equalsIgnoreCase(ext)){
				isImage = true;
				break;
			}
				
		}
		return isImage;
	}
	
	public Object getFieldList(HttpServletRequest request){
		List<Hashtable<String,Object>> fileList = new ArrayList<Hashtable<String,Object>>();
		String rootPath = WebUtil.getRootPath(request);

		String path = request.getParameter("path") != null ? request.getParameter("path") : "";
		
		String moveupDirPath = "";
		if (!"".equals(path)) {
			String str = rootPath.substring(0, rootPath.length() - 1);
			moveupDirPath = str.lastIndexOf("/") >= 0 ? str.substring(0, str.lastIndexOf("/") + 1) : "";
		}
		String currentUrl = WebUtil.getRootUrl(request) + path;
		File currentPathFile = new File(rootPath + path);
		
		UploadMessage message = checkField(currentPathFile, path);
		if(message != null){
			return message;
		}
		
		setFileInfo(fileList, currentPathFile);
		
		sortFileList(request,fileList);
		
		JSONObject result = new JSONObject();
		result.put("moveup_dir_path", moveupDirPath);
		result.put("current_dir_path", path);
		result.put("current_url", currentUrl);
		result.put("total_count", fileList.size());
		result.put("file_list", fileList);
		
		return result;
	}

	private void setFileInfo(List<Hashtable<String, Object>> fileList,
			File currentPathFile) {
		if(currentPathFile.listFiles() != null) {
			for (File file : currentPathFile.listFiles()) {
				Hashtable<String, Object> hash = new Hashtable<String, Object>();
				String fileName = file.getName();
				if(file.isDirectory()) {
					hash.put("is_dir", true);
					hash.put("has_file", (file.listFiles() != null));
					hash.put("filesize", 0L);
					hash.put("is_photo", false);
					hash.put("filetype", "");
				} else if(file.isFile()){
					String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
					hash.put("is_dir", false);
					hash.put("has_file", false);
					hash.put("filesize", file.length());
					hash.put("is_photo", Arrays.<String>asList(IMG_TYPE).contains(fileExt));
					hash.put("filetype", fileExt);
				}
				hash.put("filename", fileName);
				hash.put("datetime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(file.lastModified()));
				fileList.add(hash);
			}
		}
	}

	private UploadMessage checkField(File currentPathFile, String path) {
		UploadMessage message = new UploadMessage();
		message.setError(1);
		if (path.indexOf("..") >= 0) {
			message.setMessage("Access is not allowed.");
			return message;
		}
		if (!"".equals(path) && !path.endsWith("/")) {
			message.setMessage("Parameter is not valid.");
			return message;
		}
		
		if(!currentPathFile.isDirectory()){
			message.setMessage("Directory does not exist.");
			return message;
		}
		return null;
	}
	
	private void sortFileList(HttpServletRequest request,List<Hashtable<String,Object>> fileList){
		String order = request.getParameter("order") != null ?request.getParameter("order").toLowerCase() : "name";
		if ("size".equals(order)) {
			Collections.sort(fileList, new SizeComparator());
		} else if ("type".equals(order)) {
			Collections.sort(fileList, new TypeComparator());
		} else {
			Collections.sort(fileList, new NameComparator());
		}
	}
}
