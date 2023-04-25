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


    //No sagataves replita
    public static void size(String sourceFile) {
        try {
            FileInputStream f = new FileInputStream(sourceFile);
            System.out.println("size: " + f.available());
            f.close();
        }
        catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public static boolean equal(String firstFile, String secondFile) {
        try {
            FileInputStream f1 = new FileInputStream(firstFile);
            FileInputStream f2 = new FileInputStream(secondFile);
            int k1, k2;
            byte[] buf1 = new byte[1000];
            byte[] buf2 = new byte[1000];
            do {
                k1 = f1.read(buf1);
                k2 = f2.read(buf2);
                if (k1 != k2) {
                    f1.close();
                    f2.close();
                    return false;
                }
                for (int i=0; i<k1; i++) {
                    if (buf1[i] != buf2[i]) {
                        f1.close();
                        f2.close();
                        return false;
                    }				
                }
                } while (k1 == 0 && k2 == 0);
                    f1.close();
                    f2.close();
                    return true;
                }
            catch (IOException ex) {
                System.out.println(ex.getMessage());
                return false;
            }
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