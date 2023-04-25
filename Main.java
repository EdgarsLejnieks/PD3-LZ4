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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    String choiseStr;
    String sourceFile, resultFile, firstFile, secondFile;

    loop: while (true) {

      choiseStr = sc.next();

      switch (choiseStr) {
        case "comp":
          comp();
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
        case "exit":
          break loop;
        default:
          if (choiseStr.equals("exit")) {
            break loop;
          } else {
            System.out.println("Wrong command!");
            break;
          }
      }
    }

    sc.close();
  }

  public static void comp() {
    String sourceFile, resultFile;
    Scanner sc = new Scanner(System.in);
    while (true) {
      System.out.print("Source file name: ");
      sourceFile = sc.next();
      if (sourceFile.equals("exit")) {
        return;
      }
      File file = new File(sourceFile);
      if (!file.exists()) {
        System.out.println("File: " + sourceFile
            + " does not exist, choose a different file. If you want to exit this command, type \"exit\".");
      } else {
        break;
      }
    }

    while (true) {
      System.out.print("Archive name: ");
      resultFile = sc.next();
      if (resultFile.equals("exit")) {
        return;
      }
      File archiveFile = new File(resultFile);
      if (archiveFile.exists()) {
        System.out.println("A file named " + archiveFile
            + " already exists, choose a different name. If you want to exit this command, type \"exit\".");
      } else {
        break;
      }
    }

    try (BufferedReader reader = new BufferedReader(new FileReader(sourceFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(resultFile))) {
      int character;
      while ((character = reader.read()) != -1) {
        writer.write(character); // Pagaidām pārraksta to pašu failu uz jaunu failu
        // TODO: Implement the decoding componenet of the algorithm

      }
      System.out.println("File: " + resultFile + " has been compressed");

    } catch (IOException e) {
      e.printStackTrace();
    }

    System.out.println("starting");
    long start = System.currentTimeMillis();

    String inputText = readFile(sourceFile);
    // Convert input string to character array
    char[] inputChars = inputText.toCharArray();

    // Initialize the dictionary with all possible single-character sequences
    Map<String, Integer> dictionary = new HashMap<>();
    for (int i = 0; i < 256; i++) {
      dictionary.put(String.valueOf((char) i), i);
    }

    // Compress the input data using LZ4 algorithm
    List<Integer> outputList = new ArrayList<>();
    StringBuilder currentSequence = new StringBuilder();
    for (char c : inputChars) {
      // Build a new sequence by appending the current character to the current
      // sequence
      String newSequence = currentSequence.toString() + c;
      // System.out.println("new sequence: " + newSequence + " c burts: " + c);
      // Check if the new sequence exists in the dictionary
      if (dictionary.containsKey(newSequence)) {
        // If it does, update the current sequence to the new sequence
        currentSequence = new StringBuilder(newSequence);
        // System.out.println("current sequence: " + currentSequence);
      } else {
        // If it does not, add the index of the current sequence to the output list
        outputList.add(dictionary.get(currentSequence.toString()));
        // Add the new sequence to the dictionary
        dictionary.put(newSequence, dictionary.size());
        // Reset the current sequence to the current character
        currentSequence = new StringBuilder(String.valueOf(c));
      }
    }
    // Add the index of the final current sequence to the output list
    outputList.add(dictionary.get(currentSequence.toString()));

    // Convert the output list to a byte array
    byte[] compressedData = new byte[outputList.size() * 2];
    for (int i = 0; i < outputList.size(); i++) {
      // Convert the integer value to two bytes and store it in the compressed data
      // byte array
      Integer value = outputList.get(i);
      if (value != null) {
        compressedData[i * 2] = (byte) (value & 0xFF);
        compressedData[i * 2 + 1] = (byte) ((value >> 8) & 0xFF);
      }
    }

    // Print the compressed data size and the original data size
    System.out.println("Compressed data size: " + compressedData.length + " bytes");
    System.out.println("Decompressed data size: " + inputChars.length + " bytes");
    long stop = System.currentTimeMillis();

    System.out.println("Time: " + (stop - start) + " miliseconds");
    // System.out.print("Compressed data: ");
    // for (byte b : compressedData) {
    // // Print the compressed data as a space-separated list of bytes
    // System.out.print(b + " ");
    // }
    // System.out.println("dictionary: ");
    // for (Map.Entry<String, Integer> entry : dictionary.entrySet()) {
    // System.out.println(entry.getKey() + ": " + entry.getValue());
    // }
  }

  public static String readFile(String fileName) {
    StringBuilder sb = new StringBuilder();
    try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
      String line;
      while ((line = br.readLine()) != null) {
        sb.append(line);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return sb.toString();
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
