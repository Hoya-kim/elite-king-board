package board.api.utils;

import javax.servlet.http.HttpServletRequest;

public class RequestUtils {

    public static String getRemoteAddress(HttpServletRequest request) {
        String remoteAddr = request.getHeader("X-FORWARDED-FOR");

        if (remoteAddr == null) {
            remoteAddr = request.getHeader("Proxy-Client-IP");
        }
        if (remoteAddr == null) {
            remoteAddr = request.getHeader("WL-Proxy-Client-IP");
        }
        if (remoteAddr == null) {
            remoteAddr = request.getRemoteAddr();
        }
        return remoteAddr;
    }

}
