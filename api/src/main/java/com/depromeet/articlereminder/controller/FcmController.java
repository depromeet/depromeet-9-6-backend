package com.depromeet.articlereminder.controller;

import com.depromeet.articlereminder.service.FirebaseCloudMessageService;
import com.depromeet.articlereminder.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@CrossOrigin(origins = {"*"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/fcm")
public class FcmController {

    private final FirebaseCloudMessageService firebaseCloudMessageService;
    private final MemberService memberService;


    @Scheduled()
    public ResponseEntity<String> pushFcmMessage() {

        ResponseEntity<String> response = firebaseCloudMessageService.sendMessageTo();

        return response;
    }
}
