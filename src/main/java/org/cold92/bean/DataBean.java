package org.cold92.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class DataBean {

    private String name;
    private int nowConfirm;
    private int confirm;
    private int suspect;
    private int dead;
    private int heal;
}
