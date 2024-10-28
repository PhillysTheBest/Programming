import java.util.HashMap;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class barebones {
    private HashMap<String, Integer> variables;//declare hashmap
    private static final String WHILE_KEYWORD = "while";//constant so it's not repeated multiple time

    public barebones() {
        variables = new HashMap<>();//hashmap to store all variables
    }
    //definition of methods as methods
    public void clear(String variable) {
        variables.put(variable, 0);
    }

    public void incr(String variable) {
        variables.put(variable, getValue(variable) + 1);
    }

    public void decr(String variable) {
        variables.put(variable, getValue(variable) - 1);
    }

    public int getValue(String variable) {
        return variables.getOrDefault(variable, 0);
        //return value or return 0
    }

    private int processWhileLoop(String[] commandList, int startIndex) {
        int commandIndex = startIndex;
        String whileLine = commandList[commandIndex].trim();

        if (whileLine.startsWith(WHILE_KEYWORD)) { 
            String[] whileParts = whileLine.split(" ");//split line into parts
            String variable = whileParts[1];//variable from line
            int targetValue = Integer.parseInt(whileParts[3]);//value from line
            
            // Save the start index of the loop
            int loopStartIndex = commandIndex;

            // Continue looping until the variable equals the target value
            while (getValue(variable) != targetValue) {
                commandIndex = loopStartIndex + 1; // Move past the "while" line each iteration

                while (commandIndex < commandList.length) {
                    String line = commandList[commandIndex].trim();

                    if (line.startsWith(WHILE_KEYWORD)) { 
                        commandIndex = processWhileLoop(commandList, commandIndex);
                    } else if (line.equals("end;")) {
                        break; // Exit this loop on 'end'
                    } else {
                        executeCommand(line);
                        commandIndex++;
                    }
                }
            }
        }
        return commandIndex +1;
    }

    private void executeCommand(String line) {
        // Simple command processing based on startsWith
        if (line.startsWith("clear")) {
            String variable = line.split(" ")[1].replace(";", "");
            clear(variable);
        } else if (line.startsWith("incr")) {
            String variable = line.split(" ")[1].replace(";", "");
            incr(variable);
        } else if (line.startsWith("decr")) {
            String variable = line.split(" ")[1].replace(";", "");
            decr(variable);
        }
    }

    public void run(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String[] commandList = reader.lines().toArray(String[]::new);
        //convert all lines into an array of strings
        reader.close();
        
        int commandIndex = 0; 
        while (commandIndex < commandList.length) {
            String line = commandList[commandIndex].trim();
            if (line.startsWith(WHILE_KEYWORD)) { 
                commandIndex = processWhileLoop(commandList, commandIndex);//for handling nested loops
            } else {
                executeCommand(line);
                commandIndex++; // Increment the commandIndex only after executing a command 
            }
        }
    
        // Print variable values in a new way using a lambda expression
        variables.forEach((key, value) -> System.out.println(key + ": " + value));
    }    

    public static void main(String[] args) throws Exception {
        barebones interpreter = new barebones();
        String filename = "multiply.txt"; //insert file name
        interpreter.run(filename);
    }
}