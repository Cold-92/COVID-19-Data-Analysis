package org.cold92.controller;

import com.google.gson.Gson;
import org.cold92.bean.*;
import org.cold92.handler.TencentDataHandler;
import org.cold92.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
public class DataController {

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

    @GetMapping("/totalTable")
    public String list(Model model) {
        List<TotalTableBean> beanList = totalTableService.list();
        model.addAttribute("beanList", beanList);
        return "totalTable.html";
    }

    /**
     * 根據id的不同切換數據源,實現爬蟲的多數據源
     * @param id 如果id=1，則使用tencent的數據源，如果id=2，使用cloveDoc的數據源
     * @return
     */
    @GetMapping("/totalTable/{id}")
    public String listById(Model model, @PathVariable int id) {
        List<TotalTableBean> beanList = totalTableService.listById(id);
        model.addAttribute("beanList", beanList);
        return "totalTable.html";
    }

    /**
     * 接收数据形成单条折线图
     * @param model
     * @return
     */
    @GetMapping("/nowConfirm")
    public String nowConfirm(Model model) {
        List<NowConfirmBean> beanList = nowConfirmService.list();
        // 折线图x轴
        List<String> dateList = new ArrayList<>();
        // 折线图y轴
        List<Integer> nowConfirmList = new ArrayList<>();
        for (int i = 0; i < beanList.size(); i++) {
            NowConfirmBean bean = beanList.get(i);
            dateList.add(bean.getDate());
            nowConfirmList.add(bean.getNowConfirm());
        }
        model.addAttribute("dateList", new Gson().toJson(dateList));
        model.addAttribute("nowConfirmList", new Gson().toJson(nowConfirmList));
        return "nowConfirmChart.html";
    }

    /**
     * 接收数据形成双条折线图
     * @param model
     * @return
     */
    @GetMapping("/newConfirm")
    public String newConfirm(Model model) {
        List<NewConfirmBean> beanList = newConfirmService.list();
        // 折线图x轴
        List<String> dateList = new ArrayList<>();
        // 折线图y轴
        List<Integer> confirmList = new ArrayList<>();
        List<Integer> suspectList = new ArrayList<>();
        for (int i = 0; i < beanList.size(); i++) {
            NewConfirmBean bean = beanList.get(i);
            dateList.add(bean.getDate());
            confirmList.add(bean.getConfirm());
            suspectList.add(bean.getSuspect());
        }
        model.addAttribute("dateList", new Gson().toJson(dateList));
        model.addAttribute("confirmList", new Gson().toJson(confirmList));
        model.addAttribute("suspectList", new Gson().toJson(suspectList));
        return "newConfirmConstituteChart.html";
    }

    /**
     * 接收数据形成三条折线图
     * @param model
     * @return
     */
    @GetMapping("/confirmHealDead")
    public String confirmHealDead(Model model) {
        List<ConfirmHealDeadBean> beanList = confirmHealDeadService.list();
        List<String> dateList = new ArrayList<>();
        List<Integer> confirmList = new ArrayList<>();
        List<Integer> healList = new ArrayList<>();
        List<Integer> deadList = new ArrayList<>();
        for (ConfirmHealDeadBean bean : beanList) {
            dateList.add(bean.getDate());
            confirmList.add(bean.getConfirm());
            healList.add(bean.getHeal());
            deadList.add(bean.getDead());
        }
        model.addAttribute("dateList", new Gson().toJson(dateList));
        model.addAttribute("confirmList", new Gson().toJson(confirmList));
        model.addAttribute("healList", new Gson().toJson(healList));
        model.addAttribute("deadList", new Gson().toJson(deadList));
        return "confirmHealDeadChart.html";
    }

    /**
     * 接收数据形成三条折线图
     * @param model
     * @return
     */
    @GetMapping("/rate")
    public String rate(Model model) {
        List<RateBean> beanList = rateService.list();
        List<String> dateList = new ArrayList<>();
        List<Double> deadRateList = new ArrayList<>();
        List<Double> healRateList = new ArrayList<>();
        for (RateBean bean : beanList) {
            dateList.add(bean.getDate());
            deadRateList.add(bean.getDeadRate());
            healRateList.add(bean.getHealRate());
        }
        model.addAttribute("dateList", new Gson().toJson(dateList));
        model.addAttribute("deadRateList", new Gson().toJson(deadRateList));
        model.addAttribute("healRateList", new Gson().toJson(healRateList));
        return "rateChart.html";
    }

    /**
     * 接收数据形成柱状图
     * @param model
     * @return
     */
    @GetMapping("/cityTop")
    public String cityTop(Model model) {
        List<CityTopBean> beanList = cityTopService.list();
        List<String> nameList = new ArrayList<>();
        List<Integer> confirmList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            CityTopBean bean = beanList.get(i);
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
    @GetMapping("/nowConfirmConstitute")
    public String nowConfirmConstitute(Model model) {
        List<NowConfirmConstituteBean> beanList = nowConfirmConstituteService.list();
        List<String> nameList = new ArrayList<>();
        for (NowConfirmConstituteBean bean : beanList) {
            nameList.add(bean.getName());
        }
        model.addAttribute("nameList", new Gson().toJson(nameList));
        model.addAttribute("beanList", new Gson().toJson(beanList));
        return "nowConfirmConstituteChart.html";
    }

    /**
     * 接收数据形成地图
     * @param model
     * @return
     */
    @GetMapping("/map")
    public String map(Model model) {
        List<MapBean> beanList = mapService.list();
        model.addAttribute("beanList", new Gson().toJson(beanList));
        return "mapChart.html";
    }
}
