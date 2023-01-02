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
 * 员工调岗申请表
 */
@Entity
@Table(name = "em_transfer_position")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeTransferPosition implements Serializable {
    private static final long serialVersionUID = -8219874409969363525L;

    @Id
    private String userId;  //员工id

    private String post;  //岗位

    private String erank;  //职级

    private String reportingObject;  //汇报对象

    private String hrbp;  //人力资源业务合作伙伴

    private Date adjustmentTime;  //调岗时间

    private String adjustmentCause;  //调岗原因

    private String enclosure;  //附件

    private String managementForm;  //管理形式

    private String workingCity;  //工作城市

    private String currentContractStartTime;  //现合同开始时间

    private String currentContractClosingTime;  //现合同结束时间

    private String workingPlace;  //工作地点

    private String initialContractStartTime;  //首次合同开始时间

    private String initialContractClosingTime;  //首次合同结束时间

    private String contractPeriod;  //合同期限

    private Integer renewalNumber;  //续签次数

    private String recommenderBusinessPeople;  //推荐企业/人

    private Integer estatus;  //单据状态 1-未执行 2-已执行

    private Date createTime;  //创建时间
}
