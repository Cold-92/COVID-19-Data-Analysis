package org.cold92.service.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.cold92.bean.TotalTableBean;
import org.cold92.handler.CloveDocDataHandler;
import org.cold92.handler.TencentDataHandler;
import org.cold92.mapper.TotalTableMapper;
import org.cold92.service.TotalTableService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TotalTableServiceImpl extends ServiceImpl<TotalTableMapper, TotalTableBean> implements TotalTableService {

    @Override
    public List<TotalTableBean> listById(int id) {
        List<TotalTableBean> beanList = null;
        try {
            if (id == 1) {
                beanList = TencentDataHandler.getTotalData();
            } else if (id == 2) {
                beanList = CloveDocDataHandler.getTotalData();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return beanList;
    }
}
