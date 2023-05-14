import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

//the Parser Object reads through the lines of a file and parses the A, C and L instructions 
public class Parser {
    protected String currentLine;
    protected String nextLine;
    protected BufferedReader reader;
    protected static int lineCount = 0;// meant for saving label symbols lines

    public Parser(File prog) {
        try {
            this.reader = new BufferedReader(new FileReader(prog));
            this.currentLine = null;
            nextLine();// the first line without whiteSpace

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void advance() {
        currentLine = nextLine;
        nextLine();
        if (currentLine != null && !(currentLine.charAt(0) == '('))// only count C and A instructions
            lineCount++;
    }

    public void nextLine() {// update to next instruction
        try {
            nextLine = reader.readLine();
            while (hasMoreLines() && (nextLine.isEmpty() || nextLine.startsWith("//"))) {// check if empty or a comment
                nextLine = reader.readLine();
            }
            if (nextLine != null) {
                if (nextLine.contains("//")) {// if we have comment on an instruction line remove it
                    nextLine = nextLine.substring(0, nextLine.indexOf('/'));
                }
                nextLine = strip(nextLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Boolean hasMoreLines() {
        return nextLine != null;
    }

    public String instructionType() {
        if (currentLine.charAt(0) == '@')
            return "A";
        if (currentLine.charAt(0) == '(')
            return "L";
        return "C";
    }

    public String symbol() {// remove @ or () in symboles and return the symbol itself2
        if (currentLine.charAt(0) == '@')
            return currentLine.substring(1);
        return currentLine.substring(1, currentLine.length() - 1);
    }

    public String dest() {// reutrn destination
        if (currentLine.contains("=")) {
            String[] line = currentLine.split("=");
            return line[0];
        }
        return "";
    }

    public String comp() {// return computation
        int begin = currentLine.indexOf("=");
        int end = currentLine.indexOf(";");
        if (end != -1) {// case that we have a ; in line
            return currentLine.substring(begin + 1, end);
        }
        // case that we dont have a ; in line
        return currentLine.substring(begin + 1);
    }

    public String jump() {// return jump
        int begin = currentLine.indexOf(";");
        if (begin != -1)
            return currentLine.substring(begin + 1);
        return "";// case we dont have a jump instruction
    }

    public String strip(String str) {
        // Check for null or empty input
        if (str == null || str.length() == 0) {
            return str;
        }

        // Trim whitespace from the beginning and end of the string
        int start = 0;
        int end = str.length() - 1;
        while (start < end && Character.isWhitespace(str.charAt(start))) {
            start++;
        }
        while (end > start && Character.isWhitespace(str.charAt(end))) {
            end--;
        }

        // Return the stripped string
        return str.substring(start, end + 1);
    }

}
