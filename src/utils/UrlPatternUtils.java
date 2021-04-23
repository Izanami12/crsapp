package utils;

import java.util.Collection;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import javax.servlet.http.HttpServletRequest;

public class UrlPatternUtils {
    
    private static boolean hasUrlPattern(final ServletContext servletContext, final String urlPattern) {

        final Map<String, ? extends ServletRegistration> map = servletContext.getServletRegistrations();

        for (final String servletName : map.keySet()) {
            final ServletRegistration sr = map.get(servletName);

            final Collection<String> mappings = sr.getMappings();
            if (mappings.contains(urlPattern)) {
                return true;
            }

        }
        return false;
    }

    // servletPath:
    // ==> /spath
    // ==> /spath/*
    // ==> *.ext
    // ==> /
    public static String getUrlPattern(final HttpServletRequest request) {
        final ServletContext servletContext = request.getServletContext();
        final String servletPath = request.getServletPath();
        final String pathInfo = request.getPathInfo();

        String urlPattern = null;
        if (pathInfo != null) {
            urlPattern = servletPath + "/*";
            return urlPattern;
        }
        urlPattern = servletPath;

        boolean has = UrlPatternUtils.hasUrlPattern(servletContext, urlPattern);
        if (has) {
            return urlPattern;
        }
        final int i = servletPath.lastIndexOf('.');
        if (i != -1) {
            final String ext = servletPath.substring(i + 1);
            urlPattern = "*." + ext;
            has = UrlPatternUtils.hasUrlPattern(servletContext, urlPattern);

            if (has) {
                return urlPattern;
            }
        }
        return "/";
    }
}
