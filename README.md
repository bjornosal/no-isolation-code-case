# No Isolation Code Case

Code case solution by Bjørn Olav Salvesen
Written in Java SE 1.8

### Assumptions 
Mostly assumptions as for what environment is required for running the full solution. 
- You need an IDE that is able to handle Maven builds, to make sure that the unit tests will run.
- You need to have a file with phone numbers, if you do not have a file,
one will be generated for you and used for the entirety of the program.  
- As the assignment text contradicts itself saying both
    - __"Any phone number that cannot be processed must be written as-is to the output file. We
       also want a report at the end specifying what phone numbers that was impossible to
       normalize along with its line number from the input file."__
       
       AND
       
    - **"A path to the file where you should output the phone numbers. This file should be
       of equal length (number of rows) to the input file and all rows should be in the
       same order as in the input file."**
       
     , I have made the assumption that you require a file for testing how well my program performed,
     so I took the liberty of creating an additional file with the same name as the output file,
     just ending "-with-line-numbers.txt" instead. 
- You mentioned using 3rd party libraries for completing this task, but as I am running unit tests using JUnit,
and I am not aware of any way of resolving this without any 3rd-party library.


### How to run

##### Program
I have attached both an IntelliJ project, as well as an Eclipse project, 
seeing as they are amongst the current most popular IDEs for Java. 

1. Open project, and open App class. 
2. In main method, set filepaths for input and output. 
3. Run main method.
4. Files should now be saved to your desired location. 

##### Tests
For the tests, the easiest would be to run the command "mvn test", 
or simply run all tests in NormalizationHandlerTest.
They do require access to JUnit. 

##### Files
Attaching a file with 1000 generated phone numbers.
This file is generated using the class PhoneNumberGenerator.