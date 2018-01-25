package com.dsv.road.master_data.test;

import com.dsv.road.master_data.client.MasterDataClient;
import com.dsv.road.master_data.client.MasterDataClientException;
import com.dsv.road.shared.masterdata.dto.DtoHsCode;
import com.dsv.road.shared.masterdata.dto.DtoHsCodeText;
import org.junit.Assert;
import org.junit.Ignore;

import java.util.ArrayList;

/**
 * Created by EXT.K.Theilgaard on 27-05-2015.
 */
public class CommodityTestPart {

	private MasterDataClient client;
	private String hsCode = "01042010TEST";
	private String language = "en";
	private String hsCodeId, hsCodeTextId;

	private static DtoHsCode getDtoHsCodeInstance(String hsCode) {
		DtoHsCode dtoHsCode = new DtoHsCode();
		dtoHsCode.setHsCode(hsCode);
		dtoHsCode.setValidFrom("1998-01-01");
		dtoHsCode.setValidTo("2018-01-01");
		dtoHsCode.setHsCodeTextObjects(new ArrayList<DtoHsCodeText>());
		return dtoHsCode;
	}

	private static DtoHsCodeText getDtoHsCodeTextInstance(String hsCode, String language) {
		DtoHsCodeText dtoHsCodeText = new DtoHsCodeText();
		dtoHsCodeText.setHsCode(hsCode);
		dtoHsCodeText.setLanguage(language);
		dtoHsCodeText.setDescription("Incredibly well-mannered piglets");
		return dtoHsCodeText;
	}

	public void before(MasterDataClient client) {
		this.client = client;
		try {
			hsCodeTextId = client.createHsCodeText(getDtoHsCodeTextInstance(hsCode, language));
			hsCodeId = client.createHsCode(getDtoHsCodeInstance(hsCode));
		} catch (MasterDataClientException e) {
			Assert.fail(e.toString());
		}
	}

	public void after() {
		try {
			client.deleteHsCodeText(Long.valueOf(hsCodeTextId));
			client.deleteHsCode(Long.valueOf(hsCodeId));
		} catch (MasterDataClientException e) {
			Assert.fail(e.toString());
		}
	}

	public void testGetHsCodeText() {
		try {
			String hsCodetextid = client.createHsCodeText(getDtoHsCodeTextInstance(hsCode, language));
			DtoHsCodeText dtoHsCodeText = client.getHsCodeText(Long.valueOf(hsCodetextid));
			Assert.assertNotNull(dtoHsCodeText);
		} catch (MasterDataClientException e) {
			Assert.fail(e.toString());
		}
	}

	public void testUpdateHsCodeText() {
		try {
			DtoHsCodeText existingHsCodeText = client.getHsCodeText(Long.valueOf(hsCodeTextId));
			existingHsCodeText.setDescription("Slightly rude piglets. Att: They bite!");
			client.updateHsCodeText(existingHsCodeText.getId(), existingHsCodeText);
			DtoHsCodeText updatedHsCodeText = client.getHsCodeText(Long.valueOf(hsCodeTextId));
			Assert.assertNotEquals(updatedHsCodeText.getDescription(), getDtoHsCodeTextInstance(hsCode, language));
		} catch (MasterDataClientException e) {
			Assert.fail(e.toString());
		}
	}

	public void testHsCodeContainsHsCodeText() {
		try {
			String hsCodeId = client.createHsCode(getDtoHsCodeInstance(hsCode));
			DtoHsCode dtoHsCode = client.getHsCode(Long.valueOf(hsCodeId));
			for (DtoHsCodeText dtoHsCodeText : dtoHsCode.getHsCodeTextObjects()) {
				if (dtoHsCodeText.getHsCode().equals(hsCode) && dtoHsCodeText.getLanguage().equals(language)) {
					Assert.assertEquals(dtoHsCodeText.getDescription(), getDtoHsCodeTextInstance(hsCode, language).getDescription());
				}
			}
			client.deleteHsCode(dtoHsCode.getId());
		} catch (MasterDataClientException e) {
			Assert.fail(e.toString());
		}
	}

	public void testGetHsCode() {
		try {
			DtoHsCode dtoHsCode = client.getHsCode(Long.valueOf(hsCodeId));
			Assert.assertNotNull(dtoHsCode);
		} catch (MasterDataClientException e) {
			Assert.fail(e.toString());
		}
	}

	public void testUpdateHsCode() {
		try {
			DtoHsCode newHsCode = getDtoHsCodeInstance(hsCode);
			newHsCode.setValidFrom("0001-01-01");
			client.updateHsCode(Long.valueOf(hsCodeId), newHsCode);
			DtoHsCode updatedHsCode = client.getHsCode(Long.valueOf(hsCodeId));
			Assert.assertNotEquals(newHsCode.getValidFrom(), updatedHsCode.getValidFrom());
		} catch (MasterDataClientException e) {
			Assert.fail(e.toString());
		}
	}
}