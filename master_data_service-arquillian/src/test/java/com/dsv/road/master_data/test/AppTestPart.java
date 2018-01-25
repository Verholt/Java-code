package com.dsv.road.master_data.test;

import com.dsv.road.master_data.client.MasterDataClient;
import com.dsv.road.master_data.client.MasterDataClientException;
import com.dsv.road.shared.masterdata.dto.*;
import com.dsv.road.shared.masterdata.dto.constraints.MasterDataConstants;
import com.dsv.shared.collections.CollectionUtils;
import com.dsv.shared.date.DateStringConverter;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;

/**
 * Unit test for simple App.
 */
public class AppTestPart {
    private final static Logger LOGGER = LoggerFactory.getLogger(AppTestPart.class);
    MasterDataClient client;
    String testPrefix = "Test";
    String testRole = "testRole";

    public static String extractId(String uri) {
        if (uri == null || uri.lastIndexOf("/") == -1) {
            return null;
        }
        return uri.substring(uri.lastIndexOf("/") + 1);
    }

    public void before(MasterDataClient client) {
        this.client = client;
    }

    public void acceptedTestForBuilds() {
        Assert.assertTrue(true);
    }

    public void getAllIncoTerms() {
        try {
            List<DtoIncoTerm> list = client.getIncoTerms();
            Assert.assertNotNull(list);
        } catch (MasterDataClientException e) {
            LOGGER.error("", e);
            Assert.fail(e.toString());
        }
    }

    public void getAllCurrencies() {
        try {
            List<DtoCurrency> list = client.getCurrencies();
            Assert.assertNotNull(list);
        } catch (MasterDataClientException e) {
            LOGGER.error("", e);
            Assert.fail(e.toString());
        }
    }

    public void getAllPackageTypes() {
        try {
            List<DtoPackageType> list = client.getPackageTypes();
            Assert.assertNotNull(list);
        } catch (MasterDataClientException e) {
            LOGGER.error("", e);
            Assert.fail(e.toString());
        }
    }

    public void idIterationTest() {
        String testId1 = client.getNextId(testPrefix);
        String testId2 = client.getNextId(testPrefix);
        Long id1 = Long.parseLong(testId1.replaceAll("[\\D]", ""));
        Long id2 = Long.parseLong(testId2.replaceAll("[\\D]", ""));
        Assert.assertTrue(id1 < id2);
    }

    public void createRoleTest() {
        Long id = createRole();
        LOGGER.info("createRoleTest " + id);
        boolean exists = client.existsRole(id);
        LOGGER.info("createRoleTest " + exists);
        deleteRole(id);
        Assert.assertTrue(exists);
        Assert.assertNotNull(id);
    }

    public void readRoletest() {
        Long id = createRole();
        try {
            DtoRole dtoRole = client.getRole(id);
            Assert.assertNotNull(dtoRole);
        } catch (MasterDataClientException e) {
            LOGGER.error("", e);
            Assert.fail();
        }
        deleteRole(id);
    }

    public void readListTest() {
        Long id1 = createRole();
        Long id2 = createRole();
        try {
            List<DtoRole> list = client.searchRoles("test");
            Assert.assertTrue(list.size() > 1);
        } catch (MasterDataClientException e) {
            LOGGER.error("", e);
            Assert.fail();
        }
        deleteRole(id1);
        deleteRole(id2);

    }

    private Long createRole() {
        try {
            DtoRole dtoRole = new DtoRole();
            dtoRole.setRole(testRole);
            String id = client.createRole(dtoRole);
            Assert.assertNotNull(id);
            return Long.valueOf(id);
        } catch (MasterDataClientException e) {
            LOGGER.error("", e);
            Assert.fail(e.toString());
            return -1L;
        }
    }

    public void getPackageType() {
        try {
            String known = "BARR";
            Assert.assertTrue(client.existsPackageType(known));
            DtoPackageType p = client.getPackageType(known);
            assertEquals(p.getShortAbbreviation(), known);
        } catch (MasterDataClientException e) {
            LOGGER.error("", e);
            Assert.fail(e.toString());
        }
    }

    public void createPackageType() {
        try {
            DtoPackageType p = new DtoPackageType();
            p.setShortAbbreviation("TEST");
            p.setLongAbbreviation("THIS IS A TEST PACKAGE TYPE");
            p.setDescription("NOT MUCH TO SAY ABOUT IT REALLY");
            p.setAllowStack(true);
            String createdPUri = client.createPackageType(p);
            DtoPackageType createdP = client.getPackageType(createdPUri);
            assertEquals(p.getDescription(), createdP.getDescription());
            assertEquals(p.getLongAbbreviation(),
                    createdP.getLongAbbreviation());
            // Clean-up
            Assert.assertTrue(client.deletePackageType(p.getShortAbbreviation()));
        } catch (MasterDataClientException e) {
            LOGGER.error("", e);
            Assert.fail(e.toString());
        }
    }

