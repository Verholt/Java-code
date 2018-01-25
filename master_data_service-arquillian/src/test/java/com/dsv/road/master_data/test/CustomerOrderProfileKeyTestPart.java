package com.dsv.road.master_data.test;

import com.dsv.road.master_data.client.MasterDataClient;
import com.dsv.road.master_data.client.MasterDataClientException;
import com.dsv.road.shared.masterdata.dto.DtoProfileKey;
import com.dsv.road.shared.masterdata.dto.DtoProfileKeyList;
import org.junit.Assert;

import static org.assertj.core.api.Assertions.assertThat;

public class CustomerOrderProfileKeyTestPart {
	public static final String TEST_PART = "TEST";
	private static final String TEST_PROFILE_KEY_KEY = TEST_PART + "_PROFILE_KEY_KEY";
	private MasterDataClient client;
	private Long id;

	private static DtoProfileKey getDtoProfileKeyInstance() {
		DtoProfileKey dtoProfileKey = new DtoProfileKey();
		dtoProfileKey.setDescription("This is the description");
		dtoProfileKey.setKey(TEST_PROFILE_KEY_KEY);
		return dtoProfileKey;
	}

	public void before(MasterDataClient client) {
		this.client = client;
		try {
			cleanProfileKeys(client);
			id = client.createProfileKey(getDtoProfileKeyInstance());
		} catch (MasterDataClientException e) {
			Assert.fail(e.toString());
		}
	}

	private void cleanProfileKeys(MasterDataClient client) throws MasterDataClientException {
		DtoProfileKeyList profileKeyList = client.searchProfileKeys(TEST_PROFILE_KEY_KEY);
		for (DtoProfileKey key : profileKeyList.getList()) {
			client.deleteProfileKey(key.getId());
		}
	}

	public void after() {
		try {
			client.deleteProfileKey(id);
		} catch (MasterDataClientException e) {
			Assert.fail(e.toString());
		}
	}

	public void testGetProfileKey() {
		try {
			DtoProfileKey dtoProfileKey = client.getProfileKey(id);
			Assert.assertNotNull(dtoProfileKey);
		} catch (MasterDataClientException e) {
			Assert.fail(e.toString());
		}
	}

	public void testUpdateProfileKey() {
		try {
			DtoProfileKey dtoProfileKey = client.getProfileKey(id);
			dtoProfileKey.setDescription("This is again an updated description");
			client.updateProfileKey(dtoProfileKey);
		} catch (MasterDataClientException e) {
			Assert.fail(e.toString());
		}
	}

	public void testSearchProfileKey() {
		try {
			DtoProfileKeyList list = client.searchProfileKeys(TEST_PART);
			assertThat(list.getList()).isNotEmpty();
		} catch (MasterDataClientException e) {
			Assert.fail(e.toString());
		}
	}
}