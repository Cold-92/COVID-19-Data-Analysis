package org.cold92.controller;

import org.cold92.bean.DataBean;
import org.cold92.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/covid")
public class DataController {

    @Autowired
    private DataService dataService;

    @GetMapping
    public String list(Model model) {
        List<DataBean> beanList = dataService.list();
        model.addAttribute("beanList", beanList);
        return "list.html";
    }
}
