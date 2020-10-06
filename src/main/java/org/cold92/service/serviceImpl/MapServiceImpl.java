package org.cold92.service.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.cold92.bean.MapBean;
import org.cold92.mapper.MapMapper;
import org.cold92.service.MapService;
import org.springframework.stereotype.Service;

@Service
public class MapServiceImpl extends ServiceImpl<MapMapper, MapBean> implements MapService {
}
