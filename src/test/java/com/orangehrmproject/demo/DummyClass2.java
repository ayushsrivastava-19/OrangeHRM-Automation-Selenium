package com.orangehrmproject.demo;

import com.demoproject.base.BaseClass;
import org.testng.annotations.Test;

public class DummyClass2 extends BaseClass {
    @Test
    public void dummyMethod2()
    {
        String title = driver.getTitle();
        assert title.equals("OrangeHRM") : "Test Failed - Title Mismatch";
        System.out.println("Test Passed");
    }
}
