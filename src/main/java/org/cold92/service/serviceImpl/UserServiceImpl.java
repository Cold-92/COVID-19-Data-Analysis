package org.cold92.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.cold92.bean.UserBean;
import org.cold92.mapper.UserMapper;
import org.cold92.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 该Service类区别与图表Service类，该类的主要操作依靠Mapper来实现
 * UserDetailsService接口用于返回用户相关数据
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private HttpServletRequest request;

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
     * 根据用户名查询用户
     * @param username
     * @return
     */
    @Override
    public UserBean getUserByUsername(String username) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("username", username);
        UserBean user = userMapper.selectOne(wrapper);
        return user == null ? null : user;
    }

    /**
     * 授权的具体操作, 认证框架自动完成
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserBean user = this.getUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("登录用户：" + username + "不存在");
        }
        // 如果登录成功，将角色存入session
        request.getSession().setAttribute("login-name", username);
        // 将数据库的role解析为UserDetails的权限集合
        // AuthorityUtils.commaSeparatedStringToAuthorityList将逗号分割的字符串转成权限对象集合
        // 在定义角色时，必须要加上ROLE前缀，在controller中使用角色时，不需要加
        user.setAuthorities(AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_" + user.getRole()));
        return user;
    }
}
