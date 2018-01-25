package com.dsv.road.shared.masterdata.dto;

import java.util.ArrayList;
import java.util.List;

public class DtoProfileKeyList {
	
	List<DtoProfileKey> list;

	public DtoProfileKeyList() {
		// On purpose for Dozer, Jackson etc.
	}

	public DtoProfileKeyList(List<DtoProfileKey> list) {
		this.list = list;
	}

	public List<DtoProfileKey> getList() {
		if(list == null){
			list = new ArrayList<>();
		}
		return list;
	}

	public void setList(List<DtoProfileKey> list) {
		this.list = list;
	}	

}
