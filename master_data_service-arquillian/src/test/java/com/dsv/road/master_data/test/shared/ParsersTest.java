package com.dsv.road.master_data.test.shared;

import com.dsv.road.shared.masterdata.dto.DtoHsCode;
import com.dsv.road.shared.masterdata.dto.DtoPackageType;
import com.dsv.road.shared.masterdata.dto.DtoRole;
import com.dsv.road.shared.masterdata.parser.CommodityParser;
import com.dsv.road.shared.masterdata.parser.PackageTypeParser;
import com.dsv.road.shared.masterdata.parser.RoleParser;
import com.dsv.road.shared.masterdata.parser.base.XLSXParserException;
import junit.framework.TestCase;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * Created by EXT.K.Theilgaard on 15-07-2015.
 */
public class ParsersTest {
    private final static Logger LOGGER  = LoggerFactory.getLogger(ParsersTest.class);

    @Test
    public void commodityImportTest() {
        File file = new File("src/test/resources/commoditiesEnglish.xlsx");
        CommodityParser commodityParser = new CommodityParser();
        try {
            FileInputStream fis = new FileInputStream(file);
            List<DtoHsCode> hsCodes = commodityParser.parseXLSXFile(fis);
            TestCase.assertTrue(!hsCodes.isEmpty());
            LOGGER.debug("Parsed " + hsCodes.size() + " hsCodess");
        } catch (XLSXParserException | FileNotFoundException e) {
            TestCase.fail(e.toString());
        }
    }

    @Test
    public void packageTypeImportTest() {
        File file = new File("src/test/resources/packageTypes.xlsx");
        PackageTypeParser packageTypeParser = new PackageTypeParser();
        try {
            FileInputStream fis = new FileInputStream(file);
            List<DtoPackageType> packageTypes = packageTypeParser.parseXLSXFile(fis);
            TestCase.assertTrue(!packageTypes.isEmpty());
            LOGGER.debug("Parsed " + packageTypes.size() + " PackageTypes");
        } catch (XLSXParserException | FileNotFoundException e) {
            TestCase.fail(e.toString());
        }
    }


    @Test
    public void rolesImportTest(){
        File file = new File("src/test/resources/roles.xlsx");
        RoleParser roleParser = new RoleParser();
        try{
            FileInputStream fis = new FileInputStream(file);
            List<DtoRole> roles = roleParser.parseXLSXFile(fis);
            TestCase.assertTrue(!roles.isEmpty());
            LOGGER.debug("Parsed "+roles.size()+ " roles");
        } catch (XLSXParserException | FileNotFoundException e) {
            TestCase.fail(e.toString());
        }
    }
}
