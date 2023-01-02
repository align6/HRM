package com.ihrm.domain.company;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "co_department")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Department implements Serializable {
    private static final long serialVersionUID = 2767873005893305311L;
    @Id
    private String id;//id

    private String parentId;//父级id

    private String companyId;//企业id

    private String departmentName;//部门名称

    private String departmentCode;//部门编码

    private String managerId;//负责人id；

    private String managerName;//负责人名字

    private String introduce;//介绍

    private Date createTime;//创建时间
}
