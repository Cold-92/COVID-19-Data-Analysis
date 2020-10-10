package org.cold92;

import org.cold92.handler.MailHandler;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {

	@Autowired
	private MailHandler handler;

	@Test
	void contextLoads() {
		handler.send();
	}
}
