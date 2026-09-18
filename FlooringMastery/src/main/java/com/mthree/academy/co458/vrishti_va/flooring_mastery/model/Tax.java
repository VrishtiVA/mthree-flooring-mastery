package com.mthree.academy.co458.vrishti_va.flooring_mastery.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Tax {

    private String stateAbbreviation;
    private String stateName;
    private BigDecimal taxRate;

    public Tax() {}

    public Tax(String stateAbbreviation, String stateName, BigDecimal taxRate) {
        this.stateAbbreviation = stateAbbreviation;
        this.stateName = stateName;
        this.taxRate = taxRate;
    }

    /* ----- Getters ----- */
    public String getStateAbbreviation() {return stateAbbreviation;}
    public String getStateName() {return stateName;}
    public BigDecimal getTaxRate() {return taxRate;}

    /* ----- Setters ----- */
    /* There are no setter methods for this class, as all fields are read-only. */

    @Override
    public String toString() {
        return "Tax{" +
                "stateAbbreviation='" + stateAbbreviation + '\'' +
                ", stateName='" + stateName + '\'' +
                ", taxRate=" + taxRate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Tax tax = (Tax) o;
        return Objects.equals(stateAbbreviation, tax.stateAbbreviation) && Objects.equals(stateName, tax.stateName) && Objects.equals(taxRate, tax.taxRate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stateAbbreviation, stateName, taxRate);
    }

}
