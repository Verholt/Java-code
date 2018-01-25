package com.dsv.road.master_data.client;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import com.dsv.shared.properties.Properties;
import org.apache.wink.client.ClientConfig;
import org.apache.wink.client.RestClient;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

public class ClientApplication extends Application{
	 private static final int TIMEOUT = 3000000;
	 private static String baseUrl = null;
	private Set<Object> singletons = Collections.emptySet();

	    public static RestClient getClient() {
			ClientApplication clientApplication = new ClientApplication();
			Set<Object> s = new HashSet<>();
			s.add(new JacksonJsonProvider());
			clientApplication.setSingletons(s);
			ClientConfig clientConfig = new ClientConfig()
					.applications(clientApplication);
			clientConfig.connectTimeout(TIMEOUT);
			clientConfig.readTimeout(TIMEOUT);
			return new RestClient(clientConfig);
	    }

		public static String baseURL(){
			return baseURL(10039);
		}
	    
	    public static String baseURL(int port) {
	    	if (baseUrl == null) {
				baseUrl = Properties.getDsvMasterDataResturl(port);

			}
			return baseUrl;
	    }

	    public static String localBaseUrl() {
	    	StringBuilder localBaseUrl = new StringBuilder();

	    	localBaseUrl.append("http://localhost:10039");
	    	localBaseUrl.append(System.getProperty("com.dsv.road.masterdata.service.contextPath", "/master_data_service-web"));
	    	localBaseUrl.append("/rest");

			return localBaseUrl.toString();
	    }

		@Override
	    public Set<Object> getSingletons() {
	        return singletons;
	    }
	    
	    public void setSingletons(final Set<Object> singletons) {
	        this.singletons = singletons;
	    }

}
