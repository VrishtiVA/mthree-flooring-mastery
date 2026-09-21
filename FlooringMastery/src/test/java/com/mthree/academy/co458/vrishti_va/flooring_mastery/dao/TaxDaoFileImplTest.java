package com.mthree.academy.co458.vrishti_va.flooring_mastery.dao;

import com.mthree.academy.co458.vrishti_va.flooring_mastery.dto.SampleTax;
import com.mthree.academy.co458.vrishti_va.flooring_mastery.model.Tax;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TaxDaoFileImplTest {

    private static final String TEST_TAX_FILE = "TestData/Taxes.txt";
    private TaxDao taxDao;

    @BeforeEach
    void setUp() {
        this.taxDao = new TaxDaoFileImpl(TEST_TAX_FILE);
    }

    /**
     * Test that all expected tax states are returned in a list
     */
    @Test
    void testGetAllTaxes() {

        //Act
        List<Tax> allTaxes = taxDao.getAllTaxes();

        //Assert
        assertNotNull(allTaxes, "The returned taxes should not be null");
        assertEquals(4, allTaxes.size(), "4 taxes should have been found in the list.");
        assertTrue(allTaxes.containsAll(List.of(
            SampleTax.getSampleTax1(),
            SampleTax.getSampleTax2(),
            SampleTax.getSampleTax3(),
            SampleTax.getSampleTax4()
        )));
    }

    /**
     * Test that all expected tax states are returned in a map.
     */
    @Test
    void testGetAllTaxesIndexedByState() {

        //Act
        Map<String, Tax> allTaxes = taxDao.getAllTaxesIndexedByState();

        //Assert
        assertNotNull(allTaxes, "The returned taxes should not be null");
        assertEquals(4, allTaxes.size(), "4 taxes should have been found in the map.");
        assertEquals(SampleTax.getSampleTax1(), allTaxes.get(SampleTax.getSampleTax1().getStateAbbreviation().toUpperCase()));
        assertEquals(SampleTax.getSampleTax2(), allTaxes.get(SampleTax.getSampleTax2().getStateAbbreviation().toUpperCase()));
        assertEquals(SampleTax.getSampleTax3(), allTaxes.get(SampleTax.getSampleTax3().getStateAbbreviation().toUpperCase()));
        assertEquals(SampleTax.getSampleTax4(), allTaxes.get(SampleTax.getSampleTax4().getStateAbbreviation().toUpperCase()));
    }
}