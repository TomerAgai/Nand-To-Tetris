import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.File;

public class HackAssembler {
    static SymbolTable symbolTable;
    static Parser parser;
    static BufferedWriter writer;

    /**
     * build an Hack Assembler
     * translates Hack Assembly program to machine language
     * 
     * @param source file which is .asm file
     */

    public static void main(String[] args) {
        String path;
        File source = new File(args[0]);
        path = args[0].replace(".asm", ".hack");
        File target = new File(path);// create a new file of type .hack
        try {
            symbolTable = new SymbolTable();
            parser = new Parser(source);
            Code.initialize();
            writer = new BufferedWriter(new FileWriter(target));
            // activate the stages of translation to machine language
            firstPass();
            secondPass(source);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // Reads the program lines, one by one focusing only on (label) declarations
    // adds the symbols to the symbolTable according to their line appearance
    public static void firstPass() {

        while (parser.hasMoreLines()) {
            parser.advance();
            while (parser.hasMoreLines() && parser.instructionType() != "L") {// run until L instruct found
                parser.advance();

            }
            if (!symbolTable.contains(parser.currentLine)) {// check if not already in the table
                symbolTable.addEntry(parser.symbol(), Parser.lineCount);
            }

        }
        try {// close the file
            parser.reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Parses the A and C instructions and adds it to the new file as 16 bit binary
    // code
    public static void secondPass(File source) {
        int address;
        String binaryAddress, instruct;
        try {
            parser = new Parser(source);// open the file again

            while (parser.hasMoreLines()) {
                parser.advance();
                if (parser.instructionType() == "A") {// A instruction

                    if (!Character.isDigit(parser.currentLine.charAt(1))) {// case of varibale symbol
                        if (!symbolTable.contains(parser.symbol())) {// new varibale symbol
                            symbolTable.addEntry(parser.symbol(), SymbolTable.countVar);// place in the next free
                                                                                        // address
                            SymbolTable.countVar++;
                        }
                        address = symbolTable.getAddress(parser.symbol());

                    } else {// regular A intruction
                        address = Integer.parseInt(parser.symbol());// convert string to int
                    }
                    binaryAddress = (Integer.toBinaryString(address));// convert int to binary
                    writer.write(String.format("%16s", binaryAddress).replaceAll(" ", "0") + "\n");

                } else {
                    if (parser.instructionType() == "C") {// C instruction
                        String comp = Code.comp(parser.comp());
                        String dest = Code.dest(parser.dest());
                        String jump = Code.jump(parser.jump());
                        // build the 16 bit String
                        instruct = "111" + comp + dest + jump;
                        writer.write(instruct + "\n");

                    }
                }
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
