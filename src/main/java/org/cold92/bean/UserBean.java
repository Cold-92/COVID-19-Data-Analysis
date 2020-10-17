package org.cold92.bean;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Data @AllArgsConstructor @NoArgsConstructor
@TableName("user")
public class UserBean implements Serializable, UserDetails {

   // 数据中主键自增配置
   @TableId(value = "id", type = IdType.AUTO)
   private int id;
   private String username;
   private String password;
   private String email;
   // 插入时给MP自动create_date字段自动赋值
   @TableField(value = "create_time", fill = FieldFill.INSERT)
   private Date createTime;
   // 插入和更新时MP自动给update_date字段赋值
   @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
   private Date updateTime;
   // 插入和更新时MP自动给role字段赋值
   @TableField(value = "role", fill = FieldFill.INSERT)
   private String role;

   // 忽略映射字段
   @TableField(exist = false)
   private List<GrantedAuthority> authorities;

   @Override
   public Collection<? extends GrantedAuthority> getAuthorities() {
      return this.authorities;
   }

   @Override
   public String getUsername() {
      return this.username;
   }

   /**
    * 账户是否未过期, 过期无法验证
    * @return
    */
   @Override
   public boolean isAccountNonExpired() {
      return true;
   }

   /**
    * 指定用户是否解锁, 锁定的用户无法进行身份验证
    * @return
    */
   @Override
   public boolean isAccountNonLocked() {
      return true;
   }

   /**
    * 指示是否已过期的用户的密码, 过期的密码防止认证
    * @return
    */
   @Override
   public boolean isCredentialsNonExpired() {
      return true;
   }

   @Override
   public boolean isEnabled() {
      return true;
   }
}
