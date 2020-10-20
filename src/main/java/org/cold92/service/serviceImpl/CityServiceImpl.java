package org.cold92.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.cold92.bean.CityBean;
import org.cold92.mapper.CityMapper;
import org.cold92.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class CityServiceImpl extends ServiceImpl<CityMapper, CityBean>
        implements CityService {

    @Autowired
    private CityMapper cityMapper;

    /**
     * 获取全部城市疫情信息
     * @return
     */
    public List<CityBean> getAllCities() {
        return cityMapper.selectList(null);
    }

    /**
     * 由城市名获取城市疫情信息
     * @param name
     * @return
     */
    public CityBean getCityByName(String name) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("area", name);
        return cityMapper.selectOne(wrapper);
    }
}
