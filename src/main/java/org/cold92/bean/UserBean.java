package org.cold92.bean;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Role;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data @AllArgsConstructor @NoArgsConstructor
@TableName("user")
public class UserBean implements Serializable {

   private int id;
   private String userName;
   private String password;
   private String email;
   // 插入时给MP自动create_date字段自动赋值
   @TableField(fill = FieldFill.INSERT)
   private Date createTime;
   // 插入和更新时MP自动给update_date字段赋值
   @TableField(fill = FieldFill.INSERT_UPDATE)
   private Date updateTime;
   // 表示该属性不为数据库表字段，但又是必须使用的
   @TableField(exist = false)
   private List<GrantedAuthority> authorities;
}
