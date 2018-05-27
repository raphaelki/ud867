package com.example.test.jokes;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class JokeTeller {

    private final String JOKES_FILENAME = "/jokes.json";
    private Random random;
    private List<String> jokes;

    public JokeTeller() {
        random = new Random();
        jokes = readJokesFromResourceFile();
    }

    private List<String> readJokesFromResourceFile() {
        InputStream inputStream = getClass().getResourceAsStream(JOKES_FILENAME);
        InputStreamReader reader = new InputStreamReader(inputStream);
        JSONParser parser = new JSONParser();
        JSONArray jsonData = null;
        try {
            jsonData = (JSONArray) parser.parse(reader);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        ArrayList<String> jokes = new ArrayList<>(jsonData.size());
        for (int index = 0; index < jsonData.size(); index++) {
            String joke = (String) jsonData.get(index);
            jokes.add(joke);
        }
        return jokes;
    }

    public String getRandomJoke() {
        return jokes.get(random.nextInt(jokes.size()));
    }
}
