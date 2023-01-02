package com.ihrm.domain.employee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 员工详细信息表
 */
@Entity
@Table(name = "em_user_company_personal")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCompanyPersonal implements Serializable {
    private static final long serialVersionUID = 6101334013744860858L;

    @Id
    private String userId; //员工id

    private String username;  //员工姓名

    private String mobile;  //电话号码

    private String entryTime;  //入职时间

    private String departmentName;  //部门名称

    private String companyId;  //企业id

    private String sex;  //性别

    private String dateOfBirth;  //出生日期

    private String nationalArea;  //国家地区

    private String idNumber;  //身份证号

    private String nativePlace;  //籍贯

    private String nation;  //民族

    private String maritalStatus;  //婚姻状况

    private String politicalOutlook;  //政治面貌

    private String placeOfResidence;  //现居住地

    private String personalMailbox;  //个人邮箱

    private String emergencyContact;  //紧急联系人

    private String emergencyContactNumber;  //紧急联系电话

    private String remarks;  //备注
}
