package org.cold92.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 订阅数据的用户
 */
@Data @AllArgsConstructor @NoArgsConstructor
@TableName("user_order")
public class OrderBean {

    private String username;
    private String email;
    private String city;
}
