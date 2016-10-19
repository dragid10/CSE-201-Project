import java.io.*;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
            String line, county = null, voterNum;

            // Reads all .csv files in a directory one at a time and proceeds to do things with them
            // We will have to tweak this to work with JFileChooser
            for (File file : dir.listFiles()) {

                votNumWriter.flush();
                countWriter.flush();

                // The read in file is assigned to a BufferedReader called inFile
                BufferedReader inFile = new BufferedReader(new FileReader(file));

                // The while loop that reads in each line from the .csv file
                while ((line = inFile.readLine()) != null) {

                    if (line.indexOf(',') == -1) {
                        // Throw new exception if the line is corrupt
                        throw new IllegalArgumentException();
                    } else {
                        // Isolates county and voting nums in each line, and adds them to their respective arraylists
                        county = line.substring(0, line.indexOf(','));
                        voterNum = line.substring(line.indexOf(',') + 1, line.length());
                        counties.add(county);
                        votingNumbers.add(voterNum);
                    }
                }

//                inFile.close();

                /*
                * Goes through each arraylist and prints the indicies to a text file
                * The PrintWriter must first be closed before it can write to the votingNums
                * If not closed, it will NOT write.
                */
                for (String s : counties) {
//                    pFile.println(s);
                    countWriter.append(s).append('\n');

                    // Used to check if the counties are correct
//                    System.out.println(s);
                }

                for (String s : votingNumbers) {
//                    pFile.println(s);
                    votNumWriter.append(s).append('\n');
                    // Used to check if the voting nums are correct
//                    System.out.println(s);
                }

                counties.clear();
                votingNumbers.clear();

            }

            // TODO work on error handling better! (get line # where dat is corrupt)
            // TODO get program to continue when error occurs!
        } catch (IOException e) {
           /* Date date = new Date();
            Format formatter = new SimpleDateFormat("YYYY-MM-dd_hh-mm-ss");
            PrintWriter err = new PrintWriter("logs/" + formatter.format(date) + ".txt");
            err.append("The File could not be opened for writing, or wrong type of file provided!\n");
            err.append("-----------------------------------------------");
            e.printStackTrace(err);*/
            e.printStackTrace();

        } catch (IllegalArgumentException p) {
            Throwable throwable = p;
            Date date = new Date();
            Format formatter = new SimpleDateFormat("YYYY-MM-dd_hh-mm-ss");
            PrintWriter err = new PrintWriter("logs/" + formatter.format(date) + ".txt");
            err.append("The Line could not be read, or is corrupt! at line " + getLineNumber() + '\n');
            err.append("------------------------------------------------------\n\r\n\r");
            err.append("StackTrace: \n");
            p.printStackTrace(err);
//            err.append(p.getMessage());
//            err.append(p.getLocalizedMessage());
            err.flush();
            err.close();

        }

    }

    public static int getLineNumber() {
        return Thread.currentThread().getStackTrace()[2].getLineNumber();
    }
}

