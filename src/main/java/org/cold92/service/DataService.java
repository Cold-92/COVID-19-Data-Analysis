package org.cold92.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.cold92.bean.TotalDataBean;

import java.util.List;

public interface DataService extends IService<TotalDataBean> {

    List<TotalDataBean> listById(int id);
}
