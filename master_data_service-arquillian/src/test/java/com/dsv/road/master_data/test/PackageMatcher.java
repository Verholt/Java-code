package com.dsv.road.master_data.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by EXT.K.Theilgaard on 26/08/2015.
 */
public class PackageMatcher {
    private static final Logger LOGGER = LoggerFactory.getLogger(PackageMatcher.class);

    public static String getMasterDataServiceEJB(String paths) {
        Pattern pattern = Pattern.compile("WEB-INF\\/lib\\/master_data_service-ejb.*\\.jar");
        Matcher m = pattern.matcher(paths);
        if (m.find()) {
            return m.group();
        } else {
            LOGGER.error("FATAL: Did not find master_data_service-ejb in package");
            return null;
        }
    }
}
