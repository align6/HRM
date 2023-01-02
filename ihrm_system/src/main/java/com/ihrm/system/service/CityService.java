package com.ihrm.system.service;

import com.ihrm.common.utils.IdWorker;
import com.ihrm.domain.system.City;
import com.ihrm.system.dao.CityDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CityService {
    @Autowired
    private CityDao cityDao;

    @Autowired
    private IdWorker idWorker;

    /**
     * 保存城市
     */
    public void add(City city){
        String id = idWorker.nextId()+"";
        city.setId(id);
        cityDao.save(city);
    }

    /**
     * 更新城市
     */
    public void update(City city){
        City temp = cityDao.findById(city.getId()).get();
        temp.setName(city.getName());
        temp.setCreateTime(new Date());
        cityDao.save(temp);
    }

    /**
     * 根据id删除城市
     */
    public void deleteById(String id){
        cityDao.deleteById(id);
    }

    /**
     * 根据id查询城市
     */
    public City findById(String id){
        return cityDao.findById(id).get();
    }

    /**
     * 查询所有城市
     */
    public List<City> findAll(){
        return cityDao.findAll();
    }
}
