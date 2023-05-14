import java.util.TreeMap;

// An Object that contains TreeMap which targets symbols (by names) to address in memory 
public class SymbolTable {
    private TreeMap<String, Integer> table;
    static int countVar = 16;// count the next free address for variable symbols

    public SymbolTable() {
        this.table = new TreeMap<>();

        // initialize predefined symbols
        table.put("R0", 0);
        table.put("R1", 1);
        table.put("R2", 2);
        table.put("R3", 3);
        table.put("R4", 4);
        table.put("R5", 5);
        table.put("R6", 6);
        table.put("R7", 7);
        table.put("R8", 8);
        table.put("R9", 9);
        table.put("R1O", 10);
        table.put("R11", 11);
        table.put("R12", 12);
        table.put("R13", 13);
        table.put("R14", 14);
        table.put("R15", 15);
        table.put("SCREEN", 16384);
        table.put("SP", 0);
        table.put("LCL", 1);
        table.put("ARG", 2);
        table.put("THIS", 3);
        table.put("THAT", 4);

    }

    public void addEntry(String symbol, int address) {// add a symbole to the table
        table.put(symbol, address);
    }

    public boolean contains(String symbol) {// check if a given symbole is in the table
        return table.get(symbol) != null;
    }

    public int getAddress(String symbol) {// gets the address of a given symbol
        return (int) table.get(symbol);
    }
}
