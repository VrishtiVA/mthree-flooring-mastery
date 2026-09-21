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

    @Test
    void testGetAllTaxes() {

        //Act
        List<Tax> allTaxes = taxDao.getAllTaxes();

        //Assert
        assertNotNull(allTaxes);
        assertEquals(4, allTaxes.size());
        assertTrue(allTaxes.containsAll(List.of(
            SampleTax.getSampleTax1(),
            SampleTax.getSampleTax2(),
            SampleTax.getSampleTax3(),
            SampleTax.getSampleTax4()
        )));
    }

    @Test
    void testGetAllTaxesIndexedByState() {

        //Act
        Map<String, Tax> allTaxes = taxDao.getAllTaxesIndexedByState();

        //Assert
        assertNotNull(allTaxes);
        assertEquals(4, allTaxes.size());
        assertEquals(allTaxes.get(SampleTax.getSampleTax1().getStateAbbreviation().toUpperCase()), SampleTax.getSampleTax1());
        assertEquals(allTaxes.get(SampleTax.getSampleTax2().getStateAbbreviation().toUpperCase()), SampleTax.getSampleTax2());
        assertEquals(allTaxes.get(SampleTax.getSampleTax3().getStateAbbreviation().toUpperCase()), SampleTax.getSampleTax3());
        assertEquals(allTaxes.get(SampleTax.getSampleTax4().getStateAbbreviation().toUpperCase()), SampleTax.getSampleTax4());
    }
}