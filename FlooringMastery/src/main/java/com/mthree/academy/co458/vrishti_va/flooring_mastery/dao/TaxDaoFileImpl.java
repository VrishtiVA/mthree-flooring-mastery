package com.mthree.academy.co458.vrishti_va.flooring_mastery.dao;

import com.mthree.academy.co458.vrishti_va.flooring_mastery.model.Tax;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.time.*;
import java.util.*;

public class TaxDaoFileImpl implements TaxDao {

    private static final String DELIMITER = "::";
    private final String TAX_FILE;
    private final File TAX_FILE_POINTER;
    private LocalDateTime taxFileLastRead;

    //Hold mappings of tax states abbreviations to taxes
    private Map<String, Tax> allTaxes;

    public TaxDaoFileImpl(String taxFile) {

        this.TAX_FILE = taxFile;
        this.TAX_FILE_POINTER = new File(TAX_FILE);
        this.taxFileLastRead = null;
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
        if (FileReadOptimiser.doesFileNeedReading(TAX_FILE_POINTER, taxFileLastRead))
            loadTaxesFromFile();

        //Return all taxes
        return new ArrayList<>(allTaxes.values());
    }

    @Override
    public Map<String, Tax> getAllTaxesIndexedByState() {

        //Read tax file
        if (FileReadOptimiser.doesFileNeedReading(TAX_FILE_POINTER, taxFileLastRead))
            loadTaxesFromFile();

        //Return all taxes in an unmodifiable map
        return Map.copyOf(allTaxes);
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
     * This method assumes the file will include a header row.
     * @throws PersistenceException If the method is unable to read from the taxes file.
     * @implNote Loading the state abbreviation key as upper case intends to make searching the map easier.
     */
    private void loadTaxesFromFile() throws PersistenceException {

        //Open file in read mode
        Scanner fileScanner;
        LocalDateTime readTime = LocalDateTime.now();
        try {
            fileScanner = new Scanner(new BufferedReader(new FileReader(TAX_FILE)));
        } catch (FileNotFoundException e) {
            throw new PersistenceException("Unable to load tax details.", e);
        }

        //Skip header row
        fileScanner.nextLine();

        //Read contents of the tax file
        Tax currentTax;
        while (fileScanner.hasNextLine()) {

            //Read current tax line
            currentTax = unmarshallTax(fileScanner.nextLine());

            //Populate taxes map
            allTaxes.put(currentTax.getStateAbbreviation().toUpperCase(), currentTax);
        }

        //Clean up
        fileScanner.close();
        this.taxFileLastRead = readTime;
    }

}