    public void updatePackageType() {
        try {
            DtoPackageType p = new DtoPackageType();
            p.setShortAbbreviation("TEST");
            p.setLongAbbreviation("THIS IS A TEST PACKAGE TYPE");
            p.setDescription("NOT MUCH TO SAY ABOUT IT REALLY");
            p.setAllowStack(true);
            client.createPackageType(p);
            DtoPackageType createdP = client.getPackageType(p.getShortAbbreviation());
            createdP.setDescription("FUNKY FUNK!");
            final String shortAbbreviation = client.updatePackageType(createdP);
            DtoPackageType updatedP = client.getPackageType(createdP.getShortAbbreviation());
            Assert.assertNotEquals(p.getDescription(), updatedP.getDescription());
            Assert.assertEquals("TEST", shortAbbreviation);
            // Clean-up
            Assert.assertTrue(client.deletePackageType(p.getShortAbbreviation()));
        } catch (MasterDataClientException e) {
            LOGGER.error("", e);
            Assert.fail(e.toString());
        }
    }

    public void getIncoTerm() {
        try {
            String known = "EXW";
            Assert.assertTrue(client.existsIncoterms(known));
            DtoIncoTerm incoTerm = client.getIncoTerm(known);
            assertEquals(incoTerm.getShortAbbreviation(), known);
        } catch (MasterDataClientException e) {
            LOGGER.error("", e);
            Assert.fail(e.toString());
        }
    }

    public void createIncoTerm() {
        try {
            DtoIncoTerm incoTerm = new DtoIncoTerm();
            incoTerm.setShortAbbreviation("TEST");
            incoTerm.setLongAbbreviation("THIS IS A TEST INCOTERM");
            incoTerm.setDescription("NOT MUCH TO SAY ABOUT IT REALLY");
            String createdPUri = client.createIncoTerm(incoTerm);
            DtoIncoTerm createdIncoTerm = client.getIncoTerm(createdPUri);
            assertEquals(incoTerm.getDescription(), createdIncoTerm.getDescription());
            assertEquals(incoTerm.getLongAbbreviation(), createdIncoTerm.getLongAbbreviation());
            // Clean-up
            Assert.assertTrue(client.deleteIncoTerm(incoTerm.getShortAbbreviation()));
        } catch (MasterDataClientException e) {
            LOGGER.error("", e);
            Assert.fail(e.toString());
        }
    }

    public void updateIncoTerm() {
        try {
            DtoIncoTerm incoTerm = new DtoIncoTerm();
            incoTerm.setShortAbbreviation("TEST");
            incoTerm.setLongAbbreviation("THIS IS A TEST INCOTERM");
            incoTerm.setDescription("NOT MUCH TO SAY ABOUT IT REALLY");
            client.createIncoTerm(incoTerm);
            DtoIncoTerm createdIncoTerm = client.getIncoTerm(incoTerm.getShortAbbreviation());
            createdIncoTerm.setDescription("FUNKY FUNK!");
            final String shortAbbreviation = client.updateIncoTerm(createdIncoTerm);
            DtoIncoTerm updatedIncoTerm = client.getIncoTerm(createdIncoTerm.getShortAbbreviation());
            Assert.assertNotEquals(incoTerm.getDescription(), updatedIncoTerm.getDescription());
            Assert.assertEquals("TEST", shortAbbreviation);
            // Clean-up
            Assert.assertTrue(client.deleteIncoTerm(incoTerm.getShortAbbreviation()));
        } catch (MasterDataClientException e) {
            LOGGER.error("", e);
            Assert.fail(e.toString());
        }
    }

    public void getCurrency() {
        try {
            String known = "DKK";
            Assert.assertTrue(client.existsCurrency(known));
            DtoCurrency currency = client.getCurrency(known);
            assertEquals(currency.getShortAbbreviation(), known);
        } catch (MasterDataClientException e) {
            LOGGER.error("", e);
            Assert.fail(e.toString());
        }
    }

