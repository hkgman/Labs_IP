package ru.ulstu.is.sbapp.speaker.service;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import ru.ulstu.is.sbapp.speaker.domain.IMethods;
import ru.ulstu.is.sbapp.speaker.domain.MethodInteger;
import ru.ulstu.is.sbapp.speaker.domain.MethodString;

@Service
public class MethodService {
    private final ApplicationContext applicationContext;
    public MethodService(ApplicationContext applicationContext)
    {
        this.applicationContext=applicationContext;
    }
    public String Sum(Object firstObj,Object secondObj,String type)
    {
        final IMethods method=(IMethods) applicationContext.getBean(type);
        if (method instanceof MethodString){
            return String.format("%s", method.Sum(firstObj,secondObj));
        }else{
            return String.format("%s", method.Sum(Integer.parseInt(firstObj.toString()),Integer.parseInt(secondObj.toString())));
        }
    }
    public String Com(Object firstObj,Object secondObj,String type)
    {
        final IMethods method=(IMethods) applicationContext.getBean(type);
        if (method instanceof MethodString){
            return String.format("%s", method.Com(firstObj,secondObj));
        }else{
            return String.format("%s", method.Com(Integer.parseInt(firstObj.toString()),Integer.parseInt(secondObj.toString())));
        }
    }
    public String Dif(Object firstObj,Object secondObj,String type)
    {
        final IMethods method=(IMethods) applicationContext.getBean(type);
        if (method instanceof MethodString){
            return String.format("%s", method.Dif(firstObj,secondObj));
        }else{
            return String.format("%s", method.Dif(Integer.parseInt(firstObj.toString()),Integer.parseInt(secondObj.toString())));
        }
    }
    public String Div(Object firstObj,Object secondObj,String type)
    {
        final IMethods method=(IMethods) applicationContext.getBean(type);
        if (method instanceof MethodString){
            return String.format("%s", method.Div(firstObj,secondObj));
        }else{
            return String.format("%s", method.Div(Integer.parseInt(firstObj.toString()),Integer.parseInt(secondObj.toString())));
        }
    }
}
