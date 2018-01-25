package com.dsv.road.masterdata.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class DangerousGoodsDivisionTest {

    @Test
    public void shouldCorrectlyAppendAnotherDangerousGoodsDivision() {
        DangerousGoodsDivision first = new DangerousGoodsDivision("a", "b", null, null, null, null, false);
        DangerousGoodsDivision second = new DangerousGoodsDivision("a ", "b2", " c ", "", null, null, false);
        DangerousGoodsDivision combined = new DangerousGoodsDivision();
        combined.append(first);
        combined.append(second);

        DangerousGoodsDivision expected = new DangerousGoodsDivision("a", "b, b2", "c", null, null, null, false);

        assertEquals(expected, combined);
    }

}