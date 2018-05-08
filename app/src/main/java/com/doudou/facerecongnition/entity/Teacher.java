package com.doudou.facerecongnition.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class Teacher implements Serializable {
    private Integer id;
    private String account;
    private String password;
    private String name;
    private String phoneNumber;
    private String inviteCode;
    private String imagePath;
    private String imageName;
}
