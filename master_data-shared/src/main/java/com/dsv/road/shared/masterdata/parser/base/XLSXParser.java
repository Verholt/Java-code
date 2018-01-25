package com.dsv.road.shared.masterdata.parser.base;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Created by EXT.K.Theilgaard on 14-07-2015.
 */
public abstract class XLSXParser<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(XLSXParser.class);
    private Map<String, String> settings = new HashMap<>();

    /**
     * Contructs a worksheet from a .xlsx file.
     *
     * @param is .xlsx file input stream
     * @return Worksheet to parse
     */
    private XSSFSheet fileToWorksheet(InputStream is, Map<String, String> map) throws XLSXParserException {
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(is);
            int worksheet = 0;
            if (map.containsKey("worksheet")) {
                worksheet = Integer.valueOf(map.get("worksheet"));
            }
            return workbook.getSheetAt(worksheet);
        } catch (IOException e) {
            LOGGER.error("Failed creating XSSFWorkbook from file input stream", e);
            throw new XLSXParserException(e);
        }
    }

    protected List<T> parseXLSXFile(InputStream is, Set<String> columns) throws XLSXParserException {
        XSSFSheet sheet = fileToWorksheet(is, getSettings());
        Map<String, Integer> indexes = getColumnIndexes(sheet, columns);
        return parseSheet(sheet, indexes, getSettings());
    }

    private List<T> parseSheet(XSSFSheet sheet, Map<String, Integer> indexMap, Map<String, String> map) {
        Iterator<Row> rowIterator = sheet.iterator();
        // Skip header row
        rowIterator.next(); 

        LOGGER.debug("Parsing " + (sheet.getPhysicalNumberOfRows() - 1) + " rows...");
        List<T> result = new ArrayList<>();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            result.add(parseRow(indexMap, row, map));
        }
        return result;
    }

    /**
     * Maps a set of Column names to indexes in the sheet passed.
     *
     * @param sheet   The worksheet used for mapping
     * @param columns The columns to map
     * @return A map of column to index
     */
    private Map<String, Integer> getColumnIndexes(XSSFSheet sheet, Set<String> columns) {
        Map<String, Integer> columnIndexes = new HashMap<>();
        Iterator<Row> rowIterator = sheet.iterator();
        Row columnRow = rowIterator.next();
        Iterator<Cell> cellIterator = columnRow.cellIterator();
        LOGGER.debug("Mapping " + columns.size() + " columns to indexes");
        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            String cellValue = cell.getStringCellValue().toUpperCase();
            for (int i = 0; i < columns.toArray().length; i++) {
                String c = columns.toArray()[i].toString().toUpperCase();
                if (cellValue.equals(c)) {
                    columnIndexes.put(c, cell.getColumnIndex());
                    LOGGER.debug("Mapped [ " + cell.getColumnIndex() + ": " + c + "]");
                }
            }
        }
        return columnIndexes;
    }

    public void setSetting(String key, String value) {
        settings.put(key, value);
    }

    public void removeSetting(String key) {
        settings.remove(key);
    }

    public Map<String, String> getSettings() {
        return settings;
    }

    protected abstract T parseRow(Map<String, Integer> indexMap, Row row, Map<String, String> map);
}
