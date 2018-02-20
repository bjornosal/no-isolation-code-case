package codecase;

import java.io.*;
import java.util.ArrayList;
import java.util.Set;

public class PrintHandler {

    //TODO WRite printwithFRomAndTo
    public void printToFile(Set<String> phoneNumbers, String filePath) {

        ArrayList<String> phoneNumbersToPrint = new ArrayList<>();
        phoneNumbersToPrint.addAll(phoneNumbers);

        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(filePath), "utf-8"))) {

            for(int i = 0; i < phoneNumbersToPrint.size(); i++) {
                writer.write(phoneNumbersToPrint.get(i));
                if(i != phoneNumbersToPrint.size()-1) {
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
