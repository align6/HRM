package com.ihrm.employee.service;

import com.ihrm.common.service.BaseService;
import com.ihrm.common.utils.IdWorker;
import com.ihrm.domain.employee.EmployeeArchive;
import com.ihrm.employee.dao.ArchiveDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ArchiveService extends BaseService {

    @Autowired
    private ArchiveDao archiveDao;

    @Autowired
    private IdWorker idWorker;

    /**
     * 保存归档表
     */
    public void save(EmployeeArchive archive){
        String id = idWorker.nextId()+"";
        archive.setId(id);
        archive.setCreateTime(new Date());
        archiveDao.save(archive);
    }

    /**
     * 返回最新的归档表
     */
    public EmployeeArchive findLast(String companyId, String month){
        EmployeeArchive archive = archiveDao.findByLast(companyId,month);
        return archive;
    }

    /**
     * 分页返回某公司某年的全部归档表
     */
    public List<EmployeeArchive> findAll(String companyId, String year, Integer page, Integer size){
        Integer index = (page-1) * size;
        return archiveDao.findAllData(companyId, year+"%", index, size);
    }

    /**
     * 返回某公司某月的归档表数量
     */
    public Long countAll(String companyId, String year){
        return archiveDao.countAllData(companyId, year+"%");
    }

    /**
     * 构造动态条件查询
     * 可以通过companyId或者year去查询
     * 通过动态查询后分页返回归档表
     */
    public Page<EmployeeArchive> findSearch(Map<String,Object> map, int page, int size){

        Specification<EmployeeArchive> spec = new Specification<EmployeeArchive>() {
            @Override
            public Predicate toPredicate(Root<EmployeeArchive> root, CriteriaQuery<?> queryuery, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<>();
                //企业id
                if (!StringUtils.isEmpty(map.get("companyId"))){
                    predicateList.add(cb.like(root.get("companyId").as(String.class), (String) map.get("companyId")));
                }
                //年份
                if (!StringUtils.isEmpty(map.get("year"))){
                    predicateList.add(cb.like(root.get("year").as(String.class), (String) map.get("year")));
                }
                return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));
            }
        };
        return archiveDao.findAll(spec, PageRequest.of(page-1,size));
    }
}
