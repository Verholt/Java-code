package com.dsv.road.shared.masterdata.parser;

import com.dsv.road.shared.masterdata.dto.DtoPackageType;
import com.dsv.road.shared.masterdata.parser.base.XLSXParser;
import com.dsv.road.shared.masterdata.parser.base.XLSXParserException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by EXT.K.Theilgaard on 14-07-2015.
 */
public class PackageTypeParser extends XLSXParser<DtoPackageType> {
    private static final String[] COLUMN_ARRAY = new String[]{"SHORTABBREVIATION", "LONGABBREVIATION", "DESCRIPTION",
            "LENGHT", "HEIGHT", "WIDTH", "LDM", "ALLOWSTACK", "STACKMAXWEIGHT", "NONSTACKMAXWEIGHT", "STACKMAXHEIGHT",
            "NONSTACKMAXHEIGHT"};
    private static final Set<String> COLUMNS = new HashSet<>(Arrays.asList(COLUMN_ARRAY));

    /**
     * Parses an Excel .XLSX file to a list of DtoPackageType
     *
     * @param f .xlsx file to be parsed.
     * @return Parsed list of DtoPackageType
     * @throws XLSXParserException
     */
    public List<DtoPackageType> parseXLSXFile(InputStream f) throws XLSXParserException {
        return parseXLSXFile(f, COLUMNS);
    }

    @Override
    protected DtoPackageType parseRow(Map<String, Integer> columnIndexes, Row row, Map<String, String> settings) {
        DtoPackageType dtoPackageType = new DtoPackageType();

        for (String column : columnIndexes.keySet()) {
            Cell cell = row.getCell(columnIndexes.get(column));
            if (cell == null) {
                continue;
            }
            switch (column) {
                case "SHORTABBREVIATION":
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    dtoPackageType.setShortAbbreviation(cell.getStringCellValue());
                    break;
                case "LONGABBREVIATION":
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    dtoPackageType.setLongAbbreviation(cell.getStringCellValue());
                    break;
                case "DESCRIPTION":
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    dtoPackageType.setDescription(cell.getStringCellValue());
                    break;
                case "LENGHT":
                    cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                    dtoPackageType.setLength((int) cell.getNumericCellValue());
                    break;
                case "HEIGHT":
                    cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                    dtoPackageType.setHeight((int) cell.getNumericCellValue());
                    break;
                case "WIDTH":
                    cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                    dtoPackageType.setWidth((int) cell.getNumericCellValue());
                    break;
                case "LDM":
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    dtoPackageType.setLdm(BigDecimal.valueOf(Double.valueOf(cell.getStringCellValue())));
                    break;
                case "ALLOWSTACK":
                    cell.setCellType(Cell.CELL_TYPE_BOOLEAN);
                    dtoPackageType.setAllowStack(cell.getBooleanCellValue());
                    break;
                case "STACKMAXWEIGHT":
                    cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                    dtoPackageType.setStackMaxWeight((int) cell.getNumericCellValue());
                    break;
                case "NONSTACKMAXWEIGHT":
                    cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                    dtoPackageType.setNonStackMaxWeight((int) cell.getNumericCellValue());
                    break;
                case "STACKMAXHEIGHT":
                    cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                    dtoPackageType.setStackMaxHeight((int) cell.getNumericCellValue());
                    break;
                case "NONSTACKMAXHEIGHT":
                    cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                    dtoPackageType.setNonStackMaxHeight((int) cell.getNumericCellValue());
                    break;
                default:
                    break;
            }
        }
        return dtoPackageType;
    }
}
