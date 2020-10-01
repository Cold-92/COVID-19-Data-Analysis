package org.cold92.handler;

import com.google.gson.Gson;
import org.cold92.bean.DataBean;
import org.cold92.service.DataService;
import org.cold92.util.HttpURLConnectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 處理騰訊提供的數據源：響應回來的是純json數據
 */
@Component
public class TencentDataHandler {

    // 国内疫情数据源(騰訊)
    public static String CHINA_DATA_TENCENT = "https://view.inews.qq.com/g2/getOnsInfo?name=disease_h5";

    @Autowired
    private DataService dataService;

    /**
     * 數據持久化（選擇騰訊數據源初始化數據）
     */
    public void persistData() {
        try {
            List<DataBean> beanList = analysisJsonData();
            // 每次持久化數據之前，先清空本地數據
            dataService.remove(null);
            // 持久化數據到本地
            dataService.saveBatch(beanList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 程序運行時，自動持久化實時數據
     * @PostConstruct：該方法在服務器加載servlet時執行，只執行一次，在init()之前調用
     */
    @PostConstruct
    public void initData() {
        persistData();
    }

    /**
     * 定時自動持久化實時數據 (每一個小時持久化數據一次)
     * @Scheduled：使用cron表達式作爲參數進行定時執行方法操作
     */
    @Scheduled(cron = "1-59 0 0/1 * * ?")
    public void updateData() {
        persistData();
    }

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
        String responseJson = HttpURLConnectionUtil.doGet(CHINA_DATA_TENCENT);
        Map responseMap = gson.fromJson(responseJson, Map.class);
        // 接收到的json中data是以string的形式存储的，不是json
        String dataStr = (String) responseMap.get("data");
        Map data = gson.fromJson(dataStr, Map.class);
        ArrayList areaTree = (ArrayList) data.get("areaTree");
        Map areaTreeElement = (Map) areaTree.get(0);
        ArrayList children = (ArrayList) areaTreeElement.get("children");
        for (int i = 0; i < children.size(); i++) {
            Map childrenMap = (Map) children.get(i);
            String area = (String) childrenMap.get("name");
            Map total = (Map) childrenMap.get("total");
            // 解析数据
            double nowConfirm = (Double) total.get("nowConfirm");
            double confirm = (Double) total.get("confirm");
            double suspect = (Double) total.get("suspect");
            double dead = (Double) total.get("dead");
            double heal = (Double) total.get("heal");
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
