package com.ihrm.domain.attendance.vo;

import com.ihrm.domain.attendance.entity.Attendance;
import lombok.Data;

import java.util.List;

@Data
public class AtteItemVo {

    private String id; //id

    private String username; //用户名

    private String workNumber; //工号

    private String departmentId; //部门id

    private String departmentName; //部门名称

    private String mobile; //手机

    private List<Attendance> attendanceRecord; //考勤记录
}
