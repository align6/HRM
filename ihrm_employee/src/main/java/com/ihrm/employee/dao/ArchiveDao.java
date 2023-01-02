package com.ihrm.employee.dao;

import com.ihrm.domain.employee.EmployeeArchive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArchiveDao extends JpaRepository<EmployeeArchive,String>, JpaSpecificationExecutor<EmployeeArchive> {
    @Query(value = "select * from em_archive where company_id=?1 and month=?2 order by create_time desc LIMIT 1;", nativeQuery = true)
    EmployeeArchive findByLast(String companyId, String month);

    @Query(value = "select * from em_archive where company_id = ?1 and month LIKE ?2 group by month having max (create_time) limit ?3,?4;", nativeQuery = true)
    List<EmployeeArchive> findAllData(String companyId, String year, Integer index, Integer size);

    @Query(value = "select count(DISTINCT month) from em_archive where company_id=?1 and month like ?2;", nativeQuery = true)
    long countAllData(String companyId, String year);
}
