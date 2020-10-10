package org.cold92.bean;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor @NoArgsConstructor
@TableName("user")
public class UserBean {

   private String userName;
   private String password;
   private String email;
   // 插入时给MP自动create_date字段自动赋值
   @TableField(fill = FieldFill.INSERT)
   private Date createTime;
   // 插入和更新时MP自动给update_date字段赋值
   @TableField(fill = FieldFill.INSERT_UPDATE)
   private Date updateTime;
}
