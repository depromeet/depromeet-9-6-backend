package com.depromeet.articlereminder.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {
    public static ResponseEntity<Object> generateResponse(String message, String status, Object responseObj) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("status", status);
        map.put("comment", message);
        map.put("data", responseObj);

        return new ResponseEntity<Object>(map, HttpStatus.OK);
    }
}
