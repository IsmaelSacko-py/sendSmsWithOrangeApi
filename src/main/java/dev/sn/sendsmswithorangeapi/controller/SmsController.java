package dev.sn.sendsmswithorangeapi.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import dev.sn.sendsmswithorangeapi.util.Constante;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@RestController
public class SmsController
{
    private final RestTemplate restTemplate = new RestTemplate();
    private final Constante constante = new Constante();

    public String getToken(){

        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(constante.getAuthorizationHeaders());
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(constante.getTokenUrl(), request, String.class);
        String responseBody = responseEntity.getBody();

        try{
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(responseBody);
            //model.addAttribute("response", response.getBody());
            return root.get("access_token").asText();
        }catch (Exception e){
            return null;
        }
    }

    @PostMapping("/sms")
    public ResponseEntity<String> sendSms(){
        final String token = getToken();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request;

        try{
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode root = mapper.createObjectNode();
            root.withObject("outboundSMSMessageRequest").put("address", "tel:+"+constante.getDevPhoneNumber());
            root.withObject("outboundSMSMessageRequest").put("senderAddress", "tel:+"+constante.getDevPhoneNumber());
            root.withObject("outboundSMSMessageRequest").withObject("outboundSMSTextMessage").put("message", "Hello!");

            request = new HttpEntity<>(root.toString(), headers);
        }catch (Exception e){
            return null;
        }

        return restTemplate.postForEntity(constante.getSendSmsUrl(), request, String.class);

    }
}
