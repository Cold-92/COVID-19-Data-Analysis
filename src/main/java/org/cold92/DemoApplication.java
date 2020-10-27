package org.cold92;

import org.cold92.handler.TencentDataHandler;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
//@SpringBootApplication(exclude = SecurityAutoConfiguration.class)// 启动但是忽略spring-security的认证
@MapperScan("org.cold92.mapper")// MP扫描
@EnableScheduling // 打开对定时任务调用
@EnableAsync //打开异步任务开关
@EnableCaching // 开启缓存
public class DemoApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(DemoApplication.class, args);
	}
}
