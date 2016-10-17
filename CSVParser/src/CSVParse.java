import java.io.*;
import java.util.ArrayList;

/*
* Will still have to tweak even more to ge the individual voting nums (democratic, republican, green, etc..
*/
public class CSVParse {
    public void parse() throws FileNotFoundException {
        // Houses the counties from the read in line
        ArrayList<String> counties = new ArrayList<>();

        // Houses the voting nums from the read in line
        ArrayList<String> votingNumbers = new ArrayList<>();

        // Opens a PrintWriter for the future to use.
        PrintWriter pFile = new PrintWriter("OutputFiles/counties.txt");

        // Gotta encompass everything in a try, catch or else it'll yell at you.
        try {
            File dir = new File("VoterRegFiles/");
            // Read in lines will be assigned to this variable
            String line = "";
            // counties will be assigned to this variable
            String county = "";

            // Voter numbers will be assigned to this variable
            String voterNum = "";

            // Reads all .csv files in a directory one at a time and proceeds to do things with them
            // We will have to tweak this to work with JFileChooser
            for (File file : dir.listFiles()) {
                // The read in file is assigned to a BufferedReader called inFile
                BufferedReader inFile = new BufferedReader(new FileReader(file));

                // The while loop that reads in each line from the .csv file
                while ((line = inFile.readLine()) != null) {
                    // Isolates county and voting nums in each line, and adds them to their respective arraylists
                    county = line.substring(0, line.indexOf(','));
                    voterNum = line.substring(line.indexOf(',') + 1, line.length());
                    counties.add(county);
                    votingNumbers.add(voterNum);
                }
                // The BufferedReader MUST be closed or else it will not print to the text file
                inFile.close();

                /*
                * Goes through each arraylist and prints the indicies to a text file
                * The PrintWriter must first be closed before it can write to the votingNums
                * If not closed, it will NOT write.
                */
                for (String s : counties) {
                    pFile.println(s);
                    // Used to check if the counties are correct
                    // System.out.println(s);
                }
                pFile.close();

                pFile = new PrintWriter("OutputFiles/votingNumbers.txt");
                for (String s : votingNumbers) {
                    pFile.println(s);
                    // Used to check if the voting nums are correct
                    // System.out.println(s);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Must close pFile in the try block if doing something else after printing votingNumbers
            pFile.close();
        }
    }
}

