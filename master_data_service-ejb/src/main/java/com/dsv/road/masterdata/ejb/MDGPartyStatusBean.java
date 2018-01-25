package com.dsv.road.masterdata.ejb;

import com.dsv.road.masterdata.jpa.MDGPartyStatusManager;
import com.dsv.road.masterdata.model.MDGPartyStatus;
import com.dsv.road.shared.masterdata.dto.DtoMDGPartyStatus;
import com.dsv.shared.dozer.DozerUtility;
import com.dsv.shared.logger.LoggerInterceptor;
import org.dozer.Mapper;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.sql.Timestamp;

/**
 * @author ext.jesper.munkholm
 * 
 *         This implements the business layer of the customer order module
 */
@Stateless
@Interceptors(LoggerInterceptor.class)
public class MDGPartyStatusBean {

	@Inject
	MDGPartyStatusManager mDGPartyStatusManager;

	Mapper mapper = DozerUtility.getMapper();

	public DtoMDGPartyStatus getMDGPartyStatus(String statusCode,String locality) {
		MDGPartyStatus mDGPartyStatus = mDGPartyStatusManager.getMDGPartyStatus(statusCode,locality);
		if (mDGPartyStatus != null) {
			return mapper.map(mDGPartyStatus, DtoMDGPartyStatus.class);
		} else {
			return null;
		}
	}

	public DtoMDGPartyStatus createMDGPartyStatus(DtoMDGPartyStatus dtoMDGPartyStatus) {
		MDGPartyStatus mDGPartyStatus = mapper.map(dtoMDGPartyStatus, MDGPartyStatus.class);
		mDGPartyStatus.setCreatedAt(new Timestamp(System.currentTimeMillis()));
		mDGPartyStatus.setCreatedBy("Manually created");
		mDGPartyStatus = mDGPartyStatusManager.createMDGPartyStatus(mDGPartyStatus);
		return mapper.map(mDGPartyStatus, DtoMDGPartyStatus.class);
	}

	public boolean deleteMDGPartyStatus(String statusCode, String locality) {
		return mDGPartyStatusManager.deleteMDGPartyStatus(statusCode, locality);
	}

	public DtoMDGPartyStatus updateMDGPartyStatus(DtoMDGPartyStatus newMDGPartyStatus) {
		MDGPartyStatus mDGPartyStatus = mapper.map(newMDGPartyStatus, MDGPartyStatus.class);
		mDGPartyStatus.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
		mDGPartyStatus.setUpdatedBy("Manually updated");
		mDGPartyStatus = mDGPartyStatusManager.updateMDGPartyStatus(mDGPartyStatus);
		return mapper.map(mDGPartyStatus, DtoMDGPartyStatus.class);
	}

}