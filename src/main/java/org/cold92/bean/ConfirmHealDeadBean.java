package org.cold92.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 三条折线图：全国累计确诊/治愈/死亡趋势bean
 */
@Data @AllArgsConstructor @NoArgsConstructor
@TableName("confirm_heal_dead")
public class ConfirmHealDeadBean {

    // x轴: 日期
    private String date;
    // y轴：累计确诊, 累计治愈, 累计死亡
    private int confirm;
    private int heal;
    private int dead;
}
