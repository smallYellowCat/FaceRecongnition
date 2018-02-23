package com.doudou.facerecongnition.entity;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class User {

    private int id;
    private String authId;
    private String name;
    private String image_url;
    private byte[] imageData;

}
