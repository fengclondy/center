package cn.htd.searchcenter.support;

import java.text.SimpleDateFormat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class CustomJacksonObjectMapper extends ObjectMapper {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6143244026726508644L;

	public CustomJacksonObjectMapper() {
		super();
		// this.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
		// this.configure(SerializationFeature.WRAP_ROOT_VALUE, true);
		// this.setSerializationInclusion(Include.NON_NULL);
		configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		// DateFormat.
		// com.fasterxml.jackson.databind.util.StdDateFormat

	}
}
