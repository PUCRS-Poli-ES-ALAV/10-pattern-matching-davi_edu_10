import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
/**
* Class for handling simple file readings and writings.
* 
* @author Davi-K-Silva
* @version 1.2
* 
*/
public class FileHandler {
    private File file;
    Scanner reader;
    /** Represents the lines in the file as an String array.
    */
    private String[] lines;
     /** Represents the lines in the file as an String ArrayList.
    */
    ArrayList<String> linesAL; 

    /** Creates a Filehandler instance and reads the given file lines.
     * @param filename The name of the file to be read.
    */
    public FileHandler(String filename) { 
        file = new File(filename);
        readToLines();
    }

    /** Creates a Filehandler instance 
     * @since 1.2
    */
    public FileHandler() {
    }

    
    /** Reads the file and store lines data.  
    */
    private void readToLines(){
        try {
            reader = new Scanner(file);
        } catch (Exception e) {
            System.out.println(e);
        }
    
        linesAL = new ArrayList<>();
        while(reader.hasNextLine()){
            linesAL.add(reader.nextLine());
        }
        reader.close();
        lines = linesAL.toArray(new String[linesAL.size()]);
    }

    /** Get file lines as an array.
     * @return A String array with the file lines.
    */
    public String[] getLinesAsArray(){
        return lines;
    }

    /** Get file lines as an array 
     * @param filename The name of the file to be read.
     * @return A String array with the file lines.
     * @since 1.2
    */
    public String[] getLinesAsArray(String filename){
        file = new File(filename);
        readToLines();
        return lines;
    }

    /** Get file lines as an String Arraylist.
     * @return A String ArrayList with the file lines.
    */
    public ArrayList<String> getLinesAsArrayList(){
        return linesAL;
    }

    /** Get file lines as an String Arraylist.
     * @param filename The name of the file to be read.
     * @return A String ArrayList with the file lines.
     * @since 1.2
    */
    public ArrayList<String> getLinesAsArrayList(String filename){
        file = new File(filename);
        readToLines();
        return linesAL;
    }

    /** Write in a file from a String ArrayList
     * @param lines file in a String ArrayList with lines to be writen
     * @param outname name of the file to be writen
     * @since 1.2
    */
    public void writeFile(ArrayList<String> lines, String outname){
        try {
            FileWriter writer = new FileWriter(outname);
            for(String line: lines){
                if(lines.indexOf(line) == lines.size() - 1){
                    writer.write(line);
                } else {
                    writer.write(line+"\n");
                }
            }
            writer.close();
            System.out.println("File sucessfully wrote in " + outname);
          } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }
    }

    /** Write in a file from a String Array
     * @param lines file in a String Array with lines to be writen
     * @param outname name of the file to be writen
     * @since 1.2
    */
    public void writeFile(String[] lines, String outname){
        try {
            FileWriter writer = new FileWriter(outname);
            for(int i=0; i<lines.length; i++){
                if(i == lines.length - 1){
                    writer.write(lines[i]);
                } else {
                    writer.write(lines[i]+"\n");
                }
            }
            writer.close();
            System.out.println("File sucessfully wrote in " + outname);
          } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }
    }

}