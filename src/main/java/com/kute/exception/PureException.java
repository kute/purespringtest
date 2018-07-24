package com.kute.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * created by kute on 2018/07/21 19:59
 * 抛出此异常时 http status 设置
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, code = HttpStatus.BAD_REQUEST, reason = "Bad Request")
public class PureException extends RuntimeException {
}
