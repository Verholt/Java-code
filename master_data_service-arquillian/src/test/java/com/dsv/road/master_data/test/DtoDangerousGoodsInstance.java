package com.dsv.road.master_data.test;

import com.dsv.road.shared.masterdata.dto.*;

import java.util.ArrayList;
import java.util.List;

public class DtoDangerousGoodsInstance {

	public static DtoDangerousGoodsBundle getDtoDangerousGoodsBundle() {
		DtoDangerousGoodsBundle dangerousGoodsBundle = new DtoDangerousGoodsBundle();
		dangerousGoodsBundle = fillDangerousGoods(dangerousGoodsBundle);
		return dangerousGoodsBundle;
	}

	public static DtoDangerousGoodsBundle fillDangerousGoods(DtoDangerousGoodsBundle dtoDangerousGoodsBundle) {
		List<DtoDangerousGoods> dtoDangerousGoodsList = new ArrayList<>();
		dtoDangerousGoodsList.add(getDangerousGoods(2L));
		dtoDangerousGoodsList.add(getDangerousGoods(3L));
		dtoDangerousGoodsList.add(getDangerousGoods(4L));
		dtoDangerousGoodsBundle.setDangerousGoodsList(dtoDangerousGoodsList);
		return dtoDangerousGoodsBundle;
	}

	public static DtoDangerousGoods getDangerousGoods(long id){
		DtoDangerousGoods dtoDangerousGoods = new DtoDangerousGoods();
		dtoDangerousGoods.setAddDesc("adrDesc");
		dtoDangerousGoods.setUnNumber("00015"+id);
		dtoDangerousGoods.setCreatedBy("UNAUTHENTICATED");
		dtoDangerousGoods.setIata(getDangerousGoodsDivision());
		dtoDangerousGoods.setImdg(getDangerousGoodsDivision());
		dtoDangerousGoods.setAdr(getAdr());
		return fillDangerousGoodsText(dtoDangerousGoods,id);
	}

	private static DtoDangerousGoodsDivision getDangerousGoodsDivision(){
		DtoDangerousGoodsDivision division = new DtoDangerousGoodsDivision();
		return fillDangerousGoodsDivision(division);

	}
	private static DtoDangerousGoodsDivision fillDangerousGoodsDivision(DtoDangerousGoodsDivision division) {
		division.setExceptedQuantities("42");
		division.setLabels("label");
		division.setPackingGroup("II");
		division.setSpecialProvisions("bread");
		division.setClassId("42");
		division.setDangerous(true);
		return division;
	}

	private static DtoDangerousGoodsAdr getAdr(){
		DtoDangerousGoodsAdr adr = new DtoDangerousGoodsAdr();
		fillDangerousGoodsDivision(adr);
		adr.setClassifCode("U238");
		adr.setTransportCategory("13");
		adr.setTunnelCode("aX42");
		adr.setDangerous(false);
		return adr;
	}


	public static DtoDangerousGoods fillDangerousGoodsText(DtoDangerousGoods dtoDangerousGoods, Long id) {
		List<DtoDangerousGoodsText> dtoDangerousGoodsTexts = new ArrayList<>();
		dtoDangerousGoodsTexts.add(getDangerousGoodstext(id * 10L + 1L));
		dtoDangerousGoodsTexts.add(getDangerousGoodstext(id * 10L + 2L));
		dtoDangerousGoodsTexts.add(getDangerousGoodstext(id * 10L + 3L));
		dtoDangerousGoods.setDangerousGoodsTexts(dtoDangerousGoodsTexts);
		return dtoDangerousGoods;
	}

	public static DtoDangerousGoodsText getDangerousGoodstext(long id) {
		DtoDangerousGoodsText dtoDangerousGoodsText = new DtoDangerousGoodsText();
		dtoDangerousGoodsText.setDangerousGoodsText("Pb67"+id);
		dtoDangerousGoodsText.setLanguageCode("en");
		dtoDangerousGoodsText.setCreatedBy("UNAUTHENTICATED");
		return dtoDangerousGoodsText;
	}
}
