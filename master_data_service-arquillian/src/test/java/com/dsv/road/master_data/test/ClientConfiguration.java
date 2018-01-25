package com.dsv.road.master_data.test;

/**
 * Created by EXT.K.Theilgaard on 27-05-2015.
 */
public class ClientConfiguration {
    public static String baseURL() {
        String contextPath = System.getProperty("contextPath",
                "/master_data_service-web");
        String httpPort = System.getProperty("httpPort", "9091");
        return "http://localhost" + ":" + httpPort + contextPath + "/rest";
    }
}
