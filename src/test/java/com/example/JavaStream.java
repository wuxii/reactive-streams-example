package com.example;

import org.junit.Test;

import java.util.Arrays;

/**
 * @author wuxii
 */
public class JavaStream {

    @Test
    public void streamTest() {
        Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 0)
                .parallelStream()
                .forEach(System.out::print);
    }

}
