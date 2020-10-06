package org.cold92.service.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.cold92.bean.CityTopBean;
import org.cold92.mapper.CityTopMapper;
import org.cold92.service.CityTopService;
import org.springframework.stereotype.Service;

@Service
public class CityTopServiceImpl extends ServiceImpl<CityTopMapper, CityTopBean>
        implements CityTopService {
}
