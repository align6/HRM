package com.ihrm.domain.system;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 权限--api
 */
@Entity
@Table(name = "pe_permission_api")
@Getter
@Setter
public class PermissionApi implements Serializable {
    private static final long serialVersionUID = 1874129237877089053L;

    @Id
    private String id;  //主键

    private String apiUrl;  //链接

    private String apiMethod;  //请求类型

    private String apiLevel;  //权限等级：1为通用接口权限，2为需校验接口权限
}