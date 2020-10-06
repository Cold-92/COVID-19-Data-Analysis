package org.cold92.service.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.cold92.bean.NewConfirmBean;
import org.cold92.mapper.NewConfirmMapper;
import org.cold92.service.NewConfirmService;
import org.springframework.stereotype.Service;

@Service
public class NewConfirmServiceImpl extends ServiceImpl<NewConfirmMapper, NewConfirmBean>
        implements NewConfirmService {
}
