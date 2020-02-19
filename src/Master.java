import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class Master {

    //Guarda en un vector cada letra
    private static ArrayList<String> inputWords = new ArrayList();

    public static ArrayList<Character> chars;
    public static ArrayList<Integer> lineIndexes;
    public static List<List<Integer>> wordIndexes;
    public static int sizeLineIndexes, sizeChars;
    public static int[][] alphabetizedIndexes;

    private static String wordSearch = "";


    public static void main(String[] args) {
        try {
            directoryRead("/Users/ulisesurielcabrerarubio/Desktop/ARSO");
            MasterControl.input(new FileReader("src/input.txt"));
            MasterControl.circularShift();
            MasterControl.alphabetizing();
            chars = MasterControl.chars;
            lineIndexes = MasterControl.lineIndexes;
            wordIndexes = MasterControl.wordIndexes;
            alphabetizedIndexes = MasterControl.alphabetizedIndexes;
            sizeLineIndexes = MasterControl.sizeLineIndexes;
            sizeChars = MasterControl.sizeChars;
            output(new FileWriter(new File("src/output.txt")));
        } catch (IOException e) {
            System.out.println("No se puedo escribir el archivo");
        }
    }

    private static void directoryRead(String path) throws IOException {
        Writer writer = (new FileWriter(new File("src/input.txt")));

        File folderFile = new File(path);
        if (folderFile.exists()) {
            File[] files = folderFile.listFiles();
            getDirectory(files);
        }
        for (String text : inputWords) {
            writer.write(text + "\n");
        }
        writer.flush();
    }

    private static void getDirectory(File[] archivo) {
        for (File file : archivo) {
            boolean isFolder = file.isDirectory();
            if (isFolder) {
                File[] files = file.listFiles();
                getDirectory(files);
            }
            inputWords.add(file.getName());
        }
    }

    private static void output(Writer writer) throws IOException {
        String word = "";
        for (int index = 0; index < alphabetizedIndexes.length; ++index) {
            if (alphabetizedIndexes[index][1] == 0) {
                int wordStart = alphabetizedIndexes[index][2];
                int lineEnd = sizeLineIndexes > alphabetizedIndexes[index][0] + 1 ? lineIndexes.get(alphabetizedIndexes[index][0] + 1) : sizeChars;

                // Desde la palabra hasta el final de la linea
                int wordEnd = wordStart;
                for (int charIndex = wordStart; charIndex < lineEnd; charIndex++) {
                    word += chars.get(charIndex);
                    wordEnd++;
                }

                if (wordEnd == sizeChars) {
                    word += " ";
                }

                if (index + 1 < alphabetizedIndexes.length)
                    word += '\n';

                if(word.contains(wordSearch)){
                    writer.write(word);
                }
            }
            word = "";
        }
        writer.flush();
    }

}