package com.ihrm.domain.employee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 月度员工归档表
 */
@Entity
@Table(name = "em_archive")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeArchive implements Serializable {
    private static final long serialVersionUID = -3593071336082822268L;

    @Id
    private String id;  //主键id

    private String opUser;  //操作用户

    private String month;  //月份

    private String companyId;  //企业id

    private Integer totals;  //总人数

    private Integer payrolls; //在职人数

    private Integer departures;  //离职人数

    private String data;  //数据

    private Date createTime;  //创建时间
}
