package com.mthree.academy.co458.vrishti_va.flooring_mastery.dao;

import com.mthree.academy.co458.vrishti_va.flooring_mastery.model.Tax;

import java.util.List;
import java.util.Map;

public interface TaxDao {

    /**
     * Get all the current tax information.
     * @return A list of Tax objects.
     */
    public List<Tax> getAllTaxes();

    /**
     * Get all the current tax information.
     * @return A map of Tax objects, keyed by state.
     */
    public Map<String, Tax> getAllTaxesIndexedByState();

}
