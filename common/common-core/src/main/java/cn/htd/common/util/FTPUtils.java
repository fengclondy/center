package cn.htd.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.UUID;

import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * <p>
 * Description: [FTP文件上传帮助类]
 * </p>
 */
public class FTPUtils {
	private Logger LOG = LoggerFactory.getLogger(FTPUtils.class);

	private FTPClient ftpClient = null;
	private String url;
	private int port = 21;
	private String username;
	private String password;

	/**
	 * 
	 * <p>
	 * Discription:[构造器方法描述]
	 * </p>
	 */
	public FTPUtils(String url, String username, String password) {
		this.url = url;
		this.username = username;
		this.password = password;
	}

	/**
	 * 
	 * <p>
	 * Discription:[构造器方法描述]
	 * </p>
	 */
	public FTPUtils(String url, int port, String username, String password) {
		this.url = url;
		this.port = port;
		this.username = username;
		this.password = password;
	}

	/**
	 * 
	 * <p>
	 * Discription:[方法功能中文描述]
	 * </p>
	 * 
	 * @param path          FTP服务器保存目录,如果是根目录则为"/"；如果目录不存在，会自动创建
	 * @param is            上传文件文件流
	 * @param contentType   上传文件文件类型
	 * @return
	 */
	public String upload(String path, InputStream is, String contentType) {
		LOG.info("FTP上传文件开始...");
		int year = Calendar.getInstance().get(Calendar.YEAR);
		int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
		int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
		// 图片存储分年月日文件夹存储
		String dir = "/" + year + "/" + month + "/" + day + "/";
		path += dir;
		String remoteName = UUID.randomUUID().toString() + contentType;
		boolean isFinish = false;
		try {

			this.ftpClient = new FTPClient();
			LOG.info("开始连接FTP服务器：" + this.url + ":" + this.port);
			this.ftpClient.connect(this.url, this.port);
			LOG.info("FTP服务器连接成功，开始登录FTP服务器...");
			this.ftpClient.login(this.username, this.password);
			LOG.info("FTP服务器登录成功，开始更改操作的目录...");
			// 转移工作目录至指定目录下
			this.changeMakeWorkingDir(path);
			this.ftpClient.setBufferSize(1024);
			this.ftpClient.setControlEncoding("GBK");
			ftpClient.enterLocalPassiveMode();
			// 设置文件类型（二进制）
			this.ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			LOG.info("开始往FTP服务器上传文件...");
			isFinish = this.ftpClient.storeFile(new String(remoteName.getBytes("GBK"), "iso-8859-1"), is);
			if (isFinish) {
				LOG.info("FTP服务器文件上传成功");
			} else {
				LOG.info("FTP服务器文件上传失败");
				return null;
			}
		} catch (Exception e) {
			LOG.info("往FTP服务器上传文件失败：" + e);
			return null;
		} finally {
			try {
				if (isFinish) {
					is.close();
				}
			} catch (IOException e1) {
				LOG.error("关闭文件流出错", e1);
			}
			if (this.ftpClient.isConnected()) {
				try {
					this.ftpClient.disconnect();
				} catch (IOException e) {
					LOG.error("关闭FTP客户端出错");
				}
			}
		}
		return dir + remoteName;
	}

	/**
	 * 
	 * <p>
	 * Discription:[切换目录，如果目录不存在会自动创建]
	 * </p>
	 * 
	 * @param path    要切换的工作区路径
	 */
	private void changeMakeWorkingDir(String path) throws IOException {
		String[] dirs = path.split("/");
		for (String dir : dirs) {
			dir = new String(dir.getBytes("GBK"), "iso-8859-1");
			if (dir != null && !"".equals(dir)) {
				this.ftpClient.makeDirectory(dir);
			}
			this.ftpClient.changeWorkingDirectory(dir);
		}
	}

	public String getUrl() {
		return url;
	}

	public int getPort() {
		return port;
	}

}