    public void createCurrency() {
        try {
            DtoCurrency currency = new DtoCurrency();
            currency.setShortAbbreviation("TEST");
            currency.setLongAbbreviation("THIS IS A TEST CURRENCY");
            currency.setDescription("NOT MUCH TO SAY ABOUT IT REALLY");
            String createdPUri = client.createCurrency(currency);
            DtoCurrency createdCurrency = client.getCurrency(createdPUri);
            assertEquals(currency.getDescription(), createdCurrency.getDescription());
            assertEquals(currency.getLongAbbreviation(), createdCurrency.getLongAbbreviation());
            // Clean-up
            Assert.assertTrue(client.deleteCurrency(currency.getShortAbbreviation()));
        } catch (MasterDataClientException e) {
            LOGGER.error("", e);
            Assert.fail(e.toString());
        }
    }

    public void updateCurrency() {
        try {
            DtoCurrency currency = new DtoCurrency();
            currency.setShortAbbreviation("TEST");
            currency.setLongAbbreviation("THIS IS A TEST CURRENCY");
            currency.setDescription("NOT MUCH TO SAY ABOUT IT REALLY");
            client.createCurrency(currency);
            DtoCurrency createdCurrency = client.getCurrency(currency.getShortAbbreviation());
            createdCurrency.setDescription("FUNKY FUNK!");
            final String shortAbbreviation = client.updateCurrency(createdCurrency);
            DtoCurrency updatedCurrency = client.getCurrency(createdCurrency.getShortAbbreviation());
            Assert.assertNotEquals(currency.getDescription(), updatedCurrency.getDescription());
            Assert.assertEquals("TEST", shortAbbreviation);
            // Clean-up
            Assert.assertTrue(client.deleteCurrency(currency.getShortAbbreviation()));
        } catch (MasterDataClientException e) {
            LOGGER.error("", e);
            Assert.fail(e.toString());
        }
    }

    private void deleteRole(Long id) {
        try {
            int responseCode = client.deleteRole(id);
            assertEquals(204, responseCode);
        } catch (MasterDataClientException e) {
            LOGGER.error("", e);
            Assert.fail(e.toString());
        }
    }

    public void getReferenceTypes() throws Exception {
        List<DtoReferenceType> referenceTypes = client.getReferenceTypes();
        assertThat(CollectionUtils.isEmpty(referenceTypes), is(false));
        Assert.assertTrue(client.existsReferenceType(referenceTypes.get(0).getId()));
    }

    public void cRUDNumberCollection() throws MasterDataClientException {
        long id = -1;
        String testName = "testName";
        String testCountry = "testCountry";
        String testLocation = "testLocation";
        String testOutputFormat = "(402)5706593{#########}";
        int amountOfNumbers = 10;
        try {
            String updatedDesciption = "CHANGED MDATA INTEGRATION TEST";

            id = createNumberCollection(testName, testCountry, testLocation, testOutputFormat,client);
            NumberCollectionDto numberCollection = client.getNumberCollection(id);
            numberCollection.setDescription("CHANGED MDATA INTEGRATION TEST");
            boolean updateStatus = client.updateNumberCollection(numberCollection);
            NumberCollectionDto updatedNumberCollection = client.getNumberCollection(id);
            List<NumberCollectionDto> numberCollectionDtos = client.searchNumberCollections(updatedDesciption.substring(0, 8), MasterDataConstants.DetailLevel.FULL);
            List<NumberCollectionDto> numberCollectionDtoLites = client.searchNumberCollections(updatedDesciption.substring(0, 8), MasterDataConstants.DetailLevel.LITE);
            List<String> generatedNumbers = client.getNumbers(testName, amountOfNumbers, testCountry, testLocation);
            assertTrue(generatedNumbers.size() == amountOfNumbers);
            assertThat(numberCollectionDtos.isEmpty(), is(Boolean.FALSE));
            assertThat(null == numberCollectionDtoLites.get(0).getNumberRanges(), is(Boolean.TRUE));
            assertThat(updateStatus, is(Boolean.TRUE));
            assertThat(updatedNumberCollection.getDescription(), is(updatedDesciption));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            Assert.fail("error was thrown in cRUDNumberCollection: " + e.getMessage());
        } finally {
            // Clean-up
            if (id > 0) {
                Assert.assertTrue(client.deleteNumberCollection(id));
                LOGGER.debug("cRUDNumberCollection deleted");
            }
        }
    }
    public void numberRangeTest() throws MasterDataClientException {
        long collectionId = -1;
        try {
            int updatedRangedNo = 2;
            LOGGER.debug("numberRangeTest started");
            collectionId = createNumberCollection();
            Long rangeId = client.createNumberRange(getNumberRange("does not matter"), collectionId);
            List<NumberRangeDto> numberRangeDtos = client.getRangesForCollectionCollections(collectionId);
            NumberRangeDto numberRangeDto = client.getNumberRange(collectionId, rangeId);
            numberRangeDto.setRangeNo(updatedRangedNo);
            client.updateNumberRange(numberRangeDto);
            NumberRangeDto updatedRange = client.getNumberRange(collectionId, rangeId);
            assertThat(updatedRange.getRangeNo(), is(updatedRangedNo));
            assertThat(numberRangeDto.getNumberCollectionId(), is(collectionId));
            assertThat(numberRangeDtos.isEmpty(), is(Boolean.FALSE));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            Assert.fail("error was thrown in numberRangeTest: " + e.getMessage());
        } finally {
            // Clean-up
            if (collectionId > 0) {
                Assert.assertTrue(client.deleteNumberCollection(collectionId));
                LOGGER.debug("numberRangeTest deleted");
            }
        }
    }


