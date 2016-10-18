import java.io.*;
import java.util.ArrayList;

/*
* Will still have to tweak even more to ge the individual voting nums (democratic, republican, green, etc..
*/
class CSVParse {
    void parse() throws FileNotFoundException {
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
            // counties will be assigned to this variable
//            String county = "";

            // Voter numbers will be assigned to this variable
//            String voterNum = "";

            // Reads all .csv files in a directory one at a time and proceeds to do things with them
            // We will have to tweak this to work with JFileChooser
            for (File file : dir.listFiles()) {

                votNumWriter.flush();
                countWriter.flush();

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

//                inFile.close();

                /*
                * Goes through each arraylist and prints the indicies to a text file
                * The PrintWriter must first be closed before it can write to the votingNums
                * If not closed, it will NOT write.
                */
                for (String s : counties) {
//                    pFile.println(s);
                    countWriter.append(s + '\n');

                    // Used to check if the counties are correct
//                    System.out.println(s);
                }

                for (String s : votingNumbers) {
//                    pFile.println(s);
                    votNumWriter.append(s + '\n');
                    // Used to check if the voting nums are correct
//                    System.out.println(s);
                }

                counties.clear();
                votingNumbers.clear();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Must close pFile in the try block if doing something else after printing votingNumbers

    }
}

