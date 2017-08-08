package cn.htd.common.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.DeleteObjectsRequest;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import cn.htd.common.ExecuteResult;

/**
 * Description: [OSS(阿里云存储)文件上传帮助类]
 */
public class OssUploadUtils {

	private Logger logger = LoggerFactory.getLogger(OssUploadUtils.class);

	/**
	 * Bucket是OSS上的命名空间，也是计费、权限控制、日志记录等高级功能的管理实体；
	 * Bucket名称在整个OSS服务中具有全局唯一性，且不能修改；存储在OSS上的每个
	 * Object必须都包含在某个Bucket中。一个应用，例如图片分享网站，可以对应一个
	 * 或多个Bucket。一个用户最多可创建10个Bucket，但每个bucket中存放的object
	 * 的数量没有限制，存储容量每个buckte最高支持2PB 注意，每个bucket只能绑定一个域名
	 */
	private String bucket;
	/** 域名 */
	private String endpoint;
	/** Access Key ID和Access Key Secret是您访问阿里云API的密钥 */
	private String accessKeyId;
	private String accessKeySecret;

	/**
	 * 当参数无Bucket时
	 */
	public OssUploadUtils(String endpoint, String accessKeyId, String accessKeySecret) {
		this.endpoint = endpoint;
		this.accessKeyId = accessKeyId;
		this.accessKeySecret = accessKeySecret;
	}

	/**
	 * 当参数有Bucket时
	 */
	public OssUploadUtils(String bucket, String endpoint, String accessKeyId, String accessKeySecret) {
		this.bucket = bucket;
		this.endpoint = endpoint;
		this.accessKeyId = accessKeyId;
		this.accessKeySecret = accessKeySecret;
	}

	/**
	 * 初始化OSSClient
	 */
	public OSSClient initOssClient() {
		// 初始化OSSClient
		logger.info("开始连接OSS存储服务器域名：" + this.endpoint + "，accessKeyId:" + this.accessKeyId + "，accessKeySecret:" + this.accessKeySecret);
		OSSClient client = new OSSClient(this.endpoint, this.accessKeyId, this.accessKeySecret);
		return client;
	}

