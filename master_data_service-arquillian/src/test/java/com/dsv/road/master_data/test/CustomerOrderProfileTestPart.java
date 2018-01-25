package com.dsv.road.master_data.test;

import com.dsv.road.master_data.client.MasterDataClient;
import com.dsv.road.master_data.client.MasterDataClientException;
import com.dsv.road.shared.masterdata.dto.*;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CustomerOrderProfileTestPart {

	public static final String TEST_PROFILE_NAME = "TEST_PROFILE_NAME";
	public static final String TEST_PROFILE_KEY_KEY = "TEST_PROFILE_KEY_KEY";

	private final static Logger LOGGER = LoggerFactory.getLogger(CustomerOrderProfileTestPart.class);

	private MasterDataClient client;
	private Long profileId;
	private Long profileKeyId;

    public void before(MasterDataClient client) {
		this.client = client;
		try {
            cleanProfileKeys(client);
			LOGGER.info("Creating profile key...");
			profileKeyId = client.createProfileKey(getDtoProfileKeyInstance(TEST_PROFILE_KEY_KEY));

            cleanProfiles(client);
			LOGGER.info("Creating profile...");
			profileId = client.createProfile(getDtoProfileInstance());
		} catch (MasterDataClientException e) {
			LOGGER.error("", e);
			Assert.fail(e.toString());
		}
	}

    private void cleanProfileKeys(MasterDataClient client) throws MasterDataClientException {
        LOGGER.info("Looking for colliding profile keys");
        DtoProfileKeyList profileKeyList = client.searchProfileKeys(TEST_PROFILE_KEY_KEY);
        LOGGER.info(" >>>>> Found:  " + profileKeyList.getList().size());
        for (DtoProfileKey key : profileKeyList.getList()) {
            client.deleteProfileKey(key.getId());
            LOGGER.info("Cleaned-up test profile key with ID: " + key.getId());
        }
    }

    private void cleanProfiles(MasterDataClient client) throws MasterDataClientException {
        LOGGER.info("Looking for colliding profile ");
        DtoProfileList profileList = client.searchProfiles(TEST_PROFILE_NAME);
        LOGGER.info(" >>>>> Found:  " + profileList.getList().size());
        for (DtoProfile key : profileList.getList()) {
            client.deleteProfile(key.getId());
            LOGGER.info("Cleaned-up test profile with ID: " + key.getId());
        }
    }

    public void after() throws MasterDataClientException {
		client.deleteProfile(profileId);
		client.deleteProfileKey(profileKeyId);
		LOGGER.info("---------------> Profile has been deleted");
	}

	public void testGetProfile() throws MasterDataClientException {
		LOGGER.info("--------------->" + profileId);
		DtoProfile dtoProfile = client.getProfile(profileId);
		LOGGER.info("--------------->" + dtoProfile.getAttributes().get(0).getKey());
		Assert.assertNotNull(dtoProfile);
	}

	public void testSearchProfile() throws MasterDataClientException {
		DtoProfileList list = client.searchProfiles("Te");
		LOGGER.info("Search list size is " + list.getList().size());
		assertThat(list.getList()).isNotEmpty();
	}



	public void testAddProfileAttributeToProfile() throws MasterDataClientException {
		DtoProfileAttribute attr = new DtoProfileAttribute();
		long otherKey = client.createProfileKey(getDtoProfileKeyInstance("OTHER_KEY"));
		attr.setKeyId(otherKey);
		attr.setUsage("change");
		attr.setValue("added key to profile");
		client.addAttributeToProfile(profileId, attr);
		client.deleteProfileKey(otherKey);
		LOGGER.info("---------------> Profile key has been added");
	}

	public void testRemoveProfileAttributeFromProfile() throws MasterDataClientException {
		DtoProfile dtoProfile = client.getProfile(profileId);
		Long attrId = dtoProfile.getAttributes().get(0).getId();
		client.deleteAttributeFromProfile(profileId, attrId);
		LOGGER.info("---------------> A profile key has been removed");
	}

	private DtoProfile getDtoProfileInstance() {
		DtoProfile dtoProfile = new DtoProfile();
		dtoProfile.setName(TEST_PROFILE_NAME);
		dtoProfile.setDescription("Test description");
		List<DtoProfileAttribute> keyValues = new ArrayList<>();
		DtoProfileAttribute keyValue = new DtoProfileAttribute();
		keyValue.setKey(TEST_PROFILE_KEY_KEY);
		keyValue.setKeyId(profileKeyId);
		keyValue.setUsage("change");
		keyValue.setValue("This is the value");
		keyValues.add(keyValue);
		dtoProfile.setAttributes(keyValues);
		return dtoProfile;
	}

	private DtoProfileKey getDtoProfileKeyInstance(String key) {
		DtoProfileKey dtoProfileKey = new DtoProfileKey();
		dtoProfileKey.setDescription("This is the test description for " + key);
		dtoProfileKey.setKey(key);
		return dtoProfileKey;
	}
}