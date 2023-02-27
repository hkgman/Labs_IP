package ru.ulstu.is.sbapp.speaker.domain;

import org.springframework.stereotype.Component;

@Component(value = "int")
public class MethodInteger implements IMethods<Integer> {
    @Override
    public Integer Sum(Integer firstObj, Integer secondObj) {
        return firstObj + secondObj;
    }
    @Override
    public Integer Dif(Integer firstObj, Integer secondObj) {
        return firstObj -secondObj;
    }
    @Override
    public Integer Com(Integer firstObj, Integer secondObj) {
        return firstObj * secondObj;
    }
    @Override
    public Integer Div(Integer firstObj, Integer secondObj) {
        int num = secondObj;
        if (num == 0){
            return null;
        }else{
            return firstObj / num;
        }
    }

}