	/**
	 * @param path            FTP服务器保存目录,如果是根目录则为"/"；如果目录不存在，会自动创建 上传文件文件流
	 * @param contentType     上传文件文件类型
	 */
	public String upload(String path, InputStream inputStream, String contentType, Long length) {
		logger.info("OSS上传文件开始...");
		int year = Calendar.getInstance().get(Calendar.YEAR);
		int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
		int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
		// 图片存储分年月日文件夹存储
		String dir = "/" + year + month + day;
		path = dir + path +"/";
		String remote = UUID.randomUUID().toString() + contentType;
		String remoteName = remote.replaceAll("\\-","");
		boolean isFinish = false;
		OSSClient oSSClient = null;
		try {
			try {
				oSSClient = initOssClient();
			} catch (Exception e) {
				logger.info("连接OSS云存储服务器失败：" + e);
				return null;
			}
			String bucketName = this.bucket;
			bucketName = isNotBucketName(bucketName, oSSClient);
			if (bucketName != null && !"".equals(bucketName)) {
				// 转移工作目录至指定目录下
				this.changeMakeWorkingDir(path, bucketName, oSSClient);
				// 创建上传Object的Metadata
				ObjectMetadata meta = new ObjectMetadata();
				// 必须设置ContentLength
				meta.setContentLength(length);
				String fileName = new String(remoteName.getBytes("GBK"), "iso-8859-1");
				path = path.substring(1, path.length());
				String objectName = path + fileName;
				// 上传Object.
				PutObjectResult result = oSSClient.putObject(bucketName, objectName, inputStream, meta);
				if (result != null) {
					isFinish = true;
					logger.info("OSS云存储服务器文件上传成功!");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("往OSS云存储服务器上传文件失败：" + e);
		} finally {
			try {
				if (isFinish) {
					inputStream.close();
				}
			} catch (IOException e1) {
				logger.error("关闭文件流出错", e1);
			}
		}
		return "/"+path + remoteName;
	}

	/**
	 * 判断bucketName是否存在，否则新建
	 * 
	 * @param bucketName
	 * @param oSSClient
	 */
	public String isNotBucketName(String bucketName, OSSClient oSSClient) {
		// 获取Bucket的存在信息
		boolean exists = oSSClient.doesBucketExist(bucketName);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		String nowTime = null;
		try {
			nowTime = sdf.format(new Date());
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 输出结果
		if (!exists) {
			bucketName = nowTime.toString() + "bucketname";
			try {
				oSSClient.createBucket(bucketName);
				logger.info("自动创建bucketName:" + bucketName + "成功！");
			} catch (OSSException e) {
				e.printStackTrace();
				logger.error("自动创建bucketName:" + bucketName + "失败，请手动创建！");
				return null;
			}
		}
		return bucketName;
	}

	/**
	 * 
	 * 切换目录，如果目录不存在会自动创建
	 * @param path      要切换的工作区路径
	 */
	private void changeMakeWorkingDir(String path, String bucketName, OSSClient oSSClient) throws IOException {
		path = path.substring(1, path.length());
		// 获取Bucket的存在信息
		boolean exists = oSSClient.doesObjectExist(bucketName, path);
		if (!exists) {
			path = new String(path.getBytes("GBK"), "iso-8859-1");
			if (path != null && !"".equals(path)) {
				// 要创建的文件夹名称,在满足Object命名规则的情况下以"/"结尾
				String objectName = path;
				ObjectMetadata objectMeta = new ObjectMetadata();
				/*
				 * 这里的size为0,注意OSS本身没有文件夹的概念,这里创建的文件夹本质上是一个size为0的Object,dataStream仍然可以有数据
				 */
				byte[] buffer = new byte[0];
				ByteArrayInputStream in = new ByteArrayInputStream(buffer);
				objectMeta.setContentLength(0);
				try {
					oSSClient.putObject(bucketName, objectName, in, objectMeta);
				} catch (Exception e) {
					logger.info("创建文件夹:" + path + "失败！");
					e.printStackTrace();
				} finally {
					in.close();
				}
			}
		}
	}

	/**
	 * 批量删除oss文件
	 * oss删除无返回值，是void方法，成功调用就是删除成功，用try catch处理 
	 *
	 */
	public ExecuteResult<String> deleteObjectFiles(List<String> filePathList) {
		ExecuteResult<String> result = new ExecuteResult<String>();
		logger.info("OSS删除文件开始...");

		OSSClient oSSClient = null;
		DeleteObjectsRequest objectRequests = new DeleteObjectsRequest(this.bucket);// 定义参数实体（bucketName以及文件集合）

		try {
			try {
				oSSClient = initOssClient();
			} catch (Exception e) {
				logger.info("连接OSS云存储服务器失败：" + e);
				result.setResultMessage("连接OSS云存储服务器失败失败！");
			}
			String bucketName = this.bucket;
			bucketName = isNotBucketName(bucketName, oSSClient);
			if (bucketName != null && !"".equals(bucketName)) {
				objectRequests.setBucketName(bucketName);
				objectRequests.setKeys(filePathList);
				oSSClient.deleteObjects(objectRequests);
				result.setResultMessage("删除成功！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setResultMessage("删除失败！");
			logger.info("OSS内部云服务器删除文件失败：" + e);
		} finally {
			logger.info("OSS删除文件结束！.");
		}
		return result;
	}

	/**
	 * 批量获取oss文件
	 * oss删除无返回值，是void方法，成功调用就是删除成功，用try catch处理 
	 */
	public ExecuteResult<List<byte[]>> getObjectFiles(List<String> filePathList) {
		ExecuteResult<List<byte[]>> result = new ExecuteResult<List<byte[]>>();
		logger.info("OSS获取文件byte数组开始...");
		List<byte[]> listByte = new ArrayList<byte[]>();
		OSSClient oSSClient = null;
		try {
			try {
				oSSClient = initOssClient();
			} catch (Exception e) {
				logger.info("连接OSS云存储服务器失败：" + e);
				result.setResultMessage("连接OSS云存储服务器失败失败！");
			}
			String bucketName = this.bucket;
			bucketName = isNotBucketName(bucketName, oSSClient);
			if (bucketName != null && !"".equals(bucketName)) {
				for (String strs : filePathList) {
					GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, strs);
					OSSObject ossObject = oSSClient.getObject(getObjectRequest);
					InputStream in = ossObject.getObjectContent();
					ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
					byte[] buff = new byte[100];
					int rc = 0;
					while ((rc = in.read(buff, 0, 100)) > 0) {
						swapStream.write(buff, 0, rc);
					}
					byte[] bytes = swapStream.toByteArray();
					listByte.add(bytes);
				}
				result.setResult(listByte);
				result.setResultMessage("OSS获取文件byte数组成功！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setResultMessage("OSS获取文件byte数组失败！");
			logger.info("OSS内部云服务器获取文件byte数组失败：" + e);
		} finally {
			logger.info("OSS获取文件byte数组结束！.");
		}
		return result;
	}

	public static void main(String[] args) {
		OssUploadUtils ossUploadUtils = new OssUploadUtils("dsy20", "dsy20.oss.gzdata.com.cn", "D0OQ6nTqjNmr9h6c","OdwNzcNSfgoHGUNcLktlPdKIUT4sKh");
		File file = new File("D:/QQ截图20160224153616.jpg");
		InputStream is = null;
		try {
			is = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String url = ossUploadUtils.upload("/album", is, ".jpg", file.length());
		System.out.println(url);
	}
}
