package com.ihrm.domain.system.response;

import com.ihrm.domain.system.Role;
import com.ihrm.domain.system.User;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class UserResult implements Serializable {
    private static final long serialVersionUID = 5837905578199497307L;

    @Id
    private String id;  //主键

    private String mobile;  //手机号码，不能为空，不能重复

    private String username;  //用户名称，不能为空

    private String password;  //密码

    private Integer enableState;  //启用状态 0为禁用 1为启用

    private Date createTime;  //创建时间

    private String companyId;  //公司id

    private String companyName;  //公司名称

    private String departmentId;  //部门ID

    private String departmentName;  //部门名称

    private Date entryTime;  //入职时间

    private Integer employmentForm;  //聘用形式

    private String workNumber;  //工号

    private String managementForm;  //管理形式

    private String workingCity;  //工作城市

    private Date correctionTime;  //转正时间

    private Integer inServiceStatus;  //在职状态 1.在职 2.离职

    private String staffPhoto; //用户头像

    private List<String> roleIds = new ArrayList<>();

    public UserResult(User user){
        BeanUtils.copyProperties(user,this);
        for (Role role : user.getRoles()){
            this.roleIds.add(role.getId());
        }
    }
}
