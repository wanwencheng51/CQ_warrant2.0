package com.cq.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.cq.service.OperlogService;
import com.cq.util.WarrantException;
import com.cq.util.tools;

public class FileOpAction {
	static Logger log = Logger.getLogger(FileOpAction.class);
	private String errorMsg;
	
	@Resource OperlogService operlogService;

	private String ment;
	private File[] upload;
	private String[] uploadContentType;
	private String[] uploadFileName;
	private String savePath;
	private String name;

	private boolean checkFilePath(String filePath) {
		if ((filePath == null) || (filePath.length() == 0)) {
			return false;
		}
		File dir = new File(filePath);
		if (!(dir.exists())) {
			return dir.mkdirs();
		}
		return true;
	}

	private String[] listFile(String filePath) {
		File[] fl = null;
		String[] fileList = null;

		if ((filePath == null) || (filePath.length() == 0)) {
			log.warn("获取文件列表时参数异常" + filePath);
			return null;
		}

		File dir = new File(filePath);
		if (!(dir.exists())) {
			errorMsg = "文件目录" + filePath + "不存在";
			log.error(errorMsg);
			throw new WarrantException(errorMsg);
		} else {
			fl = dir.listFiles();
			if (fl == null || fl.length == 0) {
				return null;
			} else {
				fileList = new String[fl.length];
				for (int i = 0; i < fl.length; i++) {
					if (fl[i].isFile())
						fileList[i] = fl[i].getName();
				}
			}
		}
		return fileList;
	}

	public void getFileList() throws Exception {
		String[] fileList = null;
		String filePath = getSavePath() + ment;

		try {
			fileList = listFile(filePath);
			if (fileList != null) {
				tools.outputInfo(JSONArray.fromObject(fileList));
			} else {
				tools.outputInfo("");
			}
		} catch (Exception e) {
			errorMsg = "查询文件列表时系统发生异常";
			tools.throwException(e, log, errorMsg);
		}
	}

	public void downloadFile() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		response.reset();
		response.setContentType("application/x-download");

		OutputStream outp = null;
		FileInputStream in = null;

		try {
			String tmpName = new String(name.getBytes("ISO-8859-1"), "UTF-8");
			String filedownload = getSavePath() + ment + "\\" + tmpName;
			String filedisplay = URLEncoder.encode(tmpName, "UTF-8");
			response.addHeader("Content-Disposition", "attachment;filename="
					+ filedisplay);

			outp = response.getOutputStream();

			in = new FileInputStream(filedownload);
			byte[] b = new byte[1024];
			int i = 0;

			while ((i = in.read(b)) > 0) {
				outp.write(b, 0, i);
			}
			outp.flush();
			operlogService.logFileOperation(tools.getLoginUser(), "用户下载了文件：" + ment + "/" + filedisplay);
		} catch (Exception e) {
			errorMsg = "下载文件时系统发生异常";
			tools.throwException(e, log, errorMsg);
		} finally {
			if (in != null) {
				in.close();
				in = null;
			}
			if (outp != null) {
				outp.close();
				outp = null;
			}
		}
	}

	public void deleteFile() throws Exception {
		String filedownload = getSavePath() + ment + "\\" + name;
		File file = new File(filedownload);
		if (file.isFile() && file.exists()) {
			boolean flag = file.delete();
			if (flag == true) {
				operlogService.logFileOperation(tools.getLoginUser(), "用户删除了文件：" + ment + "/" + name);
				tools.outputInfo("success");
			} else {
				tools.outputInfo("fail");
			}
		} else {
			errorMsg = "删除" + name + "文件时出错";
			log.error(errorMsg);
			throw new WarrantException(errorMsg);
		}
	}

	public void uploadFile() throws Exception {
		String rets = null;
		String filePath = getSavePath();
		filePath = filePath + ment;
		if (checkFilePath(filePath) == false) {
			rets = "文件目录不存在：" + filePath;
			ServletActionContext.getRequest().getSession()
					.setAttribute("msg", rets);
		}

		File[] files = getUpload();
		if (files == null || files.length == 0) {
			rets = "获取需要上传的文件错误";
			ServletActionContext.getRequest().getSession()
					.setAttribute("msg", rets);
		}

		FileOutputStream fos = null;
		FileInputStream fis = null;
		try {
			for (int i = 0; i < files.length; i++) {
				String fileName = filePath + "\\" + getUploadFileName()[i];
				fos = new FileOutputStream(fileName);
				fis = new FileInputStream(files[i]);

				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = fis.read(buffer)) > 0) {
					fos.write(buffer, 0, len);
				}
				fos.close();
				fos = null;
				fis.close();
				fis = null;
				operlogService.logFileOperation(tools.getLoginUser(),
						"用户上传了文件：" + ment + "/" + getUploadFileName()[i]);
			}
		} catch (Exception e) {
			errorMsg = "上传文件时系统发生异常";
			tools.throwException(e, log, errorMsg);
		} finally {
			if (fos != null) {
				fos.close();
				fos = null;
			}
			if (fis != null) {
				fis.close();
				fis = null;
			}
		}
	}
	
	public String getMent() {
		return ment;
	}

	public void setMent(String ment) {
		this.ment = ment;
	}

	public File[] getUpload() {
		return upload;
	}

	public void setUpload(File[] upload) {
		this.upload = upload;
	}

	public String[] getUploadContentType() {
		return uploadContentType;
	}

	public void setUploadContentType(String[] uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	public String[] getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String[] uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public String getSavePath() {
		return ServletActionContext.getServletContext().getRealPath(savePath);
	}

	public String getSavePath(String wid) {
		return ServletActionContext.getServletContext().getRealPath(wid);
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
