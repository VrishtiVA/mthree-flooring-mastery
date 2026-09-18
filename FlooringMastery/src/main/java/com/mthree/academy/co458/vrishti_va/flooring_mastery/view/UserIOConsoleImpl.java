package com.mthree.academy.co458.vrishti_va.flooring_mastery.view;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class UserIOConsoleImpl implements UserIO {

    Scanner inputReader;

    public UserIOConsoleImpl() {
        this.inputReader = new Scanner(System.in);
    }

    @Override
    public void print(String message) {
        System.out.println(message);
    }

    @Override
    public String readString(String prompt) {

        //Desired input
        String userInput;

        //Prompt the user and accept string input
        System.out.print(prompt);
        userInput = this.inputReader.nextLine();

        //Return the String read in
        return userInput;
    }

    @Override
    public int readInt(String prompt) {

        //Desired input
        int userInput;

        //Retry input until acceptable
        do {
            try {
                //Prompt the user and take int input
                System.out.print(prompt);
                userInput = this.inputReader.nextInt();

                //If reached here, return valid input
                return userInput;

            } catch (InputMismatchException e) {
                System.out.println("Invalid input, please try again.");

            } finally {
                //Absorb rest of line to prevent problems for subsequent reads.
                this.inputReader.nextLine();
            }
        } while (true);

    }

    @Override
    public int readInt(String prompt, int min, int max) {

        //Desired input
        int userInput;

        //Retry input until acceptable
        do {
            try {
                //Prompt the user and take int input
                System.out.print(prompt);
                userInput = this.inputReader.nextInt();

                //Apply range check
                if (userInput < min || userInput > max) {
                    throw new IllegalArgumentException();
                }

            } catch (InputMismatchException | IllegalArgumentException e) {
                System.out.printf("Invalid input, it should be %d <= x <= %d. Please try again.\n", min, max);
                continue;

            } finally {
                //Absorb rest of line to prevent problems for subsequent reads.
                this.inputReader.nextLine();
            }

            //If reached here, return valid input.
            return userInput;

        } while (true);
    }

    @Override
    public double readDouble(String prompt) {

        //Desired input
        double userInput;

        //Retry input until acceptable
        do {
            try {
                //Prompt the user and take double type input
                System.out.print(prompt);
                userInput = this.inputReader.nextDouble();

                //If reached here, return valid input
                return userInput;

            } catch (InputMismatchException e) {
                System.out.println("Invalid input, please try again.");

            } finally {
                //Absorb rest of line to prevent problems for subsequent reads.
                this.inputReader.nextLine();
            }
        } while (true);

    }

    @Override
    public double readDouble(String prompt, double min, double max) {

        //Desired input
        double userInput;

        //Retry input until acceptable
        do {
            try {
                //Prompt the user and take double type input
                System.out.print(prompt);
                userInput = this.inputReader.nextDouble();

                //Apply range check
                if (userInput < min || userInput > max) {
                    throw new IllegalArgumentException();
                }

            } catch (InputMismatchException | IllegalArgumentException e) {
                System.out.printf("Invalid input, it should be %f <= x <= %f. Please try again.\n", min, max);
                continue;

            } finally {
                //Absorb rest of line to prevent problems for subsequent reads.
                this.inputReader.nextLine();
            }

            //If reached here, return valid input.
            return userInput;

        } while (true);
    }

    @Override
    public float readFloat(String prompt) {

        //Desired input
        float userInput;

        //Retry input until acceptable
        do {
            try {
                //Prompt the user and take float type input
                System.out.print(prompt);
                userInput = this.inputReader.nextFloat();

                //If reached here, return valid input
                return userInput;

            } catch (InputMismatchException e) {
                System.out.println("Invalid input, please try again.");

            } finally {
                //Absorb rest of line to prevent problems for subsequent reads.
                this.inputReader.nextLine();
            }
        } while (true);

    }

    @Override
    public float readFloat(String prompt, float min, float max) {

        //Desired input
        float userInput;

        //Retry input until acceptable
        do {
            try {
                //Prompt the user and take float type input
                System.out.print(prompt);
                userInput = this.inputReader.nextFloat();

                //Apply range check
                if (userInput < min || userInput > max) {
                    throw new IllegalArgumentException();
                }

            } catch (InputMismatchException | IllegalArgumentException e) {
                System.out.printf("Invalid input, it should be %f <= x <= %f. Please try again.\n", min, max);
                continue;

            } finally {
                //Absorb rest of line to prevent problems for subsequent reads.
                this.inputReader.nextLine();
            }

            //If reached here, return valid input.
            return userInput;

        } while (true);
    }

    @Override
    public long readLong(String prompt) {

        //Desired input
        long userInput;

        //Retry input until acceptable
        do {
            try {
                //Prompt the user and take long type input
                System.out.print(prompt);
                userInput = this.inputReader.nextLong();

                //If reached here, return valid input
                return userInput;

            } catch (InputMismatchException e) {
                System.out.println("Invalid input, please try again.");

            } finally {
                //Absorb rest of line to prevent problems for subsequent reads.
                this.inputReader.nextLine();
            }
        } while (true);
    }

    @Override
    public long readLong(String prompt, long min, long max) {

        //Desired input
        long userInput;

        //Retry input until acceptable
        do {
            try {
                //Prompt the user and take long type input
                System.out.print(prompt);
                userInput = this.inputReader.nextLong();

                //Apply range check
                if (userInput < min || userInput > max) {
                    throw new IllegalArgumentException();
                }

            } catch (InputMismatchException | IllegalArgumentException e) {
                //The comma in %,d separates numbers with commas for improved readability.
                System.out.printf("Invalid input, it should be %,d <= x <= %,d. Please try again.\n", min, max);
                continue;

            } finally {
                //Absorb rest of line to prevent problems for subsequent reads.
                this.inputReader.nextLine();
            }

            //If reached here, return valid input.
            return userInput;

        } while (true);
    }

    @Override
    public LocalDate readDate(String prompt, boolean isOptional, LocalDate min, LocalDate max) {

        //Desired input
        LocalDate userInput;
        String userInputString;

        //Retry input until acceptable
        do {
            try {
                //Prompt the user and take input for date
                System.out.print(prompt);
                userInputString = this.inputReader.nextLine().trim();

                //Parse the date input while handling if its optional
                if (isOptional) {
                    userInput = userInputString.isBlank() ? null : LocalDate.parse(userInputString, DATE_FORMAT);
                } else {
                    userInput = LocalDate.parse(userInputString, DATE_FORMAT);
                }

                //If provided null since optional, allow escape now with null
                if (userInput == null) return null;

                //Apply range check
                if ((min != null && userInput.isBefore(min)) || (min != null && userInput.isAfter(max))) {
                    throw new IllegalArgumentException();
                }

            } catch (DateTimeParseException | IllegalArgumentException e) {
                //Display appropriate feedback
                if (min != null && max != null) {
                    System.out.printf("Invalid input, it should be a valid date in %s format between %s and %s. Please try again.\n", DATE_FORMAT, min, max);
                } else if (min != null) {
                    System.out.printf("Invalid input, it should be a valid date in %s format after %s. Please try again.\n", DATE_FORMAT, min);
                } else if (max != null ){
                    System.out.printf("Invalid input, it should be a valid date in %s format before %s. Please try again.\n", DATE_FORMAT, max);
                } else {
                    System.out.printf("Invalid input, it should be a valid date in %s format. Please try again.\n", DATE_FORMAT);
                }
                continue;
            }

            //If reached here, return valid input.
            return userInput;

        } while (true);
    }

}
