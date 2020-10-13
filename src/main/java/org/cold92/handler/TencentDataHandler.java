package org.cold92.handler;

import com.google.gson.Gson;
import org.cold92.bean.*;
import org.cold92.service.*;
import org.cold92.util.HttpClientUtil;
import org.cold92.util.HttpURLConnectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * 處理騰訊提供的數據源：響應回來的是純json數據
 */
@Component
public class TencentDataHandler {

    // 国内各省份总体疫情数据源(腾讯)
    public static String CHINA_TOTAL_DATA_TENCENT = "https://view.inews.qq.com/g2/getOnsInfo?name=disease_h5";

    // 单条折线图数据源
    private static String CHINA_CHART_DATA = "https://view.inews.qq.com/g2/getOnsInfo?name=disease_other";

    @Autowired
    private TotalTableService totalTableService;
    @Autowired
    private NowConfirmService nowConfirmService;
    @Autowired
    private NewConfirmService newConfirmService;
    @Autowired
    private ConfirmHealDeadService confirmHealDeadService;
    @Autowired
    private RateService rateService;
    @Autowired
    private CityTopService cityTopService;
    @Autowired
    private NowConfirmConstituteService nowConfirmConstituteService;
    @Autowired
    private MapService mapService;

    @Autowired
    private MailHandler mailHandler;

