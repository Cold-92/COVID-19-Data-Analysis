package org.cold92.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 饼状图bean：全国现有确诊构成
 */
@Data @AllArgsConstructor @NoArgsConstructor
public class NowConfirmStatisBean {

    // 港澳台病例, 境外输入病例, 31省本土病例
    private String name;
    // 病例数
    private int value;
}
