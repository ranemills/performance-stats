package com.mills.performances.models;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;

import static org.junit.Assert.*;

public class BellBoardImportTest {

    @Test
    public void testEqualsAndHashCode()
    {
        EqualsVerifier.forClass(BellBoardImport.class)
                      .suppress(Warning.NONFINAL_FIELDS)
                      .verify();
    }

}