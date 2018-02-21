package codecase;

import java.io.*;
import java.util.ArrayList;
import java.util.Set;

public class FileHandler {

    /**
     * Prints a set to file
     * @param phoneNumbers to print
     * @param filePath location to print to
     */
    public void printSetToFile(Set<String> phoneNumbers, String filePath) {

        ArrayList<String> phoneNumbersToPrint = new ArrayList<>(phoneNumbers);

        printArrayListToFile(phoneNumbersToPrint, filePath);
    }

    /**
     * Retrieves phone numbers from a file path
     * @param filePath file to get numbers from
     * @return ArrayList of numbers.
     */
    public ArrayList<String> getPhoneNumbersFromFile(String filePath) {
        ArrayList<String> phoneNumbersFromFile = new ArrayList<>();

        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            while (bufferedReader.ready()) {
                phoneNumbersFromFile.add(bufferedReader.readLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("What?! There is no file there.");
        } catch (IOException e) {
            System.out.println("Damn. Our librarian broke.");
        }
        return phoneNumbersFromFile;
    }

    /**
     * Prints an ArrayList to a file.
     * @param phoneNumbers arraylist with phone numbers
     * @param filePath where to print
     */
    public void printArrayListToFile(ArrayList<String> phoneNumbers, String filePath) {
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(filePath), "utf-8"))) {

            for (int i = 0; i < phoneNumbers.size(); i++) {
                writer.write(phoneNumbers.get(i));
                if (i != phoneNumbers.size() - 1) {
                    writer.write(System.lineSeparator());
                }
            }
        } catch (UnsupportedEncodingException e) {
            System.out.println("Let's hope it's the encoding.");
        } catch (FileNotFoundException e) {
            System.out.println("Let's hope we find the file again.");
        } catch (IOException e) {
            System.out.println("Let's hope it's nothing wrong with the printer. It always jams.");
        }
    }
}
