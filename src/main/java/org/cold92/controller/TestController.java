package org.cold92.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试Spring-Security权限访问控制
 */
@RestController
public class TestController {

    @GetMapping("/read")
    public String read() {
        return "查看数据";
    }

    @GetMapping("/update")
    public String update() {
        return "更新数据";
    }
}
