package batch;

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;


@CrossOrigin(origins = {"*"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/fcm")
public class FcmController {

    private final FirebaseCloudMessageService firebaseCloudMessageService;


    @Scheduled()
    public ResponseEntity<String> getFcmToken() {
        String url =  "https://fcm.googleapis.com/fcm/send";

        // create headers
        HttpHeaders headers = new HttpHeaders();
        // set `content-type` header
        headers.setContentType(MediaType.APPLICATION_JSON);
        // set `accept` header
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Authorization", "key=AAAAbFXwImY:APA91bF2D6vJ9k4qi-TLbZKe8o6cUhSdJkIDl2puals343FpMd5WlQK6d926UW0K4vbWsMrXjvusbTc0el6W3PWT2Hw_qYJrvK-iy6zfFCigO5l76ygLL3yAmYMzajazycT_u9j1qS1S");

        AlaramBatchDataDTO alaramBatchDataDTO = new AlaramBatchDataDTO("junshock5 링크 줍줍 테스트 중입니다.","junshock5 푸시가 잘 갔나요?");

        AlaramBatchDTO alaramBatchDTO = AlaramBatchDTO.builder()
                .to("cC-X_trqT6KVXiUiiU9hOq:APA91bFVZThAG8Ps5pM8VghO2jjVDYSqVy2OJcdbW3Rclu0bvHNEZC2_4sBHhNEeuOXN-l5cLxBYN2A-bRELhSO8i72e6O8yEXGD2N5O9n5i1h2ybnnOdfzRU2xtVAUieWMnU27NcB4x"
        ).data(alaramBatchDataDTO).build();


        HttpEntity<AlaramBatchDTO> requestEntity = new HttpEntity<>(alaramBatchDTO, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> result = restTemplate.postForEntity(url, requestEntity, String.class);

        return result;
    }
}
