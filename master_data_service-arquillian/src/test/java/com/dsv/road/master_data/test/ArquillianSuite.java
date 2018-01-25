package com.dsv.road.master_data.test;

import com.dsv.road.master_data.client.MasterDataClient;
import com.dsv.road.master_data.client.MasterDataClientException;
import com.dsv.road.master_data.test.*;
import com.dsv.road.shared.masterdata.rest.MasterDataRestClientImpl;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.io.File;
import java.net.URI;

/**
 * Created by EXT.P.Komisarski on 02-09-2015.
 * <p/>
 * In arquillian 1.0.0 there is no option to configure multiple test files with
 * one deployment. Work on this is still in progress -
 * https://issues.jboss.org/browse/ARQ-197 <code>ArquillianSuite</code> was
 * created to avoid multiple deployments when there is too many
 * arquillian-related-tests.
 */
@RunWith(Arquillian.class)
@FixMethodOrder(MethodSorters.JVM)
public class ArquillianSuite {
    private AppTestPart appTest = new AppTestPart();
    private CommodityTestPart commodityTest = new CommodityTestPart();
    private CustomerOrderProfileTestPart customerOrderProfileTest = new CustomerOrderProfileTestPart();
    private CustomerOrderProfileKeyTestPart customerOrderProfileKeyTest = new CustomerOrderProfileKeyTestPart();
    private ImportRolesTestPart importRolesTestPart = new ImportRolesTestPart();
    private ImportCommoditiesTestPart importCommoditiesTestPart = new ImportCommoditiesTestPart();
    private ImportDangerousGoodsTestPart importDangerousGoodsTestPart = new ImportDangerousGoodsTestPart();
    private MasterDataClient client;

    @Deployment
    public static WebArchive createDeployment() {
        WebArchive war = ShrinkWrap.createFromZipFile(WebArchive.class, new File("../master_data_service-web/target/master_data_service-web.war"));
        // Add all tests to war
        war.addClasses(CustomerOrderProfileKeyTestPart.class, CustomerOrderProfileTestPart.class, ClientConfiguration.class, CommodityTestPart.class,
                AppTestPart.class, ImportRolesTestPart.class,  ImportCommoditiesTestPart.class, DtoDangerousGoodsInstance.class, ImportDangerousGoodsTestPart.class);
        war.addAsResource(new File("../master_data_service-import/src/main/resources/import/data/roles.xlsx"), "roles.xlsx");
        war.addAsResource(new File("src/test/resources/testdata/commoditiesEnglishSMALL.xlsx"), "commodities.xlsx");
        war.addAsResource(new File("src/test/resources/testdata/dangerousGoodsSMALL.xlsx"), "dangerousGoods.xlsx");
        war.setWebXML("web_without_security.xml");
        File[] files = Maven.resolver().loadPomFromFile("pom.xml").importRuntimeAndTestDependencies().resolve().withTransitivity().asFile();
        war.addAsLibraries(files);
        // Inject beans.xml and mocks
        String ejbPath = PackageMatcher.getMasterDataServiceEJB(war.toString(true));
        JavaArchive ejb = war.getAsType(JavaArchive.class, ejbPath);
        ejb.addAsResource("META-INF/beans.xml");
        // Inject mock package
        ejb.addPackage("com.dsv.road.master_data.mocks");
        ejb.addPackage("com.dsv.road.master_data.client");
        return war;
    }

    @Before
    public void before() {
        client = new MasterDataClient(ClientConfiguration.baseURL());
        commodityTest.before(client);
        appTest.before(client);
        final MasterDataRestClientImpl restClient = new MasterDataRestClientImpl(URI.create(ClientConfiguration.baseURL()));
        importRolesTestPart.before(restClient);
        importCommoditiesTestPart.before(this.client);
        importDangerousGoodsTestPart.before(this.client);
    }

    @After
    public void after() {
        commodityTest.after();
    }

    /********************
     * CommodityTest
     ********************/

    @Test
    public void testGetText() {
        commodityTest.testGetHsCodeText();
    }

    @Test
    public void testUpdateText() {
        commodityTest.testUpdateHsCodeText();
    }

    @Test
    public void testContainsText() {
        commodityTest.testHsCodeContainsHsCodeText();
    }

    @Test
    public void testGet() {
        commodityTest.testGetHsCode();
    }

    @Test
    public void testUpdate() {
        commodityTest.testUpdateHsCode();
    }


    /********************
     * CustomerOrderProfileTest
     ********************/

