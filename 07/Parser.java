import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Parser for reading lines written in VM
 */
public class Parser {

    protected BufferedReader reader;
    protected String currentLine;
    protected String nextLine;
    protected static String sourceName;

    /**
     * construct a Parser
     * 
     * @param source
     */
    public Parser(File source) {
        try {
            this.reader = new BufferedReader(new FileReader(source));
            this.currentLine = null;
            nextLine();
            // save the name of the file for the static segment in CodeWriter
            int point = source.getName().indexOf(".");
            sourceName = source.getName().substring(0, point);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void advance() {
        currentLine = nextLine;
        nextLine();
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

    // returns command type of VM command
    public String commandType() {
        if (currentLine.contains("push"))
            return "push";
        if (currentLine.contains("pop"))
            return "pop";
        if (currentLine.contains("if"))
            return "if-goto";
        if (currentLine.contains("goto"))
            return "goto";
        if (currentLine.contains("label"))
            return "label";
        if (currentLine.contains("function"))
            return "function";
        if (currentLine.contains("return"))
            return "return";
        if (currentLine.contains("call"))
            return "call";
        return "arithmetic";
    }

    // return first argument of the line
    public String arg1() {
        String cmd = commandType();
        if (cmd == "arithmetic" || cmd == "return")
            return currentLine;
        String[] args = currentLine.split(" ");
        return args[1];
    }

    // return second argument of the line
    public int arg2() {
        String[] args = currentLine.split(" ");
        return Integer.parseInt(args[2]);
    }

    // strip a string from spaces at the beginning and ending of a line
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