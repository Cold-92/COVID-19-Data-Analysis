package org.cold92.service;

import org.cold92.bean.PermissionBean;

import java.util.List;

public interface PermissionService {

    List<PermissionBean> getPermissionsByUserId(int userId);
}
