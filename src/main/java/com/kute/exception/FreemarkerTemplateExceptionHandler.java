package com.kute.exception;

import freemarker.core.Environment;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Writer;

/**
 * created by kute on 2018/04/07 13:41
 */
public class FreemarkerTemplateExceptionHandler implements TemplateExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(FreemarkerTemplateExceptionHandler.class);

    @Override
    public void handleTemplateException(TemplateException e, Environment environment, Writer writer) throws TemplateException {
        logger.error("handleTemplateException error:{}, env:{}, writer:{}", e, environment, writer);
    }
}
