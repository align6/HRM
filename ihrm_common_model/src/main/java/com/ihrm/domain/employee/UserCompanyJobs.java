package com.ihrm.domain.employee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 员工岗位信息表
 */
@Entity
@Table(name = "em_user_company_jobs")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCompanyJobs implements Serializable {
    private static final long serialVersionUID = 6827114120579633848L;

    @Id
    private String userId;  //员工id

    private String companyId;  //公司id

    private String post;  //岗位

    private String workMailbox;  //工作邮箱

    private String correctionEvaluation;  //转正评价

    private String reportId;  //汇报对象id

    private String reportName;  //汇报对象姓名

    private String correctionState;  //转正状态

    private String workingCity;  //工作城市

    private String currentContractStartTime;  //现合同开始时间

    private String currentContractClosingTime;  //现合同结束时间

    private String initialContractStartTime;  //首次合同开始时间

    private String initialContractClosingTime;  //首次合同结束时间

    private String contractPeriod;  //合同期限

    private String contractDocuments;  //合同文件

    private Integer renewalNumber;  //续签次数

    private String recruitmentChannels;  //招聘渠道

    private String recommenderBusinessPeople;  //推荐企业人
}
