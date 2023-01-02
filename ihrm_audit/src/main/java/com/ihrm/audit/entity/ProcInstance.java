package com.ihrm.audit.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 业务流程实例表
 */
@Entity
@Table(name = "proc_instance")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProcInstance implements Serializable {
    private static final long serialVersionUID = 1796419966792510258L;

    @Id
    @Column(name = "process_id")
    private String processId; //流程实例id

    @Column(name = "process_definition_id")
    private String processDefinitionId; //流程定义id

    @Column(name = "process_key")
    private String processKey; //流程标识

    @Column(name = "process_name")
    private String processName; //流程名称

    @Column(name = "process_state")
    private String processState; //流程状态（1审批中；2审批通过；3审批不通过；4撤销)

    @Column(name = "user_id")
    private String userId; //申请人id

    @Column(name = "username")
    private String username; //申请人名称

    @Column(name = "proc_curr_node_user_id")
    private String procCurrNodeUserId; //当前节点审批人

    @Column(name = "proc_curr_node_user_name")
    private String procCurrNodeUserName; //当前节点审批人名称

    @Column(name = "proc_apply_time")
    private Date procApplyTime; //申请时间

    @Column(name = "proc_end_time")
    private Date procEndTime; //结束流程时间

    @Column(name = "proc_data")
    private String procData; //申请的业务数据

    @Column(name = "department_id")
    private String departmentId; //部门id

    @Column(name = "department_name")
    private String departmentName; //部门名称

    @Column(name = "entry_time")
    private Date entryTime; //入职时间
}
