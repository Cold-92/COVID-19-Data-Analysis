package org.cold92.handler;

import com.google.gson.Gson;
import org.apache.tomcat.util.net.jsse.JSSEUtil;
import org.cold92.bean.DataBean;
import org.cold92.util.HttpURLConnectionUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataHandler {

    // 国内疫情数据源
    public static String CHINA_DATA = "https://view.inews.qq.com/g2/getOnsInfo?name=disease_h5";

    /**
     * 解析爬虫获取的json数据并解析成bean
     * @return
     * @throws Exception
     */
    public static List<DataBean> analysisJsonData() throws Exception {
        // 解析json字符串成bean
        Gson gson = new Gson();
        // 存放bean
        List<DataBean> beanList = new ArrayList<>();
        // 获取实时json数据
        String responseJson = HttpURLConnectionUtil.doGet(CHINA_DATA);
        Map responseMap = gson.fromJson(responseJson, Map.class);
        // 接收到的json中data是以string的形式存储的，不是json
        String dataStr = (String) responseMap.get("data");
        Map data = gson.fromJson(dataStr, Map.class);
        ArrayList areaTree = (ArrayList) data.get("areaTree");
        Map areaTreeElement = (Map) areaTree.get(0);
        ArrayList children = (ArrayList) areaTreeElement.get("children");
        for (int i = 0; i < children.size(); i++) {
            Map childrenMap = (Map) children.get(i);
            String name = (String) childrenMap.get("name");
            Map total = (Map) childrenMap.get("total");
            // 解析数据
            double nowConfirm = (Double) total.get("nowConfirm");
            double confirm = (Double) total.get("confirm");
            double suspect = (Double) total.get("suspect");
            double dead = (Double) total.get("dead");
            double heal = (Double) total.get("heal");
            DataBean bean = new DataBean();
            bean.setName(name);
            bean.setNowConfirm((int) nowConfirm);
            bean.setConfirm((int) confirm);
            bean.setSuspect((int) suspect);
            bean.setDead((int) dead);
            bean.setHeal((int) heal);
            beanList.add(bean);
        }
        return beanList;
    }
}
