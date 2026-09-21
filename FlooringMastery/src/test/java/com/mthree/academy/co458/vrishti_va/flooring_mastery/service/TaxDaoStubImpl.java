package com.mthree.academy.co458.vrishti_va.flooring_mastery.service;

import com.mthree.academy.co458.vrishti_va.flooring_mastery.dao.TaxDao;
import com.mthree.academy.co458.vrishti_va.flooring_mastery.dto.SampleTax;
import com.mthree.academy.co458.vrishti_va.flooring_mastery.model.Tax;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TaxDaoStubImpl implements TaxDao {

    private Map<String, Tax> allTaxes;

    public TaxDaoStubImpl() {
        
        allTaxes = new HashMap<>();

        //Populate taxes map
        Tax tax = SampleTax.getSampleTax1();
        allTaxes.put(tax.getStateAbbreviation(), tax);
        tax = SampleTax.getSampleTax2();
        allTaxes.put(tax.getStateAbbreviation(), tax);
        tax = SampleTax.getSampleTax3();
        allTaxes.put(tax.getStateAbbreviation(), tax);
        tax = SampleTax.getSampleTax4();
        allTaxes.put(tax.getStateAbbreviation(), tax);
    }

    @Override
    public List<Tax> getAllTaxes() {
        return new ArrayList<>(allTaxes.values());
    }

    @Override
    public Map<String, Tax> getAllTaxesIndexedByState() {
        return Map.copyOf(allTaxes);
    }

}
