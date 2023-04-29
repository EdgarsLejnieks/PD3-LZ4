/*
Project:
Recreation of LZ4

 Code Conventions:
 classes -    Donkey, DonkeyKong
 interfaces - Donkey, DonkeyKong
 methods -    donkey, donkeyKong
 variables -  donkey, donkeyKong
 constants -  DONKEY, DONKEYKONG

 Date: 03.2023 - 04.2023
 Authors:
• Edgars Lejnieks, 221RDB168 • Leader
• Ričards Ivars Uldukis, 221RDB151
• Normunds Paserns, 221RDB195
• Roberts Andris Barlots, 221RDB162
• Elvis Avotiņš, 220RX0898
RTU, DITF, 7.Grupa

Member tasks reassigned as of 04/21/2023 11:26 PM

Project is incomplete. Compression is possible but decompression is partial and incomplete.
 */

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        String choiseStr;
        String sourceFile, firstFile, secondFile;

        loop: while (true) {

            choiseStr = sc.next();

            switch (choiseStr) {
                case "comp":
                    comp();
                    break;
                case "decomp":
                    decomp();
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
                    if (choiseStr.equals("exit")) { // Condition 'choiseStr.equals("exit")' is always 'false'
                        break loop;
                    } else {
                        System.out.println("Wrong command!");
                        break;
                    }
            }
        }

        sc.close();
    }

    // Assigned: Roberts, Ričards, Elvis
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
        /*
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
         */

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
            // Build a new sequence by appending the current character to the current sequence
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
                dictionary.put(newSequence, dictionary.size());
                currentSequence = new StringBuilder(String.valueOf(c));
            }
        }
        // Add the index of the final current sequence to the output list
        outputList.add(dictionary.get(currentSequence.toString()));

        // Convert the output list to a byte array
        byte[] compressedData = new byte[outputList.size() * 2];
        for (int i = 0; i < outputList.size(); i++) {
            // Convert the integer value to two bytes and store it in the compressed data byte array
            Integer value = outputList.get(i);
            if (value != null) {
                compressedData[i * 2] = (byte) (value & 0xFF); // returns the value as is
                compressedData[i * 2 + 1] = (byte) ((value >> 8) & 0xFF); // (-1 or 0) AND (-1)
            }
        }

        // Output dictionary to file for debug =================================
        /*
        String debugDictionaryFileName = "debug_"+resultFile;
        try{
            FileOutputStream debugDictionaryOutput = new FileOutputStream(debugDictionaryFileName);

            // Read each item in the dictionary above iterative 255
            for (int j = 256; j < dictionary.size(); j++) {
                // Naive method of fetching key of hashmap value
                String key = null;
                for (HashMap.Entry<String, Integer> entry : dictionary.entrySet()) {
                    if (entry.getValue().equals(j)) {
                        key = entry.getKey();
                        break;
                    }
                }
                int value = j;
                String line = key + ";n;" + value + "\n";
                // Output key and value to file line by line
                debugDictionaryOutput.write(line.getBytes());
            }
            debugDictionaryOutput.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
        */
        // - Edgars
        //==============================================================

        // Print the compressed data size and the original data size
        System.out.println("Compressed data size: " + compressedData.length + " bytes");
        System.out.println("Decompressed data size: " + inputChars.length + " bytes");
        long stop = System.currentTimeMillis();

        try{
            FileOutputStream output = new FileOutputStream(resultFile);
            output.write(compressedData);
            output.close();
        }catch (IOException e) {
            e.printStackTrace();
        }


        System.out.println("Time: " + (stop - start) + " miliseconds");
        //System.out.print("Compressed data: ");

//        for (byte b : compressedData) {
//            // Print the compressed data as a space-separated list of bytes
//            System.out.print(b + " ");
//        }

