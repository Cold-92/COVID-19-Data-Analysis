package org.cold92.controller;

import com.google.gson.Gson;
import org.cold92.bean.DataBean;
import org.cold92.bean.SingleChartBean;
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
        List<DataBean> beanList = dataService.list();
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
        List<DataBean> beanList = dataService.listById(id);
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
}
