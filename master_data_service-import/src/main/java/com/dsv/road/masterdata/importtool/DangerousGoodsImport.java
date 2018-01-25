package com.dsv.road.masterdata.importtool;

import com.dsv.road.shared.masterdata.rest.MasterDataClientException;
import com.dsv.road.shared.masterdata.rest.MasterDataRestClient;
import com.dsv.road.shared.masterdata.dto.DtoDangerousGoodsBundle;
import com.dsv.road.shared.masterdata.parser.DangerousGoodsParser;
import com.dsv.road.shared.masterdata.parser.base.XLSXParserException;
import com.dsv.road.shared.masterdata.rest.MasterDataRestClientImpl;
import com.dsv.shared.importtool.RemoteImportConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URI;

public class DangerousGoodsImport {
    private static final Logger LOGGER = LoggerFactory.getLogger(DangerousGoodsImport.class);
    private static final String DANGEROUS_GOODS_FILE = "src/main/resources/import/config/DangerousGoodsRemoteConfigFile.xml";

    public static void main(String[] args) {
        RemoteImportConfiguration conf;
        if (args.length == 0) {
            conf = new MasterdataRemoteImportConfiguration(DANGEROUS_GOODS_FILE);
        } else {
            conf = new MasterdataRemoteImportConfiguration(args[0]);
        }
        runImport((MasterdataRemoteImportConfiguration) conf);
    }

    public static Long runImport(MasterdataRemoteImportConfiguration configuration) {
        try {
            return runImport(new FileInputStream(new File(configuration.getExcelFilePath())), configuration.getRestEndpoint());
        } catch (FileNotFoundException e) {
            LOGGER.error(e.getLocalizedMessage(), e);
            return null;
        }
    }

    public static Long runImport(InputStream is, String endpoint) {
        LOGGER.info("Using [" + endpoint + "] for import of Dangerous Goods data.");
        DangerousGoodsParser dangerousGoodsParser = new DangerousGoodsParser();
        MasterDataRestClient client = new MasterDataRestClientImpl(URI.create(endpoint));
        try {
            LOGGER.info("Parsing Dangerous Goods data...");
            DtoDangerousGoodsBundle bundle = dangerousGoodsParser.parseXLSXFile(is);
            LOGGER.info("Uploading Dangerous Goods bundle...");
            return client.createDangerousGoodsBundle(bundle);
        } catch (XLSXParserException | MasterDataClientException e) {
            LOGGER.error(e.getLocalizedMessage(), e);
            return null;
        }
    }
}
