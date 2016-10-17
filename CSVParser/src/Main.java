import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        CSVParse csvParse = new CSVParse();
        try {
            csvParse.parse();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
