package org.cold92.controller;

import org.cold92.handler.MailHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class MailController {

    @Autowired
    private MailHandler mailHandler;

    /**
     * 发送邮件
     * @return
     */
    @GetMapping("/send")
    public String send() {
        mailHandler.sendByTemplate();
        return "success";
    }
}
