import java.util.Hashtable;

public class SymbolTable {
    // private static final Hashtable<Integer, String> map;
    // static {
    // map = new Hashtable<Integer, String>();
    // map.put(JackTokens.STATIC, "static");
    // map.put(JackTokens.FIELD, "this");
    // map.put(JackTokens.ARG, "argument");
    // map.put(JackTokens.VAR, "local");

    // }

    private Hashtable<String, Info> classTable;
    private Hashtable<String, Info> subTable;
    private int staticIndex, fieldIndex, argIndex, varIndex;

    private class Info {
        private String type;
        private String kind;
        private int index;

        public Info(String t, String k, int i) {
            type = t;
            if (k.equals("VAR")) {
                kind = "local";
            } else if (k.equals("ARG")) {
                kind = "argument";
            } else if (k.equals("FIELD")) {
                kind = "this";
            } else {
                kind = k.toLowerCase();
            }
            index = i;
        }
    }

    public SymbolTable() {
        classTable = new Hashtable<String, Info>();
        subTable = new Hashtable<String, Info>();
    }

    public void startSubroutine() {
        // start a new subroutine scope. i.e. reset the subTable
        subTable = new Hashtable<String, Info>();
        argIndex = 0;
        varIndex = 0;
    }

    public void define(String n, String t, String k) {
        // n: name, t: type(int, string ...), k: kind(static, field ...)
        Info newInfo;
        if (k.equals("STATIC") || k.equals("FIELD")) {
            if (k.equals("STATIC"))
                newInfo = new Info(t, k, staticIndex++);
            else
                newInfo = new Info(t, k, fieldIndex++);
            classTable.put(n, newInfo);

        } else if (k.equals("ARG") || k.equals("VAR")) {
            if (k.equals("ARG"))
                newInfo = new Info(t, k, argIndex++);
            else
                newInfo = new Info(t, k, varIndex++);
            subTable.put(n, newInfo);
        }
    }

    public int varCount(String k) {
        // k: kind
        switch (k) {
            case "STATIC":
                return staticIndex;
            case "FIELD":
                return fieldIndex;
            case "ARG":
                return argIndex;
            case "VAR":
                return varIndex;
            default:
                return -1;
        }
    }

    public String kindOf(String name) {
        if (subTable.containsKey(name)) {
            return subTable.get(name).kind;
        }

        if (classTable.containsKey(name)) {
            return classTable.get(name).kind;
        }

        return "KIND_ERROR";
    }

    public String typeOf(String name) {
        if (subTable.containsKey(name)) {
            return subTable.get(name).type;
        }

        if (classTable.containsKey(name)) {
            return classTable.get(name).type;
        }

        return "TYPE_ERROR";
    }

    public int indexOf(String name) {
        if (subTable.containsKey(name)) {
            return subTable.get(name).index;
        }

        if (classTable.containsKey(name)) {
            return classTable.get(name).index;
        }

        return -1;
    }

}