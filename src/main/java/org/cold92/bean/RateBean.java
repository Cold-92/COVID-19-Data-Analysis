package org.cold92.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 治愈率/病死率折线图bean
 */
@Data @AllArgsConstructor @NoArgsConstructor
@TableName("rate")
public class RateBean {

    // x轴: 日期
    private String date;
    // y轴: 病死率, 治愈率
    private double deadRate;
    private double healRate;
}
