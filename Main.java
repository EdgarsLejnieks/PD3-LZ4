// LZ4

// Code Conventions:
// classes -    Donkey, DonkeyKong
// interfaces - Donkey, DonkeyKong
// methods -    donkey, donkeyKong
// variables -  donkey, donkeyKong
// constants -  DONKEY, DONKEYKONG
import java.util.Scanner;
import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileInputStream;  
import java.io.File;


class Main {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String choiseStr;
		String sourceFile, resultFile, firstFile, secondFile;
		
		loop: while (true) {
			
			choiseStr = sc.next();
								
			switch (choiseStr) {
			case "comp":
				System.out.print("Source file name: ");
				sourceFile = sc.next();
				System.out.print("Archive name: ");
				resultFile = sc.next();
				comp(sourceFile, resultFile);
				break;
			case "decomp":
				System.out.print("Archive name: ");
				sourceFile = sc.next();
				System.out.print("File name: ");
				resultFile = sc.next();
				decomp(sourceFile, resultFile);
				break;
			case "size":
				System.out.print("File name: ");
				sourceFile = sc.next();
				size(sourceFile);
				break;
			case "equal":
				System.out.print("First file name: ");
				firstFile = sc.next();
				System.out.print("Second file name: ");
				secondFile = sc.next();
				System.out.println(equal(firstFile, secondFile));
				break;
			case "about":
				about();
				break;
			default:
				if (choiseStr.equals("exit")){
					break loop;
				}
				else{
					System.out.println("Wrong command!");
					break;
				}
			}
		}
		sc.close();
}


public static void comp(String sourceFile, String resultFile){
	File file = new File("myfile.txt");
	if (!file.exists()) {
		System.out.println("File: " + sourceFile + " does not exist");
		return;
	}
    try (BufferedReader reader = new BufferedReader(new FileReader(sourceFile));
         BufferedWriter writer = new BufferedWriter(new FileWriter(resultFile))) {
        int character;
        while ((character = reader.read()) != -1) {
            writer.write(character); // Pagaidām pārraksta to pašu failu uz jaunu failu
			//TODO: Implement the decoding componenet of the algorithm
        }
        System.out.println("File: " + resultFile + " has been compressed");
    } catch (IOException e) {
        e.printStackTrace();
    }
}

public static void decomp(String sourceFile, String resultFile){
    //TODO: Implement the decoding componenet of the algorithm
}

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

public static void about(){
        System.out.println("221RDB168, Edgars Lejnieks, 7. grupa");
        System.out.println("221RDB151, Ričards Ivars Uldukis, 7. grupa ");
        System.out.println("221RDB195, Normunds Paserns, 7.grupa");
        System.out.println("221RDB162, Roberts Andris Barlots, 7.grupa");
        System.out.println("220RX0898, Elvis Avotiņš, 7. grupa");
}
}
