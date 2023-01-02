package com.ihrm.domain.system;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "pe_permission")
@Getter
@Setter
@NoArgsConstructor
@DynamicInsert(true)
@DynamicUpdate(true)
public class Permission implements Serializable {
    private static final long serialVersionUID = -2670700006353645395L;

    @Id
    private String id;  //主键

    private String name; //权限名称

    private Integer type;  //权限类型 1为菜单 2为功能 3为API

    private String code;  //权限标识，判断是否要进行拦截

    private String description;  //权限描述

    /**
     * 一个菜单--多个按钮
     * 一个菜单--多个api
     * 一个按钮--多个api
     * 即存在这种从属关系
     */
    private String parentId;  //父id

    private String enVisible;  //是否查询所有权限 0：查询全部 1：只查询企业所属权限

    public Permission(String name, Integer type, String code, String description) {
        this.name = name;
        this.type = type;
        this.code = code;
        this.description = description;
    }


}