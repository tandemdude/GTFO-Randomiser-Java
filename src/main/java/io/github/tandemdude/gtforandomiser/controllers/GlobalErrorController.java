package io.github.tandemdude.gtforandomiser.controllers;

import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("${server.error.path:${error.path:/error}}")
public class GlobalErrorController extends AbstractErrorController {
    public GlobalErrorController() {
        super(new DefaultErrorAttributes());
    }

    @RequestMapping
    public ResponseEntity<?> error(HttpServletRequest req) {
        var opts = ErrorAttributeOptions
                .of(ErrorAttributeOptions.Include.BINDING_ERRORS, ErrorAttributeOptions.Include.MESSAGE);
        var status = getStatus(req);
        var errorPayload = getErrorAttributes(req, opts);
        return new ResponseEntity<>(errorPayload, status);
    }
}
