package batch;

import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class FcmMessage {
    private boolean validate_only;
    private Message message;

    @Builder
    @Data
    public static class Message{
        private Notification notification;
        private String token;
    }

    @Builder
    @Data
    public static class Notification{
        private String title;
        private String body;
        private String image;
    }

}
