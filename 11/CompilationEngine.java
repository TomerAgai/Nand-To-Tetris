import java.io.File;

/**
 * CompilationEngine runs the whole process, compiles the given class and what's
 * in it.
 * The compilation engine uses the tokenizer for parsing, and vmwrtier for
 * writing into the output file
 */
public class CompilationEngine {
    protected JackTokenizer tokenizer;
    protected VMWriter writer;
    protected String tab = "";
    protected SymbolTable table;
    protected String className;
    protected String currentName;
    protected String currentKind;
    protected String currentType;
    protected int whileCounter, ifCounter;

    public CompilationEngine(File input, File output) {
        this.tokenizer = new JackTokenizer(input);
        this.table = new SymbolTable();
        writer = new VMWriter(output.getAbsolutePath());
    }

    public void addToTable() {
        table.define(currentName, currentType, currentKind);
    }

    /**
     * compiles an entire class
     */
    public void compileClass() {
        tokenizer.advance();
        tokenizer.advance();
        if (checkIdentifier()) {
            // className meant for identification of subroutine by name
            className = tokenizer.identifier();
        } else {
            System.out.println("illegal class name identifier");
            return;
        }

        // write {
        tokenizer.advance();
        if (!checkSymbol("{")) {
            System.out.println("missing { for class");
            return;
        }

        // write class variables
        tokenizer.advance();
        while (tokenizer.keyword().equals("STATIC") ||
                tokenizer.keyword().equals("FIELD")) {
            compileClassVarDec();
            tokenizer.advance();
        }

        // write subroutine declarations
        while (tokenizer.keyword().equals("CONSTRUCTOR") ||
                tokenizer.keyword().equals("FUNCTION") ||
                tokenizer.keyword().equals("METHOD")) {
            compileSubRoutine();
            tokenizer.advance();
        }

        // write }
        if (!checkSymbol("}")) {
            System.out.println("missing } for class");
            return;
        }
    }

    /**
     * The compileClassVarDec method is used for compiling a class variable
     * declaration.
     * It writes the class variable declaration to the output file.
     */

    public void compileClassVarDec() {
        currentKind = tokenizer.keyword();
        tokenizer.advance();
        if (!checkAndWriteType()) {
            System.out.println("illegal type for class var dec");
            return;
        }
        tokenizer.advance();

        if (checkIdentifier()) {
            addToTable();
        } else {
            System.out.println("illegal classVar identifier");
            return;
        }

        // check for ", varName"
        tokenizer.advance();
        while (tokenizer.symbol().equals(",")) {
            tokenizer.advance();
            if (checkIdentifier()) {
                addToTable();
            } else {
                System.out.println("illegal classVar identifier");
                return;
            }
            tokenizer.advance();
        }

        // match ;
        if (!checkSymbol(";")) {
            System.out.println("no ending ;");
            return;
        }
    }

    /**
     * compiles a subroutine which could be method construcor and function
     */

    public void compileSubRoutine() {

        // reset subroutine symbol table
        table.startSubroutine();

        String subRoutineKind = tokenizer.keyword();

        tokenizer.advance();
        if (!checkAndWriteType()) {
            System.out.println("Illegal type name for subroutine");
            return;
        }

        String currentSubName = null;

        // check subroutine identifier
        tokenizer.advance();
        if (checkIdentifier()) {
            currentSubName = className + "." + currentName;
        } else {
            System.out.println("illegal subroutine name");
            return;
        }

        // the first Arg is self in methods
        if (subRoutineKind.equals("METHOD")) {
            table.define("this", className, "ARG");
        }

        // check for parameters
        tokenizer.advance();
        if (checkSymbol("(")) {
            compileParameterList();
        } else {
            System.out.println("missing (");
            return;
        }
        if (!checkSymbol(")")) {
            System.out.println("missing )");
            return;
        }

        // subroutine body
        tokenizer.advance();
        if (checkSymbol("{")) {
            tokenizer.advance();
            while (tokenizer.tokenType().equals("KEYWORD") &&
                    tokenizer.token().equals("var")) {
                compileVarDec();
                tokenizer.advance();
            }
        } else {
            System.out.println("missing {");
            return;
        }

        // write function
        writer.writeFunction(currentSubName, table.varCount("VAR"));

        if (subRoutineKind.equals("CONSTRUCTOR")) {

            int numOfFields = table.varCount("FIELD");
            if (numOfFields > 0)
                writer.writePush("constant", numOfFields);
            // memory for the constructor
            writer.writeCall("Memory.alloc", 1);
            writer.writePop("pointer", 0);

        } else if (subRoutineKind.equals("METHOD")) {
            writer.writePush("argument", 0);
            writer.writePop("pointer", 0);
        }

        compileStatements();

        if (!checkSymbol("}")) {
            System.out.println("missing } for subroutine call");
        }
    }

