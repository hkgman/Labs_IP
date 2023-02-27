package ru.ulstu.is.sbapp.speaker.domain;

public interface IMethods<T> {
    T Sum(T firstObj,T secondObj);
    T Dif (T firstObj, T secondObj);
    T Com(T firstObj,T secondObj);
    T Div (T firstObj, T secondObj);
}
