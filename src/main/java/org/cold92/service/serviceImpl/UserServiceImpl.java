package org.cold92.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.cold92.bean.UserBean;
import org.cold92.mapper.UserMapper;
import org.cold92.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 该Service类区别与图表Service类，该类的主要操作依靠Mapper来实现
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 增加用户
     * @param user
     * @return
     */
    @Override
    public int insertUser(UserBean user) {
        int result = userMapper.insert(user);
        return result;
    }

    /**
     * 通过用户名查找用户
     * @param userName
     * @return
     */
    public UserBean getUserByName(String userName) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("user_name", userName);
        UserBean userBean = userMapper.selectOne(wrapper);
        return userBean;
    }
}
