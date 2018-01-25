package com.dsv.road.shared.masterdata.parser;

import com.dsv.road.shared.masterdata.dto.*;
import com.dsv.road.shared.masterdata.parser.base.XLSXParser;
import com.dsv.road.shared.masterdata.parser.base.XLSXParserException;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.InputStream;
import java.util.*;

/**
 * Created by EXT.K.Theilgaard on 14-07-2015.
 */
public class DangerousGoodsParser extends XLSXParser<DtoDangerousGoods> {
    private static final String[] COLUMN_ARRAY = new String[]{
            "UNNUMBER", "DGMID", "NAMEANDDESCRIPTION", "ADR_PSN", "IMDG_PSN", "IATA_PSN", "ADR_CLASS", "IMDG_CLASS", "IATA_CLASS", "ADR_PG", "IMDG_PG", "IATA_PG", "ADR_SPECIALPROVISIONS", "IMDG_SPECIALPROVISIONS", "IATA_SPECIALPROVISIONS"
            , "ADR_EXCEPTEDQUANTITIES", "IMDG_EXCEPTEDQUANTITIES", "IATA_EXCEPTEDQUANTITIES", "ADR_FACTOR", "ADR_CLASSIFICATIONCODE", "ADR_TRANSPORTCATEGORY", "ADR_TUNNELCODE", "IATA_LABEL_1", "IATA_LABEL_2", "IATA_LABEL_3", "IMDG_LABEL_1"
            , "IMDG_LABEL_2", "IMDG_LABEL_3", "IMDG_LABEL_4", "ADR_LABEL_1", "ADR_LABEL_2", "ADR_LABEL_3", "ADR_LABEL_4", "ADR_PSN_EN", "ADR_PSN_FR", "ADR_PSN_DE", "ADR_PSN_DA", "ADR_PSN_NO", "ADR_PSN_SV", "ADR_PSN_FI", "ADR_PSN_NL", "ADR_PSN_ES"
            , "ADR_PSN_PT", "ADR_PSN_LT", "ADR_PSN_LV", "ADR_PSN_ET", "ADR_PSN_PL", "ADR_PSN_CS", "ADR_PSN_SK", "ADR_PSN_HU", "ADR_PSN_RO", "ADR", "IATA", "IMDG"
    };
    private static final Set<String> COLUMNS = new HashSet<>(Arrays.asList(COLUMN_ARRAY));

    public DtoDangerousGoodsBundle parseXLSXFile(InputStream f) throws XLSXParserException {
        DtoDangerousGoodsBundle dtoDangerousGoodsBundle = new DtoDangerousGoodsBundle();
        dtoDangerousGoodsBundle.setDangerousGoodsList(parseXLSXFile(f, COLUMNS));
        return dtoDangerousGoodsBundle;
    }

