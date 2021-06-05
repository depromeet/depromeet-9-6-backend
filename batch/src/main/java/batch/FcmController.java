package batch;

import lombok.RequiredArgsConstructor;
import okhttp3.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


@CrossOrigin(origins = {"*"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/fcm")
public class FcmController {

    private final FirebaseCloudMessageService firebaseCloudMessageService;

    @GetMapping("token")
    public Response getFcmToken() throws Exception {
        // JSONObject->RequestBody
        JSONObject obj = new JSONObject();
        JSONArray arrTemp = new JSONArray();

        obj.put("to", "cC-X_trqT6KVXiUiiU9hOq:APA91bFVZThAG8Ps5pM8VghO2jjVDYSqVy2OJcdbW3Rclu0bvHNEZC2_4sBHhNEeuOXN-l5cLxBYN2A-bRELhSO8i72e6O8yEXGD2N5O9n5i1h2ybnnOdfzRU2xtVAUieWMnU27NcB4x");

        JSONObject obj2 = new JSONObject();
        obj2.put("title", "junshock5 링크 줍줍 테스트 중입니다.");
        arrTemp.add(obj2);
        obj2 = new JSONObject();
        obj2.put("body", "junshock5 푸시가 잘 갔나요?");
        arrTemp.add(obj2);
        obj.put("data", arrTemp);

        System.out.println(obj.toString());

//
//        AlaramDTO alaramDTO = AlaramDTO.builder()
//                .to("cC-X_trqT6KVXiUiiU9hOq:APA91bFVZThAG8Ps5pM8VghO2jjVDYSqVy2OJcdbW3Rclu0bvHNEZC2_4sBHhNEeuOXN-l5cLxBYN2A-bRELhSO8i72e6O8yEXGD2N5O9n5i1h2ybnnOdfzRU2xtVAUieWMnU27NcB4x")
//                .data(arrTemp)
//                .build();


        Response response = firebaseCloudMessageService.sendMessageTo("cC-X_trqT6KVXiUiiU9hOq:APA91bFVZThAG8Ps5pM8VghO2jjVDYSqVy2OJcdbW3Rclu0bvHNEZC2_4sBHhNEeuOXN-l5cLxBYN2A-bRELhSO8i72e6O8yEXGD2N5O9n5i1h2ybnnOdfzRU2xtVAUieWMnU27NcB4x"
                , "testTitle", obj.toString(), "path");
        return response;
    }
    @GetMapping("token2")
    public ResponseEntity<String> getFcmToken2() throws Exception {
        String url =  "https://fcm.googleapis.com/fcm/send";

        // create headers
        HttpHeaders headers = new HttpHeaders();
        // set `content-type` header
        headers.setContentType(MediaType.APPLICATION_JSON);
        // set `accept` header
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Authorization", "key=AAAAbFXwImY:APA91bF2D6vJ9k4qi-TLbZKe8o6cUhSdJkIDl2puals343FpMd5WlQK6d926UW0K4vbWsMrXjvusbTc0el6W3PWT2Hw_qYJrvK-iy6zfFCigO5l76ygLL3yAmYMzajazycT_u9j1qS1S");

        // create a map for post parameters
        Map<String, Object> map = new HashMap<>();


        JSONObject obj = new JSONObject();
        JSONArray arrTemp = new JSONArray();
        obj.put("to", "cC-X_trqT6KVXiUiiU9hOq:APA91bFVZThAG8Ps5pM8VghO2jjVDYSqVy2OJcdbW3Rclu0bvHNEZC2_4sBHhNEeuOXN-l5cLxBYN2A-bRELhSO8i72e6O8yEXGD2N5O9n5i1h2ybnnOdfzRU2xtVAUieWMnU27NcB4x");
        JSONObject obj2 = new JSONObject();
        obj2.put("title", "junshock5 링크 줍줍 테스트 중입니다.");
        arrTemp.add(obj2);
        obj2 = new JSONObject();
        obj2.put("body", "junshock5 푸시가 잘 갔나요?");
        arrTemp.add(obj2);
        obj.put("data", arrTemp);

        map.put("to", "cC-X_trqT6KVXiUiiU9hOq:APA91bFVZThAG8Ps5pM8VghO2jjVDYSqVy2OJcdbW3Rclu0bvHNEZC2_4sBHhNEeuOXN-l5cLxBYN2A-bRELhSO8i72e6O8yEXGD2N5O9n5i1h2ybnnOdfzRU2xtVAUieWMnU27NcB4x");
        map.put("data", arrTemp.toJSONString());

        // build the request
        HttpEntity<Map<String, Object>>  request = new HttpEntity<>(obj, headers);
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> result = restTemplate.postForEntity(url, map, String.class);
//
//        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
//        parameters.add("to",  "cC-X_trqT6KVXiUiiU9hOq:APA91bFVZThAG8Ps5pM8VghO2jjVDYSqVy2OJcdbW3Rclu0bvHNEZC2_4sBHhNEeuOXN-l5cLxBYN2A-bRELhSO8i72e6O8yEXGD2N5O9n5i1h2ybnnOdfzRU2xtVAUieWMnU27NcB4x");
//        parameters.add("data", arrTemp.toString());
//        ResponseEntity<String> res = new RestTemplate().postForEntity(url, parameters, String.class);

        return null;
    }
}
