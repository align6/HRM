package com.ihrm.domain.attendance.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "atte_leave_config")
@Data
@NoArgsConstructor
@AllArgsConstructor
/**
 * 请假配置表
 */
public class LeaveConfig implements Serializable {
    private static final long serialVersionUID = -8375926696659654197L;

    @Id
    private String id; //id

    private String companyId; //公司id

    private String departmentId; //部门id

    private String leaveType; //请假类型

    private Integer isEnable; //是否可用 0开启 1 关闭
}
