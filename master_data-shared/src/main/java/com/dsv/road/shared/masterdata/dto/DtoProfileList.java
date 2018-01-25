package com.dsv.road.shared.masterdata.dto;

import java.util.ArrayList;
import java.util.List;

public class DtoProfileList {
	
	List<DtoProfile> list;

	public DtoProfileList() {
		// On purpose for Dozer, Jackson etc.
	}

	public DtoProfileList(List<DtoProfile> list) {
		this.list = list;
	}

	public List<DtoProfile> getList() {
		if(list == null){
			list = new ArrayList<>();
		}
		return list;
	}

	public void setList(List<DtoProfile> list) {
		this.list = list;
	}

}
