package org.cold92.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 全国各个城市疫情
 */
@Data @AllArgsConstructor @NoArgsConstructor
@TableName("city")
public class CityBean {

    // 地名
    private String area;
    // 当前确诊
    private int nowConfirm;
    // 累计确诊
    private int confirm;
    // 疑似确诊
    private int suspect;
    // 已死亡
    private int dead;
    // 已治愈
    private int heal;
}
