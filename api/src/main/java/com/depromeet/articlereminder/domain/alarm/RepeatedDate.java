package com.depromeet.articlereminder.domain.alarm;

import io.swagger.annotations.ApiModel;

@ApiModel
public enum RepeatedDate {
    EVERYDAY,
    EVERYDAY_EXCEPT_HOLIDAYS,
    WEEKDAYS,
    WEEKDAYS_EXCEPT_HOLIDAYS,
    WEEKENDS,
    WEEKENDS_EXCEPT_HOLIDAYS,
    TEST
}
