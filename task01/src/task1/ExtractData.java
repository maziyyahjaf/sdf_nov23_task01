package task1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;
import java.util.HashMap;
import java.util.Map;
import java.util.Collections;

public class ExtractData {

    private Reader reader;
    private List<AppData> appList;
    private Set<String> uniqueCategories;
    private int count;

    public ExtractData(Reader reader) {
        this.reader = reader;
    }

    public List<AppData> readCSV() throws FileNotFoundException, IOException {

        List<AppData> appList = new ArrayList<>();
        Map<String, List<AppData>> appMap = new HashMap<>();
        // get the 3 columns
        try (BufferedReader br = new BufferedReader(reader)) {

            String currentLine;
            boolean first = true;
            count = 0;

            while ((currentLine = br.readLine()) != null) {
                String[] fields = currentLine.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                count++;
                // skip header
                if (first || fields.length < 13) {
                    first = false;
                    continue;
                }

                AppData app = new AppData();
                app.setAppName(fields[0]);
                app.setAppCategory(fields[1]);
                app.setAppRating(fields[2]);
                appList.add(app);
                

                

            }

            System.out.println(appMap);

            return appList;

        }

    }

    public Set<String> analyse(List<AppData> appList) {
        // i need to get the unique categories
        Set<String> uniqueCategories = new HashSet<String>();

        for (AppData app : appList) {
            String category = app.getAppCategory();
            uniqueCategories.add(category);
        }

        return uniqueCategories;

    }

    // create a new list of appData based on each category?
    public Map<String, AppData> filterBasedOnCategory(List<AppData> appList, Set<String> uniqueCategory) {

        Map<String, AppData> mappedApplications = new HashMap<>();

        // what is happening is that you are mapping ONE unique category to ONE instance
        // of AppData

        // how to map the category to the appData??
        for (String category : uniqueCategory) {
            for (AppData app : appList) {
                if (app.getAppCategory().equalsIgnoreCase(category)) {
                    mappedApplications.put(category, app);
                }
            }
        }

        // System.out.println(mappedApplications);
        return mappedApplications;

    }

    public Map<String, Integer> count(List<AppData> appList) {

        Map<String, Integer> countOfApps = new HashMap<>();
        for (AppData app : appList) {
            countOfApps.compute(app.getAppCategory(), (k, v) -> (v == null) ? 1 : v + 1);

            // if (countOfApps.containsKey(app.getAppCategory())) {
            // int updatedCount = countOfApps.get(app.getAppCategory()) + 1;
            // countOfApps.put(app.getAppCategory(), updatedCount);
            // // what about compute??
            // } else {

            // countOfApps.put(app.getAppCategory(), 1);
            // }

        }

        return countOfApps;

    }

    public List<Map<String, AppData>> returnAppNameRating(List<AppData> appList) {

        Map<String, AppData> high = new HashMap<>();
        Map<String, AppData> low = new HashMap<>();
        List<Map<String, AppData>> highLowList = new ArrayList<>();

        for (AppData app : appList) {
            if (Double.isNaN(Double.parseDouble(app.getAppRating()))) {
                continue;
            }
            high.compute(app.getAppCategory(), (k, v) -> (v == null) ? app
                    : ((Double.parseDouble(v.getAppRating()) > Double.parseDouble(app.getAppRating()) ? v : app)));
            low.compute(app.getAppCategory(), (k, v) -> (v == null) ? app
                    : ((Double.parseDouble(v.getAppRating()) < Double.parseDouble(app.getAppRating()) ? v : app)));
        }

        Collections.addAll(highLowList, high, low);

        return highLowList;

    }

    public Map<String, Double> returnAverageRating(List<AppData> appList) {

        Map<String, Double> averageRating = new HashMap<>();
        Map<String, Integer> trueCount = new HashMap<>();

        for (AppData app : appList) {
            if (Double.isNaN(Double.parseDouble(app.getAppRating()))) {
                continue;
            }
            averageRating.compute(app.getAppCategory(), (k, v) -> (v == null) ? Double.parseDouble(app.getAppRating())
                    : v + Double.parseDouble(app.getAppRating()));
            trueCount.compute(app.getAppCategory(), (k, v) -> (v == null) ? 1 : v + 1);

        }

        for (Map.Entry<String, Double> entry : averageRating.entrySet()) {
            String key = entry.getKey();
            Double value = entry.getValue();
            Integer count = trueCount.get(key);
            Double averageR = value / count;
            averageRating.put(key, averageR);
        }

        return averageRating;
    }

    public Map<String, Integer> returnDiscardedEntry(List<AppData> appList) {

        Map<String, Integer> discardedEntry = new HashMap<>();

        for (AppData app : appList) {

            if (Double.isNaN(Double.parseDouble(app.getAppRating()))) {
                discardedEntry.compute(app.getAppCategory(), (k, v) -> (v == null) ? 1 : v + 1);

            }

        }

        return discardedEntry;
    }

    public void printAnswers(List<AppData> appList) {
        var categories = analyse(appList);
        var totalCount = count(appList);
        var highLow= returnAppNameRating(appList);
        var high = highLow.get(0);
        var low = highLow.get(1);
        var avgRating = returnAverageRating(appList);
        var discarded = returnDiscardedEntry(appList);

        for (String category : categories) {
            System.out.println(category);
            System.out.println(totalCount.get(category));
            System.out.println(high.get(category));
            System.out.println(low.get(category));
            System.out.println(avgRating.get(category));
            System.out.println(discarded.get(category));

        }
        System.out.println("Lines read: " + count);
        
        



    }

}
