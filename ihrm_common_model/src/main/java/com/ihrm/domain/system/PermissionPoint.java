package com.ihrm.domain.system;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 权限--按钮
 */
@Entity
@Table(name = "pe_permission_point")
@Getter
@Setter
public class PermissionPoint implements Serializable {
    private static final long serialVersionUID = 4127687400208626100L;

    @Id
    private String id;  //主键

    private String pointClass; //权限类型

    private String pointIcon;  //按钮图标

    private String pointStatus; //按钮状态

}