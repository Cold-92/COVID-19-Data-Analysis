package org.cold92.service;

import org.cold92.bean.DataBean;
import org.cold92.handler.DataHandler;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DataServiceImpl implements DataService {

    @Override
    public List<DataBean> list() {
        List<DataBean> beanList = null;
        try {
            beanList = DataHandler.analysisJsonData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return beanList;
    }
}
