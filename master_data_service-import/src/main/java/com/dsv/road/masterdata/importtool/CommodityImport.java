package com.dsv.road.masterdata.importtool;

import com.dsv.road.shared.masterdata.dto.DtoHsCode;
import com.dsv.road.shared.masterdata.dto.DtoHsCodeText;
import com.dsv.road.shared.masterdata.rest.MasterDataClientException;
import com.dsv.road.shared.masterdata.rest.MasterDataRestClient;
import com.dsv.road.shared.masterdata.rest.MasterDataRestClientImpl;
import com.dsv.shared.importtool.RemoteImportConfiguration;
import com.dsv.shared.joda.JodaUtility;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URI;
import java.util.*;

/**
 * Created by EXT.K.Theilgaard on 27-05-2015.
 */
//TODO:rewrite to use parser file, like the rest.
public class CommodityImport {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommodityImport.class);
    private static final String CONFIGFILE = "import/config/CommodityRemoteConfigFile.xml";
    private static final int WORKSHEET = 0;

    public static void main(String args[]) {
        RemoteImportConfiguration configuration = null;
        if (args.length != 0 && args[0] != null) {
            configuration = new MasterdataRemoteImportConfiguration(args[0]);
        } else {
            configuration = new MasterdataRemoteImportConfiguration(CONFIGFILE);
        }
        try {
            runImport((MasterdataRemoteImportConfiguration) configuration);
        } catch (IOException e) {
            LOGGER.error("", e);
        }
    }

    public static List<String> runImport(final MasterdataRemoteImportConfiguration configuration) throws FileNotFoundException {
        MasterdataRemoteImportConfiguration masterConfiguration = configuration;
        if (masterConfiguration == null) {
            masterConfiguration = new MasterdataRemoteImportConfiguration(CONFIGFILE);
        }
        boolean createhsCodesTexts = Boolean.valueOf(masterConfiguration.getSettings().get("createHsCodeTextsOnly"));
        String endpoint = masterConfiguration.getRestEndpoint();
        String language = masterConfiguration.getSettings().get("language");
        InputStream is = new FileInputStream(new File(masterConfiguration.getExcelFilePath()));
        return runImport(is, language, endpoint, createhsCodesTexts);
    }

    public static List<String> runImport(InputStream excelFileInputStream, String language, String endpoint, boolean createhsCodesTextsOnly) throws FileNotFoundException {
        LOGGER.info("Importing worksheet from excel file...");
        XSSFSheet sheet = importSheetFromXSLX(excelFileInputStream);
        LOGGER.info("Mapping columns to column indexes...");
        Map<COLUMN, Integer> columnIndexes = mapColumnnamesToIndexes(sheet);
        LOGGER.info("Parsing worksheet...");
        long startParseTime = System.currentTimeMillis();
        List<DtoHsCode> hsCodes = parseCommoditiesFromSheet(columnIndexes, sheet, language);
        LOGGER.info("Finished parsing " + hsCodes.size() + " rows in " + (System.currentTimeMillis() - startParseTime) + "ms");
        List<String> uploaded = uploadRows(hsCodes, endpoint, createhsCodesTextsOnly);
        LOGGER.info("Done!");
        return uploaded;
    }

    private static List<String> uploadRows(List<DtoHsCode> hsCodes, String endpoint, boolean createhsCodesTextsOnly) {
        if (createhsCodesTextsOnly) {
            List<DtoHsCodeText> dtohsCodesTexts = new ArrayList<>();
            for (DtoHsCode dtoHsCode : hsCodes) {
                dtohsCodesTexts.addAll(dtoHsCode.getHsCodeTextObjects());
            }
            LOGGER.info("Uploading " + hsCodes.size() + " hsCodes to [" + endpoint + "]...");
            uploadHsCodeTextList(dtohsCodesTexts, endpoint);
            return null;
        } else {
            LOGGER.info("Uploading " + hsCodes.size() + " hsCodes to [" + endpoint + "]...");
            return uploadHsCodeList(hsCodes, endpoint);
        }
    }

    private static XSSFSheet importSheetFromXSLX(InputStream is) {
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(is);
            XSSFSheet sheet = workbook.getSheetAt(WORKSHEET);
            if (sheet == null) {
                LOGGER.error("FATAL! Could not get worksheet from file");
            }
            return sheet;
        } catch (IOException e) {
            LOGGER.error("Failed creating XSSFWorkbook from input stream " + e.getMessage(), e);
            return null;
        }
    }

    private static Map<COLUMN, Integer> mapColumnnamesToIndexes(XSSFSheet sheet) {
        Map<COLUMN, Integer> columnIndexes = new HashMap<>();
        Iterator<Row> rowIterator = sheet.iterator();
        Row columnRow = rowIterator.next();
        Iterator<Cell> cellIterator = columnRow.cellIterator();
        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            String cellValue = cell.getStringCellValue().toUpperCase();
            for (int i = 0; i < COLUMN.values().length; i++) {
                if (cellValue.equals(COLUMN.values()[i].toString().toUpperCase())) {
                    COLUMN value = COLUMN.valueOf(cellValue);
                    columnIndexes.put(value, cell.getColumnIndex());
                }
            }
        }
        return columnIndexes;
    }

    private static List<DtoHsCode> parseCommoditiesFromSheet(Map<COLUMN, Integer> columnIndexes, XSSFSheet sheet, String language) {
        Iterator<Row> rowIterator = sheet.iterator();
        rowIterator.next(); // Skip column row

        LOGGER.debug("Parsing " + sheet.getPhysicalNumberOfRows() + " rows");
        List<DtoHsCode> result = new ArrayList<>();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            parseCommodityRow(columnIndexes, row, result, language);
        }
        return result;
    }

    private static void parseCommodityRow(Map<COLUMN, Integer> columnIndexes, Row row, List<DtoHsCode> hsCodes, String language) {
        DtoHsCode dtoHsCode = new DtoHsCode();
        DtoHsCodeText dtoHsCodeText = new DtoHsCodeText();

        for (COLUMN column : columnIndexes.keySet()) {
            Cell cell = row.getCell(columnIndexes.get(column));
            cell.setCellType(Cell.CELL_TYPE_STRING);
            switch (column) {
                case CN8CODE:
                    dtoHsCode.setHsCode(cell.getStringCellValue());
                    dtoHsCodeText.setHsCode(cell.getStringCellValue());
                    break;
                case DESCRIPTION:
                    dtoHsCodeText.setDescription(cell.getStringCellValue());
                    break;
                default:
                    break;
            }
        }
        // Using current date as validFrom. Using +1 year as validTo.
        dtoHsCode.setValidFrom(JodaUtility.getLocalDateAsString(new LocalDate()));
        dtoHsCode.setValidTo(JodaUtility.getLocalDateAsString(new LocalDate().plusYears(1)));
        LOGGER.debug("[" + row.getRowNum() + "] " + dtoHsCode.getHsCode() + ": " + dtoHsCodeText.getDescription());
        dtoHsCodeText.setLanguage(language);
        dtoHsCode.getHsCodeTextObjects().add(dtoHsCodeText);
        hsCodes.add(dtoHsCode);
    }

    private static List<String> uploadHsCodeList(List<DtoHsCode> hsCodes, String endpoint) {
        MasterDataRestClient client = new MasterDataRestClientImpl(URI.create(endpoint));
        List<String> ids = new ArrayList<>();
        try {
            List<DtoHsCode> temp = new ArrayList<>();
            long startTime = System.currentTimeMillis();
            for (int i = 0; i < hsCodes.size(); i += 100) {
                temp.clear();
                temp.addAll(hsCodes.subList(i, Math.min(i + 100, hsCodes.size())));
                printStatus(i, 100, hsCodes.size(), startTime);
                for(DtoHsCode hsCode: temp) {
                    ids.add(client.createHsCode(hsCode));
                }
            }
            LOGGER.info("Finished uploading in " + (System.currentTimeMillis() - startTime) / 1000 + "." + System.currentTimeMillis() % 1000 + " seconds!");
        } catch (MasterDataClientException e) {
            LOGGER.error("", e);
        }
        return ids;
    }

    private static void uploadHsCodeTextList(List<DtoHsCodeText> hsCodesTexts, String endpoint) {
        MasterDataRestClient client = new MasterDataRestClientImpl(URI.create(endpoint));
        try {
            List<DtoHsCodeText> temp = new ArrayList<>();
            long startTime = System.currentTimeMillis();
            for (int i = 0; i < hsCodesTexts.size(); i += 100) {
                temp.clear();
                temp.addAll(hsCodesTexts.subList(i, Math.min(i + 100, hsCodesTexts.size())));
                printStatus(i, 100, hsCodesTexts.size(), startTime);
                for (DtoHsCodeText hsCodeText : temp) {
                    client.createHsCodeText(hsCodeText);
                }
            }
            LOGGER.info("Finished uploading in " + (System.currentTimeMillis() - startTime) / 1000 + "." + System.currentTimeMillis() % 1000 + " seconds!");
        } catch (MasterDataClientException e) {
            LOGGER.error("", e);
        }
    }

    private static void printStatus(int i, int stepSize, int size, long startTime) {
        long timeSpent = System.currentTimeMillis() - startTime;
        long timeSpentPerUnit = timeSpent / Math.max(i, 1);
        int unitsLeft = size - i;
        LOGGER.info("Uploading rows " + i + " to " + Math.min(i + stepSize, size) + ", estimated time left: " + timeSpentPerUnit * unitsLeft / 1000 + " seconds");
    }

    private enum COLUMN {
        CN8CODE, DESCRIPTION
    }
}
