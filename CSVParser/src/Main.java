import java.io.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        parse();
    }

    static void parse() throws FileNotFoundException {
        ArrayList<String> counties = new ArrayList<>();
        ArrayList<String> votingNumbers = new ArrayList<>();
        PrintWriter countyFile = new PrintWriter("counties.txt");
        PrintWriter numFile = new PrintWriter("votingNumbers.txt");
        try {
            BufferedReader inFile = new BufferedReader(new FileReader("RegisteredOhioVoters.csv"));
            String line;
            String county;
            String voterNum;
            while ((line = inFile.readLine()) != null) {
                county = line.substring(0, line.indexOf(','));
                voterNum = line.substring(line.indexOf(',') + 1, line.length());
                counties.add(county);
                votingNumbers.add(voterNum);
            }
            inFile.close();


            for (String s : counties) {
                countyFile.println(s);
//                System.out.println(s);
            }
            for (String s : votingNumbers) {
                numFile.println(s);

//                System.out.println(s);
            }


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (countyFile != null && numFile != null)
                countyFile.close();
                numFile.close();
        }
    }
}
