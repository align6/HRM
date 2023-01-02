package com.ihrm.domain.employee.response;

import com.ihrm.domain.employee.EmployeeResignation;
import com.ihrm.domain.employee.UserCompanyPersonal;
import com.ihrm.domain.poi.ExcelAttribute;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class EmployeeReportResult {

    @ExcelAttribute(sort = 0)
    private String userId; //员工id

    @ExcelAttribute(sort = 1)
    private String username;  //员工姓名

    @ExcelAttribute(sort = 2)
    private String mobile;  //电话号码

    @ExcelAttribute(sort = 5)
    private String entryTime;  //入职时间

    @ExcelAttribute(sort = 4)
    private String departmentName;  //部门名称

    private String companyId;  //企业id

    private String sex;  //性别

    private String dateOfBirth;  //出生日期

    @ExcelAttribute(sort = 3)
    private String nationalArea;  //国家地区

    private String idNumber;  //身份证号

    private String idCardPhotoPositive;  //身份证照片-正面

    private String idCardPhotoBack;  //身份证照片-背面

    private String nativePlace;  //籍贯

    private String nation;  //民族

    private String maritalStatus;  //婚姻状况

    private String politicalOutlook;  //政治面貌

    private String placeOfResidence;  //现居住地

    private String personalMailbox;  //个人邮箱

    private String emergencyContact;  //紧急联系人

    private String emergencyContactNumber;  //紧急联系电话

    private String resume;  //简历

    private String remarks;  //备注

    @ExcelAttribute(sort = 8)
    private String resignationTime;  //离职时间

    @ExcelAttribute(sort = 6)
    private String turnoverType;  //离职类型

    @ExcelAttribute(sort = 7)
    private String leavingReasons;  //离职原因

    public EmployeeReportResult(UserCompanyPersonal personal, EmployeeResignation resignation){
        BeanUtils.copyProperties(personal,this);
        if (resignation!=null){
            BeanUtils.copyProperties(resignation,this);
        }
    }
}
