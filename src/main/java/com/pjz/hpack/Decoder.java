package com.pjz.hpack;

import com.pjz.hpack.entity.HeaderField;
import com.pjz.hpack.utils.BinaryStringSplitter;
import com.pjz.hpack.utils.HeaderUtils;

import java.util.HashMap;
import java.util.List;

public class Decoder {

    public String decode(String encoded) {
        List<Integer> integers = parseEncoded(encoded);
        HashMap<String, String> map = new HashMap<>();
        for (Integer integer : integers) {
            HeaderField entry = StaticTable.getEntry(integer);
            map.put(entry.getName(), entry.getValue());
        }
        return HeaderUtils.parseHeaderMap(map);
    }

    private List<Integer> parseEncoded(String encoded) {
        List<String> binaryString = BinaryStringSplitter.splitBinaryString(encoded);
        return binaryString.stream().map(s -> Integer.parseInt(s, 2)).toList();
    }

}
