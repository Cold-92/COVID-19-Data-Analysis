package org.cold92.controller;

import com.google.gson.Gson;
import org.cold92.bean.*;
import org.cold92.handler.TencentDataHandler;
import org.cold92.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
public class DataController {

    @Autowired
    private DataService dataService;

    @GetMapping("/list")
    public String list(Model model) {
        List<TotalDataBean> beanList = dataService.list();
        model.addAttribute("beanList", beanList);
        return "list.html";
    }

    /**
     * 根據id的不同切換數據源,實現爬蟲的多數據源
     * @param id 如果id=1，則使用tencent的數據源，如果id=2，使用cloveDoc的數據源
     * @return
     */
    @GetMapping("/list/{id}")
    public String listById(Model model, @PathVariable int id) {
        List<TotalDataBean> beanList = dataService.listById(id);
        model.addAttribute("beanList", beanList);
        return "list.html";
    }

    /**
     * 接收数据形成单条折线图
     * @param model
     * @return
     */
    @GetMapping("/singleLineChart")
    public String singleLineChart(Model model) {
        List<SingleChartBean> beanList = TencentDataHandler.getSingleChartData();
        // 折线图x轴
        List<String> dateList = new ArrayList<>();
        // 折线图y轴
        List<Integer> nowConfirmList = new ArrayList<>();
        for (int i = 0; i < beanList.size(); i++) {
            SingleChartBean bean = beanList.get(i);
            dateList.add(bean.getDate());
            nowConfirmList.add(bean.getNowConfirm());
        }
        model.addAttribute("dateList", new Gson().toJson(dateList));
        model.addAttribute("nowConfirmList", new Gson().toJson(nowConfirmList));
        return "singleLineChart.html";
    }

    /**
     * 接收数据形成双条折线图
     * @param model
     * @return
     */
    @GetMapping("/doubleLineChart")
    public String doubleLineChart(Model model) {
        List<DoubleChartBean> beanList = TencentDataHandler.getDoubleChartData();
        // 折线图x轴
        List<String> dateList = new ArrayList<>();
        // 折线图y轴
        List<Integer> confirmList = new ArrayList<>();
        List<Integer> suspectList = new ArrayList<>();
        for (int i = 0; i < beanList.size(); i++) {
            DoubleChartBean bean = beanList.get(i);
            dateList.add(bean.getDate());
            confirmList.add(bean.getConfirm());
            suspectList.add(bean.getSuspect());
        }
        model.addAttribute("dateList", new Gson().toJson(dateList));
        model.addAttribute("confirmList", new Gson().toJson(confirmList));
        model.addAttribute("suspectList", new Gson().toJson(suspectList));
        return "doubleLineChart.html";
    }

    /**
     * 接收数据形成三条折线图
     * @param model
     * @return
     */
    @GetMapping("/trebleLineChart")
    public String trebleLineChart(Model model) {
        List<TrebleChartBean> beanList = TencentDataHandler.getTrebleChartData();
        List<String> dateList = new ArrayList<>();
        List<Integer> confirmList = new ArrayList<>();
        List<Integer> healList = new ArrayList<>();
        List<Integer> deadList = new ArrayList<>();
        for (TrebleChartBean bean : beanList) {
            dateList.add(bean.getDate());
            confirmList.add(bean.getConfirm());
            healList.add(bean.getHeal());
            deadList.add(bean.getDead());
        }
        model.addAttribute("dateList", new Gson().toJson(dateList));
        model.addAttribute("confirmList", new Gson().toJson(confirmList));
        model.addAttribute("healList", new Gson().toJson(healList));
        model.addAttribute("deadList", new Gson().toJson(deadList));
        return "trebleLineChart.html";
    }

    /**
     * 接收数据形成三条折线图
     * @param model
     * @return
     */
    @GetMapping("/rateLineChart")
    public String rateLineChart(Model model) {
        List<RateLineChartBean> beanList = TencentDataHandler.getRateLineChartData();
        List<String> dateList = new ArrayList<>();
        List<Double> deadRateList = new ArrayList<>();
        List<Double> healRateList = new ArrayList<>();
        for (RateLineChartBean bean : beanList) {
            dateList.add(bean.getDate());
            deadRateList.add(bean.getDeadRate());
            healRateList.add(bean.getHealRate());
        }
        model.addAttribute("dateList", new Gson().toJson(dateList));
        model.addAttribute("deadRateList", new Gson().toJson(deadRateList));
        model.addAttribute("healRateList", new Gson().toJson(healRateList));
        return "rateLineChart.html";
    }

    /**
     * 接收数据形成柱状图
     * @param model
     * @return
     */
    @GetMapping("/cityTopChart")
    public String cityTopChart(Model model) {
        List<CityTopChartBean> beanList = TencentDataHandler.getCityTopChartData();
        List<String> nameList = new ArrayList<>();
        List<Integer> confirmList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            CityTopChartBean bean = beanList.get(i);
            nameList.add(bean.getName());
            confirmList.add(bean.getConfirm());
        }
        model.addAttribute("nameList", new Gson().toJson(nameList));
        model.addAttribute("confirmList", new Gson().toJson(confirmList));
        return "cityTopChart.html";
    }

    /**
     * 接收数据形成柱状图
     * @param model
     * @return
     */
    @GetMapping("/nowConfirmStatis")
    public String nowConfirmStatis(Model model) {
        List<NowConfirmStatisBean> beanList = TencentDataHandler.getNowConfirmStatis();
        List<String> nameList = new ArrayList<>();
        for (NowConfirmStatisBean bean : beanList) {
            nameList.add(bean.getName());
        }
        model.addAttribute("nameList", new Gson().toJson(nameList));
        model.addAttribute("beanList", new Gson().toJson(beanList));
        return "nowConfirmStatis.html";
    }

    /**
     * 接收数据形成地图
     * @param model
     * @return
     */
    @GetMapping("/mapChart")
    public String mapChart(Model model) {
        List<MapChartBean> beanList = TencentDataHandler.getMapChartData();
        model.addAttribute("beanList", new Gson().toJson(beanList));
        return "mapChart.html";
    }
}
