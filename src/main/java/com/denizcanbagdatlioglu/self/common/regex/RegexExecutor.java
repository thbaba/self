package com.denizcanbagdatlioglu.self.common.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexExecutor{

    public static boolean atLeastOne(String regex, String value) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(value);
        return matcher.find();
    }

    public static boolean noOne(String regex, String value) {
        return !atLeastOne(regex, value);
    }

}
