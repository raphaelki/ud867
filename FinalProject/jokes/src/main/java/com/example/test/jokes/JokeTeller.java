package com.example.test.jokes;

import java.util.Random;

public class JokeTeller {

    private Random random;

    public JokeTeller(){
        random = new Random();
    }

    private String[] jokes = {
            "Joke 1",
            "Joke 2",
            "Joke 3",
            "Joke 4"
    };

    public String getRandomJoke(){
        return jokes[random.nextInt(jokes.length)];
    }
}
