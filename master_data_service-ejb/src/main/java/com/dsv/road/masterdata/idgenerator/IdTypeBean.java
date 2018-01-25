package com.dsv.road.masterdata.idgenerator;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.interceptor.Interceptors;

import com.dsv.shared.logger.LoggerInterceptor;

@Stateless
@Interceptors(LoggerInterceptor.class)
public class IdTypeBean {
	@Inject
	IdTypeManager idTypeManager;
	private static final long MAX_ID_NUMBER= 999999999999999L;

	public String getId(String prefix) {
		IdType idType = idTypeManager.getIdType(prefix);
		if (idType == null) {
			return null;
		}
		return prefix + idType.getValue();
	}

	public String nextId(String prefix) {
		IdType idType = idTypeManager.getIdType(prefix);
		if (idType == null) {
			idType = new IdType();
			idType.setName(prefix);
			idType.setValue(0L);
		}
		if (idType.getValue() < MAX_ID_NUMBER) {
			idType.setValue(idType.getValue() + 1);
		} else {
			idType.setValue(1L);
		}
		idType = idTypeManager.insertIdType(idType);
		return prefix + idType.getValue();
	}

	public IdTypeManager getIdTypeManager() {
		return idTypeManager;
	}

	public void setIdTypeManager(IdTypeManager idTypeManager) {
		this.idTypeManager = idTypeManager;
	}

	
}
