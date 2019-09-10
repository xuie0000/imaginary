package xuk.imaginary.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author xuie
 * @date 17-8-15
 */
public class DateUtils {
    private static final String MATCH_REGEX = "(\\d+)-(\\d+)-(\\d+)T(\\S+)Z";

    public static String getDate(String s) {
        Matcher m = Pattern.compile(MATCH_REGEX).matcher(s);
        if (!m.matches() || m.groupCount() < 4) {
            return "";
        }
        return m.group(1) + "/" + m.group(2) + "/" + m.group(3);
    }
}
