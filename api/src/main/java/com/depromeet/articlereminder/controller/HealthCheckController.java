package com.depromeet.articlereminder.controller;

import com.depromeet.articlereminder.common.Constants;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {
    @ApiOperation("Health Check")
    @RequestMapping(
            value = "/healthcheck/_check",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity healthCheck() {
        return ResponseEntity.ok(Constants.SupportedVersions.getAllVersions());
    }
}
