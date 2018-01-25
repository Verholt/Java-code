package com.dsv.road.master_data.test;

import com.dsv.road.masterdata.importtool.CommodityImport;
import com.dsv.road.master_data.client.MasterDataClient;
import com.dsv.road.master_data.client.MasterDataClientException;
import com.dsv.road.shared.masterdata.dto.DtoHsCode;
import com.dsv.road.shared.masterdata.dto.DtoHsCodeText;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by EXT.K.Theilgaard on 14/10/2015.
 */
public class ImportCommoditiesTestPart {
    private final static Logger LOGGER  = LoggerFactory.getLogger(ImportCommoditiesTestPart.class);
    private static String localSeverEndpoint = "http://localhost:10039/master_data_service-web/rest/";
    private MasterDataClient client;

    public static void main(String args[]) {
        ImportCommoditiesTestPart importRolesTestPart = new ImportCommoditiesTestPart();
        importRolesTestPart.before(new MasterDataClient(localSeverEndpoint));
        importRolesTestPart.testUploadAndCheck();
    }

    public void before(MasterDataClient client) {
        this.client = client;
    }

    public void testUploadAndCheck() {
        InputStream is = this.getClass().getResourceAsStream("/commodities.xlsx");
        Assert.assertFalse("File does not exist.", is == null);
        try {
            List<String> hsCodeIds = CommodityImport.runImport(is, "en", ClientConfiguration.baseURL(), false);
            LOGGER.info(hsCodeIds.size() + " hsCodes uploaded. Deleting again...");
            Assert.assertFalse(hsCodeIds.isEmpty());
            for (String hsCodeId : hsCodeIds) {
                if(StringUtils.isNotEmpty(hsCodeId)) {
                    LOGGER.info("Deleting HsCode: " + hsCodeId);
                    DtoHsCode hsCode = client.getHsCode(Long.parseLong(hsCodeId));
                    client.deleteHsCode(hsCode.getId());
                    for (DtoHsCodeText hsCodeText : hsCode.getHsCodeTextObjects()) {
                        LOGGER.info("Deleting HsCodeText: " + hsCodeText.getId());
                        client.deleteHsCodeText(hsCodeText.getId());
                    }
                }
            }
        } catch (FileNotFoundException | MasterDataClientException e) {
            LOGGER.error(e.getLocalizedMessage());
        }
    }
}
