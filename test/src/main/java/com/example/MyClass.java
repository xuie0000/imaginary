package com.example;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyClass {
    private static final String TAG = "MyClass";

    public static void main(String[] args) {
        System.out.println("hello.");
        String dateString = "2017-08-11T14:05:53.749Z";
        String MATCH_REGEX = "(\\d+)-(\\d+)-(\\d+)T(\\S+)Z";
        Matcher m = Pattern.compile(MATCH_REGEX).matcher(dateString);
        if (!m.matches()) {
            System.out.println("Regex did not match");
            return;
        } else if (m.groupCount() < 4) {
            System.out.println("Regex match only returned " + m.groupCount() + " groups");
            return;
        }
        System.out.println(m.group(1) + ", " + m.group(2) + ", " + m.group(3) + ", " + m.group(4));

    }
}
