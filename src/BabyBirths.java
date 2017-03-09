import edu.duke.*;
import org.apache.commons.csv.*;
import org.apache.commons.csv.CSVRecord;

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
      int rank = 0;
        FileResource fr = new FileResource("testing/yob" + year +"short.csv");
        CSVParser parser = fr.getCSVParser(false);
        for (CSVRecord record : parser) {
            if(record.get(1).equals(gender)) {
                rank++;
                if(record.get(0).equals(name)) {
                    return rank;
                }
            }

            else {
                return -1;
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
        System.out.println("fuckwit was ranked number " + getRank(2012, "fuckwit", "M") +
                           " of that year");

    }
}
