package com.company;

public class Pair<T1,T2> {
    private T1 element1;
    private T2 element2;
    public Pair(T1 t1, T2 t2) {
        element1 = t1;
        element2 = t2;
    }
    public T1 getElement1() {
        return element1;
    }
    public T2 getElement2() {
        return element2;
    }
}
