package com.doudou.facerecongnition.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Sign {
    private int id;
    private int studentId;
    private int studentNumber;
    private String studentName;
    private int courseId;
    private Date signTime;
    private int status;
}
