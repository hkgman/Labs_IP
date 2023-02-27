package ru.ulstu.is.sbapp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.ulstu.is.sbapp.speaker.service.MethodService;

@SpringBootTest
class SbappApplicationTests {
	@Autowired
	MethodService methodService;
	@Test
	void testSumInt() {
		final String res=methodService.Sum(1,1,"int");
		Assertions.assertEquals(2,Integer.parseInt(res));
	}
	@Test
	void testDifInt() {
		final String res=methodService.Dif(1,1,"int");
		Assertions.assertEquals(0,Integer.parseInt(res));
	}
	@Test
	void testComInt() {
	final String res=methodService.Com(3,2,"int");
		Assertions.assertEquals(6,Integer.parseInt(res));
	}
	@Test
	void testDivInt() {
		final String res=methodService.Div(6,2,"int");
		Assertions.assertEquals(3,Integer.parseInt(res));
	}
	@Test
	void testSumStr() {
		final String res=methodService.Sum("1","1","string");
		Assertions.assertEquals("11",res);
	}
	@Test
	void testDifStr() {
		final String res=methodService.Dif("2133","2","string");
		Assertions.assertEquals("133",res);
	}
	@Test
	void testComStr() {
		final String res=methodService.Com("abc","ba","string");
		Assertions.assertEquals("abcabcabc",res);
	}
	@Test
	void testDivStr() {
		final String res=methodService.Div("1010","2","string");
		Assertions.assertEquals("false",res);
	}
}
