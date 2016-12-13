package com.todo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 
 * @author Saurabh
 *
 */
public class AppLoggerUtil {
	private static Logger log;
	public static void logError(Class<?> clazz,String msg,Exception e) {
		String error = "fatal error";
		log = LoggerFactory.getLogger(clazz);
		log.error(error+":: "+msg,e);
		log = null;
	}
	
	public static void logInfo(Class<?> clazz,String msg) {
        log = LoggerFactory.getLogger(clazz);
        log.info(msg);
        log = null;
    }
}
