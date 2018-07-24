package com.kute.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * created by kute on 2018/07/21 20:07
 */
@ControllerAdvice
public class ControllerExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler({PureException.class})
    public String handlerPureException() {
        logger.error("redirect:/erro");
        return "redirect:/error";
    }

}
