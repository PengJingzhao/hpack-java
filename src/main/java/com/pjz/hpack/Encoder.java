package com.pjz.hpack;

import com.pjz.hpack.utils.BinaryStringSplitter;
import com.pjz.hpack.utils.HeaderUtils;

import java.util.Map;

public class Encoder {

    public String encode(String header) {
        StringBuffer encoded = new StringBuffer();
        Map<String, String> map = HeaderUtils.parseHeaderString(header);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            int index = StaticTable.searchIndex(key);
            encoded.append(BinaryStringSplitter.toBinaryStringWithLength(index, 8));
        }
        return encoded.toString();
    }
}
