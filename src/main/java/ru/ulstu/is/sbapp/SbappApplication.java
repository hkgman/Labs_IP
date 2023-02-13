package ru.ulstu.is.sbapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SbappApplication {

	public static void main(String[] args) {
		SpringApplication.run(SbappApplication.class, args);
	}
	@GetMapping("/String")
	public String Vvod(@RequestParam(value = "str", defaultValue = "none") String str) {
		return String.format("Вы ввели %s!", str);
	}
	@GetMapping("/sum")
	public String sum(@RequestParam(required = false,defaultValue = "0") float firstNum,
					  @RequestParam(required = false,defaultValue = "0") float secondNum)
	{
		return Float.toString(firstNum+secondNum);
	}
	@GetMapping("/dif")
	public String dif(@RequestParam(required = false,defaultValue = "0") float firstNum,
					  @RequestParam(required = false,defaultValue = "0") float secondNum)
	{
		return Float.toString(firstNum-secondNum);
	}
	@GetMapping("/com")
	public String com(@RequestParam(required = false,defaultValue = "0") float firstNum,
					  @RequestParam(required = false,defaultValue = "0") float secondNum)
	{
		return Float.toString(firstNum*secondNum);
	}
	@GetMapping("/div")
	public String div(@RequestParam(required = false,defaultValue = "0") float firstNum,
					  @RequestParam(required = false,defaultValue = "0") float secondNum)
	{
		if(secondNum==0)
		{
			return "Error";
		}
		return Float.toString(firstNum/secondNum);
	}
}

