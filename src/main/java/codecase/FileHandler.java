package codecase;

import java.io.*;
import java.util.ArrayList;
import java.util.Set;

public class FileHandler {

    //TODO WRite printwithFRomAndTo
    public void printToFile(Set<String> phoneNumbers, String filePath) {

        ArrayList<String> phoneNumbersToPrint = new ArrayList<>();
        phoneNumbersToPrint.addAll(phoneNumbers);

        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(filePath), "utf-8"))) {

            for (int i = 0; i < phoneNumbersToPrint.size(); i++) {
                writer.write(phoneNumbersToPrint.get(i));
                if (i != phoneNumbersToPrint.size() - 1) {
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
}
