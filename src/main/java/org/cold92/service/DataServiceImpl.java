package org.cold92.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.cold92.bean.DataBean;
import org.cold92.handler.CloveDocDataHandler;
import org.cold92.handler.TencentDataHandler;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataServiceImpl implements DataService {

    @Override
    public List<DataBean> list() {
        List<DataBean> beanList = null;
        try {
            beanList = CloveDocDataHandler.analysisHtmlData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return beanList;
    }

    /**
     * 根據id的不同切換數據源,實現爬蟲的多數據源
     * @param id 如果id=1，則使用tencent的數據源，如果id=2，使用cloveDoc的數據源
     * @return
     */
    @Override
    public List<DataBean> listById(int id) {
        List<DataBean> beanList = null;
        try {
            if (id == 1) {
                beanList = TencentDataHandler.analysisJsonData();
            } else if (id == 2) {
                beanList = CloveDocDataHandler.analysisHtmlData();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return beanList;
    }
}