    /**
     * Compiles a parameter list that might be empty
     */

    public int compileParameterList() {

        currentKind = "ARG";
        int numberOfArgs = 0;

        // write type
        tokenizer.advance();
        if (checkAndWriteType()) {
            tokenizer.advance();
            if (checkIdentifier()) {
                addToTable();
                numberOfArgs++;

            } else {
                System.out.println("illegal identifier in parameter list");
                return -1;
            }

            // if there are more arguments
            tokenizer.advance();
            while (tokenizer.symbol().equals(",")) {
                tokenizer.advance();
                if (!checkAndWriteType()) {
                    System.out.println("illegal type name");
                    return -1;
                }
                tokenizer.advance();
                if (checkIdentifier()) {
                    addToTable();
                    numberOfArgs++;
                } else {
                    System.out.println("illegal identifier name");
                    return -1;
                }
                tokenizer.advance();
            }
        }
        return numberOfArgs;
    }

    /**
     * Compiles a static variable declaration or a field declaration
     */
    public void compileVarDec() {

        currentKind = "VAR";

        // check type
        tokenizer.advance();
        if (!checkAndWriteType()) {
            System.out.println("illegal type for var");
            return;
        }

        // check varName
        tokenizer.advance();
        if (checkIdentifier()) {
            addToTable();
        } else {
            System.out.println("illegal identifier for var");
            return;
        }

        tokenizer.advance();
        // if there are more variables
        while (tokenizer.symbol().equals(",")) {
            tokenizer.advance();
            if (checkIdentifier()) {
                addToTable();
            } else {
                System.out.println("illegal identifier for var");
                return;
            }

            tokenizer.advance();
        }
    }

    /**
     * compiles statments
     */

    public void compileStatements() {
        while (tokenizer.tokenType().equals("KEYWORD")) {
            String keyword_type = tokenizer.keyword();
            switch (keyword_type) {
                case "LET":
                    compileLet();
                    tokenizer.advance();
                    break;
                case "IF": // no advance because it checks else
                    compileIf();
                    break;
                case "WHILE":
                    compileWhile();
                    tokenizer.advance();
                    break;
                case "DO":
                    compileDo();
                    tokenizer.advance();
                    break;
                case "RETURN":
                    compileReturn();
                    tokenizer.advance();
                    break;
                default:
                    System.out.println("illegal statement");
                    return;
            }
        }
    }

    public void compileLet() {
        tokenizer.advance();
        if (!checkIdentifier()) {
            System.out.println("Illegal identifier");
            return;
        }

        boolean isArray = false;
        String kind = table.kindOf(currentName);
        int index = table.indexOf(currentName);

        tokenizer.advance();
        if (checkSymbol("[")) {// check for an array
            compileArrayTerm();
            isArray = true;
            writer.writePush(kind, index);
            writer.writeArithmetic("add");
            writer.writePop("temp", 2);

            tokenizer.advance();
        }

        if (!checkSymbol("=")) {
            System.out.println("No = found");
            return;
        }

        tokenizer.advance();
        // what comes after the =
        compileExpression();

        if (isArray) {
            writer.writePush("temp", 2);
            writer.writePop("pointer", 1);
            writer.writePop("that", 0);
        } else {
            writer.writePop(kind, index);
        }

        if (!checkSymbol(";")) {
            System.out.println("missing ;");
            return;
        }
    }

    public void compileIf() {
        int localCounter = ifCounter++;

        tokenizer.advance();
        if (!checkSymbol("(")) {
            System.out.println("missing ( for if statement");
            return;
        }

        tokenizer.advance();
        compileExpression();

        // tokenizer.advance();
        if (!checkSymbol(")")) {
            System.out.println("missing ) for if statement");
            return;
        }

        // write ~if
        writer.writeArithmetic("not");

        tokenizer.advance();
        if (!checkSymbol("{")) {
            System.out.println("missing { for if statement");
            return;
        }

        // if-goto L1
        writer.writeIf("if_False", localCounter);
        tokenizer.advance();
        compileStatements();

        // goto L2
        writer.writeGoto("if_True", localCounter);

        if (!checkSymbol("}")) {
            System.out.println("missing } for if statement");
            return;
        }

        // label l1
        writer.writeLabel("if_False", localCounter);

        tokenizer.advance();
        if (checkKeyword("else")) {
            tokenizer.advance();
            if (!checkSymbol("{")) {
                System.out.println("missing { for else statment");
                return;
            }

            tokenizer.advance();
            compileStatements();

            if (!checkSymbol("}")) {
                System.out.println("missing } for if statement");
                return;
            }
            tokenizer.advance();
        }

        writer.writeLabel("if_True", localCounter);
    }

