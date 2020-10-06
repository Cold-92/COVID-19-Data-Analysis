package org.cold92.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.cold92.bean.TotalTableBean;

import java.util.List;

public interface TotalTableService extends IService<TotalTableBean> {

    List<TotalTableBean> listById(int id);
}