    @Test
    public void testGetProfile() throws MasterDataClientException {
        customerOrderProfileTest.before(client);
        customerOrderProfileTest.testGetProfile();
        customerOrderProfileTest.after();
    }

    @Test
    public void testSearchProfile() throws MasterDataClientException {
        customerOrderProfileTest.before(client);
        customerOrderProfileTest.testSearchProfile();
        customerOrderProfileTest.after();
    }

    @Test
    public void testAddProfileKeyToProfile() throws MasterDataClientException {
        customerOrderProfileTest.before(client);
        customerOrderProfileTest.testAddProfileAttributeToProfile();
        customerOrderProfileTest.after();

    }

    @Test
    public void testRemoveProfileAttributeFromProfile() throws MasterDataClientException {
        customerOrderProfileTest.before(client);
        customerOrderProfileTest.testRemoveProfileAttributeFromProfile();
        customerOrderProfileTest.after();
    }

    /********************
     * CustomerOrderProfileKeyTest
     ********************/

    @Test
    public void testGetProfileKey() {
        customerOrderProfileKeyTest.before(client);
        customerOrderProfileKeyTest.testGetProfileKey();
        customerOrderProfileKeyTest.after();
    }

    @Test
    public void testUpdateProfileKey() {
        customerOrderProfileKeyTest.before(client);
        customerOrderProfileKeyTest.testUpdateProfileKey();
        customerOrderProfileKeyTest.after();
    }

    @Test
    public void testSearchProfileKey() {
        customerOrderProfileKeyTest.before(client);
        customerOrderProfileKeyTest.testSearchProfileKey();
        customerOrderProfileKeyTest.after();
    }

    /********************
     * AppTest
     ********************/

    @Test
    public void acceptedTestForBuilds() {
        appTest.acceptedTestForBuilds();
    }

    @Test
    public void getAllIncoTerms() {
        appTest.getAllIncoTerms();
    }

    @Test
    public void getAllCurrencies() {
        appTest.getAllCurrencies();
    }


    @Test
    public void getAllPackageTypes() {
        appTest.getAllPackageTypes();
    }

    @Test
    public void idIterationTest() {
        appTest.idIterationTest();
    }

    @Test
    public void createRoleTest() {
        appTest.createRoleTest();
    }

    @Test
    public void readRoleTest() {
        appTest.readRoletest();
    }

    @Test
    public void readListTest() {
        appTest.readListTest();
    }

    @Test
    public void getPackageType() {
        appTest.getPackageType();
    }

    @Test
    public void createPackageType() {
        appTest.createPackageType();
    }

    @Test
    public void updatePackageType() {
        appTest.updatePackageType();
    }

    @Test
    public void getIncoTerm() {
        appTest.getIncoTerm();
    }

    @Test
    public void createIncoTerm() {
        appTest.createIncoTerm();
    }

    @Test
    public void updateIncoTerm() {
        appTest.updateIncoTerm();
    }

    @Test
    public void getCurrency() {
        appTest.getCurrency();
    }

    @Test
    public void createCurrency() {
        appTest.createCurrency();
    }

    @Test
    public void updateCurrency() {
        appTest.updateCurrency();
    }

    @Test
    public void getReferenceTypes() throws Exception {
        appTest.getReferenceTypes();
    }

    @Test
    public void cRUDNumberCollection() throws Exception {
        appTest.cRUDNumberCollection();
    }

    @Test
    public void numberRangeTest() throws Exception {
        appTest.numberRangeTest();
    }

    @Test
    public void cRUDReferenceType() throws Exception {
        appTest.cRUDReferenceType();
    }

    @Test
    public void cRUDMDGPartyStatusTest() throws Exception {
        appTest.cRUDMDGPartyStatusTest();
    }

    @Test
    public void cRUDDangerousGoodsPackageTypeTest() throws Exception {
        appTest.cRUDDangerousGoodsPackageTypeTest();
    }

    //    @Test
    public void dangerousGoodsCRDTest() throws Exception {
        appTest.dangerousGoodsCRDBundleTest();
    }


    /**
     * Imports
     */

    @Test
    public void importRolesTest() {
        importRolesTestPart.testUploadAndCheck();
    }

    @Test
    public void importCommoditiesTest() {
        importCommoditiesTestPart.testUploadAndCheck();
    }

    //    @Test
    public void importDangerousGoodsTest() {
        importDangerousGoodsTestPart.testUploadAndCheck();
    }
}
