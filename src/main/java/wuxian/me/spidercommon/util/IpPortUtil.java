package wuxian.me.spidercommon.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wuxian on 11/6/2017.
 */
public class IpPortUtil {

    static Pattern ipPattern = null;
    static Pattern portPattern = null;

    static {
        String reg1 = "[0-9]+[.][0-9]+[.][0-9]+[.][0-9]+";
        ipPattern = Pattern.compile(reg1);

        String reg2 = "[0-9]+";
        portPattern = Pattern.compile(reg2);

    }

    private IpPortUtil() {
    }

    public static boolean isValidIpPort(String ipport) {
        if (ipport == null || ipport.length() == 0) {
            return false;
        }

        return isVaildIpAndPort(ipport.split(":"));
    }

    public static boolean isVaildIpAndPort(String[] ipport) {
        if (ipport == null || ipport.length != 2) {
            return false;
        }

        Matcher matcher = ipPattern.matcher(ipport[0]);
        if (!matcher.matches()) {
            return false;
        }

        matcher = portPattern.matcher(ipport[1]);
        return matcher.matches();
    }
}

