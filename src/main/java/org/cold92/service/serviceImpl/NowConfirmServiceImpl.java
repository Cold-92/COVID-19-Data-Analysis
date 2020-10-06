package org.cold92.service.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.cold92.bean.NowConfirmBean;
import org.cold92.mapper.NowConfirmMapper;
import org.cold92.service.NowConfirmService;
import org.springframework.stereotype.Service;

@Service
public class NowConfirmServiceImpl extends ServiceImpl<NowConfirmMapper, NowConfirmBean>
        implements NowConfirmService {
}
