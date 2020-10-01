package org.cold92.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

// 與illness表通過mybatis-plus關聯起來, nowConfirm會自動與now_confirm匹配
@Data @AllArgsConstructor @NoArgsConstructor
@TableName("illness")
public class DataBean implements Serializable {

    private String area;
    private int nowConfirm;
    private int confirm;
    private int suspect;
    private int dead;
    private int heal;
}
