package org.cold92.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.cold92.bean.DataBean;

import java.util.List;

public interface DataService extends IService<DataBean> {

    List<DataBean> listById(int id);
}
