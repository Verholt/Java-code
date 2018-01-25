package com.dsv.road.shared.masterdata.dto;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


public class TransportDivisionTest {

    @Test
    public void shouldConvertListOfStringsToArray() {
        List<String> input = new ArrayList<>();
        input.add("IATA,IMDG");
        input.add("");
        input.add(null);
        input.add("ADR");

        TransportDivision[] output = TransportDivision.toTransportDivisionsArray(input);
        TransportDivision[] expected = new TransportDivision[] {TransportDivision.IATA, TransportDivision.IMDG, TransportDivision.ADR};

        assertArrayEquals(expected, output);
    }

}