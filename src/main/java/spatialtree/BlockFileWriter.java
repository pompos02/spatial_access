package main.java.spatialtree;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BlockFileWriter {

    private static final int BLOCK_SIZE = 32 * 1024; // 32 KB

    public static void writeToDataFile(List<SpatialDataEntry> entries, String dataFilename, int dimension ) throws IOException, ClassNotFoundException {
        Files.deleteIfExists(Path.of(helper.PATH_TO_DATAFILE)); // Resetting/Deleting dataFile data
        helper.dataDimensions = dimension;
        helper.updateMetaData(helper.PATH_TO_DATAFILE);
        ArrayList<Record> blockRecords = new ArrayList<>();
        int maxRecordsInBlock = helper.calculateMaxRecordsInBlock();
        for (SpatialDataEntry entry : entries) {
            if (blockRecords.size() == maxRecordsInBlock) {
                helper.writeDataFileBlock(blockRecords);
                blockRecords = new ArrayList<>();
            }
            blockRecords.add(new Record(entry.getId(), entry.getName(), entry.getCoordinates()));
        }
        if (blockRecords.size() > 0)
            helper.writeDataFileBlock(blockRecords);
    }



    public static void main(String[] args) throws IOException, ClassNotFoundException {
        List<SpatialDataEntry> entries = DataFileManager.loadDataFromFile("map.osm");
        writeToDataFile(entries, "datafile.dat",2);
        ArrayList<Integer> metaData = helper.readMetaData(helper.PATH_TO_DATAFILE);

        // Check if metadata is not null and print it
        if (metaData != null) {
            System.out.println("Metadata from " + helper.PATH_TO_DATAFILE + ":");
            for (Integer data : metaData) {
                System.out.println(data); // 1st data dimensions 2nd block size 3rd total blocks
            }
        } else {
            System.out.println("Failed to read metadata or metadata is empty.");
        }
    }


}