// LZ4

// Code Conventions:
// classes -    Donkey, DonkeyKong
// interfaces - Donkey, DonkeyKong
// methods -    donkey, donkeyKong
// variables -  donkey, donkeyKong
// constants -  DONKEY, DONKEYKONG

import java.io.FileInputStream;
import java.util.*;

public static 

class Algorithm {
    static Scanner sc = new Scanner(System.in);

    //Elvis un Roberts
    public static void comp(String sourceFile, String resultFile){
        //TODO: Implement the encoding component of the algorithm
    }

    //Edgars
    public static void decomp(String sourceFile, String resultFile){
        //TODO: Implement the decoding componenet of the algorithm

        System.out.println("not finished");

        System.out.println("archive path: ");
        String archivePath = sc.nextLine(); //get path of compressed file
        System.out.println("file path: ");
        String filePath = sc.nextLine(); //get path to output to


        FileInputStream file_input = new FileInputStream(archivePath);
        // TODO: Decompress file when comp is complete
        file_input.close();
    }

    //Normunds
    public static void size(String sourceFile){
        //TODO: Implement function that checks file size in bytes
    }

    //Ričards
    public static void equal(String sourceFile, String sourceFile2){
        //TODO: Implement function that checks if two files are the same
    }

}

class Main {
    static Scanner input_sc = new Scanner(System.in);
    public static void main(){

        // 03.04.2023
        // Basic main loop with switch

        while(true){
            String command = input_sc.nextLine();


            switch(command){
                default: 
                    System.out.println("wrong command");
                    continue;
                case "comp":
                    comp();
                    continue;
                case "decomp":
                    decomp();
                    continue;
                case "size":
                    decomp();
                    continue;
                case "equal":
                    decomp();
                    continue;
            }
        }

    }
    public static void about(){
        System.out.println("221RDB168, Edgars Lejnieks, 7. grupa");
        System.out.println("221RDB151, Ričards Ivars Uldukis, 7. grupa ");
        System.out.println("221RDB195, Normunds Paserns, 7.grupa");
        System.out.println("221RDB162, Roberts Andris Barlots, 7.grupa");
        System.out.println("220RX0898, Elvis Avotiņš, 7. grupa");

    }
}