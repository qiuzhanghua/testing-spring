package com.example.fauxspring;

import java.util.Map;

public class ModelMapImpl implements Model{

    Map<String, Object> map = new java.util.HashMap<>();

    @Override
    public void addAttribute(String key, Object o) {
        map.put(key, o);

    }

    @Override
    public void addAttribute(Object o) {

    }

    public Map<String, Object> getMap() {
        return map;
    }

}
