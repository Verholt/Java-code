package com.dsv.road.masterdata.model;

import java.io.Serializable;

public class MDGPartyStatusPrimaryKey implements Serializable {
	private static final long serialVersionUID = 1L;
	String statusCode;
	String locality;
	
	 @Override
	    public boolean equals(Object obj) {
	        if(obj instanceof MDGPartyStatusPrimaryKey) {
				MDGPartyStatusPrimaryKey carPk = (MDGPartyStatusPrimaryKey) obj;

				return carPk.getStatusCode().equals(statusCode) && carPk.getLocality().equals(locality);
			}
	 
	        return false;
	    }
	 
	    @Override
	    public int hashCode() {
	        return statusCode.hashCode() + locality.hashCode();
	    }

		public String getStatusCode() {
			return statusCode;
		}

		public void setStatusCode(String statusCode) {
			this.statusCode = statusCode;
		}

		public String getLocality() {
			return locality;
		}

		public void setLocality(String locality) {
			this.locality = locality;
		}

}
