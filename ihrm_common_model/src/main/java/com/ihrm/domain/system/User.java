package com.ihrm.domain.system;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ihrm.domain.poi.ExcelAttribute;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 用户实体类
 */
@Entity
@Table(name = "bs_user")
@Data
@NoArgsConstructor
public class User implements Serializable {
    private static final long serialVersionUID = -594907985119381075L;

    @Id
    private String id;  //主键

    @ExcelAttribute(sort = 2)
    private String mobile;  //手机号码，不能为空，不能重复

    @ExcelAttribute(sort = 1)
    private String username;  //用户名称，不能为空

    private String password;  //密码

    private Integer enableState;  //启用状态 0为禁用 1为启用

    private Date createTime;  //创建时间

    private String companyId;  //公司id

    private String companyName;  //公司名称

    @ExcelAttribute(sort = 6)
    private String departmentId;  //部门ID

    private String departmentName;  //部门名称

    @ExcelAttribute(sort = 5)
    private Date entryTime;  //入职时间

    @ExcelAttribute(sort = 4)
    private Integer employmentForm;  //聘用形式

    @ExcelAttribute(sort = 3)
    private String workNumber;  //工号

    private String managementForm;  //管理形式

    private String workingCity;  //工作城市

    private Date correctionTime;  //转正时间

    private Integer inServiceStatus;  //在职状态 1.在职 2.离职

    /**
     * level:String
     *  saasAdmin:saas管理员具备所有权限
     *  coAdmin:企业管理（创建租户企业时添加）
     *  user:普通用户（需要分配角色）
     */
    private String level;  //用户级别：saas管理员 公司管理员 公司用户

    private String staffPhoto;  //个人头像

    public User(Object[] values){
        this.username = values[1].toString();
        this.mobile = values[2].toString();
        this.workNumber = new DecimalFormat("#").format(values[3]).toString();
        this.employmentForm = ((Double) values[4]).intValue();
        this.entryTime = (Date) values[5];
        this.departmentId = values[6].toString(); //部门编码，暂时使用部门id代替
    }

    /**
     * JsonIgnore：忽略json转换，避免对role的属性进行转换，而role又对user的属性进行转换，造成死循环
     */
    @ManyToMany
    @JsonIgnore
    @JoinTable(name="pe_user_role",joinColumns={@JoinColumn(name="user_id",referencedColumnName="id")},
            inverseJoinColumns={@JoinColumn(name="role_id",referencedColumnName="id")}
    )
    private Set<Role> roles = new HashSet<Role>();//用户与角色 多对多

}
