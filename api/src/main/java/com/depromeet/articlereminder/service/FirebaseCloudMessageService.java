package com.depromeet.articlereminder.service;

import com.depromeet.articlereminder.dto.batch.AlaramBatchDTO;
import com.depromeet.articlereminder.dto.batch.AlaramBatchDataDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
@Slf4j
@RequiredArgsConstructor
public class FirebaseCloudMessageService {
    private final String API_URL = "https://fcm.googleapis.com/fcm/send";
    private final String FCM_TOKEN = "AAAAbFXwImY:APA91bF2D6vJ9k4qi-TLbZKe8o6cUhSdJkIDl2puals343FpMd5WlQK6d926UW0K4vbWsMrXjvusbTc0el6W3PWT2Hw_qYJrvK-iy6zfFCigO5l76ygLL3yAmYMzajazycT_u9j1qS1S";
    public ResponseEntity<String> sendMessageTo(String FCMToken, String title, String body) throws Exception {

        // create headers
        HttpHeaders headers = new HttpHeaders();
        // set `content-type` header
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
        // set `accept` header
        headers.setAccept(Collections.singletonList(org.springframework.http.MediaType.APPLICATION_JSON));
        headers.set("Authorization", "key="+FCM_TOKEN);

        AlaramBatchDataDTO alaramBatchDataDTO = new AlaramBatchDataDTO(title, body);

        AlaramBatchDTO alaramBatchDTO = AlaramBatchDTO.builder()
                .to(FCMToken
                ).data(alaramBatchDataDTO).build();


        HttpEntity<AlaramBatchDTO> requestEntity = new HttpEntity<>(alaramBatchDTO, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response;
        try{
           response = restTemplate.postForEntity(API_URL, requestEntity, String.class);

        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }

        return response;
    }

}
