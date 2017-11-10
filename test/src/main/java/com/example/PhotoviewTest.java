package com.example;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by xuie on 17-9-7.
 */

public class PhotoviewTest {
    // 00AO0001|2274105
    public static void main(String[] args) {
        String s = "00AO0001|2274105";
        String MATCH_REGEX = "(\\S+)\\|(\\S+)";
        Matcher m = Pattern.compile(MATCH_REGEX).matcher(s);
        if (!m.matches()) {
            System.out.println("Regex did not match");
            return;
        } else if (m.groupCount() < 2) {
            System.out.println("Regex match only returned " + m.groupCount() + " groups");
            return;
        }
        System.out.println(m.group(1) + ", " + m.group(2));
    }

    private void test() {
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
