package com.bjucloud.searchcenter.analyzer.util;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class IOUtils {

	public static void deleteWordFromFile(String fileName, String deleteWord) throws Exception {
		InputStream is = FileUtils.openInputStream(new File(fileName));
		List<String> list = org.apache.commons.io.IOUtils.readLines(is, "UTF-8");
		list.remove(deleteWord);
		is.close();

		OutputStream os = FileUtils.openOutputStream(new File(fileName), false);
		org.apache.commons.io.IOUtils.writeLines(list, System.getProperty("line.separator"), os, "UTF-8");
		os.flush();
		os.close();
	}
}
