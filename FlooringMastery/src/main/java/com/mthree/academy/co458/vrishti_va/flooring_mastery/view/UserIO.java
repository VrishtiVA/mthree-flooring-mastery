package com.mthree.academy.co458.vrishti_va.flooring_mastery.view;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public interface UserIO {

    //Constants - These are public static final
    public String DATE_FORMAT_PATTERN = "dd-MM-yyyy";
    public DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern(DATE_FORMAT_PATTERN);

    /**
     * Display a String to the User.
     * @param message The String displayed.
     */
    void print(String message);

    /**
     * Prompt the user to enter a string, read in a String input, and return it.
     * @param prompt The prompt message.
     * @return The String value read in.
     */
    String readString(String prompt);

    /**
     * Prompt the user to enter in an integer, read in an int input, and return it.
     * @param prompt The prompt message.
     * @return The int value that is read in.
     */
    int readInt(String prompt);

    /**
     * Prompt the user to enter an integer between a specified range, read in an int input, and return it.
     * If the number is not within the correct range, keep prompting the user for new input until it is.
     * @param prompt The prompt message.
     * @param min The min value (inclusive).
     * @param max The max value (inclusive).
     * @return The int value that is read in and within the specified range.
     */
    int readInt(String prompt, int min, int max);

    /**
     * Prompt the user to enter a double, read in a double input, and return it.
     * @param prompt The prompt message.
     * @return The double value that is read in.
     */
    double readDouble(String prompt);

    /**
     * Prompt the user to enter a double between a specified range, read in a double input, and return it.
     * If the number is not within the correct range, keep prompting the user for new input until it is.
     * @param prompt The prompt message.
     * @param min The min value (inclusive).
     * @param max The max value (inclusive).
     * @return The double value that is read in.
     */
    double readDouble(String prompt, double min, double max);

    /**
     * Prompt the user to enter a float, read in a float input, and return it.
     * @param prompt The prompt message.
     * @return The float value that is read in.
     */
    float readFloat(String prompt);

    /**
     * Prompt the user to enter a float between a specified range, read in a float input, and return it.
     * If the number is not within the correct range, keep prompting the user for new input until it is.
     * @param prompt The prompt message.
     * @param min The min value (inclusive).
     * @param max The max value (inclusive).
     * @return The float value that is read in.
     */
    float readFloat(String prompt, float min, float max);

    /**
     * Prompt the user to enter a long type number, read in a long type input, and return it.
     * @param prompt The prompt message.
     * @return The long value that is read in.
     */
    long readLong(String prompt);

    /**
     * Prompt the user to enter a long type value between a specified range, read in a long input, and return it.
     * If the number is not within the correct range, keep prompting the user for new input until it is.
     * @param prompt The prompt message.
     * @param min The min value (inclusive).
     * @param max The max value (inclusive).
     * @return The long read that is read in.
     */
    long readLong(String prompt, long min, long max);

    /**
     * Prompt the user to enter a date, read in a date input, and return it.
     * @param prompt The prompt message.
     * @return The corresponding LocalDate that is read in.
     */
    LocalDate readDate(String prompt);

    /**
     * Prompt the user to enter a date that is between a specified range, read in the date, and return it.
     * @param prompt The prompt message.
     * @param min The earliest valid date (inclusive). If not provided (null), allow earlier dates.
     * @param max The latest valid date (inclusive). If not provided (null), allow later dates.
     * @return The corresponding LocalDate that is read in.
     */
    LocalDate readDate(String prompt, LocalDate min, LocalDate max);

}
