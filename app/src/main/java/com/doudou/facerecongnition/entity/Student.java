package com.doudou.facerecongnition.entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Student {
    private int id;
    private int number;
    private String name;
    private String imagePath;
    private String face_token;
}
