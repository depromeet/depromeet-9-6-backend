package com.depromeet.articlereminder.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Entity
@Setter
@Getter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;
    private String email;
    private String name;
    private String token;
    private Date tokenStartTime;
    private Date tokenExpiredTime;

}
