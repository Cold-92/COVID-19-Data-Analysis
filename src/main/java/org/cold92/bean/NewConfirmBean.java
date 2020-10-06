package org.cold92.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 双条折线图bean：全国疫情新增趋势
 */
@Data @AllArgsConstructor @NoArgsConstructor
@TableName("new_confirm")
public class NewConfirmBean {

    // x轴: 日期
    private String date;
    // y轴: 新增确诊, 疑似确诊
    private int confirm;
    private int suspect;
}
