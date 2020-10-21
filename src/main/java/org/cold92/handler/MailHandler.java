package org.cold92.handler;

import org.cold92.bean.CityBean;
import org.cold92.bean.OrderBean;
import org.cold92.service.CityService;
import org.cold92.service.OrderService;
import org.cold92.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.MimeMessage;
import java.util.*;

/**
 * SpringBoot封装的邮件业务功能
 */
@Component
public class MailHandler {

    @Autowired
    private JavaMailSender mailSender;
    // 可以将动态数据渲染到html中去
    @Autowired
    private TemplateEngine engine;
    @Autowired
    private OrderService orderService;
    @Autowired
    private CityService cityService;

    /**
     * 发送简单邮件：只有文本内容的邮件
     */
    public void send() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("标题");
        message.setText("内容");
        message.setTo("201800995@qq.com");
        message.setFrom("326173115@qq.com");
        System.out.println("正在发送邮件");
        mailSender.send(message);
        System.out.println("已发送");
    }

    /**
     * 发送复杂邮件: 使用thymeleaf渲染一个html页面作为邮件内容
     * @Async 表示该方法为异步任务，要实现的逻辑为先返回‘发送成功’信息再执行发送方法，在分布式中运用
     * 定时自动持久化实时数据 (每一个小时持久化数据一次)
     * @Scheduled：使用cron表达式作为参数进行定时执行方法操作
     */
    @Async
    @Scheduled(cron = "0/15 0/1 * * * ?")
    public void sendByTemplate() {
        try {
            List<OrderBean> orderBeanList = orderService.getAllOrders();
            for (OrderBean order : orderBeanList) {
                String username = order.getUsername();
                String email = order.getEmail();
                String cityName = order.getCity();
                CityBean city = cityService.getCityByName(cityName);
//                String content = "亲爱的" + username + "您好，以下是今日订阅疫情数据"
//                        + "城市：" + cityName + "当前感染人数：" + city.getNowConfirm()
//                        + "累计感染人数：" + city.getConfirm() + "疑似感染人数：" + city.getSuspect()
//                        + "累计治愈人数：" + city.getHeal() + "累计死亡人数：" + city.getDead();
                MimeMessage message = mailSender.createMimeMessage();
                // 对MimeMessage的工具类, multipart: true表示支持附件
                MimeMessageHelper helper = new MimeMessageHelper(message, true);
                helper.setSubject("今日" + city.getArea() + "疫情数据统计");
                // thymeleaf数据动态渲染所需要的上下文对象
                Context context = new Context();
                // 作为context渲染html数据的容器
                Map<String, Object> map = new HashMap<>();
                map.put("title", "今日" + city.getArea() + "疫情数据统计");
                // 邮件内容
                context.setVariables(map);
                context.setVariable("username", username);
                context.setVariable("city", cityName);
                context.setVariable("nowConfirm", city.getNowConfirm());
                context.setVariable("confirm", city.getConfirm());
                context.setVariable("suspect", city.getSuspect());
                context.setVariable("heal", city.getHeal());
                context.setVariable("dead", city.getDead());
                context.setVariable("date", DateUtil.getCurrentTime()); //获取当前时间
                // 渲染出来最后的结果，真正要发送的邮件内容, template表示渲染的html
                String result = this.engine.process("mail", context);
                // html: true表示邮件内容是html不是纯文本
                helper.setText(result, true);
                helper.setFrom("326173115@qq.com");
                //可以批量发送
                helper.setTo(email);
                // 添加附件
//            String filePath = "D:\\Wallpaper\\003.jpg";
//                 文件资源其中包含附件
//            FileSystemResource resource = new FileSystemResource(new File(filePath));
//            helper.addAttachment("003.jpg", resource);
                System.out.println("已发送邮件");
                mailSender.send(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
