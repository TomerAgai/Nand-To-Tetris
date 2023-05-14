import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JackTokenizer {
    public BufferedReader reader;
    public String currentToken;
    public String[] currentLine;
    public String line;
    public int index;
    public boolean endFile;
    private static String symbolPattern = "(\\{|\\}|\\(|\\)|\\[|\\]|\\.|\\,|;|\\+|-|\\|&|\\||<|>|=|~)";
    private static String keywordPattern = "class|constructor|function|method|field|static|var|int|char|boolean|void|true|false|null|this|let|do|if|else|while|return";
    private static String identifierPattern = "^[^\\d\\W]\\w*\\Z";
    private static String intPattern = "\\d+";
    private static String stringPattern = "^\"[^\"]+\"$";

    /**
     * initial line index to 0, sets the end of file flag to false and reads the
     * first line of the file and separates symbols in it.
     * 
     * @param file
     */
    public JackTokenizer(File f) {
        try {
            this.reader = new BufferedReader(new FileReader(f));
            endFile = false;
            this.index = 0;
            try {
                this.line = reader.readLine();
                currentLine = separateSymbols(line);
            } catch (IOException e) {
                e.printStackTrace();

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * advance to next token only if there are more tokens in the line, else reads a
     * new line
     * continues until the end of the file
     */
    public void advance() {
        try {
            while (index > currentLine.length - 1) {
                line = reader.readLine();
                if (line == null) {
                    endFile = true;
                    return;
                }
                currentLine = separateSymbols(line);
                index = 0;
            }

            currentToken = currentLine[index];
            index++;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the type of the current token
     */
    public String tokenType() {
        if (currentToken.matches(symbolPattern))
            return "SYMBOL";
        if (currentToken.matches(keywordPattern))
            return "KEYWORD";
        if (currentToken.matches(identifierPattern))
            return "IDENTIFIER";
        if (currentToken.matches(intPattern))
            return "INT_CONST";
        else
            return "STRING_CONST";

    }

    public String keyWord() {
        return currentToken.toUpperCase();
    }

    public char Symbol() {
        return currentToken.charAt(0);
    }

    public String Identifier() {
        return currentToken;
    }

    public int intVal() {
        return Integer.parseInt(currentToken);
    }

    public String stringVal() {
        return currentToken;
    }

    public static String[] separateSymbols(String line) {
        // Remove comments from the line
        int commentIndex = line.indexOf("//");
        if (commentIndex >= 0) {
            line = line.substring(0, commentIndex);
            line.trim();
        }
        commentIndex = line.indexOf("/*");
        if (commentIndex >= 0) {
            line = line.substring(0, commentIndex);
            line.trim();
        }
        commentIndex = line.indexOf("*");
        if (line.trim().startsWith("*")) {
            line = line.substring(0, commentIndex);
        }
        commentIndex = line.indexOf("*/");
        if (commentIndex >= 0) {
            line = line.substring(0, commentIndex);
            line.trim();
        }

        List<String> symbols = new ArrayList<>();

        // Use a regular expression to find all symbols in the Jack language
        Pattern pattern = Pattern
                .compile("[\\s{}\"()\\[\\].,;+\\-*/&|<>=~]|([a-zA-Z_$][a-zA-Z\\d_$]*(?=[,;]))|([,;=(){}])");
        Matcher matcher = pattern.matcher(line);

        // Iterate through the input line and add the symbols to the list in order
        int lastEnd = 0;
        while (matcher.find()) {
            if (matcher.start() > lastEnd) {
                symbols.add(line.substring(lastEnd, matcher.start()));
            }
            symbols.add(line.substring(matcher.start(), matcher.end()));
            lastEnd = matcher.end();
        }

        // Add the remaining part of the line (if any) to the result
        if (lastEnd < line.length()) {
            symbols.add(line.substring(lastEnd));
        }
        int i = 0;
        String word = "";
        while (i < symbols.size()) {
            symbols.get(i);
            if (symbols.get(i).equals("\"")) {
                symbols.remove(i);
                while (!symbols.get(i).equals("\"")) {
                    word += symbols.get(i);
                    symbols.remove(i);
                }
                symbols.add(i, word);
                symbols.remove("\"");
            }
            i++;
        }
        // symbols.removeIf(str -> str.contains(" "));
        symbols.removeIf(str -> str.trim().isEmpty());
        String[] arr = symbols.toArray(new String[0]);
        for (int j = 0; j < arr.length; j++) {
            if (arr[j].equals("<"))
                arr[j] = "&lt";
            if (arr[j].equals(">"))
                arr[j] = "&gt";
            if (arr[j].equals("&"))
                arr[j] = "&amp";
        }

        return arr;
    }

}