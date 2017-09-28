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
import cn.htd.common.util.SysProperties;
import cn.htd.promotion.cpc.biz.service.BaseImageMagickService;
import cn.htd.promotion.cpc.dto.request.BaseImageDTO;
import cn.htd.promotion.cpc.dto.request.BaseImageSubDTO;

@Service("baseImageMagickService")
public class BaseImageMagickServiceImpl implements BaseImageMagickService {

	private static final Logger logger = LoggerFactory.getLogger(BaseImageMagickServiceImpl.class);

	private static String rootpath = SysProperties.getProperty("IMAGEPATH");
	private static String fontpath =  SysProperties.getProperty("FONTPATH");
	/** oss配置 */
	private static String ENDPOINT =  SysProperties.getProperty("endpoint");
	private static String ACCESS_KEYID =  SysProperties.getProperty("access_keyid");
	private static String ACCESS_KEYSECRET =  SysProperties.getProperty("access_keysecret");
	private static String BUCJET_NAME =  SysProperties.getProperty("bucket_name");

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
		String downimg = "";
		if (images.getMainImageUrl().startsWith("http")) {
			downimg = downloadImg(images.getMainImageUrl());
		} else {
			downimg = images.getMainImageUrl();
		}

		logger.info("downimg:" + downimg);
		Map<String, Integer> map = getImgInfo(downimg);
		// 宽比例
		float wb = 1;
		// 高比例
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
			if(!StringUtils.isEmpty(baseImageSubDTO.getGravity())) {
				op.gravity(baseImageSubDTO.getGravity());
			}else {
				op.gravity("northwest");
			}
			if (baseImageSubDTO.getType() == 1) {
				if (!StringUtils.isEmpty(baseImageSubDTO.getImageUrl())) {

					if (baseImageSubDTO.getImageUrl().startsWith("http")) {
						subimg = downloadImg(baseImageSubDTO.getImageUrl());
					} else {
						subimg = baseImageSubDTO.getImageUrl();
					}
					logger.info("subimg:" + subimg);
					op.addImage(subimg);
					op.geometry((int) (baseImageSubDTO.getWidth() * wb), (int) (baseImageSubDTO.getHeight() * hb),
							(int) (baseImageSubDTO.getLeft() * wb), (int) (baseImageSubDTO.getTop() * hb));
					op.composite();
				}
			} else {
				// op.font("c:\\Windows\\Fonts\\simsun.ttc");
				String font = getFontPath(baseImageSubDTO.getFontName());
				if(!StringUtils.isEmpty(font)) {
					op.font(font);
				}
				op.pointsize((int) (baseImageSubDTO.getFontSize() * wb));
				op.fill(baseImageSubDTO.getFontColor());

				String text = baseImageSubDTO.getText();
				text = text.replace("\'", "\\\'");
//				text = text.replace("(", "\\(");
//				text = text.replace(")", "\\)");
				logger.info("main text:" + text);
				op.draw("text " + (int) (baseImageSubDTO.getLeft() * wb) + "," + (int) (baseImageSubDTO.getTop() * hb)
						+ " \'" + text + "\'");
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
			logger.info("op:" + op.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IM4JavaException e) {
			e.printStackTrace();
		}
		File file = new File(rootpath + newpicName);
		if(file.exists()) {
			OssUploadUtils ossUploadUtils = new OssUploadUtils(BUCJET_NAME, ENDPOINT, ACCESS_KEYID, ACCESS_KEYSECRET);
			FileInputStream newfile = null;
			try {
				newfile = new FileInputStream(rootpath + newpicName);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			String ossimg = ossUploadUtils.upload("/materieldown", newfile, imgext, file.length());
			logger.info("ossimg:" + ossimg);
			return ossimg;
		}else {
			return "";
		}


	}

	private static String getFontPath(String fontName) {
		if(fontName.equalsIgnoreCase("simsun")||
				fontName.equalsIgnoreCase("宋体")) {
			return fontpath+"simsun.ttc";
		}
		if(fontName.equalsIgnoreCase("simhei")||
				fontName.equalsIgnoreCase("黑体")) {
			return fontpath+"simhei.ttf";
		}
		if(fontName.equalsIgnoreCase("simkai")||
				fontName.equalsIgnoreCase("楷体")) {
			return fontpath+"simkai.ttf";
		}
		if(fontName.equalsIgnoreCase("yahei")||
				fontName.equalsIgnoreCase("雅黑")) {
			return fontpath+"msyh.ttc";
		}
		if(fontName.equalsIgnoreCase("bold")||
				fontName.equalsIgnoreCase("粗体")) {
			return fontpath+"msyhbd.ttc";
		}
		return null;
	}

	@Override
	public Map<String, Integer> getImgInfo(String downimg) {
		Info imageInfo = null;
		Map<String, Integer> map = new HashMap<String, Integer>();
		try {
			if (StringUtils.isEmpty(downimg)) {
				return null;
			}
			if (downimg.startsWith("http")) {
				downimg = downloadImg(downimg);
			}
			imageInfo = new Info(downimg);
			map.put("width", imageInfo.getImageWidth());
			map.put("height", imageInfo.getImageHeight());
		} catch (InfoException e) {
			e.printStackTrace();
		}
		return map;
	}

	@Override
	public String margeImgHeight(String oldimg, int newHeight) {
		// String newimg="";
		logger.info("oldimg:" + oldimg);
		if (StringUtils.isEmpty(oldimg)) {
			return "";
		}
		if (oldimg.startsWith("http")) {
			oldimg = downloadImg(oldimg);
		}
		try {
			Info imageInfo = new Info(oldimg);
			int imgheight = imageInfo.getImageHeight();
			int imgwidth = imageInfo.getImageWidth();
			logger.info("oldimg imgheight:" + imgheight);
			logger.info("oldimg imgwidth:" + imgwidth);
			if (imgheight >= newHeight) {
				return oldimg;
			} else {
				IMOperation op = new IMOperation();
				op.addImage(oldimg);
				op.background("white");
				op.gravity("north");
				op.extent(imgwidth, newHeight);
				logger.info("oldimg newHeight:" + newHeight);
				// newimg = UUID.randomUUID().toString().replaceAll("-", "");
				op.addImage(oldimg);
				ConvertCmd cc = new ConvertCmd(false);
				// cc.setSearchPath("c:\\Program Files\\ImageMagick-6.9.9-Q16");
				cc.run(op);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return oldimg;
	}
	
	public static String margeImgHeight1(String oldimg, int newHeight) {
		// String newimg="";
		logger.info("oldimg:" + oldimg);
		if (StringUtils.isEmpty(oldimg)) {
			return "";
		}
		if (oldimg.startsWith("http")) {
			oldimg = downloadImg(oldimg);
		}
		try {
			Info imageInfo = new Info(oldimg);
			int imgheight = imageInfo.getImageHeight();
			int imgwidth = imageInfo.getImageWidth();
			if (imgheight >= newHeight) {
				return oldimg;
			} else {
				IMOperation op = new IMOperation();
				op.addImage(oldimg);
				op.background("white");
				op.gravity("north");
				op.extent(imgwidth, newHeight);
				// newimg = UUID.randomUUID().toString().replaceAll("-", "");
				op.addImage(oldimg);
				ConvertCmd cc = new ConvertCmd(false);
				cc.setSearchPath("c:\\Program Files\\ImageMagick-6.9.9-Q16");
				cc.run(op);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return oldimg;
	}

	public static Map<String, Integer> getImgInfo1(String downimg) {
		Info imageInfo = null;
		Map<String, Integer> map = new HashMap<String, Integer>();
		try {
			if (StringUtils.isEmpty(downimg)) {
				return null;
			}
			if (downimg.startsWith("http")) {
				downimg = downloadImg(downimg);
			}
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
		logger.info("main pic:" + images.getMainImageUrl());
		if (StringUtils.isEmpty(images.getMainImageUrl())) {
			return "";
		}
		String downimg = "";
		if (images.getMainImageUrl().startsWith("http")) {
			downimg = downloadImg(images.getMainImageUrl());
		} else {
			downimg = images.getMainImageUrl();
		}

		logger.info("downimg:" + downimg);
		//Map<String, Integer> map = getImgInfo(downimg);
		// 宽比例
		float wb = 1;
		// 高比例
		float hb = 1;
		//
		op.addImage(downimg);
		List<BaseImageSubDTO> slist = images.getSubImageList();
		String subimg = "";
		for (BaseImageSubDTO baseImageSubDTO : slist) {
			if(!StringUtils.isEmpty(baseImageSubDTO.getGravity())) {
				op.gravity(baseImageSubDTO.getGravity());
			}else {
				op.gravity("northwest");
			}
			if (baseImageSubDTO.getType() == 1) {
				if (!StringUtils.isEmpty(baseImageSubDTO.getImageUrl())) {

					if (baseImageSubDTO.getImageUrl().startsWith("http")) {
						subimg = downloadImg(baseImageSubDTO.getImageUrl());
					} else {
						subimg = baseImageSubDTO.getImageUrl();
					}
					logger.info("subimg:" + subimg);
					op.addImage(subimg);
					op.geometry((int) (baseImageSubDTO.getWidth() * wb), (int) (baseImageSubDTO.getHeight() * hb),
							(int) (baseImageSubDTO.getLeft() * wb), (int) (baseImageSubDTO.getTop() * hb));
					op.composite();
				}
			} else {
				String font = getFontPath(baseImageSubDTO.getFontName());
				if(!StringUtils.isEmpty(font)) {
					op.font(font);
				}
				op.pointsize((int) (baseImageSubDTO.getFontSize() * wb));
				op.fill(baseImageSubDTO.getFontColor());

				String text = baseImageSubDTO.getText();
				text = text.replaceAll("\'", "\\\\'");
				op.draw("text " + (int) (baseImageSubDTO.getLeft() * wb) + "," + (int) (baseImageSubDTO.getTop() * hb)
						+ " \'" + text + "\'");
			}
		}
		String imgext = images.getMainImageUrl();
		imgext = imgext.substring(imgext.lastIndexOf("."));
		String newpicName = "a.jpg";//UUID.randomUUID().toString().replaceAll("-", "") + imgext;
		op.addImage(rootpath + newpicName);
		ConvertCmd cc = new ConvertCmd(false);
		 cc.setSearchPath("c:\\Program Files\\ImageMagick-6.9.9-Q16");
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
		return newpicName;
	}

	private static String downloadImg(String url) {
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
			String ext = url.substring(url.lastIndexOf("."));
			if(!StringUtils.isEmpty(ext)) {
				if(ext.length()>4) {
					ext = ".jpg";	
				}
			}else {
				ext = ".jpg";
			}
			filename = rootpath + UUID.randomUUID().toString().replaceAll("-", "")
					+ ext;
			outputStream = new FileOutputStream(filename);
			byte buffer[] = new byte[8192];
			int bytesRead = 0;
			while ((bytesRead = inputStream.read(buffer, 0, 8192)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
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
//		BaseImageDTO bid = new BaseImageDTO();
//		String mainImageUrl = "d:\\bg.png";
//		bid.setMainImageUrl(mainImageUrl);
//		List<BaseImageSubDTO> subImageList =new ArrayList<BaseImageSubDTO>();
//		BaseImageSubDTO bisd= new BaseImageSubDTO();
//		bisd.setType(1);
//		String imageUrl = "d:\\u1159.jpg";
//		bisd.setImageUrl(imageUrl );
//		bisd.setLeft(140);
//		bisd.setTop(1015);
//		bisd.setWidth(521);
//		bisd.setHeight(350);
//		subImageList.add(bisd);
//		
//		bisd= new BaseImageSubDTO();
//		bisd.setType(1);
//		imageUrl = "d:\\tc.jpg";
//		bisd.setImageUrl(imageUrl );
//		bisd.setLeft(700);
//		bisd.setTop(1080);
//		bisd.setWidth(240);
//		bisd.setHeight(240);
//		subImageList.add(bisd);
//		
//		bisd= new BaseImageSubDTO();
//		bisd.setType(2);
//		bisd.setLeft(0);
//		bisd.setTop(563);
//		String text = "疯狂大减价等你来！";
//		bisd.setGravity("north");
//		bisd.setText(text);
//		bisd.setFontColor("#fff");
//		bisd.setFontSize(40);
//		//bisd.setStrokewidth(790);
//		bisd.setFontName("bold");
//		
//		subImageList.add(bisd);
//		bisd= new BaseImageSubDTO();
//		bisd.setType(2);
//		bisd.setLeft(405);
//		bisd.setTop(655);
//		text = "2017.9.1";
//		bisd.setText(text);
//		bisd.setFontColor("#fff");
//		bisd.setFontSize(31);
//		//bisd.setStrokewidth(790);
//		bisd.setFontName("bold");
//		
//		subImageList.add(bisd);
//		bisd= new BaseImageSubDTO();
//		bisd.setType(2);
//		bisd.setLeft(650);
//		bisd.setTop(655);
//		text = "2017.9.30";
//		bisd.setText(text);
//		bisd.setFontColor("#fff");
//		bisd.setFontSize(31);
//		//bisd.setStrokewidth(790);
//		bisd.setFontName("bold");
//
//		subImageList.add(bisd);
//		bisd= new BaseImageSubDTO();
//		bisd.setType(2);
//		bisd.setLeft(0);
//		bisd.setTop(735);
//		bisd.setGravity("north");
//		text = "XXX门店";
//		bisd.setText(text);
//		bisd.setFontColor("#fff");
//		bisd.setFontSize(50);
//		//bisd.setStrokewidth(790);
//		bisd.setFontName("bold");
//		
//		subImageList.add(bisd);
//		bisd= new BaseImageSubDTO();
//		bisd.setType(2);
//		bisd.setLeft(170);
//		bisd.setTop(1375);
//		text = "商品名称";
//		bisd.setText(text);
//		bisd.setFontColor("#767373");
//		bisd.setFontSize(40);
//		//bisd.setStrokewidth(790);
//		bisd.setFontName("bold");
//		
//		subImageList.add(bisd);
//		bisd= new BaseImageSubDTO();
//		bisd.setType(2);
//		bisd.setLeft(220);
//		bisd.setTop(1435);
//		text = "抢购价";
//		bisd.setText(text);
//		bisd.setFontColor("#191a1a");
//		bisd.setFontSize(36);
//		//bisd.setStrokewidth(790);
//		bisd.setFontName("bold");
//		
//		subImageList.add(bisd);
//		bisd= new BaseImageSubDTO();
//		bisd.setType(2);
//		bisd.setLeft(485);
//		bisd.setTop(1435);
//		text = "元";
//		bisd.setText(text);
//		bisd.setFontColor("#191a1a");
//		bisd.setFontSize(36);
//		//bisd.setStrokewidth(790);
//		bisd.setFontName("bold");
//		
//		subImageList.add(bisd);
//		bisd= new BaseImageSubDTO();
//		bisd.setType(2);
//		bisd.setLeft(360);
//		bisd.setTop(1425);
//		text = "2999";
//		bisd.setText(text);
//		bisd.setFontColor("#e71f19");
//		bisd.setFontSize(49);
//		//bisd.setStrokewidth(790);
//		bisd.setFontName("bold");
//				
//		subImageList.add(bisd);
//		bisd= new BaseImageSubDTO();
//		bisd.setType(2);
//		bisd.setLeft(715);
//		bisd.setTop(1360);
//		text = "XXX";
//		bisd.setText("联系人："+text);
//		bisd.setFontColor("#fff");
//		bisd.setFontSize(26);
//		//bisd.setStrokewidth(790);
//		bisd.setFontName("bold");
//				
//		subImageList.add(bisd);
//		bisd= new BaseImageSubDTO();
//		bisd.setType(2);
//		bisd.setLeft(745);
//		bisd.setTop(1395);
//		text = "1398888888";
//		bisd.setText(text);
//		bisd.setFontColor("#fff");
//		bisd.setFontSize(26);
//		//bisd.setStrokewidth(790);
//		bisd.setFontName("bold");
//		
//		subImageList.add(bisd);
//		bisd= new BaseImageSubDTO();
//		bisd.setType(2);
//		bisd.setLeft(190);
//		bisd.setTop(1535);
//		text = "活动内容：活动期间，登录“汇掌柜”活动页''''面并点%击领取，即可获赠相应优惠券。( 最多56字 )";
//		StringBuilder sb = new StringBuilder(text);
//		for(int index = 0; index < sb.length();index++){
//			if(index%20==0){
//				sb.insert(index,"\n");
//				}
//		}
//		bisd.setText(sb.toString());
//		bisd.setFontColor("#5f3122");
//		bisd.setFontSize(38);
//		bisd.setFontName("yahei");
//		//bisd.setFontName("bold");
//		//	
//		subImageList.add(bisd);
//		bisd= new BaseImageSubDTO();
//		bisd.setType(2);
//		bisd.setGravity("north");
//		bisd.setLeft(0);
//		bisd.setTop(1820);
//		text = "南京市玄武区xxxXXXX";
//		bisd.setText("店铺地址："+text);
//		bisd.setFontColor("#fff");
//		bisd.setFontSize(32);
//		//bisd.setStrokewidth(800);
//		bisd.setFontName("bold");
//		//	
//		subImageList.add(bisd);
//		
//		bid.setSubImageList(subImageList);
		
		
		BaseImageDTO bid = new BaseImageDTO();
		String mainImageUrl = "d:\\u1159a.jpg";
		bid.setMainImageUrl(mainImageUrl);
		List<BaseImageSubDTO> subImageList =new ArrayList<BaseImageSubDTO>();
		BaseImageSubDTO bisd= new BaseImageSubDTO();
		bisd.setType(1);
		String imageUrl = "d:\\tc.jpg";
		bisd.setImageUrl(imageUrl );
		bisd.setLeft(30);
		bisd.setTop(810);
		bisd.setWidth(196);
		bisd.setHeight(196);
		subImageList.add(bisd);
		
		bisd= new BaseImageSubDTO();
		bisd.setType(2);
		bisd.setLeft(70);
		bisd.setTop(260);
		String text = "测试测测测测测";
		bisd.setText(text);
		bisd.setGravity("east");
		bisd.setFontColor("#1b1b1b");
		bisd.setFontSize(26);
		//粗体
		bisd.setFontName("yahei");
		subImageList.add(bisd);
		
		bisd= new BaseImageSubDTO();
		bisd.setType(2);
		bisd.setLeft(70);
		bisd.setTop(330);
		text = "测试测测测测测";
		bisd.setText(text);
		bisd.setGravity("east");
		bisd.setFontColor("#be1819");
		bisd.setFontSize(38);
		//粗体
		bisd.setFontName("yahei");
		subImageList.add(bisd);
		
		bisd= new BaseImageSubDTO();
		bisd.setType(2);
		bisd.setLeft(70);
		bisd.setTop(450);
		text = "9999";
		bisd.setText(text);
		bisd.setGravity("east");
		bisd.setFontColor("#be1819");
		bisd.setFontSize(60);
		//粗体
		bisd.setFontName("bold");
		subImageList.add(bisd);
		
		bisd= new BaseImageSubDTO();
		bisd.setType(2);
		bisd.setLeft(450);
		bisd.setTop(1000);
		text = "￥";
		bisd.setText(text);
		bisd.setFontColor("#c0140c");
		bisd.setFontSize(34);
		//粗体
		bisd.setFontName("bold");
		subImageList.add(bisd);
		
		bisd= new BaseImageSubDTO();
		bisd.setType(2);
		bisd.setLeft(30);
		bisd.setTop(1025);
		text = "请长按识别二维码\n微信支付购买";
		bisd.setText(text);
		bisd.setFontColor("#67351d");
		bisd.setFontSize(23);
		//粗体
		bisd.setFontName("yahei");
		subImageList.add(bisd);
		bid.setSubImageList(subImageList);
		Map<String, Integer> info = getImgInfo1(bid.getMainImageUrl());
		
		String newbg = margeImgHeight1(bid.getMainImageUrl(),(int)(info.get("height")*1.5 ));
		bid.setMainImageUrl(newbg);
		
		margeImage1(bid);
	}
}
