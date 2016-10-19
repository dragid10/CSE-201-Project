import java.io.*;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/*
* Will still have to tweak even more to ge the individual voting nums (democratic, republican, green, etc..
*/
class CSVParse {
    private int lineNumber = 0;
    private File readFile;
    private PrintWriter err;

    void parse() throws FileNotFoundException {
        Date date = new Date();
        Format formatter = new SimpleDateFormat("YYYY-MM-dd_hh-mm-ss");
        err = new PrintWriter("logs/" + formatter.format(date) + ".txt");

        // Houses the counties from the read in line
        ArrayList<String> counties = new ArrayList<>();

        // Houses the voting nums from the read in line
        ArrayList<String> votingNumbers = new ArrayList<>();

        // Gotta encompass everything in a try, catch or else it'll yell at you.
        try (PrintWriter countWriter = new PrintWriter("OutputFiles/counties.txt");
             PrintWriter votNumWriter = new PrintWriter("OutputFiles/votingNumbers.txt")) {
            File dir = new File("VoterRegFiles/");
            // Read in lines will be assigned to this variable
            String line, county, voterNum;

            // Reads all .csv files in a directory one at a time and proceeds to do things with them
            // We will have to tweak this to work with JFileChooser
            for (File file : dir.listFiles()) {
                readFile = file;
                lineNumber = 0;

                votNumWriter.flush();
                countWriter.flush();

                // The read in file is assigned to a BufferedReader called inFile
                BufferedReader inFile = new BufferedReader(new FileReader(file));

                // The while loop that reads in each line from the .csv file
                while ((line = inFile.readLine()) != null) {
                    lineNumber++;

                    if (line.indexOf(',') == -1) {
                        // Prints to log file if the line is corrupt
                        printIllegalArgToLogFile();
                    } else {
                        // Isolates county and voting nums in each line, and adds them to their respective arraylists
                        county = line.substring(0, line.indexOf(','));
                        voterNum = line.substring(line.indexOf(',') + 1, line.length());
                        counties.add(county);
                        votingNumbers.add(voterNum);
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
                for (String s : votingNumbers) {
                    votNumWriter.append(s).append('\n');
                    // Used to check if the voting nums are correct
//                    System.out.println(s);
                }
                counties.clear();
                votingNumbers.clear();
            }
        } catch (IOException e) {
            printIOExcepToLogFile(e);
        } catch (IllegalArgumentException p) {
            p.printStackTrace();
        }

    }

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
        err.append("The Line(" + lineNumber + ") in " + "" + fileName + " could not be read, or is corrupt!" + "\n");
        err.append("------------------------------------------------------\n\r");
        err.flush();
    }

}

