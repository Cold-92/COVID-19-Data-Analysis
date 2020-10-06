package org.cold92.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

// 與illness表通過mybatis-plus關聯起來, nowConfirm會自動與now_confirm匹配
@Data @AllArgsConstructor @NoArgsConstructor
@TableName("total")
public class TotalTableBean implements Serializable {

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
