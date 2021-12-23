package com.company;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Coder_Uncoder_FileManager {

    //methods used to encode a word and create a file:

    public static char getRandomCharacter(char ch1, char ch2) {
        return (char) (ch1 + Math.random() * (ch2 - ch1 + 1));
    }

    public static boolean ifExistsEarlier(int index, char letter, ArrayList<Character> word) {
        ArrayList<Character> initialLetter = new ArrayList<Character>();
        initialLetter.add(letter);
        if (word.contains(initialLetter.get(0)) == false)
        {
            return true;
        }
        boolean existsEarlier = false;
        char transformedLetter;
        if (letter == 'A')
        {
            transformedLetter = 'Z';
        }
        else
            transformedLetter = (char) (letter - 1);
        ArrayList<Character> oneElement = new ArrayList<Character>();// lazy solution lol
        oneElement.add(transformedLetter);
        for (int i = 0; i < word.indexOf(letter); i++) {
            if (word.get(i).equals(oneElement.get(0))) {
                existsEarlier = true;
            }
        }
        return existsEarlier;
    }

    public static ArrayList<Character> delete(ArrayList<Character> word, File file, File file2) {
        if (word.size() == 0) {
            return null;
        }
        try {
            FileWriter writerUserFile = new FileWriter(file2, true);
            writerUserFile.write("ADD " + word.get(word.size() - 1) + System.lineSeparator());
            writerUserFile.close();
            word.remove(word.size() - 1);
            FileWriter writer = new FileWriter(file, true);
            writer.write("DELETE " + System.lineSeparator());
            writer.close();
        } catch (IOException e) {
        }
        return word;
    }

    public static ArrayList<Character> add(ArrayList<Character> word, File file, File file2) {
        char randomLetter = getRandomCharacter('A', 'Z');
        try {
            FileWriter writerUserFile = new FileWriter(file2, true);
            writerUserFile.write("DELETE " + System.lineSeparator());
            writerUserFile.close();
            word.add(randomLetter);
            FileWriter writer = new FileWriter(file, true);
            writer.write("ADD " + randomLetter + System.lineSeparator());
            writer.close();
        } catch (IOException e) {
        }

        return word;
    }

    public static ArrayList<Character> change(ArrayList<Character> word, File file, File file2) {
        char randomLetter = getRandomCharacter('A', 'Z');

        try {
            FileWriter writerUserFile = new FileWriter(file2, true);
            writerUserFile.write("CHANGE " + word.get(word.size() - 1) + System.lineSeparator());
            writerUserFile.close();
            word.set(word.size() - 1, randomLetter);
            FileWriter writer = new FileWriter(file, true);
            writer.write("CHANGE " + randomLetter + System.lineSeparator());
            writer.close();
        } catch (IOException e) {
        }

        return word;
    }

    public static ArrayList<Character> move(ArrayList<Character> word, File file, File file2) {
        //generate letter
        //move BACK (in the user-file it will be moved forward)
        //loop - repeat till a letter existing in a word will be found
        if(word.isEmpty()){
            return null;
        }
        char randomLetter = getRandomCharacter('A', 'Z');
        while ((word.contains(randomLetter) == false) && (ifExistsEarlier(word.indexOf(randomLetter), randomLetter, word) == true))
            randomLetter = getRandomCharacter('A', 'Z');
        if (randomLetter == 'A'){
            word.set(word.indexOf(randomLetter), 'Z');
        try {
            FileWriter writerUserFile = new FileWriter(file2, true);
            writerUserFile.write("MOVE " + 'Z' + System.lineSeparator());
            writerUserFile.close();
        } catch (IOException e) {}
        }
        else{
            word.set(word.indexOf(randomLetter), (char) (randomLetter - 1));
            try {
                FileWriter writerUserFile = new FileWriter(file2, true);
               writerUserFile.write("MOVE " /*+ word.get(word.indexOf(randomLetter)) */+ System.lineSeparator());
                writerUserFile.close();
            } catch (IOException e) {}
        }
        try {
            FileWriter writer = new FileWriter(file, true);
            writer.write("MOVE " + randomLetter + System.lineSeparator());
            writer.close();
        } catch (IOException e) {
        }
        return word;
    }

    public static ArrayList<Character> stringToArrayList(String s) {
        ArrayList<Character> outputWord = new ArrayList<Character>();
        for (int i = 0; i < s.length(); i++) {
            outputWord.add(s.charAt(i));
        }
        return outputWord;
    }

    public static void generateFile() {

        Scanner sc = new Scanner(System.in);
        System.out.println("Provide the word/phrase without spaces. It can only contain letters:");
        String input = sc.nextLine();
        System.out.println("How many commands would you like to have?");
        int howManyCommands = sc.nextInt();
        ArrayList<Character> word = stringToArrayList(input);

        //put the original word into a file
        //generating commands - the methods add/delete/move/change will be overwriting a file
        File outputFile = new File("Commandlist.txt");
        File reversedUserFile = new File("ReversedFileForUser.txt");
        for (int i = 0; i < howManyCommands; i++) {
            int randomNumber = (int) (Math.random() * 4);
            if (randomNumber == 0) {
                word = delete(word, outputFile, reversedUserFile);
            } else if (randomNumber == 1) {
                word = add(word, outputFile, reversedUserFile);
            } else if (randomNumber == 2) {
                word = change(word, outputFile, reversedUserFile);
            } else if (randomNumber == 3) {
                word = move(word, outputFile,reversedUserFile);
            }
        }
        try {
            FileWriter wr = new FileWriter(reversedUserFile, true);
            wr.write("Word to be decoded: " + word);
            wr.close();
        } catch (IOException e) {

        }

        //not working xd
  /*  System.out.println("Delete file?");
        boolean a = sc.nextBoolean();
        if(a){
            outputFile.delete();
            reversedUserFile.delete();
        }
        while(a==false){
            System.out.println("Delete file?");
            a = sc.nextBoolean();
        }

*/
    }
}
