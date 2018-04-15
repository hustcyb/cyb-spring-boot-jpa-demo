package com.cyb.spring.jpa.demo.util;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Json工具类
 * 
 * @author Administrator
 *
 */
public final class JsonUtils {

	/**
	 * 将Java对象转换为Json字符串
	 * 
	 * @param bean
	 *            Java对象
	 * @return Json字符串
	 */
	public static String bean2Json(Object bean) {
		String json = null;

		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_EMPTY);
		
		try {
			json = mapper.writeValueAsString(bean);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return json;
	}

	/**
	 * 将json字符串转换为Json对象
	 * 
	 * @param json
	 *            json字符串
	 * @param beanType
	 *            Json对象
	 * @return Json对象
	 */
	public static <T> T json2Bean(String json, Class<T> beanType) {
		T bean = null;

		ObjectMapper mapper = new ObjectMapper();
		try {
			bean = mapper.readValue(json, beanType);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return bean;
	}
}
