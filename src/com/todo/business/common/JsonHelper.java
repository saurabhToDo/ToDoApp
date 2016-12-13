package com.todo.business.common;
/*
 * @Saurabh
 */
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class JsonHelper {
	public static void writeResponseData(Object object,HttpServletResponse response) throws IOException{
		ObjectMapper objectMapper = new ObjectMapper();
		try{
			PrintWriter pw = response.getWriter();
			objectMapper.writeValue(pw, object);
		} catch (JsonGenerationException e) {
			throw e;
		} catch (JsonMappingException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		}
	}
}
