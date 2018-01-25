package com.dsv.road.masterdata.service;

import com.ibm.websphere.security.auth.WSSubject;
import com.ibm.websphere.security.cred.WSCredential;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.Subject;
import java.util.Iterator;
import java.util.Set;

public class WASSecurityUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(WASSecurityUtil.class);
    
	public static String getAuthenticatedUserID() {
		try {
			Subject securitySubject = WSSubject.getRunAsSubject();
			if (securitySubject != null) {
				Set<WSCredential> securityCredentials = securitySubject.getPublicCredentials(WSCredential.class);
				Iterator<WSCredential> i = securityCredentials.iterator();
				if (i.hasNext()) {
					WSCredential securityCredential = i.next();
					return securityCredential.getSecurityName();
				}
			}
		} catch (Exception e) {
		    LOGGER.error("Error while access to AuthenticatedUserID", e);
		    throw new SecurityException(e);
		}
		return null;
	}
}
