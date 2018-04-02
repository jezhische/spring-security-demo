package com.jezh.springsecurity.util.utils;

import org.apache.logging.log4j.Logger;
import org.springframework.core.env.Environment;

public class Utils {

    public static int parsePropsToInt(Environment env, Logger log, String propName) {
        String property = env.getProperty(propName);
        int result = 0;
        if (property != null) {
            try {
                result = Integer.parseInt(property);
                log.trace(String.format("%s is set to %d", propName, result));
            } catch (NumberFormatException e) {
                log.error(String.format("%s thrown when parse %s property: %s\n",
                        e.getClass().getSimpleName(), propName, e.getMessage()));
                throw new RuntimeException();
            }
        } else {
            log.error(String.format("missing %s property\n", propName));
            throw new RuntimeException();
        }
        return result;
    }
}
