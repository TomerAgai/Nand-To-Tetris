import java.io.File;

//running the assembler 
public class Main {
    public static void main(String[] args) {
        String path;
        File source = new File(args[0]);
        path = args[0].replace(".asm", ".hack");
        File target = new File(path);// create a new file of type .hack
        HackAssembler assembler = new HackAssembler(source, target);
        assembler.translator(source);// activate the stages of translation to machine language
    }
}
