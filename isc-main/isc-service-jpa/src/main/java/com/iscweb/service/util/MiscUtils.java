package com.iscweb.service.util;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Utility class for miscellaneous methods.
 *
 * @author skurenkov
 */
@Slf4j
public class MiscUtils {

    /**
     * A human-readable formatter. Useful when printing dates in generated content.
     */
    public static final String READABLE_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final DateTimeFormatter READABLE_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(READABLE_DATE_TIME_FORMAT);

    /**
     * Used as a monitor for thread pause block.
     */
    private static final MiscUtils monitor = new MiscUtils();

    public static String generateGuid() {
        return UUID.randomUUID().toString();
    }

    /**
     * Pause the current thread execution for a the provided amount of milliseconds.
     *
     * @param milliSec how long do you want me to sleep?
     */
    public static void pause(int milliSec) {
        if (milliSec > 0) {
            synchronized (monitor) {
                try {
                    log.debug("Pausing this thread for {} milliSec", milliSec);
                    monitor.wait(milliSec);
                } catch (InterruptedException ex) {
                    // No op. This is a common way to wake a paused thread.
                }
            }
        }
    }

    /**
     * Extracts links from an HTML snippet.
     *
     * @param content HTML content to extract from.
     * @return list of extracted links.
     */
    public static List<String> extractLinks(String content) {
        final ArrayList<String> result = Lists.newArrayList();

        Document doc = Jsoup.parse(content);

        Elements links = doc.select("a[href]");
        Elements media = doc.select("[src]");
        Elements imports = doc.select("link[href]");

        for (Element link : links) {
            result.add(link.attr("abs:href"));
        }
        for (Element src : media) {
            result.add(src.attr("abs:src"));
        }
        for (Element link : imports) {
            result.add(link.attr("abs:href"));
        }

        return result;
    }

    /**
     * Writes a DateTime in a human-readable format.
     *
     * @return String containing the normalized representation of the instant
     */
    public static String formatReadableDateTime(ZonedDateTime dateTime) {
        return dateTime != null ? READABLE_DATE_TIME_FORMATTER.format(dateTime) : null;
    }

    /**
     * Attempts to convert a string into a URL object. If successful, it extracts the host.
     * <br> The fallback behavior is to remove anything before the string {@literal "://"}
     * and anything after the last slash.
     * <quote>
     * getCleanDomainFromUrl("http://www.iscweb.io/login.html") -> "www.iscweb.io"
     * getCleanDomainFromUrl("ssh://github.com/poi-projects") -> "github.com"
     *
     * @param value string that references a URL.
     * @return 'clean' version of the string.
     */
    public static String getCleanDomainFromUrl(String value) {
        String result;
        try {
            URL url = new URL(value);
            result = url.getHost();
        } catch (MalformedURLException e) {
            if (value.contains("://")) {
                value = value.substring(value.indexOf("://") + 3);
            }
            if (value.contains("/")) {
                value = value.substring(0, value.indexOf("/"));
            }
            if (value.contains(":")) {
                value = value.substring(0, value.indexOf(":"));
            }
            result = value;
        }
        return result;
    }
}
