package com.depromeet.articlereminder.dto;

import lombok.Data;

import java.util.List;

@Data
public class AlarmDTO {
    private String notifyTime; // 알람 시각
    private String repeatedDate; // 반복 요일
    private boolean isEnabled; // 활성화 여부
}