    public void compileWhile() {

        int localCounter = whileCounter++;

        writer.writeLabel("while_CON", localCounter);

        tokenizer.advance();
        if (!checkSymbol("(")) {
            System.out.println("missing ( in while statement");
            return;
        }

        tokenizer.advance();
        compileExpression();

        if (!checkSymbol(")")) {
            System.out.println("missing ) in while statement");
            return;
        }

        // negate the top of stack
        writer.writeArithmetic("not");

        tokenizer.advance();
        if (!checkSymbol("{")) {
            System.out.println("missing { in while statement");
            return;
        }
        writer.writeIf("while_END", localCounter);

        tokenizer.advance();
        compileStatements();

        writer.writeGoto("while_CON", localCounter);

        if (!checkSymbol("}")) {
            System.out.println("missing } in while statement");
            return;
        }

        writer.writeLabel("while_END", localCounter);
    }

    public void compileDo() {
        tokenizer.advance();
        // Before the compileSubroutine, check if . or (
        if (checkIdentifier()) {
            String firstHalf = currentName;
            tokenizer.advance();
            if (checkSymbol(".") || checkSymbol("(")) {
                // . means that its another class, ( means its method of this class
                compileSubRoutineCall(firstHalf);
            } else {
                System.out.println("Not valid subroutine call");
                return;
            }

        } else {
            System.out.println(tokenizer.token() + " is not a valid identifier for do statement");
            return;
        }

        tokenizer.advance();
        if (!checkSymbol(";")) {
            System.out.println("missing ;");
            return;
        }
        // void returns the const 0, pop it from the stack
        writer.writePop("temp", 0);
    }

    /**
     * The compileSubRoutineCall method is used for compiling a subroutine call.
     * It handles two cases, calls to a subroutine that are preceded by an
     * expression and calls to a subroutine that are not preceded by an expression.
     */
    public void compileSubRoutineCall(String firstHalf) {
        String reg = "^[A-Z].*";
        boolean isClass;

        if (firstHalf.matches(reg))
            isClass = true;
        else
            isClass = false;

        String fullSubName = null;
        int argCount = 0;

        if (tokenizer.symbol().equals("(")) {
            fullSubName = className + "." + firstHalf;
            tokenizer.advance();
            writer.writePush("pointer", 0);
            argCount = compileExpressionList(isClass);

            if (!checkSymbol(")")) {
                System.out.println("missing ) for the expression list");
                return;
            }
        } else {
            tokenizer.advance();
            if (checkIdentifier()) {
                if (isClass) {
                    // class function, don't push this pointer
                    fullSubName = firstHalf + "." + currentName;
                } else {
                    // firstHalf must be a variable defined in the symbol table
                    fullSubName = table.typeOf(firstHalf) + "." + currentName;
                    // push b's address
                    writer.writePush(table.kindOf(firstHalf), table.indexOf(firstHalf));
                }
            } else {
                System.out.println("illegal identifier for subroutine call");
                return;
            }

            tokenizer.advance();
            if (!checkSymbol("(")) {
                System.out.println("missing ( in subroutine call");
                return;
            }

            tokenizer.advance();
            argCount = compileExpressionList(isClass);

            if (!checkSymbol(")")) {
                System.out.println(
                        tokenizer.token() + " " + tokenizer.tokenType() + " does not close ) for the expressionlist");
                return;
            }
        }
        if (fullSubName != null)
            writer.writeCall(fullSubName, argCount);
    }

    public void compileReturn() {

        tokenizer.advance();
        if (!checkSymbol(";")) {
            compileExpression();

            if (!checkSymbol(";")) {
                System.out.println("missing ;");
                return;
            }
        } else {
            writer.writePush("constant", 0);
        }
        // write return
        writer.writeReturn();
    }

    /**
     * The compileExpression method is used for compiling an expression.
     * It recursively calls the compileTerm method to handle the different terms in
     * the expression.
     */
    private void compileExpression() {
        compileTerm();

        // no advance, compileTerm needs to look one token ahead
        while (checkSymbol("+") || checkSymbol("-") || checkSymbol("*") || checkSymbol("/") ||
                checkSymbol("&") || checkSymbol("|") || checkSymbol("<") || checkSymbol(">") ||
                checkSymbol("=")) {
            String localSymbol = tokenizer.symbol();
            tokenizer.advance();
            compileTerm();
            // write operation
            if (localSymbol.equals("+")) {
                writer.writeArithmetic("add");
            } else if (localSymbol.equals("-")) {
                writer.writeArithmetic("sub");
            } else if (localSymbol.equals("*")) {
                writer.writeArithmetic("call Math.multiply 2");
            } else if (localSymbol.equals("/")) {
                writer.writeArithmetic("call Math.divide 2");
            } else if (localSymbol.equals("&")) {
                writer.writeArithmetic("and");
            } else if (localSymbol.equals("|")) {
                writer.writeArithmetic("or");
            } else if (localSymbol.equals("<")) {
                writer.writeArithmetic("lt");
            } else if (localSymbol.equals(">")) {
                writer.writeArithmetic("gt");
            } else if (localSymbol.equals("=")) {
                writer.writeArithmetic("eq");
            }

        }
    }

