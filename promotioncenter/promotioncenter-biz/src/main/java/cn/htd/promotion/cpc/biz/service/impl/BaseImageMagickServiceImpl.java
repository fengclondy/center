package cn.htd.promotion.cpc.biz.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;
import org.im4java.core.Info;
import org.im4java.core.InfoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import cn.htd.common.util.OssUploadUtils;
import cn.htd.promotion.cpc.biz.service.BaseImageMagickService;
import cn.htd.promotion.cpc.dto.request.BaseImageDTO;
import cn.htd.promotion.cpc.dto.request.BaseImageSubDTO;
@Service("baseImageMagickService")
public class BaseImageMagickServiceImpl implements BaseImageMagickService {

	private static final Logger logger = LoggerFactory.getLogger(BaseImageMagickServiceImpl.class);

	private static String rootpath = "";// SysProperties.getProperty("IMAGEPATH");
	/** oss配置 */
	private static String ENDPOINT = "";// SysProperties.getProperty("endpoint");
	private static String ACCESS_KEYID = "";// SysProperties.getProperty("access_keyid");
	private static String ACCESS_KEYSECRET = "";// SysProperties.getProperty("access_keysecret");
	private static String BUCJET_NAME = "";// SysProperties.getProperty("bucket_name");

	// wget -O /etc/yum.repos.d/Centos-6.repo
	// http://mirrors.aliyun.com/repo/Centos-6.repo
	// yum install -y ImageMagick ImageMagick-devel
	@Override
	public String margeImage(BaseImageDTO images) {

		IMOperation op = new IMOperation();
		logger.info("main pic:" + images.getMainImageUrl());
		if (StringUtils.isEmpty(images.getMainImageUrl())) {
			return "";
		}
		String downimg = downloadImg(images.getMainImageUrl());
		logger.info("downimg:" + downimg);
		Map<String, Integer> map = getImgInfo(downimg);
		//宽比例
		float wb = 1;
		//高比例
		float hb = 1;
		if (map != null) {
			if (images.getHeight() != 0 && images.getWidth() != 0) {
				wb = map.get("width").intValue() / images.getWidth();
				hb = map.get("height").intValue() / images.getHeight();
				if (wb < 1) {
					wb = 1;
				}
				if (hb < 1) {
					hb = 1;
				}
			}
		}
		op.addImage(downimg);
		List<BaseImageSubDTO> slist = images.getSubImageList();
		String subimg = "";
		for (BaseImageSubDTO baseImageSubDTO : slist) {
			if (baseImageSubDTO.getType() == 1) {
				if (!StringUtils.isEmpty(baseImageSubDTO.getImageUrl())) {
					subimg = downloadImg(baseImageSubDTO.getImageUrl());
					logger.info("subimg:" + subimg);
					op.addImage(subimg);
					op.geometry((int) (baseImageSubDTO.getWidth() * wb), (int) (baseImageSubDTO.getHeight() * hb),
							(int) (baseImageSubDTO.getLeft() * wb), (int) (baseImageSubDTO.getTop() * hb));
					op.composite();
				}
			} else {
				// op.font("c:\\Windows\\Fonts\\simsun.ttc");
				op.pointsize((int) (baseImageSubDTO.getFontSize() * wb));
				op.fill(baseImageSubDTO.getFontColor());
				op.draw("text " + (int) (baseImageSubDTO.getLeft() * wb) + "," + (int) (baseImageSubDTO.getTop() * hb) + " \'"
						+ baseImageSubDTO.getText() + "\'");
			}
		}
		String imgext = images.getMainImageUrl();
		imgext = imgext.substring(imgext.lastIndexOf("."));
		String newpicName = UUID.randomUUID().toString().replaceAll("-", "") + imgext;
		op.addImage(rootpath + newpicName);
		ConvertCmd cc = new ConvertCmd(false);
		// cc.setSearchPath("c:\\Program Files\\ImageMagick-6.9.9-Q16");
		logger.info("newpicName:" + newpicName);
		try {
			cc.setAsyncMode(true);
			cc.run(op);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IM4JavaException e) {
			e.printStackTrace();
		}
		OssUploadUtils ossUploadUtils = new OssUploadUtils(BUCJET_NAME, ENDPOINT, ACCESS_KEYID, ACCESS_KEYSECRET);
		FileInputStream newfile = null;
		try {
			newfile = new FileInputStream(rootpath + newpicName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		File file = new File(rootpath + newpicName);
		String ossimg = ossUploadUtils.upload("/materieldown", newfile, imgext, file.length());
		logger.info("ossimg:" + ossimg);
		return ossimg;

	}

	private static Map<String, Integer> getImgInfo(String downimg) {
		Info imageInfo = null;
		Map<String, Integer> map = new HashMap<String, Integer>();
		try {
			imageInfo = new Info(downimg);
			map.put("width", imageInfo.getImageWidth());
			map.put("height", imageInfo.getImageHeight());
		} catch (InfoException e) {
			e.printStackTrace();
		}
		return map;
	}

	static String margeImage1(BaseImageDTO images) {

		IMOperation op = new IMOperation();
		op.addImage(images.getMainImageUrl());
		List<BaseImageSubDTO> slist = images.getSubImageList();
		for (BaseImageSubDTO baseImageSubDTO : slist) {
			if (baseImageSubDTO.getType() == 1) {
				op.addImage(baseImageSubDTO.getImageUrl());
				op.geometry(baseImageSubDTO.getWidth(), baseImageSubDTO.getHeight(), baseImageSubDTO.getLeft(),
						baseImageSubDTO.getTop());
				op.composite();
			} else {
				op.font("c:\\Windows\\Fonts\\simsun.ttc");
				op.pointsize(baseImageSubDTO.getFontSize());
				op.fill(baseImageSubDTO.getFontColor());
				op.draw("text " + baseImageSubDTO.getLeft() + "," + baseImageSubDTO.getTop() + " \'"
						+ baseImageSubDTO.getText() + "\'");
			}

		}
		op.addImage("d:\\3.png");
		ConvertCmd cc = new ConvertCmd(false);
		cc.setSearchPath("c:\\Program Files\\ImageMagick-6.9.9-Q16");
		try {
			cc.run(op);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IM4JavaException e) {
			e.printStackTrace();
		}
		return null;

	}

	private String downloadImg(String url) {
		URL imgurl;
		File file;
		String filename = "";
		InputStream inputStream = null;
		OutputStream outputStream = null;
		try {
			imgurl = new URL(url);
			HttpURLConnection urlConnection = (HttpURLConnection) imgurl.openConnection();
			urlConnection.setRequestMethod("GET");
			urlConnection.setConnectTimeout(1000);
			urlConnection.setReadTimeout(4000);

			inputStream = urlConnection.getInputStream();
			// int fileSize = urlConnection.getContentLength();
			file = new File(rootpath);
			if (!file.exists()) {
				file.mkdirs();
			}
			filename = rootpath + UUID.randomUUID().toString().replaceAll("-", "")
					+ url.substring(url.lastIndexOf("."));
			outputStream = new FileOutputStream(filename);
			int bytesWritten = 0;
			int byteCount = 0;
			byte[] bytes = new byte[1024];
			while ((byteCount = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, bytesWritten, byteCount);
				bytesWritten += byteCount;
			}
			inputStream.close();
			outputStream.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return filename;
	}

	public static void main(String[] args) {
		BaseImageDTO e = new BaseImageDTO();
		e.setMainImageUrl("d:\\u1143.jpg");
		getImgInfo("d:\\2.png");
		BaseImageSubDTO is = new BaseImageSubDTO();
		is.setImageUrl("d:\\1.png");
		is.setWidth(55);
		is.setHeight(55);
		is.setLeft(220);
		is.setTop(220);
		is.setType(1);
		List<BaseImageSubDTO> subImageList = new ArrayList<BaseImageSubDTO>();
		subImageList.add(is);
		is = new BaseImageSubDTO();
		is.setImageUrl("d:\\1.png");
		is.setWidth(55);
		is.setHeight(55);
		is.setLeft(260);
		is.setTop(270);
		is.setType(1);
		subImageList.add(is);
		is = new BaseImageSubDTO();
		is.setText("123ert 中中中中中中中中中中中");
		is.setWidth(550);
		is.setHeight(550);
		is.setTop(80);
		is.setLeft(80);
		is.setType(2);
		is.setFontColor("red");
		is.setFontSize(30);
		subImageList.add(is);
		is = new BaseImageSubDTO();
		is.setText("123ert 中中中中中中中中中中中");
		is.setWidth(550);
		is.setHeight(550);
		is.setTop(180);
		is.setLeft(180);
		is.setType(2);
		is.setFontColor("white");
		is.setFontSize(50);
		subImageList.add(is);
		e.setSubImageList(subImageList);
		margeImage1(e);
	}
}
