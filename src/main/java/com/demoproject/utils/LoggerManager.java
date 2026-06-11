package com.demoproject.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerManager {

    public static Logger getLogger(Class<?> c){
        return LogManager.getLogger();
    }

}
