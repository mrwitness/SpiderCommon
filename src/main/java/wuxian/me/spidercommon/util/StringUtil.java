package wuxian.me.spidercommon.util;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wuxian on 12/7/2017.
 */
public class StringUtil {
    private StringUtil() {
    }

    //将日期yyyy-MM-ddformat成yyyyMMdd 一来用于直观的看出日期 二来用于比较
    public static Integer formatYYMMDD8(String origin) {
        if (origin == null || origin.length() == 0) {
            return null;
        }

        origin = origin.replaceAll("-", "");
        origin = origin.replace(".", "");
        if (origin.length() != 8) {
            if (origin.length() == 4) {
                origin += "0000";   //format成0月0号....
            } else if (origin.length() == 6) {
                origin += "00";
            } else {
                return null;
            }
        }
        if (origin.length() == 8) {
            return Integer.parseInt(origin);
        }

        return null;
    }

    public static Integer formatYYMMDD6(String origin) {
        if (origin == null || origin.length() == 0) {
            return null;
        }

        origin = origin.replaceAll("-", "");
        origin = origin.replace(".", "");
        if (origin.length() != 6) {
            if (origin.length() == 4) {
                origin += "0000";   //format成0月0号....
            } else {
                return null;
            }
        }
        if (origin.length() == 6) {
            return Integer.parseInt(origin);
        }

        return null;
    }

    public static Integer formatYYMMDD4(String origin) {
        if (origin == null || origin.length() == 0) {
            return null;
        }

        origin = origin.replaceAll("-", "");
        origin = origin.replace(".", "");
        if (origin.length() != 4) {
            return null;
        }
        return Integer.parseInt(origin);
    }

    public static String removeAllBlanks(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    public static String replaceHtmlCharactors(String origin) {
        if (origin == null || origin.length() == 0) {
            return origin;
        }

        String dest = "";
        if (origin != null) {
            Pattern p = Pattern.compile("&middot;");
            Matcher m = p.matcher(origin);
            dest = m.replaceAll("·");

            p = Pattern.compile("&ldquo;|&rdquo;");
            m = p.matcher(dest);
            dest = m.replaceAll("\"");

        }
        return dest;
    }

    public static String replaceHtmlDot(String origin) {

        if (origin == null || origin.length() == 0) {
            return origin;
        }
        String dest = "";
        if (origin != null) {
            Pattern p = Pattern.compile("&middot;");
            Matcher m = p.matcher(origin);
            dest = m.replaceAll("·");
        }
        return dest;
    }
}

