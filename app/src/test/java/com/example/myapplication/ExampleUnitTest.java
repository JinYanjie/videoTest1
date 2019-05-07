package com.example.myapplication;

import com.example.myapplication.eit_test.Count;

import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
//        assertEquals(4, 2 + 2);
        int count = new Count().count();
        System.out.println("=====             " + count);
    }
}