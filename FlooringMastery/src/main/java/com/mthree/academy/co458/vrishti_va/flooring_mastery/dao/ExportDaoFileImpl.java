package com.mthree.academy.co458.vrishti_va.flooring_mastery.dao;

import com.mthree.academy.co458.vrishti_va.flooring_mastery.model.Order;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Handles write-only to export file.
 */
@Repository
public class ExportDaoFileImpl implements ExportDao {

    private static final String DELIMITER = "::";
    private final String EXPORT_FILE;

    public ExportDaoFileImpl(@Value("${files.exports.file-name}") String exportFile) {
        this.EXPORT_FILE = exportFile;
    }

    @Override
    public void exportActiveOrders(List<Order> activeOrders) throws PersistenceException {
        writeExportsToFile(activeOrders);
    }

    /**
     * Marshall orders into String format for the export file.
     * @param order The order to marshall.
     * @return Order in String format.
     */
    private String marshallOrder(Order order) {
        return order.getOrderNumber() + DELIMITER +
            order.getCustomerName() + DELIMITER +
            order.getState() + DELIMITER +
            order.getTaxRate() + DELIMITER +
            order.getProductType() + DELIMITER +
            order.getArea() + DELIMITER +
            order.getCostPerSquareFoot() + DELIMITER +
            order.getLaborCostPerSquareFoot() + DELIMITER +
            order.getMaterialCost() + DELIMITER +
            order.getLaborCost() + DELIMITER +
            order.getTax() + DELIMITER +
            order.getTotal() + DELIMITER +
            order.getOrderDate().format(DateTimeFormatter.ofPattern("MM-dd-yyyy"));
    }

    private void writeExportsToFile(List<Order> activeOrders) throws PersistenceException {

        //Open correct file in write mode
        PrintWriter printWriter;
        try {
            printWriter = new PrintWriter(new FileWriter(EXPORT_FILE));
        } catch (IOException e) {
            throw new PersistenceException("Unable to export active orders.");
        }

        //Write header to export file
        printWriter.println(
            "OrderNumber" + DELIMITER +
            "CustomerName" + DELIMITER +
            "State" + DELIMITER +
            "TaxRate" + DELIMITER +
            "ProductType" + DELIMITER +
            "Area" + DELIMITER +
            "CostPerSquareFoot" + DELIMITER +
            "LaborCostPerSquareFoot" + DELIMITER +
            "MaterialCost" + DELIMITER +
            "LaborCost" + DELIMITER +
            "Tax" + DELIMITER +
            "Total" + DELIMITER +
            "OrderDate"
        );

        //Write each active order to file
        String orderString;
        for (Order order : activeOrders) {

            //Marshall order
            orderString = marshallOrder(order);

            //Write order immediately
            printWriter.println(orderString);
            printWriter.flush();
        }

        //Clean up
        printWriter.close();
    }

}
