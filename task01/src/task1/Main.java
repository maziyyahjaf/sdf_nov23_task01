package task1;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        
        if (args.length < 1) {
            System.out.println("Error. File not provided.");

        } else {
            Reader reader = new FileReader(args[0]);
            ExtractData extractingData = new ExtractData(reader);
            List<AppData> appsInCSV = extractingData.readCSV();

            // Set<String> categories = extractingData.analyse(appsInCSV);
            // Map<String, AppData> mapped = extractingData.filterBasedOnCategory(appsInCSV, categories);
            // System.out.println(extractingData.analyse(appsInCSV));

            // System.out.println("Count: " + extractingData.count(appsInCSV));
            // System.out.println(extractingData.returnAppNameRating(appsInCSV));
            // System.out.println(extractingData.returnAverageRating(appsInCSV));
            System.out.println(extractingData.returnDiscardedEntry(appsInCSV));
            extractingData.printAnswers(appsInCSV);





           
                   
           
           

           

           




        }

        
        
    }
    
}
