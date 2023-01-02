package com.ihrm.system.controller;

import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.domain.system.City;
import com.ihrm.system.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/system/city")
public class CityController {
    @Autowired
    private CityService cityService;

    /**
     * 保存城市
     */
    @PostMapping("")
    public Result save(@RequestBody City city){
        cityService.add(city);
        return new Result(ResultCode.SUCCESS);
    }

    @PutMapping("/{id}")
    public Result update(@PathVariable String id,@RequestBody City city){
        city.setId(id);
        cityService.update(city);
        return new Result(ResultCode.SUCCESS);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable String id){
        cityService.deleteById(id);
        return new Result(ResultCode.SUCCESS);
    }

    @GetMapping("/{id}")
    public Result findById(@PathVariable String id){
        City city = cityService.findById(id);
        return new Result(ResultCode.SUCCESS,city);
    }

    @GetMapping("")
    public Result findAll(){
        List<City> cityList = cityService.findAll();
        return new Result(ResultCode.SUCCESS,cityList);
    }
}
