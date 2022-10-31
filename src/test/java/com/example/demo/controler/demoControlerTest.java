package com.example.demo.controler;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {

	@Test
	void contextLoads() {
		demoControler controler = new demoControler();
		String demoResponse = controler.GetDemo();
		
	}

}





