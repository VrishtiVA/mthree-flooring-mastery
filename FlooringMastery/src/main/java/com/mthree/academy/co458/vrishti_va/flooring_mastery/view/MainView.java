package com.mthree.academy.co458.vrishti_va.flooring_mastery.view;

public class MainView {

    private UserIO userIO;

    public MainView(UserIO userIO) {
        this.userIO = userIO;
    }

    /* ----- Menu ----- */

    public int displayAndGetMenuSelection() {
        userIO.print(
            "\n<< Flooring Program >>" +
            "\n1. Display Orders" +
            "\n2. Add an Order" +
            "\n3. Edit an Order" +
            "\n4. Remove an Order" +
            "\n5. Export Active Orders" +
            "\n6. Quit"
        );
        return userIO.readInt("Enter Selection (#)", 1, 6);
    }

    public void displayUnknownMenuOptionWarning() {
        userIO.print("Warning: Unknown Menu Option.");
    }

    /* ----- Option Headers ----- */

    private void displayHeader(String header) {
        userIO.print("\n<< " + header + " >>");
    }

    public void displayDisplayOrdersHeader() { displayHeader("Display Orders"); }
    public void displayAddOrderHeader() { displayHeader("Add Order"); }
    public void displayEditOrderHeader() { displayHeader("Edit Order"); }
    public void displayRemoveOrderHeader() { displayHeader("Remove Order"); }
    public void displayExportActiveOrdersHeader() { displayHeader("Export Active Orders"); }

}
