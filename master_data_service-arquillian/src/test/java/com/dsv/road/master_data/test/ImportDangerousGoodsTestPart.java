package com.dsv.road.master_data.test;

import com.dsv.road.masterdata.importtool.DangerousGoodsImport;
import com.dsv.road.master_data.client.MasterDataClient;
import com.dsv.road.master_data.client.MasterDataClientException;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;

public class ImportDangerousGoodsTestPart {
    private final static Logger LOGGER  = LoggerFactory.getLogger(ImportDangerousGoodsTestPart.class);
    private static String localSeverEndpoint = "http://localhost:10039/master_data_service-web/rest/";
    private MasterDataClient client;

    public static void main(String args[]) {
        ImportDangerousGoodsTestPart importDangerousGoodsTestPart = new ImportDangerousGoodsTestPart();
        importDangerousGoodsTestPart.before(new MasterDataClient(localSeverEndpoint));
        importDangerousGoodsTestPart.testUploadAndCheck();
    }

    public void before(MasterDataClient client) {
        this.client = client;
    }

    public void testUploadAndCheck() {
        InputStream is = this.getClass().getResourceAsStream("/dangerousGoods.xlsx");
        LOGGER.info("import succeded " + is);
        Assert.assertFalse("File does not exist.", is == null);
        Long bundleId = DangerousGoodsImport.runImport(is, ClientConfiguration.baseURL());
        LOGGER.info("Imported bundle with id: " + bundleId);
        Assert.assertNotNull(bundleId);
        LOGGER.info("Cleaning.");
        try {
            int statusCode = client.deleteDangerousGoodsBundle(bundleId);
            LOGGER.info("deleted successfully."+statusCode);
        } catch (MasterDataClientException e) {
            LOGGER.info("Error occurred while deleting ."+e.getMessage());
            LOGGER.error(e.getLocalizedMessage(), e);
        }
    }
}
