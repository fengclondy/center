package cn.htd.common.mq;

import java.io.IOException;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * MQSendUtil
 * 
 * @author zhangxiaolong
 *
 */
public class MQSendUtil {

	private static final Logger LOG = LoggerFactory.getLogger(MQSendUtil.class);
	private AmqpTemplate amqpTemplate;

	/**
	 * Convert dto to json string
	 * 
	 * @param dto
	 * @return
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static String getJson(final Object dto) throws JsonGenerationException, JsonMappingException, IOException {

		// bean转json
		final ObjectMapper mapper = new ObjectMapper();

		mapper.setDateFormat(new SimpleDateFormat("yyyyMMdd.HHmmssZ"));
		final StringWriter writer = new StringWriter();
		mapper.writeValue(writer, dto);
		final String jsonString = writer.toString();
		writer.close();
		return jsonString;
	}

	/**
	 * Send object data to MQ
	 * 
	 * @param object
	 */
	public void sendToMQ(final Object object) {
		LOG.debug("Send Object to MQ, Class:" + object.getClass() + " =========");

		final StringBuffer sb = new StringBuffer();

		// try {
		// sb.append(MQSendUtil.getJson(object));
		// } catch (final JsonGenerationException e) {
		// LOG.error(e.getMessage(), e);
		// } catch (final JsonMappingException e) {
		// LOG.error(e.getMessage(), e);
		// } catch (final IOException e) {
		// LOG.error(e.getMessage(), e);
		// }

		amqpTemplate.convertAndSend(object);
	}

	/**
	 * Multiple sending data list to MQ
	 * 
	 * @param dataList
	 */
	public void multitudeSend(final List dataList) {
		if (dataList != null && dataList.size() > 0) {
			LOG.info("======== Send Object to MQ, Class:" + dataList.get(0).getClass() + ", Object Size:"
					+ dataList.size() + " =========");
		}

		final StringBuffer sb = new StringBuffer();

		for (final Object data : dataList) {

			try {
				sb.append(MQSendUtil.getJson(data));
			} catch (final JsonGenerationException e) {

				LOG.error(e.getMessage(), e);

			} catch (final JsonMappingException e) {

				LOG.error(e.getMessage(), e);

			} catch (final IOException e) {

				LOG.error(e.getMessage(), e);

			}
		}

		LOG.debug("Sending Data To Queue" + sb.toString());
		amqpTemplate.convertAndSend(sb.toString());

	}

	/**
	 * Multiple sending data list to MQ
	 * 
	 * @param dataList
	 */
	public void sendList(final List dataList) {
		if (dataList != null && dataList.size() > 0) {
			LOG.info("======== Send Object to MQ, Class:" + dataList.get(0).getClass() + ", Object Size:"
					+ dataList.size() + " =========");
		}

		final StringBuffer sb = new StringBuffer();

		// 添加最外层的中括号
		sb.append("[");

		final int lastIndex = dataList.size() - 1;
		int index = 0;

		for (final Object data : dataList) {

			try {
				sb.append(MQSendUtil.getJson(data));
				// 多条记录时要在以逗号分隔，且最后一条记录后没有逗号
				if (dataList.size() > 1 && lastIndex != index) {
					sb.append(",");
				}
			} catch (final JsonGenerationException e) {

				LOG.error(e.getMessage(), e);

			} catch (final JsonMappingException e) {

				LOG.error(e.getMessage(), e);

			} catch (final IOException e) {

				LOG.error(e.getMessage(), e);

			}
			index = index + 1;
		}

		// 添加最外层的中括号
		sb.append("]");

		LOG.debug("Sending Data To Queue" + sb.toString());
		amqpTemplate.convertAndSend(sb.toString());

	}

	public void sendToMQWithRoutingKey(Object object, String routingKey) {
		LOG.debug("Send Object to MQ, sendToMQWithRoutingKey:" + object.getClass() + " =========");

		// final StringBuffer sb = new StringBuffer();
		//
		// try {
		// sb.append(MQSendUtil.getJson(object));
		// } catch (final Exception e) {
		// LOG.error(e.getMessage(), e);
		// }

		amqpTemplate.convertAndSend(routingKey, object);
	}

	public void setAmqpTemplate(final AmqpTemplate amqpTemplate) {
		this.amqpTemplate = amqpTemplate;
	}

}
