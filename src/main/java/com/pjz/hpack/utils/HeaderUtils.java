package com.pjz.hpack.utils;

import java.util.HashMap;
import java.util.Map;

public class HeaderUtils {

    public static Map<String, String> parseHeaderString(String header) {
        Map<String, String> map = new HashMap<>();

        String[] lines = header.split("\r\n");
        for (String line : lines) {
            map.put(line.substring(0, line.lastIndexOf(":")).trim(), line.substring(line.lastIndexOf(":") + 1).trim());
        }

        return map;
    }

    public static String parseHeaderMap(Map<String, String> map) {
        StringBuffer buffer = new StringBuffer();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            buffer.append(key)
                    .append(":")
                    .append(value)
                    .append("\r\n");
        }
        return buffer.toString();
    }

}
