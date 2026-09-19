package com.mthree.academy.co458.vrishti_va.flooring_mastery.dao;

import com.mthree.academy.co458.vrishti_va.flooring_mastery.model.Tax;

import java.util.List;

public interface TaxDao {

    /**
     * Get all the current tax information.
     * @return A list of Tax objects.
     */
    public List<Tax> getAllTaxes();

}
