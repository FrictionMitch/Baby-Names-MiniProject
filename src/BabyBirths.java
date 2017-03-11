import edu.duke.*;
import org.apache.commons.csv.*;
import org.apache.commons.csv.CSVRecord;

import java.io.File;

public class BabyBirths {

    int totalBoys = 0;
    int totalGirls = 0;
    int totalNames = 0;
    int totalBoysNames = 0;
    int totalGirlsNames = 0;

    public void printNames() {
        FileResource fr = new FileResource();
//        String padded = String.format("%-20s", "Total Born ");
        for (CSVRecord record : fr.getCSVParser(false)) {
            System.out.println("Name: " + record.get(0) + " Gender: " + record.get(1) +
                               " Total Born: " + record.get(2));
        }
    }

    public void printNumberBorn() {
        FileResource fr = new FileResource();
        for(CSVRecord record : fr.getCSVParser(false)) {
            int numBorn = Integer.parseInt(record.get(2));
            if(numBorn <= 100) {
                System.out.println("Name: " + record.get(0) + ", Gender: " + record.get(1) +
                                   ", Total Born: " + record.get(2));
            }
        }
    }

    public void totalBirths(FileResource fr) {
        int totalBirths = 0;
        for(CSVRecord record : fr.getCSVParser(false)) {
            totalNames++;
            int numBorn = Integer.parseInt(record.get(2));
            totalBirths += numBorn;
            if (record.get(1).equalsIgnoreCase("M")) {
                totalBoys += numBorn;
                totalBoysNames++;
            } else {
                totalGirls += numBorn;
                totalGirlsNames++;
            }
        }
        System.out.println("Total Births = " + totalBirths);
        System.out.println("Total Boys = " + totalBoys);
        System.out.println("Total Girls = " + totalGirls);
        System.out.println("The total number of names is: " + totalNames);
        System.out.println("The total number of boys names is: " + totalBoysNames);
        System.out.println("The total number of boys names is: " + totalGirlsNames);
    }

    public void testTotalBirths() {
//        FileResource fr = new FileResource("data/example-small.csv");
        FileResource fr = new FileResource("testing/yob2012short.csv");
        totalBirths(fr);
    }

    public int getRank(int year, String name, String gender){
      int rank = -1;
      int counter = 0;
        FileResource fr = new FileResource("testing/yob" + year +"short.csv");
        CSVParser parser = fr.getCSVParser(false);
        for (CSVRecord record : parser) {
            if(record.get(1).equals(gender)) {
                counter++;
                if(record.get(0).equals(name)) {
                    rank = counter;
                    break;
                }
            }
        }

        return rank;
    }

    public void testRank(int year, String name, String gender) {
        FileResource fr = new FileResource("testing/yob" + year + "short.csv");
        CSVParser parser = fr.getCSVParser(false);
        for (CSVRecord record : parser) {
            if(record.get(2).equals(gender)) {

            System.out.println("Name: " + record.get(1)+"Gender: " + record.get(2));
            }
        }
    }

    public void getRankTest() {
//        getRank(2012, "Mason", "M");
        System.out.println("Noah was ranked number " + getRank(2012, "Noah", "M") +
                           " of that year");

    }

    public String getName(int year, int rank, String gender) {
        String name = "NO NAME";
        int counter = 0;
        FileResource fr = new FileResource("testing/yob" + year + "short.csv");
        CSVParser parser = fr.getCSVParser(false);
        for(CSVRecord record : parser) {
            if(record.get(1).equals(gender)) {
                counter++;
                if(counter == rank) {
                    name = (record.get(0));
                    System.out.println(name);
                    break;
                }
            }
        }
        return name;
    }

    public void testGetName() {
        System.out.println("The girl ranked 2nd is: " + getName(2014, 2, "F"));
    }

    public void whatIsNameInYear(String name, int year, int newYear, String gender) {
//        FileResource fileYourYear = new FileResource("testing/yob" + year + "short.csv");
//        FileResource fileNewYear = new FileResource("testing/yob" + newYear + "short.csv");

        int rank = getRank(year, name, gender);
        System.out.println("Isabella born in 2012 would be " + getName(newYear, rank, gender) + " in 2014");
    }

    public void testWhatIsNameInYear() {
        whatIsNameInYear("Isabella", 2012, 2014, "F");
    }

    public int yearOfHighestRank(String name, String gender) {
        int highestRank = -1;
        DirectoryResource dir = new DirectoryResource();
        for(File file : dir.selectedFiles()) {
            String yearString = file.getName().substring(3, 7);
//            System.out.println(yearString);
            int year = Integer.parseInt(yearString);
            int currentRank = getRank(year, name, gender);
            System.out.println("The rank for year: " + year + " was " + currentRank);
            if(currentRank == -1) {
                return highestRank;
            }
            if(highestRank != -1) {
                highestRank = Math.min(currentRank, highestRank);
            } else {
                highestRank = currentRank;
            }
        }
        return highestRank;
    }
    public void testYearOfHighestRank() {
        System.out.println("Mason was ranked highest in the year: " +
                           yearOfHighestRank("Mason", "M"));
    }

    public double getAverageRank (String name, String gender) {
        double averageRank = -1;
        int numberOfFiles = 0;
        int totalRank = 0;
        DirectoryResource dir = new DirectoryResource();
        for(File file : dir.selectedFiles()) {
            numberOfFiles++;
            String yearString = file.getName().substring(3, 7);
            int year = Integer.parseInt(yearString);
            int currentRank = getRank(year, name, gender);
            if(currentRank == -1){
                currentRank = 0;
            }
            totalRank += currentRank;
        }
        if(totalRank == 0) {
            return averageRank;
        } else {
            return averageRank = (double)(totalRank)/(double)(numberOfFiles);
        }
    }

    public void testAverageRank() {
        System.out.println("Mason\'s average was: " + getAverageRank("Mason", "M"));
    }
}
