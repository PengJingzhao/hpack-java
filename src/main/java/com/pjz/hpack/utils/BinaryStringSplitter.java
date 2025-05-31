package com.pjz.hpack.utils;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * 二进制字符串拆分工具类
 * 用于将编码后的字符串按8位二进制进行拆分
 */
public class BinaryStringSplitter {

    /**
     * 将二进制字符串按8位进行拆分
     *
     * @param binaryString 二进制字符串（只包含0和1）
     * @return List<String> 每个元素是8位二进制字符串
     */
    public static List<String> splitBinaryString(String binaryString) {
        List<String> result = new ArrayList<>();

        if (binaryString == null || binaryString.isEmpty()) {
            return result;
        }

        // 验证输入是否为有效的二进制字符串
        if (!binaryString.matches("[01]+")) {
            throw new IllegalArgumentException("输入必须是有效的二进制字符串（只包含0和1）");
        }

        // 按8位分割
        for (int i = 0; i < binaryString.length(); i += 8) {
            int endIndex = Math.min(i + 8, binaryString.length());
            String segment = binaryString.substring(i, endIndex);

            // 如果最后一段不足8位，用0补齐
            if (segment.length() < 8) {
                segment = String.format("%-8s", segment).replace(' ', '0');
            }

            result.add(segment);
        }

        return result;
    }

    /**
     * 将普通字符串转换为二进制并按8位拆分
     *
     * @param text 普通文本字符串
     * @return List<String> 每个元素是8位二进制字符串
     */
    public static List<String> textToBinary8Bits(String text) {
        List<String> result = new ArrayList<>();

        if (text == null || text.isEmpty()) {
            return result;
        }

        byte[] bytes = text.getBytes(StandardCharsets.UTF_8);

        for (byte b : bytes) {
            // 将字节转换为8位二进制字符串
            String binaryString = String.format("%8s", Integer.toBinaryString(b & 0xFF))
                    .replace(' ', '0');
            result.add(binaryString);
        }

        return result;
    }

    /**
     * 将十六进制字符串转换为二进制并按8位拆分
     *
     * @param hexString 十六进制字符串
     * @return List<String> 每个元素是8位二进制字符串
     */
    public static List<String> hexToBinary8Bits(String hexString) {
        List<String> result = new ArrayList<>();

        if (hexString == null || hexString.isEmpty()) {
            return result;
        }

        // 移除可能的0x前缀
        if (hexString.startsWith("0x") || hexString.startsWith("0X")) {
            hexString = hexString.substring(2);
        }

        // 验证是否为有效的十六进制字符串
        if (!hexString.matches("[0-9a-fA-F]+")) {
            throw new IllegalArgumentException("输入必须是有效的十六进制字符串");
        }

        // 如果长度是奇数，前面补0
        if (hexString.length() % 2 != 0) {
            hexString = "0" + hexString;
        }

        // 每两个十六进制字符转换为一个字节（8位二进制）
        for (int i = 0; i < hexString.length(); i += 2) {
            String hexPair = hexString.substring(i, i + 2);
            int decimal = Integer.parseInt(hexPair, 16);
            String binaryString = String.format("%8s", Integer.toBinaryString(decimal))
                    .replace(' ', '0');
            result.add(binaryString);
        }

        return result;
    }

    /**
     * 将Base64编码字符串转换为二进制并按8位拆分
     *
     * @param base64String Base64编码的字符串
     * @return List<String> 每个元素是8位二进制字符串
     */
    public static List<String> base64ToBinary8Bits(String base64String) {
        List<String> result = new ArrayList<>();

        if (base64String == null || base64String.isEmpty()) {
            return result;
        }

        try {
            byte[] decodedBytes = Base64.getDecoder().decode(base64String);

            for (byte b : decodedBytes) {
                String binaryString = String.format("%8s", Integer.toBinaryString(b & 0xFF))
                        .replace(' ', '0');
                result.add(binaryString);
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("无效的Base64编码字符串", e);
        }

        return result;
    }

    /**
     * 将8位二进制字符串列表合并为完整的二进制字符串
     *
     * @param binaryList 8位二进制字符串列表
     * @return String 完整的二进制字符串
     */
    public static String joinBinaryList(List<String> binaryList) {
        if (binaryList == null || binaryList.isEmpty()) {
            return "";
        }

        return String.join("", binaryList);
    }

    /**
     * 将8位二进制字符串列表转换回原始字节数组
     *
     * @param binaryList 8位二进制字符串列表
     * @return byte[] 原始字节数组
     */
    public static byte[] binaryListToBytes(List<String> binaryList) {
        if (binaryList == null || binaryList.isEmpty()) {
            return new byte[0];
        }

        byte[] result = new byte[binaryList.size()];

        for (int i = 0; i < binaryList.size(); i++) {
            String binaryString = binaryList.get(i);
            if (binaryString.length() != 8) {
                throw new IllegalArgumentException("每个二进制字符串必须是8位");
            }
            result[i] = (byte) Integer.parseInt(binaryString, 2);
        }

        return result;
    }

    /**
     * 将8位二进制字符串列表转换回文本
     *
     * @param binaryList 8位二进制字符串列表
     * @return String 原始文本
     */
    public static String binaryListToText(List<String> binaryList) {
        byte[] bytes = binaryListToBytes(binaryList);
        return new String(bytes, StandardCharsets.UTF_8);
    }

    /**
     * 将8位二进制字符串列表转换为十六进制字符串
     *
     * @param binaryList 8位二进制字符串列表
     * @return String 十六进制字符串
     */
    public static String binaryListToHex(List<String> binaryList) {
        if (binaryList == null || binaryList.isEmpty()) {
            return "";
        }

        StringBuilder hexBuilder = new StringBuilder();

        for (String binaryString : binaryList) {
            if (binaryString.length() != 8) {
                throw new IllegalArgumentException("每个二进制字符串必须是8位");
            }
            int decimal = Integer.parseInt(binaryString, 2);
            hexBuilder.append(String.format("%02X", decimal));
        }

        return hexBuilder.toString();
    }

    /**
     * 打印二进制列表，便于调试
     *
     * @param binaryList 8位二进制字符串列表
     * @param label 标签
     */
    public static void printBinaryList(List<String> binaryList, String label) {
        System.out.println("=== " + label + " ===");
        for (int i = 0; i < binaryList.size(); i++) {
            System.out.printf("第%d字节: %s (十进制: %d, 十六进制: %02X)%n",
                    i + 1,
                    binaryList.get(i),
                    Integer.parseInt(binaryList.get(i), 2),
                    Integer.parseInt(binaryList.get(i), 2));
        }
        System.out.println();
    }

    public static String toBinaryStringWithLength(int number, int bitLength) {
        String binaryStr = Integer.toBinaryString(number);
        // 如果是负数，toBinaryString返回的是补码形式的32位二进制，这里按补码返回，直接截取后面位数
        if (binaryStr.length() > bitLength) {
            // 取低位bitLength位
            return binaryStr.substring(binaryStr.length() - bitLength);
        } else {
            // 左补零
            return String.format("%" + bitLength + "s", binaryStr).replace(' ', '0');
        }
    }
}