    /**
     * compiles list of expressions, using Compile expression function
     */
    public int compileExpressionList(boolean isClass) {
        // push this pointer is done in compileSubroutineCall
        int argCounter = 1;
        if (isClass)
            argCounter = 0;

        if (!tokenizer.symbol().equals(")")) {
            compileExpression();
            argCounter++;

            // compileExpression already advanced
            while (checkSymbol(",")) {
                tokenizer.advance();
                compileExpression();
                argCounter++;

            }
        }

        return argCounter;
    }

    public void compileArrayTerm() {
        tokenizer.advance();
        compileExpression();

        if (!checkSymbol("]")) {
            System.out.println("missing ] for the array expression");
        }
    }

    /**
     * The compileTerm method is used for compiling a term.
     * It handles different types of terms such as integers, strings, keywords,
     * identifiers, and expressions
     */

    public void compileTerm() {
        if (tokenizer.tokenType().equals("INT_CONST")) {

            writer.writePush("constant", tokenizer.intVal());
            tokenizer.advance();

        } else if (tokenizer.tokenType().equals("STRING_CONST")) {

            String str = tokenizer.stringVal();
            writer.writePush("constant", str.length());
            // call String.new 1
            writer.writeCall("String.new", 1);
            for (int i = 0; i < str.length(); i++) {
                writer.writePush("constant", (int) str.charAt(i));
                writer.writeCall("String.appendChar", 2);

            }
            // now on top of the stack should be the address of an intialized string

            tokenizer.advance();
        } else if (checkKeyword("true") || checkKeyword("false") || checkKeyword("null") ||
                checkKeyword("this")) {
            if (checkKeyword("false") || checkKeyword("null")) {
                writer.writePush("constant", 0);
            } else if (checkKeyword("true")) {
                writer.writePush("constant", 1);
                writer.writeArithmetic("neg");
            } else if (checkKeyword("this")) {
                writer.writePush("pointer", 0);
            }
            tokenizer.advance();
        } else if (checkSymbol("-") || checkSymbol("~")) {
            tokenizer.advance();
            String localSymbol = tokenizer.symbol();

            compileTerm();

            // output operation
            if (localSymbol.equals("-")) {
                writer.writeArithmetic("neg");
            } else {
                writer.writeArithmetic("not");
            }
        } else if (checkIdentifier()) {
            String firstHalf = currentName;
            tokenizer.advance();
            if (checkSymbol("[")) {// check for an array
                // push the array base address
                writer.writePush(table.kindOf(firstHalf), table.indexOf(firstHalf));
                compileArrayTerm();
                writer.writeArithmetic("add");
                writer.writePop("pointer", 1);
                writer.writePush("that", 0);
                tokenizer.advance();
            } else if (checkSymbol("(") || checkSymbol(".")) {
                compileSubRoutineCall(firstHalf);
                tokenizer.advance();
            } else {
                // if symbol isnt [, (, .
                writer.writePush(table.kindOf(firstHalf), table.indexOf(firstHalf));
            }
        } else if (tokenizer.tokenType().equals("SYMBOL")) {
            if (checkSymbol("(")) {
                tokenizer.advance();
                compileExpression();
                if (checkSymbol(")")) {
                    tokenizer.advance();
                } else {
                    System.out.println("missing ) for term");
                }
            }

        } else {
            System.out.println(tokenizer.token() + " is illegal varName");
            return;
        }
    }

    private boolean checkAndWriteType() {
        String keyword = tokenizer.keyword();
        if (keyword.equals("INT") ||
                keyword.equals("CHAR") ||
                keyword.equals("BOOLEAN")) {
            currentType = tokenizer.token();
            return true;
        } else if (tokenizer.tokenType().equals("IDENTIFIER")) {
            currentType = tokenizer.token();
            return true;
        } else if (tokenizer.tokenType().equals("KEYWORD") && tokenizer.token().equals("void")) {
            currentType = tokenizer.token();
            return true;
        } else {
            return false;
        }
    }

    private boolean checkIdentifier() {
        if (tokenizer.tokenType().equals("IDENTIFIER")) {
            currentName = tokenizer.identifier();
            return true;
        } else {
            return false;
        }
    }

    private boolean checkSymbol(String s) {
        if (tokenizer.symbol().equals(s))
            return true;
        else
            return false;
    }

    private boolean checkKeyword(String k) {
        if (tokenizer.tokenType().equals("KEYWORD") &&
                tokenizer.token().equals(k)) {
            return true;
        } else {
            return false;
        }
    }

}
