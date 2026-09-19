package com.mthree.academy.co458.vrishti_va.flooring_mastery.dao;

import com.mthree.academy.co458.vrishti_va.flooring_mastery.model.Tax;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.*;

public class TaxDaoFileImpl implements TaxDao {

    private static final String DELIMITER = "::";
    private final String TAX_FILE;

    //Hold mappings of tax state abbreviations to taxes
    private Map<String, Tax> allTaxes;

    public TaxDaoFileImpl(String taxFile) {
        this.TAX_FILE = taxFile;
        this.allTaxes = new HashMap<>();
    }

    /**
     * Get all the taxes
     * @return A list of all the taxes, never null.
     *
     * @implNote
     * As a team agreement, our interpretation allows the program to notice changes to tax file during program execution,
     * and utilize the current tax file. This is why we re-read the tax file on get.
     * As a future consideration, if we wanted to improve program performance by relaxing this interpretation
     * (meaning to not account for tax file edits during program execution),
     * we could simply move reading the tax file from here into the TaxDao Constructor to pre-equip the TaxDao.
     * Alternatively, a further improvement idea may be able to consider if the file was modified since the last read.
     */
    @Override
    public List<Tax> getAllTaxes() {

        //Read tax file - see implNote
        loadTaxesFromFile();

        //Return all taxes
        return new ArrayList<>(allTaxes.values());
    }

    /**
     * Unmarshall tax String into Tax object.
     * @param taxString The tax String to unmarshall.
     * @return The corresponding Tax object.
     */
    private Tax unmarshallTax(String taxString) {

        //Break down tax string
        String[] taxData = taxString.split(DELIMITER);

        //Return rebuilt tax object
        return new Tax(
            taxData[0], //State abbreviation string
            taxData[1], //State string
            new BigDecimal(taxData[2]) //Tax rate
        );
    }

    /**
     * Read the taxes file and reflect its content into the taxes map.
     * @throws PersistenceException If the method is unable to read from the taxes file.
     */
    private void loadTaxesFromFile() throws PersistenceException {

        //Open file in read mode
        Scanner fileScanner;
        try {
            fileScanner = new Scanner(new BufferedReader(new FileReader(TAX_FILE)));
        } catch (FileNotFoundException e) {
            throw new PersistenceException("Unable to load tax details.", e);
        }

        //Read contents of the tax file
        Tax currentTax;
        while (fileScanner.hasNextLine()) {

            //Read current tax line
            currentTax = unmarshallTax(fileScanner.nextLine());

            //Populate taxes map
            allTaxes.put(currentTax.getStateAbbreviation(), currentTax);
        }
    }

}
