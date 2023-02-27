package ru.ulstu.is.sbapp.speaker.domain;

import org.springframework.stereotype.Component;

@Component(value = "string")
public class MethodString implements IMethods<String> {
    @Override
    public String Sum(String firstObj,String secondObj)
    {
        return firstObj.concat(secondObj);
    }
    @Override
    public String Dif(String firstObj,String secondObj)
    {
        if(firstObj.contains(secondObj))
        {
            return firstObj.replace(secondObj,"");
        }
        return (firstObj);
    }
    @Override
    public String Com(String firstObj,String secondObj)
    {
        String res = (firstObj);
        for (int i = 0; i < (secondObj).length(); i++) {
            res = Sum(res, firstObj);
        }
        return res;
    }
    @Override
    public String Div(String firstObj,String secondObj)
    {
        if (firstObj.contains(secondObj)){
            return "true";
        }else{
            return "false";
        }
    }
}
