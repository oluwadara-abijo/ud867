package com.example.jokeprovider;

import java.util.Random;

public class JokeClass {

    public static String getJoke() {

        //An array of seven jokes
        String[] jokes = {"Do you think February can march? \nNo, but I think April may.",
                "Today at the bank, an old lady asked me to help check her balance. So I pushed her over.",
                "I bought some shoes from a drug dealer. I don't know what he laced them with, but I've been tripping all day.",
                "I told my girlfriend she drew her eyebrows too high. She seemed surprised.",
                "My dog used to chase people on a bike a lot. It got so bad, finally I had to take his bike away.",
                "I'm so good at sleeping. I can do it with my eyes closed.",
                "My friend says to me: \"What rhymes with orange\" I said: \"No it doesn't\"",
        };

        //Generate a random button
        Random random = new Random();
        int randomNumber = random.nextInt(jokes.length);

        return jokes[randomNumber];
    }
}
