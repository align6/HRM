package com.ihrm.domain.attendance.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "atte_archive_monthly")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArchiveMonthly implements Serializable {
    private static final long serialVersionUID = 2703295487231599889L;

    @Id
    private String id; //id

    private String companyId;  //公司id

    private String archiveYear; //归档年份

    private String archiveMonth; //归档月份

    private Integer totalPeopleNum; //总人数

    private Integer fullAttePeopleNum; //全勤人数
}
