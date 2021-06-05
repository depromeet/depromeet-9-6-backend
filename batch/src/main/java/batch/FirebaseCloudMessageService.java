package batch;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.json.Json;
import com.google.auth.oauth2.GoogleCredentials;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.json.simple.JSONObject;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FirebaseCloudMessageService {
    private final String API_URL = "https://fcm.googleapis.com/fcm/send";

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");


    private final ObjectMapper objectMapper;

    public Response sendMessageTo(String targetToken, String title, String body, String path) throws Exception {
//        String message = makeMessage(targetToken, title, body, path);
//
//        RequestBody requestBody = RequestBody.create(message, MediaType.get("application/json"));

        OkHttpClient client = new OkHttpClient();
        //RequestBody tempbody = RequestBody.Companion.create(body,JSON);
        RequestBody tempbody = RequestBody.create(
                MediaType.parse("application/json"), body);
        //RequestBody body = RequestBody.create(JSON, json);
        // Json json = (JSONObject) parser.parse(stringToParse);

        Request request = new Request.Builder()
                .addHeader("Authorization","key=AAAAbFXwImY:APA91bF2D6vJ9k4qi-TLbZKe8o6cUhSdJkIDl2puals343FpMd5WlQK6d926UW0K4vbWsMrXjvusbTc0el6W3PWT2Hw_qYJrvK-iy6zfFCigO5l76ygLL3yAmYMzajazycT_u9j1qS1S")
                .addHeader("Content-Type","application/json; charset=utf-8")
                .url(API_URL)
                .post(tempbody)
                //.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                .build();

        Response response  = client.newCall(request).execute();

        // log.info(response.body().string());
        log.info(response.toString());

        return response;
    }

    private String makeMessage(String targetToken, String title, String body, String path) throws JsonProcessingException {
        FcmMessage fcmMessage = FcmMessage.builder()
                .message(FcmMessage.Message.builder()
                        .token(targetToken)
                        .notification(FcmMessage.Notification.builder()
                        .title(title)
                                .body(body)
                        .image(null)
                        .build())
                        .build()
                )
                .validate_only(false)
                .build();
        log.info(objectMapper.writeValueAsString(fcmMessage));
        return objectMapper.writeValueAsString(fcmMessage);
    }

    private String getAccessToken() throws Exception {
        // 2)
        String firebaseConfigPath = "../resources/linkzupzup-firebase-adminsdk-cdcfv-3ab66a888d.json";

        GoogleCredentials googleCredentials = GoogleCredentials.fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())
                .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));

        // accessToken 생성
        googleCredentials.refreshIfExpired();

        // GoogleCredential의 getAccessToken으로 토큰 받아온 뒤, getTokenValue로 최종적으로 받음
        // REST API로 FCM에 push 요청 보낼 때 Header에 설정하여 인증을 위해 사용
        return googleCredentials.getAccessToken().getTokenValue();
    }
}