    @Override
    protected DtoDangerousGoods parseRow(Map<String, Integer> columnIndexes, Row row, Map<String, String> settings) {
        DtoDangerousGoods dangerousGoods = DtoDangerousGoods
                .builder()
                .adr(new DtoDangerousGoodsAdr())
                .iata(new DtoDangerousGoodsDivision())
                .imdg(new DtoDangerousGoodsDivision()).build();
        List<DtoDangerousGoodsText> dangerousGoodsTexts = new ArrayList<>();
        String imdgLabels = null;
        String adrLabels = null;
        String iataLabels = null;

        for (String column : columnIndexes.keySet()) {
            Cell cell = row.getCell(columnIndexes.get(column));
            if (cell == null) {
                continue;
            }
            switch (column) {
//                for entire dangerous good
                case "DGMID":
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    dangerousGoods.setDgmId(cell.getStringCellValue());
                    break;
                case "UNNUMBER":
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    dangerousGoods.setUnNumber(cell.getStringCellValue());
                    break;
                case "NAMEANDDESCRIPTION":
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    dangerousGoods.setAddDesc(cell.getStringCellValue());
                    break;
//                for adr,imdg and adr separately
                case "IMDG_PSN":
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    dangerousGoods.getImdg().setProperShipmentName(cell.getStringCellValue());
                    break;
                case "IATA_PSN":
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    dangerousGoods.getIata().setProperShipmentName(cell.getStringCellValue());
                    break;

                case "ADR_CLASS":
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    dangerousGoods.getAdr().setClassId(cell.getStringCellValue());
                    break;
                case "IMDG_CLASS":
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    dangerousGoods.getImdg().setClassId(cell.getStringCellValue());
                    break;
                case "IATA_CLASS":
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    dangerousGoods.getIata().setClassId(cell.getStringCellValue());
                    break;

                case "ADR_PG":
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    dangerousGoods.getAdr().setPackingGroup(cell.getStringCellValue());
                    break;
                case "IMDG_PG":
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    dangerousGoods.getImdg().setPackingGroup(cell.getStringCellValue());
                    break;
                case "IATA_PG":
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    dangerousGoods.getIata().setPackingGroup(cell.getStringCellValue());
                    break;

                case "ADR_SPECIALPROVISIONS":
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    dangerousGoods.getAdr().setSpecialProvisions(cell.getStringCellValue());
                    break;
                case "IMDG_SPECIALPROVISIONS":
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    dangerousGoods.getImdg().setSpecialProvisions(cell.getStringCellValue());
                    break;
                case "IATA_SPECIALPROVISIONS":
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    dangerousGoods.getIata().setSpecialProvisions(cell.getStringCellValue());
                    break;

                case "ADR_EXCEPTEDQUANTITIES":
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    dangerousGoods.getAdr().setExceptedQuantities(cell.getStringCellValue());
                    break;
                case "IMDG_EXCEPTEDQUANTITIES":
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    dangerousGoods.getImdg().setExceptedQuantities(cell.getStringCellValue());
                    break;
                case "IATA_EXCEPTEDQUANTITIES":
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    dangerousGoods.getIata().setExceptedQuantities(cell.getStringCellValue());
                    break;

//                Only adr
                case "ADR_FACTOR":
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    dangerousGoods.getAdr().setFactor(cell.getStringCellValue());
                    break;
                case "ADR_CLASSIFICATIONCODE":
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    dangerousGoods.getAdr().setClassifCode(cell.getStringCellValue());
                    break;
                case "ADR_TRANSPORTCATEGORY":
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    dangerousGoods.getAdr().setTransportCategory(cell.getStringCellValue());
                    break;
                case "ADR_TUNNELCODE":
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    dangerousGoods.getAdr().setTunnelCode(cell.getStringCellValue());
                    break;
                case "IATA_LABEL_1":
                case "IATA_LABEL_2":
                case "IATA_LABEL_3":
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    iataLabels = addToLabel(cell, iataLabels);
                    break;
                case "IMDG_LABEL_1":
                case "IMDG_LABEL_2":
                case "IMDG_LABEL_3":
                case "IMDG_LABEL_4":
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    imdgLabels = addToLabel(cell, imdgLabels);
                    break;
                case "ADR_LABEL_1":
                case "ADR_LABEL_2":
                case "ADR_LABEL_3":
                case "ADR_LABEL_4":
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    adrLabels = addToLabel(cell, adrLabels);
                    break;
                case "ADR_PSN_EN"://NOSONAR all below intentionally use code block from ADR_PSN_RO
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    dangerousGoods.getAdr().setProperShipmentName(cell.getStringCellValue());
                case "ADR_PSN_FR":
                case "ADR_PSN_DE":
                case "ADR_PSN_DA":
                case "ADR_PSN_NO":
                case "ADR_PSN_SV":
                case "ADR_PSN_FI":
                case "ADR_PSN_NL":
                case "ADR_PSN_ES":
                case "ADR_PSN_PT":
                case "ADR_PSN_LT":
                case "ADR_PSN_LV":
                case "ADR_PSN_ET":
                case "ADR_PSN_PL":
                case "ADR_PSN_CS":
                case "ADR_PSN_SK":
                case "ADR_PSN_HU":
                case "ADR_PSN_RO":
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    DtoDangerousGoodsText dangerousGoodsText = new DtoDangerousGoodsText();
                    dangerousGoodsText.setLanguageCode(column.substring(column.lastIndexOf("_") + 1));
                    dangerousGoodsText.setDangerousGoodsText(cell.getStringCellValue());
                    dangerousGoodsTexts.add(dangerousGoodsText);
                    break;
                case "ADR":
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    dangerousGoods.getAdr().setDangerous(StringUtils.isNotBlank(cell.getStringCellValue()));
                    break;
                case "IATA":
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    dangerousGoods.getIata().setDangerous(StringUtils.isNotBlank(cell.getStringCellValue()));
                    break;
                case "IMDG":
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    dangerousGoods.getImdg().setDangerous(StringUtils.isNotBlank(cell.getStringCellValue()));
                    break;
                default:
                    break;
            }
        }
        dangerousGoods.getImdg().setLabels(imdgLabels);
        dangerousGoods.getAdr().setLabels(adrLabels);
        dangerousGoods.getIata().setLabels(iataLabels);
        dangerousGoods.setDangerousGoodsTexts(dangerousGoodsTexts);
        return dangerousGoods;
    }

    private String addToLabel(Cell cell, String labels) {

        if (StringUtils.isEmpty(labels)) {
            return cell.getStringCellValue();
        } else {
            String label = cell.getStringCellValue();
            if (StringUtils.isNotEmpty(label)) {
                labels += ", " + label;
            }
        }
        return labels;
    }
}