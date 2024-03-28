package com.project.mockito;

public class MyClassUnderTest {
    private MyService service;

    public MyClassUnderTest(MyService service) {
        this.service = service;
    }

    public int performAddition(int a, int b) {
        return service.add(a, b);
    }
}

