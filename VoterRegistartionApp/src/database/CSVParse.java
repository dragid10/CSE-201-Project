package database;
import database.VoterData;

import java.io.*;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import static database.VoterData.voterDataHashmap;

public class CSVParse {
    private static final Logger LOGGER = Logger.getLogger(CSVParse.class.getName());
    private int lineNumber;
    private File readFile;
    private PrintWriter err;

    // Directory you're reading from
    private File dir;

    public static ArrayList<VoterData> voterDataArray = new ArrayList<>();

    // Houses the counties from the read in line (Primary storage to reduce chance of error)
    private ArrayList<String> counties = new ArrayList<>();
    private ArrayList<String> precincts = new ArrayList<>();

    // Houses the voting nums from the read in line
    private ArrayList<String> demVotingNumbers = new ArrayList<>();
    private ArrayList<String> repVotingNumbers = new ArrayList<>();
    private ArrayList<String> othVotingNumbers = new ArrayList<>();

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

    public File getDir() {
        return dir;
    } 
    
    public ArrayList<VoterData> getData(){
    	ArrayList<VoterData> temp = new ArrayList<>();
    	for(VoterData v: voterDataArray){
    		temp.add(v.clone());
    	}
    	return temp;
    }

    //=============================================================== Public Methods
    public void parse(String directory) throws FileNotFoundException {
        // Sets directory to what user chooses
        dir = new File(directory);


        // Date function to name error logs
        Date date = new Date();
        Format formatter = new SimpleDateFormat("YYYY-MM-dd_hh-mm-ss");
        // TODO Give option to save error log anywhere
        err = new PrintWriter("logs/" + formatter.format(date) + ".log");

        // Gotta encompass everything in a try, catch or else it'll yell at you.
        String COUNTY_FILENAME = ".outputFiles/counties.bin";
        String DEMNUM_FILENAME = ".outputFiles/demNumbers.bin";
        String REPNUM_FILENAME = ".outputFiles/repNumbers.bin";
        String OTHNUM_FILENAME = ".outputFiles/othNumbers.bin";
        try {
        	PrintWriter countWriter = new PrintWriter(COUNTY_FILENAME);
            PrintWriter votNumWriter = new PrintWriter(DEMNUM_FILENAME);
            PrintWriter votNumWriter2 = new PrintWriter(REPNUM_FILENAME);
            PrintWriter votNumWriter3 = new PrintWriter(OTHNUM_FILENAME);
            // Read in lines will be assigned to this variable
            String line, county, precinct, demNum, repNum, othNum;

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

                    // Increments line number as for loop progresses through each file
                    setLineNumber(lineNumber + 1);

                    // Prints to log file if the line is corrupt
                    if (line.indexOf(',') == -1 || line.indexOf(',') == 0 || line.indexOf(',') == line.length() - 1 ||
                            !validLine(line)) {
                        printIllegalArgToLogFile();
                    } else {
                        // Isolates county and voting nums in each line, and adds them to their respective Arraylists
                        int countyPos = line.indexOf(','), precinctPos = line.indexOf(',', countyPos + 1),
                                demNumPos = line.indexOf(',', precinctPos + 1),
                                repNumPos = line.indexOf(',', demNumPos + 1);
                        county = line.substring(0, countyPos);
                        precinct = line.substring(countyPos + 1, precinctPos);
                        demNum = line.substring(precinctPos + 1, demNumPos);
                        repNum = line.substring(demNumPos + 1, repNumPos);
                        othNum = line.substring(repNumPos + 1, line.length());

                        // New BR used for dupLines method ot check if word is duplicated
                        BufferedReader inFile2 = new BufferedReader(new FileReader(file));
                        String searchTerm = county + "," + precinct;

                        // If line is a duplicate, then print it to the log file
                        if (!(dupLine(searchTerm, inFile2))) {
                            counties.add(county);
                            precincts.add(precinct);
                            demVotingNumbers.add(demNum);
                            repVotingNumbers.add(repNum);
                            othVotingNumbers.add(othNum);
                            voterDataArray.add(new VoterData(county, precinct, Integer.parseInt(demNum),
                                    Integer.parseInt(repNum),
                                    Integer.parseInt(othNum)));
                        } else {
                            counties.add(county + " - incomplete");
                            precincts.add(precinct);
                            demVotingNumbers.add("-666");
                            repVotingNumbers.add("-666");
                            othVotingNumbers.add("-666");
                            voterDataArray.add(new VoterData(county + " - incomplete",
                                    precinct,
                                    Integer.parseInt(demNum),
                                    Integer.parseInt(repNum),
                                    Integer.parseInt(othNum)));
                            printDuplicateLinesToLogFile(line);
                        }
                    }
                }
                LOGGER.log(Level.FINE, "Array-check!");

                /*
                * Goes through each arraylist and prints the indicies to a text file
                * The PrintWriter must first be closed before it can write to the votingNums
                * If not closed, it will NOT write.
                */
                for (String s : counties) {
                    countWriter.append(s).append('\n');
                }
                for (String s : demVotingNumbers) {
                    votNumWriter.append(s).append('\n');
                }
                for (String s : repVotingNumbers) {
                    votNumWriter2.append(s).append('\n');
                }
                for (String s : othVotingNumbers) {
                    votNumWriter3.append(s).append('\n');
                }
            }

