package com.dsv.road.masterdata.importtool;

import com.dsv.road.shared.masterdata.dto.DtoRole;
import com.dsv.road.shared.masterdata.parser.RoleParser;
import com.dsv.road.shared.masterdata.parser.base.XLSXParserException;
import com.dsv.road.shared.masterdata.rest.MasterDataClientException;
import com.dsv.road.shared.masterdata.rest.MasterDataRestClient;
import com.dsv.road.shared.masterdata.rest.MasterDataRestClientImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class RolesImport {
    private static final String ROLES_XLSX = "import/data/roles.xlsx";
    private static final String ENDPOINT = "http://localhost:10039/master_data_service-web/rest/";
    private static final Logger LOGGER = LoggerFactory.getLogger(RolesImport.class);

    public static void main(String args[]) {
        try {
            runImport(new FileInputStream(new File(ROLES_XLSX)), new MasterDataRestClientImpl(URI.create(ENDPOINT)));
        } catch (FileNotFoundException e) {
            LOGGER.error("File not found! " + e.getMessage(), e);
        }
    }

    public static List<Long> runImport(InputStream rolesXlsx, MasterDataRestClient masterDataClient) {
        List<Long> result = new ArrayList<>();
        try {
            RoleParser roleParser = new RoleParser();
            List<DtoRole> roles = roleParser.parseXLSXFile(rolesXlsx);
            LOGGER.info("Importing roles to base ENDPOINT: "+ ENDPOINT);
            LOGGER.info("Searching for old roles...");
            List<DtoRole> oldRoles = masterDataClient.searchRoles("");
            if(oldRoles.isEmpty()){
                LOGGER.info("No old roles found.");
            }else{
                LOGGER.info("Old roles found, deleting old roles...");
                for (DtoRole r : oldRoles) {
                    LOGGER.info("Deleting old role: " + r.getRole());
                    masterDataClient.deleteRole(r.getId());
                }
            }
            LOGGER.info("Importing roles...");
            for (DtoRole r : roles) {
                LOGGER.info("Uploading role: " + r.getRole());
                Long s = masterDataClient.createRole(r);
                result.add(s);
            }
        } catch (XLSXParserException | MasterDataClientException e) {
            LOGGER.error("", e);
        }
        return result;
    }
}
