package org.cold92.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.cold92.bean.DataBean;
import org.cold92.handler.CloveDocDataHandler;
import org.cold92.handler.TencentDataHandler;
import org.cold92.mapper.DataMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataServiceImpl extends ServiceImpl<DataMapper, DataBean> implements DataService {

    @Override
    public List<DataBean> listById(int id) {
        List<DataBean> beanList = null;
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
