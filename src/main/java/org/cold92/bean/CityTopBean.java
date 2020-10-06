package org.cold92.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 柱状图bean：境外输入省市TOP10
 */
@Data @AllArgsConstructor @NoArgsConstructor
@TableName("city_top")
public class CityTopBean implements Comparable<CityTopBean> {

    // x轴: 地区名称
    private String name;
    // y轴: 境外输入确诊
    private int confirm;

    // bean之间比较需要实现的方法
    @Override
    public int compareTo(CityTopBean o) {
        return o.getConfirm() - this.getConfirm();
    }
}
