package com.pjz.hpack;

public class HPackApplication {

    public static void main(String[] args) {

        String rawHeaders = ":authority: application/json\r\n" +
                ":method: post\r\n" +
                ":path: application/json";

        Encoder encoder = new Encoder();
        String encode = encoder.encode(rawHeaders);
        System.out.println(encode);

        Decoder decoder = new Decoder();
        String decode = decoder.decode(encode);
        System.out.println(decode);
    }
}
