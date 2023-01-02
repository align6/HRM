package com.ihrm.common.service;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

public class BaseService<T> {

    /**
     * 用户构造查询条件
     * 1.只查询companyId
     * 2.需要根据companyId查询
     * 3.对象中包含companyId查询
     */
    protected Specification<T> getSpec(String companyId){
        Specification spec = new Specification() {
            /**
             * root：包含所有的对象数据
             * criteriaQuery：一般不用
             * criteriaBuilder：构造查询条件
             */
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                return cb.equal(root.get("companyId").as(String.class), companyId);
            }
        };
        return spec;
    }
}
