package ru.ulstu.is.sbapp.speaker.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.ulstu.is.sbapp.speaker.service.MethodService;

@RestController
public class UserController {
    private final MethodService methodService;
    public UserController(MethodService methodService)
    {
        this.methodService=methodService;
    }

    @GetMapping("/sum")
    public String sum(@RequestParam(value = "firstObj",defaultValue = "0") String firstObj,
                      @RequestParam(value = "secondObj",defaultValue = "0") String secondObj,
                      @RequestParam(value = "type",defaultValue = "int")String type)
    {
        Object a = firstObj;
        Object b = secondObj;
        return methodService.Sum(a,b,type);
    }
    @GetMapping("/dif")
    public String dif(@RequestParam(value = "firstObj",defaultValue = "0") String firstObj,
                      @RequestParam(value = "secondObj",defaultValue = "0") String secondObj,
                      @RequestParam(value = "type",defaultValue = "int")String type)
    {
        Object a = firstObj;
        Object b = secondObj;
        return methodService.Dif(a,b,type);
    }
    @GetMapping("/com")
    public String com(@RequestParam(value = "firstObj",defaultValue = "0") String firstObj,
                      @RequestParam(value = "secondObj",defaultValue = "0") String secondObj,
                      @RequestParam(value = "type",defaultValue = "int")String type)
    {
        Object a = firstObj;
        Object b = secondObj;
        return methodService.Com(a,b,type);
    }
    @GetMapping("/div")
    public String div(@RequestParam(value = "firstObj",defaultValue = "0") String firstObj,
                      @RequestParam(value = "secondObj",defaultValue = "0") String secondObj,
                      @RequestParam(value = "type",defaultValue = "int")String type)
    {
        Object a = firstObj;
        Object b = secondObj;
        return methodService.Div(a,b,type);
    }
}
