package com.ihrm.domain.system;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "bs_city")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class City implements Serializable {
    private static final long serialVersionUID = -8930791113685752015L;

    @Id
    private String id; //城市id

    private String name; //城市名称

    private Date createTime; //创建时间
}
