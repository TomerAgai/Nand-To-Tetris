import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CompilationEngine {
    public JackTokenizer tokenizer;
    public BufferedWriter writer;
    public String tab = "";

    public CompilationEngine(File input, File output) {
        this.tokenizer = new JackTokenizer(input);
        try {
            this.writer = new BufferedWriter(new FileWriter(output));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * compiles an entire class
     */

    public void compileClass() {
        try {
            tokenizer.advance();
            writer.write(tab + "<class>" + "\n");
            tab += "\t";
            writeTag(tokenizer.currentToken, "keyword");
            tokenizer.advance();
            writeTag(tokenizer.currentToken, "identifier");
            tokenizer.advance();
            writeTag(tokenizer.currentToken, "symbol");
            tokenizer.advance();
            while (tokenizer.keyWord().equals("FIELD") || tokenizer.keyWord().equals("STATIC")) {
                compileClassVarDec();
            }
            while (tokenizer.keyWord().equals("METHOD") || tokenizer.keyWord().equals("CONSTRUCTOR")
                    || tokenizer.keyWord().equals("FUNCTION")) {
                compileSubRoutine();
                tokenizer.advance();
            }
            writeTag("}", "symbol");
            tab = tab.substring(0);
            writer.write(tab + "</class>" + "\n");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * The compileClassVarDec method is used for compiling a class variable
     * declaration.
     * It writes the class variable declaration to the output file, with proper
     * indentation.
     */

    public void compileClassVarDec() {
        try {
            writer.write(tab + "<classVarDec>" + "\n");
            tab += "\t";
            writeTag(tokenizer.currentToken, "keyword");
            tokenizer.advance();
            identifierKeyword();
            tokenizer.advance();
            writeTag(tokenizer.currentToken, "identifier");
            tokenizer.advance();
            while (tokenizer.currentToken.equals(",")) {
                tokenizer.advance();
                writeTag(",", "symbol");
                writeTag(tokenizer.currentToken, "identifier");
                tokenizer.advance();
            }
            writeTag(";", "symbol");
            tokenizer.advance();
            tab = tab.substring(1);
            writer.write(tab + "</classVarDec>" + "\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * compiles a subroutine which could be method construcor and function
     */

    public void compileSubRoutine() {
        try {
            writer.write(tab + "<subroutineDec>" + "\n");
            tab += "\t";
            writeTag(tokenizer.currentToken, "keyword");
            tokenizer.advance();
            identifierKeyword();
            tokenizer.advance();
            writeTag(tokenizer.currentToken, "identifier");
            tokenizer.advance();
            writeTag("(", "symbol");

            compileParameterList();
            writeTag(")", "symbol");

            tokenizer.advance();

            compileSubroutineBody();

            tab = tab.substring(0);
            writer.write(tab + "</subroutineDec>" + "\n");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * checks if token is identifier or keyword and writes it to the file
     */
    public void identifierKeyword() {
        if (tokenizer.tokenType() == "KEYWORD") {
            writeTag(tokenizer.currentToken, "keyword");
        } else {
            writeTag(tokenizer.currentToken, "identifier");

        }
    }

    /**
     * Compiles a parameter list that might be empty
     */

    public void compileParameterList() {
        try {

            writer.write(tab + "<parameterList>" + "\n");
            tab += "\t";
            tokenizer.advance();
            if (!tokenizer.currentToken.equals(")")) {
                identifierKeyword();
                tokenizer.advance();
                writeTag(tokenizer.currentToken, "identifier");
                tokenizer.advance();
                while (!tokenizer.currentToken.equals(")")) {
                    writeTag(",", "symbol");
                    tokenizer.advance();
                    identifierKeyword();
                    tokenizer.advance();
                    writeTag(tokenizer.currentToken, "identifier");
                    tokenizer.advance();
                }
            }
            tab = tab.substring(1);
            writer.write(tab + "</parameterList>" + "\n");
            tab = tab.substring(1);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Compiles a subroutine body
     */
    public void compileSubroutineBody() {
        try {
            writer.write(tab + "<subroutineBody>" + "\n");
            tab += "\t";
            writeTag(tokenizer.currentToken, "symbol");
            tokenizer.advance();
            while (tokenizer.currentToken.equals("var")) {
                compileVarDec();
                tokenizer.advance();
            }

            compileStatements();
            writeTag("}", "symbol");
            tab = tab.substring(1);
            writer.write(tab + "</subroutineBody>" + "\n");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Compiles a static variable declaration or a field declaration
     */
    public void compileVarDec() {
        try {
            writer.write(tab + "<varDec>\n");
            tab += "\t";
            writeTag("var", "keyword");
            tokenizer.advance();
            identifierKeyword();
            tokenizer.advance();
            writeTag(tokenizer.currentToken, "identifier");
            tokenizer.advance();
            while (tokenizer.currentToken.equals(",")) {
                writeTag(",", "symbol");
                tokenizer.advance();
                writeTag(tokenizer.currentToken, "identifier");
                tokenizer.advance();
            }
            writeTag(";", "symbol");
            tab = tab.substring(1);
            writer.write(tab + "</varDec>\n");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * compiles statments
     */

    public void compileStatements() {
        try {
            writer.write(tab + "<statements>\n");
            tab += "\t";
            while (tokenizer.tokenType().equals("KEYWORD")) {
                if (tokenizer.currentToken.equals("let")) {
                    compileLet();
                    tokenizer.advance();
                } else if (tokenizer.currentToken.equals("if")) {
                    compileIf();
                } else if (tokenizer.currentToken.equals("while")) {
                    compileWhile();
                    tokenizer.advance();
                } else if (tokenizer.currentToken.equals("do")) {
                    compileDo();
                    tokenizer.advance();
                } else if (tokenizer.currentToken.equals("return")) {
                    compileReturn();
                    tokenizer.advance();
                } else
                    break;
            }
            tab = tab.substring(1);
            writer.write(tab + "</statements>\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void compileLet() {
        try {
            writer.write(tab + "<letStatement>\n");
            tab += "\t";
            writeTag("let", "keyword");
            tokenizer.advance();
            writeTag(tokenizer.currentToken, "identifier");
            tokenizer.advance();
            if (tokenizer.currentToken.equals("[")) {
                writeTag("[", "symbol");
                tokenizer.advance();
                compileExpression();
                writeTag("]", "symbol");
                tokenizer.advance();
            }
            writeTag("=", "symbol");
            tokenizer.advance();
            compileExpression();
            writeTag(";", "symbol");
            tab = tab.substring(1);
            writer.write(tab + "</letStatement>\n");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void compileIf() {
        try {
            writer.write(tab + "<ifStatement>\n");
            tab += "\t";
            writeTag("if", "keyword");
            tokenizer.advance();
            writeTag("(", "symbol");
            tokenizer.advance();
            compileExpression();
            writeTag(")", "symbol");
            tokenizer.advance();
            writeTag("{", "symbol");
            tokenizer.advance();
            compileStatements();
            writeTag("}", "symbol");
            tokenizer.advance();
            if (tokenizer.currentToken.equals("else")) {
                writeTag("else", "keyword");
                tokenizer.advance();
                writeTag("{", "symbol");
                tokenizer.advance();
                compileStatements();
                writeTag("}", "symbol");
                tokenizer.advance();
            }
            tab = tab.substring(1);
            writer.write(tab + "</ifStatement>\n");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void compileWhile() {
        try {
            writer.write(tab + "<whileStatement>\n");
            tab += "\t";
            writeTag("while", "keyword");
            tokenizer.advance();
            writeTag("(", "symbol");
            tokenizer.advance();
            compileExpression();
            writeTag(")", "symbol");
            tokenizer.advance();
            writeTag("{", "symbol");
            tokenizer.advance();
            compileStatements();
            writeTag("}", "symbol");
            tab = tab.substring(1);
            writer.write(tab + "</whileStatement>\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void compileDo() {
        try {
            writer.write(tab + "<doStatement>\n");
            tab += "\t";
            writeTag("do", "keyword");
            tokenizer.advance();
            writeTag(tokenizer.currentToken, "identifier");
            tokenizer.advance();
            writeTag(tokenizer.currentToken, "symbol");
            compileSubRoutineCall();
            tokenizer.advance();
            writeTag(";", "symbol");
            tab = tab.substring(1);
            writer.write(tab + "</doStatement>\n");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * The compileSubRoutineCall method is used for compiling a subroutine call.
     * It handles two cases, calls to a subroutine that are preceded by an
     * expression and calls to a subroutine that are not preceded by an expression.
     */
    public void compileSubRoutineCall() {
        // if ()
        if (tokenizer.currentToken.equals("(")) {
            tokenizer.advance();
            compileExpressionList();
            writeTag(")", "symbol");
        }
        // if .
        else {
            tokenizer.advance();
            writeTag(tokenizer.currentToken, "identifier");
            tokenizer.advance();
            writeTag("(", "symbol");
            tokenizer.advance();
            compileExpressionList();
            writeTag(")", "symbol");
        }

    }

    public void compileReturn() {
        try {
            writer.write(tab + "<returnStatement>\n");
            tab += "\t";
            writeTag("return", "keyword");
            tokenizer.advance();
            if (!tokenizer.currentToken.equals(";")) {
                compileExpression();
            }
            writeTag(";", "symbol");
            tab = tab.substring(1);
            writer.write(tab + "</returnStatement>\n");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * The compileExpression method is used for compiling an expression.
     * It recursively calls the compileTerm method to handle the different terms in
     * the expression.
     */
    private void compileExpression() {
        try {
            writer.write(tab + "<expression>\n");
            tab += "\t";
            compileTerm();
            while (tokenizer.currentToken.equals("+") || tokenizer.currentToken.equals("-")
                    || tokenizer.currentToken.equals("*")
                    || tokenizer.currentToken.equals("/") || tokenizer.currentToken.equals("&amp")
                    || tokenizer.currentToken.equals("|")
                    || tokenizer.currentToken.equals("&lt") || tokenizer.currentToken.equals("&gt")
                    || tokenizer.currentToken.equals("=")) {
                if (tokenizer.currentToken.equals("&amp") || tokenizer.currentToken.equals("&lt")
                        || tokenizer.currentToken.equals("&gt")) {
                    writeTag(tokenizer.currentToken + ";", "symbol");
                } else
                    writeTag(tokenizer.currentToken, "symbol");
                tokenizer.advance();
                compileTerm();
            }
            tab = tab.substring(1);
            writer.write(tab + "</expression>\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * compiles list of expressions, using Compile expression function
     */
    private void compileExpressionList() {
        try {
            writer.write(tab + "<expressionList>\n");
            tab += "\t";
            if (!tokenizer.currentToken.equals(")")) {
                compileExpression();
                while (tokenizer.currentToken.equals(",")) {
                    writeTag(",", "symbol");
                    tokenizer.advance();
                    compileExpression();
                }
            }

            tab = tab.substring(1);
            writer.write(tab + "</expressionList>\n");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void compileArrayTerm() {
        tokenizer.advance();
        compileExpression();
        writeTag("]", "symbol");
    }

    /**
     * The compileTerm method is used for compiling a term.
     * It handles different types of terms such as integers, strings, keywords,
     * identifiers, and expressions
     */
    private void compileTerm() {
        try {
            writer.write(tab + "<term>\n");
            tab += "\t";
            if (tokenizer.tokenType().equals("INT_CONST")) {
                writeTag(tokenizer.currentToken, "integerConstant");
                tokenizer.advance();
            } else if (tokenizer.tokenType().equals("STRING_CONST")) {
                writeTag(tokenizer.currentToken, "stringConstant");
                tokenizer.advance();
            } else if (tokenizer.currentToken.equals("true") || tokenizer.currentToken.equals("false")
                    || tokenizer.currentToken.equals("null") || tokenizer.currentToken.equals("this")) {
                writeTag(tokenizer.currentToken, "keyword");
                tokenizer.advance();
            } else if (tokenizer.currentToken.equals("-") || tokenizer.currentToken.equals("~")) {
                writeTag(tokenizer.currentToken, "symbol");
                tokenizer.advance();
                compileTerm();
            } else if (tokenizer.tokenType().equals("IDENTIFIER")) {
                writeTag(tokenizer.currentToken, "identifier");
                tokenizer.advance();
                if (tokenizer.currentToken.equals("[")) {
                    writeTag(tokenizer.currentToken, "symbol");
                    compileArrayTerm();
                    tokenizer.advance();
                } else if (tokenizer.currentToken.equals("(") || tokenizer.currentToken.equals(".")) {
                    writeTag(tokenizer.currentToken, "symbol");
                    compileSubRoutineCall();
                    tokenizer.advance();
                }
            } else if (tokenizer.tokenType().equals("SYMBOL")) {
                if (tokenizer.currentToken.equals("(")) {
                    writeTag(tokenizer.currentToken, "symbol");
                    tokenizer.advance();
                    compileExpression();
                    writeTag(tokenizer.currentToken, "symbol");
                    tokenizer.advance();
                }
            }
            tab = tab.substring(1);
            writer.write(tab + "</term>\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeTag(String word, String type) {
        try {
            writer.write(tab + "<" + type + "> " + word + " </" + type + ">" + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeWriter() {
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
