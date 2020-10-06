package org.cold92.service.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.cold92.bean.ConfirmHealDeadBean;
import org.cold92.mapper.ConfirmHealDeadMapper;
import org.cold92.service.ConfirmHealDeadService;
import org.springframework.stereotype.Service;

@Service
public class ConfirmHealDeadServiceImpl extends ServiceImpl<ConfirmHealDeadMapper, ConfirmHealDeadBean>
        implements ConfirmHealDeadService {
}
