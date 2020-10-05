package org.cold92.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class TrebleChartBean {

    // x轴: 日期
    private String date;
    // y轴：累计确诊, 累计治愈, 累计死亡
    private int confirm;
    private int heal;
    private int dead;
}
