package Parser;

import VotingData.VoterData;

import java.io.*;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class CSVParse {

    private int lineNumber;
    private File readFile;
    private PrintWriter err;

    // Houses the counties from the read in line (Primary storage to reduce chance of error)
    private ArrayList<String> counties = new ArrayList<>();

    // Houses the voting nums from the read in line
    private ArrayList<String> demVotingNumbers = new ArrayList<>();
    private ArrayList<String> repVotingNumbers = new ArrayList<>();
    private ArrayList<String> othVotingNumbers = new ArrayList<>();
    private HashMap<String, VoterData> countyVoteInfo = new HashMap<>();

    //=============================================================== Constructor (Singleton)
    // Makes CSVParse a Singleton, because there only ever needs to be one instance of it
    private static CSVParse ourInstance = new CSVParse();

    public static CSVParse getInstance() {
        return ourInstance;
    }

    private CSVParse() {
        setLineNumber(0);
    }

    //=============================================================== Getters / Setters


    private int getLineNumber() {
        return lineNumber;
    }

    private void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    //=============================================================== Public Methods
    public void parse() throws FileNotFoundException {
        Date date = new Date();
        Format formatter = new SimpleDateFormat("YYYY-MM-dd_hh-mm-ss");
        err = new PrintWriter("logs/" + formatter.format(date) + ".txt");

        // Gotta encompass everything in a try, catch or else it'll yell at you.

        String OTHNUM_FILENAME = ".OutputFiles/othNumbers.bin";
        String COUNTY_FILENAME = ".OutputFiles/counties.bin";
        String DEMNUM_FILENAME = ".OutputFiles/demNumbers.bin";
        String REPNUM_FILENAME = ".OutputFiles/repNumbers.bin";
        try (PrintWriter countWriter = new PrintWriter(COUNTY_FILENAME);
             PrintWriter votNumWriter = new PrintWriter(DEMNUM_FILENAME);
             PrintWriter votNumWriter2 = new PrintWriter(REPNUM_FILENAME);
             PrintWriter votNumWriter3 = new PrintWriter(OTHNUM_FILENAME)) {
            File dir = new File("VoterRegFiles/");
            // Read in lines will be assigned to this variable
            String line, county, demNum, repNum, othNum;

            // Reads all .csv files in a directory one at a time and proceeds to do things with them
            // We will have to tweak this to work with JFileChooser
            for (File file : dir.listFiles()) {
                setLineNumber(0);
                readFile = file;

                // Flush whatever may be in the PrintWriter
                votNumWriter.flush();
                votNumWriter2.flush();
                votNumWriter3.flush();
                countWriter.flush();

                // The read in file is assigned to a BufferedReader called inFile
                BufferedReader inFile = new BufferedReader(new FileReader(file));

                // The while loop that reads in each line from the .csv file
                while ((line = inFile.readLine()) != null) {
                    setLineNumber(lineNumber + 1);

                    if (line.indexOf(',') == -1 || line.indexOf(',') == 0 || line.indexOf(',') == line.length() - 1 ||
                            !validLine(line)) {
                        // Prints to log file if the line is corrupt
                        printIllegalArgToLogFile();
                    } else {
                        // Isolates county and voting nums in each line, and adds them to their respective arraylists
                        int countyPos = line.indexOf(','), demNumPos = line.indexOf(',', countyPos + 1),
                                repNumPos = line.indexOf(',', demNumPos + 1);
                        county = line.substring(0, countyPos);
                        demNum = line.substring(countyPos + 1, demNumPos);
                        repNum = line.substring(demNumPos + 1, repNumPos);
                        othNum = line.substring(repNumPos + 1, line.length());

                        if (!counties.contains(county)) {
                            counties.add(county);
                            demVotingNumbers.add(demNum);
                            repVotingNumbers.add(repNum);
                            othVotingNumbers.add(othNum);
                        } else {
                            System.out.println("Line Not added (Duplicate)");
                        }

                    }
                }

                /*
                * Goes through each arraylist and prints the indicies to a text file
                * The PrintWriter must first be closed before it can write to the votingNums
                * If not closed, it will NOT write.
                */
                for (String s : counties) {
                    countWriter.append(s).append('\n');
                    // Used to check if the counties are correct
//                    System.out.println(s);
                }
                for (String s : demVotingNumbers) {
                    votNumWriter.append(s).append('\n');
                    // Used to check if the voting nums are correct
//                    System.out.println(s);
                }
                for (String s : repVotingNumbers) {
                    votNumWriter2.append(s).append('\n');
                    // Used to check if the voting nums are correct
//                    System.out.println(s);
                }
                for (String s : othVotingNumbers) {
                    votNumWriter3.append(s).append('\n');
                    // Used to check if the voting nums are correct
//                    System.out.println(s);
                }

                //TODO Consider deleting as it is not needed
//                counties.clear();
//                demVotingNumbers.clear();
//                repVotingNumbers.clear();
//                othVotingNumbers.clear();
            }

            countWriter.close();
            votNumWriter.close();
            votNumWriter2.close();
            votNumWriter3.close();

            System.out.println("Parsing Complete!");
        } catch (IOException e) {
            printIOExcepToLogFile(e);
        } catch (IllegalArgumentException p) {
            p.printStackTrace();
        }
    }

    public void loadVoterData() {
        for (int i = 0; i < counties.size(); i++) {
            countyVoteInfo.put(counties.get(i), new VoterData(
                    Integer.parseInt(demVotingNumbers.get(i)),
                    Integer.parseInt(repVotingNumbers.get(i)),
                    Integer.parseInt(othVotingNumbers.get(i))));
        }
        //TODO Delete Eventually
//        System.out.println("Hash-Table Check");
    }

    //=============================================================== Private Methods
    private void printIOExcepToLogFile(Exception e) throws FileNotFoundException {
        Date date = new Date();
        Format formatter = new SimpleDateFormat("YYYY-MM-dd_hh-mm-ss");
        PrintWriter err = new PrintWriter("logs/" + formatter.format(date) + ".txt");
        err.append("An IO Exception of some sort has occurred \n");
        err.append("------------------------------------------------------\n\r");
        err.append("StackTrace: \n");
        e.printStackTrace(err);
        err.flush();
    }

    private void printIllegalArgToLogFile() throws FileNotFoundException {
        String fileName = "" + readFile;
        fileName = fileName.substring(fileName.indexOf('/') + 1, fileName.length());
        err.append("The Line(").append(String.valueOf(getLineNumber())).append(") in ").append("").append(fileName)
                .append(" could not be read, or is corrupt!").append("\n");
        err.append("------------------------------------------------------\n\r");
        err.flush();
    }

    private boolean validLine(String line) {
        int numCommas = 0;

        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == ',') {
                numCommas++;
            }
        }
        return numCommas == 3;
    }
}

