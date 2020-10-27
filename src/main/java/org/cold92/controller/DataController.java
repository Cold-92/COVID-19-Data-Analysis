package org.cold92.controller;

import com.google.gson.Gson;
import org.cold92.bean.*;
import org.cold92.handler.MailHandler;
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

@Controller
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
     * @param flag 如果flag=1，則使用tencent的數據源，如果flag=2，使用cloveDoc的數據源
     * @return
     */
    @GetMapping("/totalTable/{flag}")
    public String listById(Model model, @PathVariable("flag") int flag) {
        List<TotalTableBean> beanList = totalTableService.listByFlag(flag);
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
        return "newConfirmChart.html";
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

    /**
     * 所有数据总览
     * @param model
     * @return
     */
    @GetMapping("/index")
    public String index(Model model) {
        // totalTable数据解析
        List<TotalTableBean> totalTableBeanList = totalTableService.list();
        model.addAttribute("totalTableBeanList", totalTableBeanList);
        // nowConfirm数据解析
        List<NowConfirmBean> nowConfirmBeanList = nowConfirmService.list();
        List<String> nowConfirmDateList = new ArrayList<>();
        List<Integer> nowConfirmList = new ArrayList<>();
        for (int i = 0; i < nowConfirmBeanList.size(); i++) {
            NowConfirmBean bean = nowConfirmBeanList.get(i);
            nowConfirmDateList.add(bean.getDate());
            nowConfirmList.add(bean.getNowConfirm());
        }
        model.addAttribute("nowConfirmDateList", new Gson().toJson(nowConfirmDateList));
        model.addAttribute("nowConfirmList", new Gson().toJson(nowConfirmList));
        // newConfirm数据解析
        List<NewConfirmBean> newConfirmBeanList = newConfirmService.list();
        List<String> newConfirmDateList = new ArrayList<>();
        List<Integer> newConfirmList = new ArrayList<>();
        List<Integer> newSuspectList = new ArrayList<>();
        for (int i = 0; i < newConfirmBeanList.size(); i++) {
            NewConfirmBean bean = newConfirmBeanList.get(i);
            newConfirmDateList.add(bean.getDate());
            newConfirmList.add(bean.getConfirm());
            newSuspectList.add(bean.getSuspect());
        }
        model.addAttribute("newConfirmDateList", new Gson().toJson(newConfirmDateList));
        model.addAttribute("newConfirmList", new Gson().toJson(newConfirmList));
        model.addAttribute("newSuspectList", new Gson().toJson(newSuspectList));
        // confirmHealDead数据解析
        List<ConfirmHealDeadBean> confirmHealDeadBeanList = confirmHealDeadService.list();
        List<String> confirmHealDeadDateList = new ArrayList<>();
        List<Integer> confirmHealDeadConfirmList = new ArrayList<>();
        List<Integer> confirmHealDeadHealList = new ArrayList<>();
        List<Integer> confirmHealDeadDeadList = new ArrayList<>();
        for (ConfirmHealDeadBean bean : confirmHealDeadBeanList) {
            confirmHealDeadDateList.add(bean.getDate());
            confirmHealDeadConfirmList.add(bean.getConfirm());
            confirmHealDeadHealList.add(bean.getHeal());
            confirmHealDeadDeadList.add(bean.getDead());
        }
        model.addAttribute("confirmHealDeadDateList", new Gson().toJson(confirmHealDeadDateList));
        model.addAttribute("confirmHealDeadConfirmList", new Gson().toJson(confirmHealDeadConfirmList));
        model.addAttribute("confirmHealDeadHealList", new Gson().toJson(confirmHealDeadHealList));
        model.addAttribute("confirmHealDeadDeadList", new Gson().toJson(confirmHealDeadDeadList));
        // rate数据解析
        List<RateBean> rateBeanList = rateService.list();
        List<String> rateDateList = new ArrayList<>();
        List<Double> deadRateList = new ArrayList<>();
        List<Double> healRateList = new ArrayList<>();
        for (RateBean bean : rateBeanList) {
            rateDateList.add(bean.getDate());
            deadRateList.add(bean.getDeadRate());
            healRateList.add(bean.getHealRate());
        }
        model.addAttribute("rateDateList", new Gson().toJson(rateDateList));
        model.addAttribute("deadRateList", new Gson().toJson(deadRateList));
        model.addAttribute("healRateList", new Gson().toJson(healRateList));
        // cityTop数据解析
        List<CityTopBean> cityTopBeanList = cityTopService.list();
        List<String> cityTopNameList = new ArrayList<>();
        List<Integer> cityTopConfirmList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            CityTopBean bean = cityTopBeanList.get(i);
            cityTopNameList.add(bean.getName());
            cityTopConfirmList.add(bean.getConfirm());
        }
        model.addAttribute("cityTopNameList", new Gson().toJson(cityTopNameList));
        model.addAttribute("cityTopConfirmList", new Gson().toJson(cityTopConfirmList));
        // nowConfirmConstitute数据解析
        List<NowConfirmConstituteBean> nowConfirmConstituteBeanList = nowConfirmConstituteService.list();
        List<String> nowConfirmConstituteNameList = new ArrayList<>();
        for (NowConfirmConstituteBean bean : nowConfirmConstituteBeanList) {
            nowConfirmConstituteNameList.add(bean.getName());
        }
        model.addAttribute("nowConfirmConstituteNameList", new Gson().toJson(nowConfirmConstituteNameList));
        model.addAttribute("nowConfirmConstituteBeanList", new Gson().toJson(nowConfirmConstituteBeanList));
        // map数据解析
        List<MapBean> mapBeanList = mapService.list();
        model.addAttribute("mapBeanList", new Gson().toJson(mapBeanList));
        return "index";
    }
}
