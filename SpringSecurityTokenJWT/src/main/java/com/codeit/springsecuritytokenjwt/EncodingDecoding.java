package com.codeit.springsecuritytokenjwt;

import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

public class EncodingDecoding {

    public static void main(String[] args) {

        // Java v8부터 기본 제공하는 Base64 및 Base64URL 기반의 인코더와 디코더 세팅
        // Base64
        Encoder encoder = Base64.getEncoder();
        Decoder decoder = Base64.getDecoder();
        // Base64URL
        Encoder urlEncoder = Base64.getUrlEncoder();
        Decoder urlDecoder = Base64.getUrlDecoder();

        // 1. encoder 사용해보기 (송신자가 수행)
        // 1-1. 평문 준비
        String plainText = "동해물과 백두산이 마르고 닳도록";
		// 문자열 평문을 바이트 배열로 변환(직렬화)
        byte[] plainTextToByteArr = plainText.getBytes();

		// 1-2. 인코딩
		// 1-2-1. Base64 인코딩
        byte[] encodedArr = encoder.encode(plainTextToByteArr);
		// 1-2-2. Base64URL 인코딩
		byte[] urlEncodedArr = urlEncoder.encode(plainTextToByteArr);

        // 1-3. 인코딩된 바이트 배열을 문자열로 변환(역직렬화)
        String encodedStr = new String(encodedArr);
        String urlEncodedStr = new String(urlEncodedArr);
        System.out.println("인코딩된 애국가(Base64)    = " + encodedStr);
        System.out.println("인코딩된 애국가(Base64URL) = " + urlEncodedStr);

        // 2. decoder 사용해보기 (수신자가 수행)
        // 2-1. Base64 디코딩
        byte[] decodedArr = decoder.decode(encodedStr);
        // 2-2. Base64URL 디코딩
        byte[] urlDecodedArr = urlDecoder.decode(urlEncodedStr);

        // 2-3. 디코딩된 바이트 배열을 문자열로 변환
        String decodedStr = new String(decodedArr);
        String urlDecodedStr = new String(urlDecodedArr);
        System.out.println("다시 디코딩된 애국가            = " + decodedStr);
        System.out.println("다시 디코딩된 애국가(Base64URL) = " + urlDecodedStr);
    }
}
