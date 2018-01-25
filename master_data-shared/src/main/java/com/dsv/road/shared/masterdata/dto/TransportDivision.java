package com.dsv.road.shared.masterdata.dto;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public enum TransportDivision {
    IATA, IMDG, ADR;

    private static final char SEPARATOR = ',';

    public static TransportDivision[] toTransportDivisionsArray(List<String> divisions) {
        if (divisions == null) {
            return new TransportDivision[] {};
        }
        String combinedDivisionsStr = StringUtils.join(divisions, SEPARATOR);
        String[] combinedDivisionsArray = StringUtils.split(combinedDivisionsStr, SEPARATOR);
        List<TransportDivision> combinedDivisionsList = new ArrayList<>();
        for (String division : combinedDivisionsArray) {
            combinedDivisionsList.add(TransportDivision.valueOf(division));
        }

        return combinedDivisionsList.toArray(new TransportDivision[combinedDivisionsList.size()]);
    }
}
