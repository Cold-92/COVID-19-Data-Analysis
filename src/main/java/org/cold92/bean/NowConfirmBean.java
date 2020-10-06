package org.cold92.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 单条折线图bean：中国现有确诊趋势
 */
@Data @AllArgsConstructor @NoArgsConstructor
@TableName("now_confirm")
public class NowConfirmBean {

    // x轴: 日期
    private String date;
    // y轴: 当前确诊
    private int nowConfirm;
}