    public void cRUDReferenceType() throws MasterDataClientException {
        long id = -1;
        try {
            LOGGER.debug("cRUDReferenceType started");
            id = createReferenceType();
            LOGGER.debug("cRUDReferenceType created");
            DtoReferenceType referenceType = client.getReferenceType(id);
            LOGGER.debug("cRUDReferenceType read");
            referenceType.setId(id);
            referenceType.setReferenceType("CHANGED MDATA INTEGRATION TEST");
            boolean updateReferenceType = client.updateReferenceType(referenceType);
            assertThat(updateReferenceType, is(Boolean.TRUE));
            LOGGER.debug("cRUDReferenceType updated");
            getByIdAndCompare(id, referenceType);
        } catch (Exception e) {
            LOGGER.error("", e);
            Assert.fail("error was thrown in cRUDReferenceType: " + e.getMessage());
        } finally {
            // Clean-up
            if (id > 0) {
                Assert.assertTrue(client.deleteReferenceType(id));
                LOGGER.debug("cRUDReferenceType deleted");
            }
        }
    }

    public void cRUDMDGPartyStatusTest() {
        DtoMDGPartyStatus mDGPartyStatus = new DtoMDGPartyStatus();
        mDGPartyStatus.setStatusCode("Test");
        mDGPartyStatus.setLocality("TestLocality");
        mDGPartyStatus.setDescription("This status is irrelevant");
        try {
            LOGGER.debug("status test: starting");
            client.createMDGPartyStatus(mDGPartyStatus);
            LOGGER.debug("status test: status created id : ");
            DtoMDGPartyStatus createdMDGPartyStatus = client.getMDGPartyStatus(
                    mDGPartyStatus.getStatusCode(),
                    mDGPartyStatus.getLocality());
            createdMDGPartyStatus.setDescription("still irrelevant");
            LOGGER.debug("status test: read status");
            client.updateMDGPartyStatus(createdMDGPartyStatus);
            LOGGER.debug("status test: updated status");
            DtoMDGPartyStatus updatedMDGPartyStatus = client.getMDGPartyStatus(
                    mDGPartyStatus.getStatusCode(),
                    mDGPartyStatus.getLocality());
            LOGGER.debug("status test: read updated");
            // Clean-up
            client.deleteMDGPartyStatus(mDGPartyStatus.getStatusCode(),
                    mDGPartyStatus.getLocality());
            LOGGER.debug("status test: deleted");
            Assert.assertNotEquals(mDGPartyStatus.getDescription(),
                    updatedMDGPartyStatus.getDescription());
        } catch (MasterDataClientException e) {
            try {
                client.deleteMDGPartyStatus(mDGPartyStatus.getStatusCode(), mDGPartyStatus.getLocality());
            } catch (MasterDataClientException e1) {
                LOGGER.error("", e1);
            }
            Assert.fail(e.toString());
        }
    }

