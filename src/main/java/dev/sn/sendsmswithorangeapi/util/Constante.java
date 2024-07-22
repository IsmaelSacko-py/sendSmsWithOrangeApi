package dev.sn.sendsmswithorangeapi.util;


import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@NoArgsConstructor
@Getter
public class Constante {
    @Value("${devPhoneNumber}")
    private String devPhoneNumber;

    @Value("${recipientPhoneNumber}")
    private String recipientPhoneNumber;

    @Value("${authorizationHeaders}")
    private String authorizationHeaders;

    @Value("${tokenUrl}")
    private String tokenUrl;

    @Value("${sendSmsUrl}")
    private String sendSmsUrl;

    @Value("${senderName}")
    private String senderName;
}
