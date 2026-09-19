package com.mthree.academy.co458.vrishti_va.flooring_mastery.dao;

import com.mthree.academy.co458.vrishti_va.flooring_mastery.model.Product;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.*;

public class ProductDaoFileImpl implements ProductDao {

    private static final String DELIMITER = "::";
    private final String PRODUCT_FILE;

    //Hold mappings of product type to product
    private Map<String, Product> productMap;

    public ProductDaoFileImpl(String productFile) {
        this.PRODUCT_FILE = productFile;
        this.productMap = new HashMap<>();
    }

    /**
     * Get all the products
     * @return A list of all the products, never null.
     *
     * @implNote
     * As a team agreement, our interpretation allows the program to notice changes to product file during program execution,
     * and utilize the current products file. This is why we re-read the products file on get.
     * As a future consideration, if we wanted to improve program performance by relaxing this interpretation
     * (meaning to not account for product file edits during program execution),
     * we could simply move reading the product file from here into the ProductDao constructor to pre-equip the ProductDao.
     * Alternatively, a further improvement idea may be able to consider if the file was modified since the last read.
     */
    @Override
    public List<Product> getAllProducts() {

        //Read products file - see implNote
        loadProductsFromFile();

        //Return all products
        return new ArrayList<>(productMap.values());
    }


    /**
     * Unmarshall product String into Product object.
     * @param productString The product String to unmarshall.
     * @return The corresponding Product object.
     */
    private Product unmarshallProduct(String productString) {

        //Break down product string
        String[] productData = productString.split(DELIMITER);

        //Return rebuilt product object
        return new Product(
            productData[0], //Product type
            new BigDecimal(productData[1]), //Cost per square foot
            new BigDecimal(productData[2]) //Labor cost per square foot
        );
    }

    /**
     * Read the products file and reflect its content into the products map.
     * @throws PersistenceException If the method is unable to read from the products file.
     */
    private void loadProductsFromFile() throws PersistenceException {

        //Open file in read mode
        Scanner fileScanner;
        try {
            fileScanner = new Scanner(new BufferedReader(new FileReader(PRODUCT_FILE)));
        } catch (FileNotFoundException e) {
            throw new PersistenceException("Unable to load product details.", e);
        }

        //Read contents of the product file
        Product currentProduct;
        while (fileScanner.hasNextLine()) {

            //Read current product line
            currentProduct = unmarshallProduct(fileScanner.nextLine());

            //Populate products map
            productMap.put(currentProduct.getProductType(), currentProduct);
        }
    }

}
