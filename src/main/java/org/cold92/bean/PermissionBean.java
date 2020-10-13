package org.cold92.bean;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data @AllArgsConstructor @NoArgsConstructor
@TableName("permission")
public class PermissionBean implements Serializable {

    private int id;
    // 拥有者id
    private int userId;
    // 权限中文名
    private String name;
    // 权限英文名
    private String authority;
    // 插入时给MP自动create_date字段自动赋值
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    // 插入时给MP自动update_date字段自动赋值
    @TableField(fill = FieldFill.INSERT)
    private Date updateTime;
}
