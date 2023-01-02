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
 * 转正申请表
 */
@Entity
@Table(name = "em_positive")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeePositive implements Serializable {
    private static final long serialVersionUID = 4909642702778219747L;

    @Id
    private String userId;  //员工id

    private Date correctionDate;  //转正日期

    private String correctionEvaluation;  //转正评价

    private String enclosure;  //附件

    private Integer status;  //单据状态 1-未执行 2-已执行

    private Date createTime;  //创建时间
}
