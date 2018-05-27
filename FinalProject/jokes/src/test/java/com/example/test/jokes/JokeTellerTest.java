package com.example.test.jokes;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

public class JokeTellerTest {

    @Test
    public void getJoke_responseIsNotEmpty() {
        JokeTeller jokeTeller = new JokeTeller();
        assertThat(jokeTeller.getRandomJoke(), is(not("")));
    }
}