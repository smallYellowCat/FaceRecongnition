package com.doudou.facerecongnition.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class Course {
    private int id;
    private String courseName;
    private int belongTeacherId;
    private Date beginTime;
    private Date endTime;
}
