package com.ihrm.atte.dao;

import com.ihrm.domain.attendance.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface AttendanceDao extends JpaRepository<Attendance,String>, JpaSpecificationExecutor<Attendance> {
    Attendance findByUserIdAndDay(String id,String atteDate);

    @Query(nativeQuery = true,
            value="SELECT COUNT(*) at0," +
                    "       COUNT(CASE WHEN adt_status=1 THEN 1 END) at1," +
                    "       COUNT(CASE WHEN adt_status=2 THEN 1 END) at2," +
                    "       COUNT(CASE WHEN adt_status=3 THEN 1 END) at3," +
                    "       COUNT(CASE WHEN adt_status=4 THEN 1 END) at4," +
                    "       COUNT(CASE WHEN adt_status=8 THEN 1 END) at8," +
                    "       COUNT(CASE WHEN adt_status=17 THEN 1 END) at17" +
                    "       FROM atte_attendance WHERE  user_id=?1 AND DAY LIKE ?2"
    )
    Map statisticByUser(String id, String atteDate);
}
