package org.cold92.handler;

import org.cold92.service.TotalTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
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
    private TotalTableService service;

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
     */
    @Async
    public void sendByTemplate() {
        List<String> list = new ArrayList<>();
        list.add("人生重要的，不是能力而是性格；不是成功而是价值；不是你认识多少人，而是在你离开人世时，有多少人认识了你！不是他所购买到的，而是他所创造的；不是他所得到的，而是他所付出的；不是他所学到的，而是他所传授的。");
        list.add("用快乐去奔跑，用心去倾听，用思维去发展，用努力去奋斗，用目标去衡量，用爱去生活。");
        list.add("每个人的一生都有许多梦想，但如果其中一个不断搅扰着你，剩下的就仅仅是行动了。");
        list.add("人生是由咸甜苦辣所组成，学会适应，让你的环境变得明亮；学会调节，让你的心情不再忧伤；学会宽容，让你的生活没有烦恼；学会奉献，让你的人生充满阳光。其实天很蓝，阴云总会散；其实海不宽，彼岸连此岸；其实梦很浅，万物皆自然；其实泪也甜，当你心如愿。人生原本就是修行的道场。");
        list.add("人生充满了起起落落。关键在于，在顶端时好好享受；在低谷时不失勇气。");
        list.add("路，走不通时，学会拐弯，结，解不开时，学会忘记；事，难以做时，学会放下；缘，渐行远时，选择随意。");
        try {
            MimeMessage message = mailSender.createMimeMessage();
            // 对MimeMessage的工具类, multipart: true表示支持附件
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setSubject("来自华三炮的问候");
            // thymeleaf数据动态渲染所需要的上下文对象
            Context context = new Context();
            // 作为context渲染html数据的容器
            Map<String, Object> map = new HashMap<>();
            map.put("title", "每日都要喝一碗鸡汤");
            // 随机名言一条发送
            int index = new Random().nextInt(list.size());
            map.put("content", list.get(index));
            context.setVariables(map);
            // 渲染出来最后的结果，真正要发送的邮件内容, template表示渲染的html
            String result = this.engine.process("mail", context);
            // html: true表示邮件内容是html不是纯文本
            helper.setText(result, true);
            helper.setFrom("326173115@qq.com");
            helper.setTo("229267322@qq.com");
            helper.setTo("1606988616@qq.com");
            helper.setTo("201800995@qq.com");
            //可以批量发送
//            helper.setTo("973205592@qq.com");
//            helper.setTo("876418599@qq.com");
//            helper.setTo("1528769336@qq.com");
            // 添加附件
//            String filePath = "D:\\Wallpaper\\003.jpg";
            // 文件资源其中包含附件
//            FileSystemResource resource = new FileSystemResource(new File(filePath));
//            helper.addAttachment("003.jpg", resource);
            System.out.println("正在发送邮件...");
            mailSender.send(message);
            System.out.println("已发送");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
