package com.mthree.academy.co458.vrishti_va.flooring_mastery.dao;

import java.io.File;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public interface FileReadOptimiser {

    /**
     * Method to check if the file needs re-reading, implemented as a sort of utility class.
     * This is if the file was modified since it was last read, or it hasn't been read yet.
     * @return true if file does need re-reading, otherwise false.
     * @throws PersistenceException If program cannot find tax file to check.
     * @see java.io.File For last modified method.
     */
    public static boolean doesFileNeedReading(File filePointer, LocalDateTime lastRead) throws PersistenceException {

        //If haven't yet read the file before, yes need to read.
        if (lastRead == null) return true;

        //Get last modified - milliseconds since epoch
        long lastModifiedMs = filePointer.lastModified();

        //If tax file is missing, throw error.
        if (lastModifiedMs == 0L) {
            throw new PersistenceException("Unable to load tax details.");
        }

        //Convert epoch milliseconds to instant (machine time), to local zone, to local date time for use.
        LocalDateTime lastModified = ZonedDateTime
                .ofInstant(Instant.ofEpochMilli(lastModifiedMs), ZoneId.systemDefault())
                .toLocalDateTime();

        //Return if file was modified since last reading
        return !lastRead.isAfter(lastModified);
    }

}
