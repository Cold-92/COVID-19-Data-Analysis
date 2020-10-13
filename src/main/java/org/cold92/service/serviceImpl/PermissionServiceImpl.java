package org.cold92.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.cold92.bean.PermissionBean;
import org.cold92.mapper.PermissionMapper;
import org.cold92.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public List<PermissionBean> getPermissionsByUserId(int userId) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("id", userId);
        return permissionMapper.selectList(wrapper);
    }
}