//         System.out.println("dictionary: ");
//         for (Map.Entry<String, Integer> entry : dictionary.entrySet()) {
//         System.out.println(entry.getKey() + ": " + entry.getValue());
//         }
    }

    // Autors: Roberts
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

    // Assigned: Edgars, Normunds
    public static void decomp() throws IOException {

        /*
        About: Log file creator
        Necessary because during file reconstruction special characters are broken
        To use, uncomment all sections separated with equals symbols and the close statement at the end.
        -Edgars
        */

        //==============================================
        //FileOutputStream logFileCreator = new FileOutputStream("decompressionLog.txt");
        //String log1 = "Initializing decompressor...\n";
        //logFileCreator.write(log1.getBytes());
        //==============================================


        Scanner sc = new Scanner(System.in);
        String compressedFile, decompedFile;
        while (true) {
            System.out.print("Source file name: ");
            compressedFile = sc.next();
            if (compressedFile.equals("exit")) {
                return;
            }
            File file = new File(compressedFile);
            if (!file.exists()) {
                System.out.println("File: " + compressedFile
                        + " does not exist, choose a different file. If you want to exit this command, type \"exit\".");
            } else {
                break;
            }
        }
        //==============================================
        //String logCompressedFilename = "Source file name: " + compressedFile + "\n";
        //logFileCreator.write(logCompressedFilename.getBytes());
        //==============================================
        while (true) {
            System.out.print("Archive name: ");
            decompedFile = sc.next();
            if (decompedFile.equals("exit")) {
                return;
            }
            File archiveFile = new File(decompedFile);
            if (archiveFile.exists()) {
                System.out.println("A file named " + archiveFile
                        + " already exists, choose a different name. If you want to exit this command, type \"exit\".");
            } else {
                break;
            }
        }
        //==============================================
        //String logDeCompressedFilename = "Source file name: " + decompedFile + "\n";
        //logFileCreator.write(logDeCompressedFilename.getBytes());
        //==============================================
        long start = System.currentTimeMillis();

        // Read the compressed data from file
        FileInputStream fis2 = new FileInputStream(compressedFile);
        byte[] compressedDataFromFile = fis2.readAllBytes();
        fis2.close();

        // Build a dictionary of standard symbols
        Map<Integer, String> dictionary2 = new HashMap<>();
        for (int i = 0; i < 256; i++) {
            dictionary2.put(i, String.valueOf((char) i));
        }


        // Decompress the compressed data
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < compressedDataFromFile.length; i += 2) {
            int index = (compressedDataFromFile[i] & 0xFF) | ((compressedDataFromFile[i + 1] & 0xFF) << 8);
            indices.add(index);
        }
        //==============================================
        //String indiceCount = indices.size() + "Indices" + "\n";
        //logFileCreator.write(indiceCount.getBytes());
        //==============================================

        // Reconstruct the original data
        StringBuilder outputText = new StringBuilder();
        String currentSequence = "";
        for (int index : indices) {
            String sequence;
            //==============================================
            //String logIndex = index + "\n";
            //logFileCreator.write(logIndex.getBytes());
            //==============================================
            if (index < dictionary2.size()) {
                sequence = dictionary2.get(index);
            } else if (index == dictionary2.size()) {
                sequence = currentSequence + currentSequence.charAt(0);
            } else {
                throw new IllegalStateException("Invalid index: " + index);
            }
            //==============================================
            //String logSequence = sequence + "\n";
            //logFileCreator.write(logSequence.getBytes());
            //==============================================
            outputText.append(sequence);
            if (!currentSequence.isEmpty()) {
                dictionary2.put(dictionary2.size(), currentSequence + sequence.charAt(0));
            }
            currentSequence = sequence;
            //==============================================
            //String logCurrentSequence = currentSequence + "\n";
            //logFileCreator.write(logCurrentSequence.getBytes());
            //==============================================
        }

        // Write the decompressed data to a file
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(decompedFile), StandardCharsets.UTF_8))) {
            bw.write(outputText.toString());
        }
        //logFileCreator.close();
        long stop = System.currentTimeMillis();

        // Print the original data size and the decompressed data size
        System.out.println("Compressed data size: " + compressedDataFromFile.length);
        System.out.println("Decompressed data size: " + outputText.length());
        System.out.println("Time: " + (stop - start) + " miliseconds");

    }

    // Given in example file.
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

    // Given in example file.
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
            } while (k1 == 0 && k2 == 0); // Condition 'k2 == 0' is always 'true' when reached
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