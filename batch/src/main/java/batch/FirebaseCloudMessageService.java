package batch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
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

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");


    @Scheduled
    public ResponseEntity<String> sendMessageTo(String FCMToken, String UserToken, String title, String body, String path) throws Exception {

        // create headers
        HttpHeaders headers = new HttpHeaders();
        // set `content-type` header
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
        // set `accept` header
        headers.setAccept(Collections.singletonList(org.springframework.http.MediaType.APPLICATION_JSON));
        headers.set("Authorization", "key=AAAAbFXwImY:APA91bF2D6vJ9k4qi-TLbZKe8o6cUhSdJkIDl2puals343FpMd5WlQK6d926UW0K4vbWsMrXjvusbTc0el6W3PWT2Hw_qYJrvK-iy6zfFCigO5l76ygLL3yAmYMzajazycT_u9j1qS1S");

        AlaramBatchDataDTO alaramBatchDataDTO = new AlaramBatchDataDTO("junshock5 링크 줍줍 테스트 중입니다.", "junshock5 푸시가 잘 갔나요?");

        AlaramBatchDTO alaramBatchDTO = AlaramBatchDTO.builder()
                .to(FCMToken
                ).data(alaramBatchDataDTO).build();


        HttpEntity<AlaramBatchDTO> requestEntity = new HttpEntity<>(alaramBatchDTO, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(API_URL, requestEntity, String.class);

        return response;
    }

}
