package org.cold92.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.cold92.bean.CityBean;
import org.cold92.bean.CityTopBean;

import java.util.List;

public interface CityService extends IService<CityBean> {

    List<CityBean> getAllCities();

    CityBean getCityByName(String name);
}
