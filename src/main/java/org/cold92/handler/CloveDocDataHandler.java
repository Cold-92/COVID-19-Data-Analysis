package org.cold92.handler;

import com.google.gson.Gson;
import org.cold92.bean.DataBean;
import org.cold92.util.HttpURLConnectionUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 處理丁香醫生提供的數據源：響應回來的是html頁面
 */
public class CloveDocDataHandler {

    // 国内各省份总体疫情数据源(丁香醫生)
    public static String CHINA_TOTAL_DATA_CLOVEDOC = "https://ncov.dxy.cn/ncovh5/view/pneumonia?scene=2&from=singlemessage&isappinstalled=0";

    /**
     * 获取的中国各个省份疫情的html数据并通过jsoup+gson解析成bean
     * @return
     * @throws Exception
     */
    public static List<DataBean> getTotalData() throws Exception {
        // 解析json字符串成bean
        Gson gson = new Gson();
        // 存放bean
        List<DataBean> beanList = new ArrayList<>();
        // 獲取疫情數據
        String responseHtml = HttpURLConnectionUtil.doGet(CHINA_TOTAL_DATA_CLOVEDOC);
        // 解析html數據
        Document document = Jsoup.parse(responseHtml);
        Element script = document.getElementById("getAreaStat");
        // 將html數據中的json數據提取出來
        String scriptDataStr = script.data();
        String dataStr = scriptDataStr.substring(scriptDataStr.indexOf("["), scriptDataStr.lastIndexOf("]") + 1);
        // 解析json數據
        ArrayList areaTree = (ArrayList) gson.fromJson(dataStr, List.class);
        for (int i = 0; i < areaTree.size(); i++) {
            Map total = (Map) areaTree.get(i);
            String area = (String) total.get("provinceName");
            double nowConfirm = (Double) total.get("currentConfirmedCount");
            double confirm = (Double) total.get("confirmedCount");
            double suspect = (Double) total.get("suspectedCount");
            double dead = (Double) total.get("deadCount");
            double heal = (Double) total.get("curedCount");
            DataBean bean = new DataBean();
            bean.setArea(area);
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
