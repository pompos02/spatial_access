package queries;
import main.java.spatialtree.*;
import main.java.spatialtree.Record;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RangeQuery2D {
    public static void main(String[] args) throws IOException {

        // ------------------------------------------------------------------------
        // Range Query Data
        ArrayList<Double> rStarRangeQueryTimes = new ArrayList<>();
        ArrayList<Double> seqScanRangeQueryTimes = new ArrayList<>();
        ArrayList<Double> areaOfRectangles = new ArrayList<>();
        ArrayList<Integer> rangeQueryRecords = new ArrayList<>();

        // ------------------------------------------------------------------------





        List<Record> records = DataFileManager.loadDataFromFile("cyprus.osm");

        helper.CreateDataFile(records,2, true);
        helper.CreateIndexFile(2,false);
        RStarTree rStarTree = new RStarTree(true);


        ArrayList<Bounds> queryBounds = new ArrayList<>();
        queryBounds.add(new Bounds(35.1014243 , 37.1014243 + 0.67));
        queryBounds.add(new Bounds(33.0938391 - 0.67, 37.0938391 + 0.67));


        System.out.print("R Star - Range Query: ");
        long startRangeQueryTime = System.nanoTime();
        ArrayList<Long> queryRecords = rStarTree.getDataInBoundingBox(new BoundingBox(queryBounds));
        long stopRangeQueryTime = System.nanoTime();
        //for (Long id : queryRecords)
           // System.out.print(id + ", ");
        System.out.println();
        System.out.println("Time taken: " + (double) (stopRangeQueryTime - startRangeQueryTime) / 1000000 + " ms");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("rangeQueryResults.csv"))) {
            String tagString = "Rectangle's Area" +
                    ',' +
                    "Returned Records" +
                    ',' +
                    "R* Time(ms)" +
                    ',' +
                    "Sequential Scan Time(ms)" +
                    '\n';
            writer.write(tagString);

            // Range Query File creation
           // int j = 0;
           // while(j < queryRecords.size()){
               // writer.write(String.format("rangeQueryRecords.get(j) +"," +rStarRangeQueryTimes.get(j)+ "," + seqScanRangeQueryTimes.get(j) + "\n"));
                //j++;

            //}
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}