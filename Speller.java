/**********************************
 *@novis
 *this is a main class of speller program
 *this program checks words in a text in a dictionary loaded in memory
 *this exercice is from cs50 Harvard originaly typed in C now I am retyping it to JAVA
 *---this program loads dicrionary in a memory from a text file
 *---the structrure in memory where the dictionary is loaded is TRIE
 *---after the dictionary is loaded we read text from a text file char by char
 *---we save each char until we create a word
 *---when we have a word we check that word in the dictionary using check() implemented in Dictionary.java
 *---it counts and prints out the numbed of misspeled words, the number of words ina  dictionary, number of words in the text
 ******************************************/
 import java.time.Instant;
 import java.nio.file.Path;
 import java.nio.file.Files;
 import java.nio.file.Paths;
 import java.io.FileReader;
 import java.util.Arrays;
 import java.io.IOException;
 
 public class Speller{
 	//we create a String for the path of the dictionary text file
 	public static final String DICTIONARY = "dictionaries/large";
 	//we assume the longest word will be 45 char, this is a value for size of an array
 	public static final int LENGTH = 45;
 	
 	public static void main(String[] args) throws IOException{
 		//I need to give two or three arguments by commend line when executing
 		try{
 			double duration1 = 0;
 			double duration2 = 0;
 			int n = 0; //this is the size of the dictionary assigned at line 78
 			if(args.length != 1 && args.length != 2){
 				//for now print message later in exception
 				throw new Exception("Usage: speller [dictionary] text");
 			}
 			String dictionary = (args.length == 2) ? args[0] : DICTIONARY;
 			long startTime = Instant.now().toEpochMilli();
 			boolean loaded = Dictionary.load(dictionary);
 			double duration = Instant.now().toEpochMilli() - startTime;
 			if(!loaded){
 				throw new Exception("Could not load " + dictionary);
 			}
 			String text = (args.length == 2) ? args[1] : args[0];
 			Path file = Paths.get(text);
 			
 			if(!Files.exists(file)){
 				throw new Exception("Could not load " + file.toString());
 			}
 			System.out.println("\nMISSPELLED WORDS\n");
 			int index = 0;
 			int misspellings = 0;
 			int words = 0;
 			char[] word = new char[LENGTH + 1];
 			
 			FileReader fr = new FileReader(text);
 			for(int c = fr.read(); c != -1; c = fr.read()){
 				if(Character.isAlphabetic(c) || (c == '\'' && index > 0)){
 					word[index] = (char)c;
 					index++;
 					if(index > LENGTH){
 						while(((c = fr.read()) != -1) && Character.isAlphabetic(c)){
 							index = 0;
 						}
 					}
 				}
 				else if(Character.isDigit(c)){
 					while(((c = fr.read()) != -1) && Character.isLetterOrDigit(c)){
 						
 					}
 					index = 0;
 				}
 				else if(index > 0){
 					words++;
 					long time = Instant.now().toEpochMilli();
 					String parola = new String(word);
 					String parola1 = parola.substring(0, index);
 					boolean misspelled = !Dictionary.check(parola1);
 					duration1 = Instant.now().toEpochMilli() - time;
 					if(misspelled){
 						System.out.println(parola1);
 						misspellings++;
 					}
 					index = 0;
 				}
 				long time1 = Instant.now().toEpochMilli();
 				n = Dictionary.size();
 				duration2 = Instant.now().toEpochMilli() - time1;
 			}
 			System.out.println("\nWORDS MISSPELLED:	" + misspellings);
 			System.out.println("WORDS IN DICTIONARY:	" + n);
 			System.out.println("WORDS IN TEXT:		" + words);
 			System.out.printf("TIME IN LOAD:		%.2f\n", (duration / 1000));
 			System.out.printf("TIME IN CHECK:		%.2f\n", (duration1 / 1000));
 			System.out.printf("TIME IN SIZE:		%.2f\n", (duration2 / 1000));
 			System.out.printf("TIME IN TOTAL:		%.2f\n", ((duration + duration1 + duration2) / 1000));
 			fr.close();
 		}catch(Exception e){
 			System.out.println(e.getMessage());
 			e.printStackTrace();
 		}
 	}
 }
