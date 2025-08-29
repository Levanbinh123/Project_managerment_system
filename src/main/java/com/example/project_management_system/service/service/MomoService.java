//package com.example.project_management_system.service.service;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import javax.crypto.Mac;
//import javax.crypto.spec.SecretKeySpec;
//import java.util.Base64;
//import java.util.UUID;
//import java.util.TreeMap;
//
//@Service
//public class MomoService {
//
//    @Value("${momo.partnerCode}")
//    private String partnerCode;
//
//    @Value("${momo.accessKey}")
//    private String accessKey;
//
//    @Value("${momo.secretKey}")
//    private String secretKey;
//
//    @Value("${momo.endpoint}")
//    private String endpoint;
//
//    @Value("${momo.returnUrl}")
//    private String returnUrl;
//
//    @Value("${momo.notifyUrl}")
//    private String notifyUrl;
//
//    public String createPayment(long amount, String orderInfo) throws Exception {
//        String orderId = UUID.randomUUID().toString();
//        String requestId = UUID.randomUUID().toString();
//
//        TreeMap<String, String> rawData = new TreeMap<>();
//        rawData.put("partnerCode", partnerCode);
//        rawData.put("accessKey", accessKey);
//        rawData.put("requestId", requestId);
//        rawData.put("amount", String.valueOf(amount));
//        rawData.put("orderId", orderId);
//        rawData.put("orderInfo", orderInfo);
//        rawData.put("returnUrl", returnUrl);
//        rawData.put("notifyUrl", notifyUrl);
//        rawData.put("requestType", "captureMoMoWallet");
//
//        // Tạo chữ ký HMAC SHA256
//        String rawHash = String.join("&",
//                "partnerCode=" + partnerCode,
//                "accessKey=" + accessKey,
//                "requestId=" + requestId,
//                "amount=" + amount,
//                "orderId=" + orderId,
//                "orderInfo=" + orderInfo,
//                "returnUrl=" + returnUrl,
//                "notifyUrl=" + notifyUrl,
//                "requestType=captureMoMoWallet"
//        );
//
//        String signature = hmacSHA256(rawHash, secretKey);
//
//        // Gửi request tới MoMo (có thể dùng RestTemplate hoặc HttpClient)
//        // Response trả về sẽ có payUrl để redirect người dùng
//        return signature; // demo, thực tế bạn gọi API MoMo
//    }
//
//    private String hmacSHA256(String data, String key) throws Exception {
//        Mac hmacSHA256 = Mac.getInstance("HmacSHA256");
//        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), "HmacSHA256");
//        hmacSHA256.init(secretKeySpec);
//        return Base64.getEncoder().encodeToString(hmacSHA256.doFinal(data.getBytes()));
//    }
//}