package com.bangduoduo.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bangduoduo.upload.NameComparator;
import com.bangduoduo.upload.SizeComparator;
import com.bangduoduo.upload.TypeComparator;
import com.bangduoduo.upload.UploadMessage;
import com.bangduoduo.utils.WebUtil;

@Service
public class UploadService {

	private static final Map<String, String> extMap = new HashMap<String, String>();
	static {
		extMap.put("image", "gif,jpg,jpeg,png,bmp");
		extMap.put("flash", "swf,flv");
		extMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
		extMap.put("file", "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2");
	}

	public void uploadFile(HttpServletRequest request,
			HttpServletResponse response, MultipartHttpServletRequest multiRequest) throws Exception {
		List<MultipartFile> files = multiRequest.getFiles("imgFile");

		String rootPath = WebUtil.getCurrentUploadPath(request);
		String urlPath = WebUtil.getCurrentFileUrl(request);
		FileOutputStream fos = null;
		Iterator<MultipartFile> itr = files.iterator();
		PrintWriter out = response.getWriter();
		while (itr.hasNext()) {
			try {

				MultipartFile file = itr.next();
				JSONObject message = getErrorJsonMessage(request,file);
				if(message != null){
					out.println(message.toJSONString());
					return;
				}
				fos = new FileOutputStream(rootPath
						+ file.getOriginalFilename());
				byte[] image = file.getBytes();
				fos.write(image);
				
				JSONObject obj = new JSONObject();
				obj.put("error", 0);
				obj.put("url", urlPath + file.getOriginalFilename());
				out.println(obj.toJSONString());
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					fos.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	private JSONObject getErrorJsonMessage(HttpServletRequest request,
			MultipartFile file) {
		JSONObject message = null;
		String imgName = file.getOriginalFilename();
		String ext = imgName.substring(imgName.lastIndexOf(".") + 1);
		String fileType = request.getParameter("dir");

		boolean isAllowed = false;
		for (String type : extMap.get(fileType).split(",")) {
			if (type.equalsIgnoreCase(ext)) {
				isAllowed = true;
				break;
			}

		}
		if (!isAllowed) {
			message = new JSONObject();
			message.put("error", 1);
			message.put("message","Please choose correct file!");
			
		}
		return message;
	}

	public Object getFieldList(HttpServletRequest request) {
		List<Hashtable<String, Object>> fileList = new ArrayList<Hashtable<String, Object>>();
		String rootPath = WebUtil.getRootPath(request);

		String path = request.getParameter("path") != null ? request
				.getParameter("path") : "";

		String moveupDirPath = "";
		if (!"".equals(path)) {
			String str = rootPath.substring(0, rootPath.length() - 1);
			moveupDirPath = str.lastIndexOf("/") >= 0 ? str.substring(0,
					str.lastIndexOf("/") + 1) : "";
		}
		String currentUrl = WebUtil.getRootUrl(request) + path;
		File currentPathFile = new File(rootPath + path);

		UploadMessage message = checkField(currentPathFile, path);
		if (message != null) {
			return message;
		}

		setFileInfo(fileList, currentPathFile);

		sortFileList(request, fileList);

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
		if (currentPathFile.listFiles() != null) {
			for (File file : currentPathFile.listFiles()) {
				Hashtable<String, Object> hash = new Hashtable<String, Object>();
				String fileName = file.getName();
				if (file.isDirectory()) {
					hash.put("is_dir", true);
					hash.put("has_file", (file.listFiles() != null));
					hash.put("filesize", 0L);
					hash.put("is_photo", false);
					hash.put("filetype", "");
				} else if (file.isFile()) {
					String fileExt = fileName.substring(
							fileName.lastIndexOf(".") + 1).toLowerCase();
					hash.put("is_dir", false);
					hash.put("has_file", false);
					hash.put("filesize", file.length());
					hash.put("is_photo",
							Arrays.<String> asList(extMap.get("image").split(","))
									.contains(fileExt));
					hash.put("filetype", fileExt);
				}
				hash.put("filename", fileName);
				hash.put("datetime",
						new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(file
								.lastModified()));
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

		if (!currentPathFile.isDirectory()) {
			message.setMessage("Directory does not exist.");
			return message;
		}
		return null;
	}

	private void sortFileList(HttpServletRequest request,
			List<Hashtable<String, Object>> fileList) {
		String order = request.getParameter("order") != null ? request
				.getParameter("order").toLowerCase() : "name";
		if ("size".equals(order)) {
			Collections.sort(fileList, new SizeComparator());
		} else if ("type".equals(order)) {
			Collections.sort(fileList, new TypeComparator());
		} else {
			Collections.sort(fileList, new NameComparator());
		}
	}
}
