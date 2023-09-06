package org.example;

import org.springframework.stereotype.Component;

@Component
public class MyBean {

    @InjectRandomStringFromList(value = {"String1", "String2", "String3"}, prefix = "RandomPrefix-")
    private String randomString;

    public String getRandomString() {
        return randomString;
    }

    public void setRandomString(String randomString) {
        this.randomString = randomString;
    }
}

