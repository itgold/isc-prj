package com.iscweb.simulator.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.iscweb.common.model.IApplicationComponent;
import com.iscweb.common.model.dto.response.StringResponseDto;
import com.iscweb.simulator.dto.ErrorResponseDto;
import com.iscweb.simulator.exception.MyCustomException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/test")
public class TestController implements IApplicationComponent {

    @RequestMapping(value = "/ping", method = RequestMethod.GET)
    public StringResponseDto<Void> ping() {
        log.debug("Ping/Pong Controller called!");

        if (true) {
            throw new MyCustomException("Not suppose to be called!");
        }
        return StringResponseDto.of("pong!");
    }

    @ExceptionHandler(MyCustomException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.PAYMENT_REQUIRED)
    public ErrorResponseDto handleError(HttpServletRequest req, Exception ex) throws JsonProcessingException {
        return new ErrorResponseDto("Exception error: " + ex.getMessage());
    }

    @Override
    public Logger getLogger() {
        return log;
    }
}
