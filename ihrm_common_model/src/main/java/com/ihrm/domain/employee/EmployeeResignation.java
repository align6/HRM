package com.ihrm.domain.employee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 离职申请表
 */
@Entity
@Table(name = "em_resignation")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeResignation implements Serializable {
    private static final long serialVersionUID = -1586264513739336830L;

    @Id
    private String userId;  //员工id

    private String resignationTime;  //离职时间

    private String turnoverType;  //离职类型

    private String leavingReasons;  //离职原因

    /**
     * 补偿金是对职工工龄的一种补偿，只要符合法定情形，单位就应该想向职工发放
     */
    private String compensation;  //补偿金

    /**
     *    代通知金在性质上属于一种赔偿责任，以单位有过错为前提，其过错的具体表现
     * 形式是没有按法定要求提前通知，责任的范围是职工的工资损失
     */
    private String notifications;  //代通知金

    /**
     *     当一名员工从一家企业离职，他的社保信息需要从这家公司删减，当员工进入
     * 下一家公司时才能正常办理增员，才可以继续购买社保
     */
    private String socialSecurityReductionMonth;  //社保减员月

    private String providentFundReductionMonth;  //公积金减员月

    private String picture;  //图片

    private Date createTime;  //创建时间
}