    /**
     * 數據持久化（選擇騰訊數據源初始化數據）
     */
    public void persistData() {
        try {
            List<TotalTableBean> totalTableBeanList = getTotalData();
            List<NowConfirmBean> nowConfirmBeanList = getNowConfirmData();
            List<NewConfirmBean> newConfirmBeanList = getNewConfirmData();
            List<ConfirmHealDeadBean> confirmHealDeadBeanList = getConfirmHealDeadData();
            List<RateBean> rateBeanList = getRateData();
            List<CityTopBean> cityTopBeanList = getCityTopData();
            List<NowConfirmConstituteBean> nowConfirmConstituteBeanList = getNowConfirmConstituteData();
            List<MapBean> mapBeanList = getMapData();
            // 每次持久化数据之前，先清空本地数据
            totalTableService.remove(null);
            nowConfirmService.remove(null);
            newConfirmService.remove(null);
            confirmHealDeadService.remove(null);
            rateService.remove(null);
            cityTopService.remove(null);
            nowConfirmConstituteService.remove(null);
            mapService.remove(null);
            // 持久化数据到本地
            totalTableService.saveBatch(totalTableBeanList);
            nowConfirmService.saveBatch(nowConfirmBeanList);
            newConfirmService.saveBatch(newConfirmBeanList);
            confirmHealDeadService.saveBatch(confirmHealDeadBeanList);
            rateService.saveBatch(rateBeanList);
            cityTopService.saveBatch(cityTopBeanList);
            nowConfirmConstituteService.saveBatch(nowConfirmConstituteBeanList);
            mapService.saveBatch(mapBeanList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 程序运行时，自动持久化实时数据
     * @PostConstruct：该方法在服务器加載servlet时执行，只执行一次，在init()之前调用
     */
//    @PostConstruct
    public void initData() {
        persistData();
    }

    /**
     * 定时自动持久化实时数据 (每一个小时持久化数据一次)
     * @Scheduled：使用cron表达式作为参数进行定时执行方法操作
     */
    @Scheduled(cron = "1-59 0 0/1 * * ?")
    public void updateData() {
        persistData();
    }

    /**
     * 获取中国各个省份疫情的json数据并通过gson解析成bean
     * @return
     */
    public static List<TotalTableBean> getTotalData() {
        // 解析json字符串成bean
        Gson gson = new Gson();
        // 存放bean
        List<TotalTableBean> beanList = new ArrayList<>();
        // 获取实时json数据
        String responseJson = HttpURLConnectionUtil.doGet(CHINA_TOTAL_DATA_TENCENT);
        Map responseMap = gson.fromJson(responseJson, Map.class);
        // 接收到的json中data是以字符串的形式存储的，不是json字符串
        String dataStr = (String) responseMap.get("data");
        Map data = gson.fromJson(dataStr, Map.class);
        List areaTree = (ArrayList) data.get("areaTree");
        Map areaTreeElement = (Map) areaTree.get(0);
        List children = (ArrayList) areaTreeElement.get("children");
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
            TotalTableBean bean = new TotalTableBean();
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

    /**
     * 获取单条折线图的json实时数据并通过gson解析成bean
     * @return
     */
    public static List<NowConfirmBean> getNowConfirmData() {
        Gson gson = new Gson();
        List<NowConfirmBean> beanList = new ArrayList<>();
        // 使用HttpClient封装的http请求获取的数据
        String responseJson = HttpClientUtil.doGet(CHINA_CHART_DATA);
        Map responseMap = gson.fromJson(responseJson, Map.class);
        // data中存的是json字符串，不是json对象
        String dataStr = (String) responseMap.get("data");
        Map data = gson.fromJson(dataStr, Map.class);
        List chinaDayList = (ArrayList) data.get("chinaDayList");
        for (int i = 0; i < chinaDayList.size(); i++) {
            Map chinaDayMap = (Map) chinaDayList.get(i);
            String date = (String) chinaDayMap.get("date");
            double nowConfirm = (Double) chinaDayMap.get("nowConfirm");
            NowConfirmBean bean = new NowConfirmBean();
            bean.setDate(date);
            bean.setNowConfirm((int) nowConfirm);
            beanList.add(bean);
        }
        return beanList;
    }

    /**
     * 获取双条条折线图的json实时数据并通过gson解析成bean
     * @return
     */
    public static List<NewConfirmBean> getNewConfirmData() {
        Gson gson = new Gson();
        List<NewConfirmBean> beanList = new ArrayList<>();
        // 使用HttpClient封装的http请求获取的数据
        String responseJson = HttpClientUtil.doGet(CHINA_CHART_DATA);
        Map responseMap = gson.fromJson(responseJson, Map.class);
        String dataStr = (String) responseMap.get("data");
        Map data = gson.fromJson(dataStr, Map.class);
        List chinaDayAddList = (ArrayList) data.get("chinaDayAddList");
        for (int i = 0; i < chinaDayAddList.size(); i++) {
            Map chinaDayAddMap = (Map) chinaDayAddList.get(i);
            String date = (String) chinaDayAddMap.get("date");
            double confirm = (Double) chinaDayAddMap.get("confirm");
            double suspect = (Double) chinaDayAddMap.get("suspect");
            NewConfirmBean bean = new NewConfirmBean();
            bean.setDate(date);
            bean.setConfirm((int) confirm);
            bean.setSuspect((int) suspect);
            beanList.add(bean);
        }
        return beanList;
    }

    /**
     * 获取三条条折线图的json实时数据并通过gson解析成bean
     * @return
     */
    public static List<ConfirmHealDeadBean> getConfirmHealDeadData() {
        Gson gson = new Gson();
        List<ConfirmHealDeadBean> beanList = new ArrayList<>();
        String responseJson = HttpClientUtil.doGet(CHINA_CHART_DATA);
        Map responseMap = gson.fromJson(responseJson, Map.class);
        String dataStr = (String) responseMap.get("data");
        Map data = gson.fromJson(dataStr, Map.class);
        List chinaDayList = (ArrayList) data.get("chinaDayList");
        for (int i = 0; i < chinaDayList.size(); i++) {
            Map chinaDayMap = (Map) chinaDayList.get(i);
            String date = (String) chinaDayMap.get("date");
            double confirm = (Double) chinaDayMap.get("confirm");
            double heal = (Double) chinaDayMap.get("heal");
            double dead = (Double) chinaDayMap.get("dead");
            ConfirmHealDeadBean bean = new ConfirmHealDeadBean();
            bean.setDate(date);
            bean.setConfirm((int) confirm);
            bean.setHeal((int) heal);
            bean.setDead((int) dead);
            beanList.add(bean);
        }
        return beanList;
    }

    /**
     * 获取病死率/治愈率折线图的json实时数据并通过gson解析成bean
     * @return
     */
    public static List<RateBean> getRateData() {
        Gson gson = new Gson();
        List<RateBean> beanList = new ArrayList<>();
        String responseJson = HttpClientUtil.doGet(CHINA_CHART_DATA);
        Map responseMap = gson.fromJson(responseJson, Map.class);
        String dataStr = (String) responseMap.get("data");
        Map data = gson.fromJson(dataStr, Map.class);
        List chinaDayList = (ArrayList) data.get("chinaDayList");
        for (int i = 0; i < chinaDayList.size(); i++) {
            Map chinaDayMap = (Map) chinaDayList.get(i);
            String date = (String) chinaDayMap.get("date");
            double deadRate = Double.parseDouble((String) chinaDayMap.get("deadRate"));
            double healRate = Double.parseDouble((String) chinaDayMap.get("healRate"));
            RateBean bean = new RateBean();
            bean.setDate(date);
            bean.setDeadRate(deadRate);
            bean.setHealRate(healRate);
            beanList.add(bean);
        }
        return beanList;
    }

    /**
     * 获取境外输入省市TOP10柱状图的json实时数据并通过gson解析成bean
     * @return
     */
    public static List<CityTopBean> getCityTopData() {
        Gson gson = new Gson();
        List<CityTopBean> beanList = new ArrayList<>();
        String responseJson = HttpClientUtil.doGet(CHINA_TOTAL_DATA_TENCENT);
        Map responseMap = gson.fromJson(responseJson, Map.class);
        String dataStr = (String) responseMap.get("data");
        Map data = gson.fromJson(dataStr, Map.class);
        List areaTree = (List) data.get("areaTree");
        Map areaTreeElement = (Map) areaTree.get(0);
        List children = (List) areaTreeElement.get("children");
        for (int i = 0; i < children.size(); i++) {
            Map childrenMap = (Map) children.get(i);
            String name = (String) childrenMap.get("name");
            List subChildren = (List) childrenMap.get("children");
            for (int j = 0; j < subChildren.size(); j++) {
                Map subChildrenElement = (Map) subChildren.get(j);
                if ("境外输入".equals(subChildrenElement.get("name"))) {
                    Map total = (Map) subChildrenElement.get("total");
                    double confirm = (Double) total.get("confirm");
                    CityTopBean bean = new CityTopBean();
                    bean.setName(name);
                    bean.setConfirm((int) confirm);
                    beanList.add(bean);
                }
            }
        }
        // 从大到小对List进行排序
        Collections.sort(beanList);
        return beanList;
    }

    /**
     * 获取全国现有确诊构成饼状图的json实时数据并通过gson解析成bean
     * @return
     */
    public static List<NowConfirmConstituteBean> getNowConfirmConstituteData() {
        Gson gson = new Gson();
        List<NowConfirmConstituteBean> beanList = new ArrayList<>();
        String responseJson = HttpClientUtil.doGet(CHINA_CHART_DATA);
        Map responseMap = gson.fromJson(responseJson, Map.class);
        String dataStr = (String) responseMap.get("data");
        Map data = gson.fromJson(dataStr, Map.class);
        Map<String, Double> nowConfirmStatis = (Map) data.get("nowConfirmStatis");
        for (Map.Entry<String, Double> entry : nowConfirmStatis.entrySet()) {
            NowConfirmConstituteBean bean = new NowConfirmConstituteBean();
            double value = entry.getValue();
            switch (entry.getKey()) {
                case "gat": {
                    bean.setName("港澳台病例：" + (int) value + "例");
                    bean.setValue((int) value);
                    break;
                }
                case "import": {
                    bean.setName("境外输入病例：" + (int) value + "例");
                    bean.setValue((int) value);
                    break;
                }
                case "province": {
                    bean.setName("31省本土病例：" + (int) value + "例");
                    bean.setValue((int) value);
                    break;
                }
            }
            beanList.add(bean);
        }
        return beanList;
    }

    /**
     * 获取全国确证地图的json实时数据并通过gson解析成bean
     * @return
     */
    public static List<MapBean> getMapData() {
        Gson gson = new Gson();
        List<MapBean> beanList = new ArrayList<>();
        String responseJson = HttpClientUtil.doGet(CHINA_TOTAL_DATA_TENCENT);
        Map responseMap = gson.fromJson(responseJson, Map.class);
        String dataStr = (String) responseMap.get("data");
        Map data = (Map) gson.fromJson(dataStr, Map.class);
        List areaTree = (List) data.get("areaTree");
        Map areaTreeElement = (Map) areaTree.get(0);
        List children = (List) areaTreeElement.get("children");
        for (int i = 0; i < children.size(); i++) {
            Map childrenMap = (Map) children.get(i);
            String name = (String) childrenMap.get("name");
            Map total = (Map) childrenMap.get("total");
            double nowConfirm = (Double) total.get("nowConfirm");
            MapBean bean = new MapBean();
            bean.setName(name);
            bean.setValue((int) nowConfirm);
            beanList.add(bean);
        }
        return beanList;
    }
}
