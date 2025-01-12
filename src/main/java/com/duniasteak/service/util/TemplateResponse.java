package com.duniasteak.service.util;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class TemplateResponse {
    public Map<Object, Object> success(Object data) {
        Map<Object, Object> map = new HashMap<>();
        map.put("data", data);
        map.put("message", "success");
        map.put("code", 200);
        return map;
    }

    public Map<Object, Object> error(Object message) {
        Map<Object, Object> map = new HashMap<>();
        map.put("error", message);
        map.put("code", 400);
        return map;
    }

    public Map<Object, Object> error(Object message, int code) {
        Map<Object, Object> map = new HashMap<>();
        map.put("error", message);
        map.put("code", code);
        return map;
    }

    public Map<Object, Object> notFound(Object message) {
        Map<Object, Object> map = new HashMap<>();
        map.put("error", message);
        map.put("code", 404);
        return map;
    }
}
