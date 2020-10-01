package org.cold92;

import org.cold92.handler.TencentDataHandler;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("org.cold92.mapper")
@EnableScheduling // 打開對定時任務的使用
public class DemoApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(DemoApplication.class, args);
		TencentDataHandler.analysisJsonData();
	}

}
