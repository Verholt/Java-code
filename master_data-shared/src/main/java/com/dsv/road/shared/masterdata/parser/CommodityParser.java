package com.dsv.road.shared.masterdata.parser;

import com.dsv.road.shared.masterdata.dto.DtoHsCode;
import com.dsv.road.shared.masterdata.dto.DtoHsCodeText;
import com.dsv.road.shared.masterdata.parser.base.XLSXParser;
import com.dsv.road.shared.masterdata.parser.base.XLSXParserException;
import com.dsv.shared.joda.JodaUtility;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.joda.time.LocalDate;

import java.io.InputStream;
import java.util.*;

/**
 * Created by EXT.K.Theilgaard on 14-07-2015.
 */
public class CommodityParser extends XLSXParser<DtoHsCode> {
    private static final String[] COLUMN_ARRAY = new String[]{"CN8CODE", "DESCRIPTION"};
    private static final Set<String> COLUMNS = new HashSet<>(Arrays.asList(COLUMN_ARRAY));

    public CommodityParser() {
        setSetting("language", "en");
    }

    public List<DtoHsCode> parseXLSXFile(InputStream f) throws XLSXParserException {
        return parseXLSXFile(f, COLUMNS);
    }

    @Override
    protected DtoHsCode parseRow(Map<String, Integer> columnIndexes, Row row, Map<String, String> settings) {
        DtoHsCode dtoHsCode = new DtoHsCode();
        DtoHsCodeText dtoHsCodeText = new DtoHsCodeText();

        for (String column : columnIndexes.keySet()) {
            Cell cell = row.getCell(columnIndexes.get(column));
            cell.setCellType(Cell.CELL_TYPE_STRING);
            switch (column) {
                case "CN8CODE":
                    dtoHsCode.setHsCode(cell.getStringCellValue());
                    dtoHsCodeText.setHsCode(cell.getStringCellValue());
                    break;
                case "DESCRIPTION":
                    dtoHsCodeText.setDescription(cell.getStringCellValue());
                    break;
                default:
                    break;
            }
        }
        // Using current date as validFrom. Using +1 year as validTo.
        dtoHsCode.setValidFrom(JodaUtility.getLocalDateAsString(new LocalDate()));
        dtoHsCode.setValidTo(JodaUtility.getLocalDateAsString(new LocalDate().plusYears(1)));
        dtoHsCodeText.setLanguage(settings.get("language"));
        dtoHsCode.getHsCodeTextObjects().add(dtoHsCodeText);
        return dtoHsCode;
    }
}
