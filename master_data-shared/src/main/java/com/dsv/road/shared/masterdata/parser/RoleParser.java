package com.dsv.road.shared.masterdata.parser;

import com.dsv.road.shared.masterdata.dto.DtoRole;
import com.dsv.road.shared.masterdata.parser.base.XLSXParser;
import com.dsv.road.shared.masterdata.parser.base.XLSXParserException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by EXT.K.Theilgaard on 14-07-2015.
 */
public class RoleParser extends XLSXParser<DtoRole> {
    private static final String[] COLUMN_ARRAY = new String[]{"ROLE"};
    private static final Set<String> COLUMNS = new HashSet<>(Arrays.asList(COLUMN_ARRAY));

    public RoleParser() {
        setSetting("language", "en");
    }

    public List<DtoRole> parseXLSXFile(InputStream f) throws XLSXParserException {
        return parseXLSXFile(f, COLUMNS);
    }

    @Override
    protected DtoRole parseRow(Map<String, Integer> columnIndexes, Row row, Map<String, String> settings) {
        DtoRole role = new DtoRole();

        for (String column : columnIndexes.keySet()) {
            Cell cell = row.getCell(columnIndexes.get(column));
            cell.setCellType(Cell.CELL_TYPE_STRING);
            switch (column) {
                case "ROLE":
                    role.setRole(cell.getStringCellValue());
                    break;
                default:
                    break;
            }
        }
        return role;
    }
}
