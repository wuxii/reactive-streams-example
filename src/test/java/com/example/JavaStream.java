package com.example;

import lombok.extern.log4j.Log4j2;
import org.junit.Test;

import java.util.Arrays;

/**
 * @author wuxii
 */
@Log4j2
public class JavaStream {

    @Test
    public void streamTest() {
        Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 0)
                .parallelStream()
                .forEach(log::info);
    }

}
