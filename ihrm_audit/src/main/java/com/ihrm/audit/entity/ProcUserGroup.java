package com.ihrm.audit.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "proc_user_group")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProcUserGroup implements Serializable {
	private static final long serialVersionUID = 15263387728340621L;

	@Id
	private String id; //id

	private String name; //组名

	private String param; //入参

	private String isql; //sql语句
}
