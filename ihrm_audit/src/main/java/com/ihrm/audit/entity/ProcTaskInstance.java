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
 * 业务流程任务表
 */
@Entity
@Table(name = "proc_task_instance")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProcTaskInstance implements Serializable {
    private static final long serialVersionUID = 8844783290285797610L;

    @Column(name = "process_id")
    private String processId; //流程实例id

    @Id
    @Column(name = "task_id")
    private String taskId; //任务实例id

    @Column(name = "task_key")
    private String taskKey; //任务节点key

    @Column(name = "task_name")
    private String taskName; //任务节点

    @Column(name = "should_user_id")
    private String shouldUserId; //应审批用户id

    @Column(name = "should_user_name")
    private String shouldUserName; //应审批用户名字

    @Column(name = "handle_user_id")
    private String handleUserId; //实际处理用户id

    @Column(name = "handle_user_name")
    private String handleUserName; //实际处理用户名字

    @Column(name = "handle_time")
    private Date handleTime; //处理时间

    @Column(name = "handle_opinion")
    private String handleOpinion; //处理意见

    @Column(name = "handle_type")
    private String handleType; //处理类型（2审批通过；3审批不通过；4撤销）
}
