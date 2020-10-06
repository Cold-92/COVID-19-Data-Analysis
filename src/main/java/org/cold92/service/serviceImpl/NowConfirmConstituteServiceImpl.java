package org.cold92.service.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.cold92.bean.NowConfirmConstituteBean;
import org.cold92.mapper.NowConfirmConstituteMapper;
import org.cold92.service.NowConfirmConstituteService;
import org.springframework.stereotype.Service;

@Service
public class NowConfirmConstituteServiceImpl
        extends ServiceImpl<NowConfirmConstituteMapper, NowConfirmConstituteBean>
        implements NowConfirmConstituteService {
}