            // Closes all PrintWriters
            countWriter.close();
            votNumWriter.close();
            votNumWriter2.close();
            votNumWriter3.close();
            LOGGER.log(Level.FINE, "Parsing Complete!");
        } catch (IOException e) {
            printIOExcepToLogFile(e);
        } catch (IllegalArgumentException p) {
            p.printStackTrace();
        }
    }

    public void loadVoterData() {
        // Loads the voting data into the HashMap from Arraylists
        for (int i = 0; i < counties.size(); i++) {
            String curCounty = counties.get(i);

            voterDataHashmap.put(curCounty, new VoterData(
                    counties.get(i),
                    precincts.get(i),
                    Integer.parseInt(demVotingNumbers.get(i)),
                    Integer.parseInt(repVotingNumbers.get(i)),
                    Integer.parseInt(othVotingNumbers.get(i))));
        }
        LOGGER.log(Level.FINE, "HashMultiMap-check!");
    }
    
    //=============================================================== Private Methods

    private void printIOExcepToLogFile(Exception e) throws FileNotFoundException {
        Date date = new Date();
        Format formatter = new SimpleDateFormat("YYYY-MM-dd_hh-mm-ss");
        PrintWriter err = new PrintWriter("logs/" + formatter.format(date) + ".log");
        err.append("An IO Exception of some sort has occurred \n");
        err.append("------------------------------------------------------\n\r");
        err.append("StackTrace: \n");
        e.printStackTrace(err);
        err.flush();
    }

    private void printIllegalArgToLogFile() {
        String fileName = "" + readFile;
        fileName = fileName.substring(fileName.indexOf('/') + 1, fileName.length());
        err.append("[Error: Corrupt or Invalid line] on line(").append(String.valueOf(getLineNumber())).append(") in ")
                .append("").append(fileName).append("\n");
        err.append("------------------------------------------------------\n\r");
        err.flush();
    }

    private void printDuplicateLinesToLogFile(String dupLine) {
        String fileName = "" + readFile;
        fileName = fileName.substring(fileName.indexOf('/') + 1, fileName.length());
        err.append("[Error: Duplicate Entry] on line(").append(String.valueOf(getLineNumber())).append(") in ")
                .append("").append(fileName).append(": ").append(dupLine).append("\n");
        err.append("------------------------------------------------------\n\r");
        err.flush();
    }

    private boolean validLine(String line) {
        int numCommas = 0;

        // If the current line does have 3 commas, then it is invalid
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == ',') {
                numCommas++;
            }
        }
        return numCommas == 4;
    }

    private boolean dupLine(String search, BufferedReader br) {
//        Checks to see if county and precinct are located anywhere else through file, if so then it is duplicate
        int occurrence = 0;
        try {
            String line;
            while ((line = br.readLine()) != null) {
                int index = -1;
                while ((index = line.indexOf(search, index + 1)) != -1)
                    occurrence++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return occurrence >= 2;
    }
}