    public void cRUDDangerousGoodsPackageTypeTest() {
        LOGGER.info("DangerousGoodsPackageTypeCrud test");
        DtoDangerousGoodsPackageType dtoDangerousGoodsPackageType = DtoDangerousGoodsPackageType.builder().shortAbbreviation("TESTA").typeOfPackaging("testType").remarks("No remarks").build();
        String id = "NReal";
        try {

            id = client.createDangerousGoodsPackageType(dtoDangerousGoodsPackageType);
            DtoDangerousGoodsPackageType createdTransportLocation = client.getDangerousGoodsPackageType(id);
            createdTransportLocation.setTypeOfPackaging("still Test");
            boolean exists = client.existsDangerousGoodsPackageType(id);
            List<DtoDangerousGoodsPackageType> list = client.getDangerousGoodsPackageTypes();
            client.updateDangerousGoodsPackageType(createdTransportLocation);
            DtoDangerousGoodsPackageType updatedTransportLocation = client.getDangerousGoodsPackageType(id);
//			 Clean-up
            client.deleteDangerousGoodsPackageType(id);
            Assert.assertTrue(exists);
            Assert.assertFalse(list.isEmpty());
            Assert.assertFalse(dtoDangerousGoodsPackageType.getTypeOfPackaging().equals(
                    updatedTransportLocation.getTypeOfPackaging()));
        } catch (MasterDataClientException e) {
            try {
                client.deleteDangerousGoodsPackageType(id);
            } catch (MasterDataClientException e1) {
                e1.printStackTrace();
            }
            Assert.fail(e.toString());
        }
    }

    public long createReferenceType() throws MasterDataClientException {
        DtoReferenceType referenceType = new DtoReferenceType();
        referenceType.setReferenceType("MDATA INTEGRATION TEST REFERENCE TYPE");
        Long id = client.createReferenceType(referenceType);
        assertThat(id, notNullValue());
        return id;
    }

    public long createNumberCollection() throws MasterDataClientException {
        return createNumberCollection("testName", "testCountry", "testLocation", "(402)5706593{#########}",client);
    }

    public static long createNumberCollection(String name, String country, String location, String outputFormat,MasterDataClient mClient) throws MasterDataClientException {
        NumberRangeDto numberRange = getNumberRange(outputFormat);
        ArrayList<NumberRangeDto> numberRanges = new ArrayList<>();
        numberRanges.add(numberRange);
        NumberCollectionDto numberCollectionDto = NumberCollectionDto.builder()
                .description("test")
                .lastAssigned(DateStringConverter.dateToString(new Date()))
                .name(name)
                .sharedNumberCollection(42L)
                .warningGiven(DateStringConverter.dateToString(new Date()))
                .warningLimit(1337L)
                .warningMethod("warned")
                .warningWhen(1000)
                .warningWho("the ceo primarily")
                .numberRanges(numberRanges)
                .country(country)
                .location(location)
                .build();
        LOGGER.info("cRUDNumberCollection object initialized");
        Long id = mClient.createNumberCollection(numberCollectionDto);
        assertThat(id, notNullValue());
        return id;
    }

    public static NumberRangeDto getNumberRange(String outputFormat) {
        Date today = new Date();
        return NumberRangeDto.builder()
                .rangeNo(1)
                .cycle(2)
                .lastAssigned(DateStringConverter.dateToString(new Date()))
                .maxValue(1337L)
                .minValue(42L)
                .outputFormat(outputFormat)
                .recentValue(50L)
                .startValue(51L)
                .state(ActiveState.ACTIVE)
                .unbroken(101)
                .validFrom(DateStringConverter.dateToString(new Date(today.getTime() - (1000 * 60 * 60 * 24))))
                .validTo(DateStringConverter.dateToString(new Date(today.getTime() + (1000 * 60 * 60 * 24))))
                .step(9)
                .checkDigitMethodType(CheckDigitMethodType.NONE)
                .build();
    }

    void getByIdAndCompare(Long id, DtoReferenceType referenceType)
            throws MasterDataClientException {
        DtoReferenceType referenceTypeRead = client.getReferenceType(id);
        assertEquals("should have equal reference type",
                referenceType.getReferenceType(),
                referenceTypeRead.getReferenceType());
        assertEquals("should have equal id", id, referenceTypeRead.getId());
    }

    public void dangerousGoodsCRDBundleTest() {
        try {
            Long id = client.createDangerousGoodsBundle(DtoDangerousGoodsInstance.getDtoDangerousGoodsBundle());
            LOGGER.info("Dangerous goods bundle id: " + id);
            DtoDangerousGoodsBundle dangerousGoodsBundle = client.getDangerousGoodsBundle(id);
            int originalSize = dangerousGoodsBundle.getDangerousGoodsList().size();
            int currentSize = client.searchCurrentDangerousGoodsList("", TransportDivision.ADR).size();
            LOGGER.info("Dangerous goods bundle size: " + currentSize + "original size: " + originalSize);
            client.deleteDangerousGoodsBundle(id);
            LOGGER.info("Dangerous goods bundle delete done");
            Assert.assertFalse(originalSize == 0);
            Assert.assertFalse(currentSize == 0);
        } catch (MasterDataClientException e) {
            LOGGER.error(e.getMessage(), e);
            Assert.fail(e.toString());
        }
    }
}
