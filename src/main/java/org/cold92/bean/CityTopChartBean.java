package org.cold92.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 柱状图bean：境外输入省市TOP10
 */
@Data @AllArgsConstructor @NoArgsConstructor
public class CityTopChartBean implements Comparable<CityTopChartBean> {

    // x轴: 地区名称
    private String name;
    // y轴: 境外输入确诊
    private int confirm;

    // bean之间比较需要实现的方法
    @Override
    public int compareTo(CityTopChartBean o) {
        return o.getConfirm() - this.getConfirm();
    }
}
