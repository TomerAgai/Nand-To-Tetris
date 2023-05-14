import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.StringBuilder;
import java.util.*;

/**
 * The tokentizer parses the input file lines into tokens (used by the
 * compilation engine)
 */

public class JackTokenizer {
    private static String[] symbolArray = { "{", "}", "(", ")", "[", "]", ".",
            ",", ";", "+", "-", "*", "/", "&", "|", "<", ">", "=", "~" };
    private static List symbols = Arrays.asList(symbolArray);

    private static String symbolPattern = "(^[^\"]*)(\\{|\\}|\\(|\\)|\\[|\\]|\\.|\\,|;|\\+|-|\\*|/|&|\\||<|>|=|~)(.*)";
    private static String commentPattern = "(^//.*)|(^/\\*.*)|^\\*.*";
    private static String keywordPattern = "class|constructor|function|method|field|static|var|int|char|boolean|void|true|false|null|this|let|do|if|else|while|return";
    private static String identifierPattern = "^[^\\d\\W]\\w*\\Z";
    private static String intPattern = "\\d+";
    private static String stringPattern = "^\"[^\"]+\"$";

    protected BufferedReader reader;
    private String currentLine;
    private String[] currentWords; // words of the line
    private int currentIndex; // the current index of word in currentWords
    private String token;

    public JackTokenizer(File f) {
        try {
            this.reader = new BufferedReader(new FileReader(f));
            setCurrentWords();
            this.currentIndex = 0; // set initial index to 0
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean hasMoreTokens() {
        try {
            if (currentLine != null || currentIndex < currentWords.length) {
                return true;
            } else {
                reader.close();
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    // advance to the next token
    public void advance() {
        if (!hasMoreTokens()) {
            return;
        }

        if (currentIndex >= currentWords.length) {// the line has ended
            setCurrentWords();
            currentIndex = 0;
        }

        token = currentWords[currentIndex++];
        System.out.println(token);
    }

    private void setCurrentWords() {
        do {// until the line is not a comment
            try {
                currentLine = reader.readLine().trim();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } while (currentLine.equals("") ||
                isComment());
        String[] words = currentLine.split("//");
        currentLine = words[0];
        if (currentLine.length() > 1 &&
                (currentLine.contains("\"") || currentLine.matches(symbolPattern))) {
            replaceStringLiteral();
        }
        // split to array of words seperated by one or more whitespace chararacters
        currentWords = currentLine.split("\\s+");
    }

    // change space of string literals to "_", surround symbols with spaces
    private void replaceStringLiteral() {
        StringBuilder str = new StringBuilder();
        boolean replace = false;

        for (int i = 0; i < currentLine.length(); i++) {
            String c = currentLine.substring(i, i + 1);
            if (c.equals("\"")) {
                if (replace) {
                    replace = false;
                } else {
                    replace = true;
                }
            }
            if (c.equals(" ")) {
                if (replace) {
                    c = "_";
                }
            }

            if (symbols.contains(c)) {
                if (!replace) {
                    c = " " + c + " ";
                }
            }

            str.append(c);
        }
        currentLine = str.toString();
    }

    // check if a line is a comment
    private boolean isComment() {
        return currentLine.matches(commentPattern);
    }

    // return the token Type
    public String tokenType() {
        if (token.matches(keywordPattern)) {
            return "KEYWORD";
        } else if (token.matches(symbolPattern)) {
            return "SYMBOL";
        } else if (token.matches(identifierPattern)) {
            return "IDENTIFIER";
        } else if (token.matches(intPattern)) {
            return "INT_CONST";
        } else if (token.matches(stringPattern)) {
            return "STRING_CONST";
        } else {
            return "ERROR";
        }
    }

    // return the keyword type
    public String keyword() {
        if (tokenType() != "KEYWORD") {
            return "ERROR";
        }
        if (token.equals("class")) {
            return "CLASS";
        } else if (token.equals("method")) {
            return "METHOD";
        } else if (token.equals("function")) {
            return "FUNCTION";
        } else if (token.equals("constructor")) {
            return "CONSTRUCTOR";
        } else if (token.equals("int")) {
            return "INT";
        } else if (token.equals("boolean")) {
            return "BOOLEAN";
        } else if (token.equals("char")) {
            return "CHAR";
        } else if (token.equals("void")) {
            return "VOID";
        } else if (token.equals("var")) {
            return "VAR";
        } else if (token.equals("static")) {
            return "STATIC";
        } else if (token.equals("field")) {
            return "FIELD";
        } else if (token.equals("let")) {
            return "LET";
        } else if (token.equals("do")) {
            return "DO";
        } else if (token.equals("if")) {
            return "IF";
        } else if (token.equals("else")) {
            return "ELSE";
        } else if (token.equals("while")) {
            return "WHILE";
        } else if (token.equals("return")) {
            return "RETURN";
        } else if (token.equals("true")) {
            return "TRUE";
        } else if (token.equals("false")) {
            return "FALSE";
        } else if (token.equals("null")) {
            return "NULL";
        } else if (token.equals("this")) {
            return "THIS";
        } else {
            System.out.println("28749uihefowiufhewnejs");
            return "ERROR";
        }
    }

    public String token() {
        return token;
    }

    public String symbol() {
        if (tokenType() != "SYMBOL") {
            return "Error";
        } else {
            return token;
        }
    }

    public String identifier() {
        if (tokenType() != "IDENTIFIER") {
            return "ERROR";
        }
        return token;
    }

    public int intVal() {
        if (tokenType() != "INT_CONST") {
            return -1;
        }
        return Integer.parseInt(token);
    }

    public String stringVal() {
        if (tokenType() != "STRING_CONST") {
            return "ERROR";
        }
        token = token.replace("_", " ");
        return token.replace("\"", "");
    }

}