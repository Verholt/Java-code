package com.dsv.road.master_data.test;

import com.dsv.road.masterdata.importtool.RolesImport;
import com.dsv.road.shared.masterdata.rest.MasterDataRestClient;
import com.dsv.road.shared.masterdata.rest.MasterDataRestClientImpl;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.net.URI;
import java.util.List;

/**
 * Created by EXT.K.Theilgaard on 14/10/2015.
 */
public class ImportRolesTestPart {
    private final static Logger LOGGER  = LoggerFactory.getLogger(ImportRolesTestPart.class);
    private static String localSeverEndpoint = "http://localhost:10039/master_data_service-web/rest/";
    private MasterDataRestClient client;

    public static void main(String args[]) {
        ImportRolesTestPart importRolesTestPart = new ImportRolesTestPart();
        importRolesTestPart.before(new MasterDataRestClientImpl(URI.create(localSeverEndpoint)));
        importRolesTestPart.testUploadAndCheck();
    }

    public void before(MasterDataRestClient client) {
        this.client = client;
    }

    public void testUploadAndCheck() {
        InputStream is = this.getClass().getResourceAsStream("/roles.xlsx");
        Assert.assertFalse("File does not exist.", is == null);
        List<Long> roles = RolesImport.runImport(is, client);
        for (Long s : roles) {
            LOGGER.info("Imported role with id: " + s);
        }
        Assert.assertTrue(!roles.isEmpty());
    }
}
