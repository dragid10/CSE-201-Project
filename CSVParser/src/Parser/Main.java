package Parser;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        CSVParse csvParse = new CSVParse();
        try {
            csvParse.parse();
        } catch (FileNotFoundException e) {
            Date date = new Date();
            Format formatter = new SimpleDateFormat("YYYY-MM-dd_hh-mm-ss");
            PrintWriter err = new PrintWriter("logs/" + formatter.format(date) + ".txt");
            err.append("The file is not found!!\n");
            err.append("------------------------------------------------------\n\r");
            err.append("StackTrace: \n");
            e.printStackTrace(err);
            err.flush();
        }
    }
}
