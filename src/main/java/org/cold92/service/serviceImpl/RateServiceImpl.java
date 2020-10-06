package org.cold92.service.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.cold92.bean.RateBean;
import org.cold92.mapper.RateMapper;
import org.cold92.service.RateService;
import org.springframework.stereotype.Service;

@Service
public class RateServiceImpl extends ServiceImpl<RateMapper, RateBean> implements RateService {
}
