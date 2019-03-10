/*************************************
 *@novis
 *week 5 from cs 50
 *speller now written in java
 ***************************************/
 import java.io.BufferedReader;
 import java.io.FileReader;
 import java.nio.file.Paths;
 import java.nio.file.Path;
 import java.io.IOException;
 
 
 class Dictionary{
 	public static Node trie;
 	public static int num_words = 0;
 	public static boolean loaded = false;
 	
 	public static boolean check(final String word){
 		int index;
 		Node trav = trie;
 		for(int i = 0; i < word.length(); i++){
 			if(word.charAt(i) == '\''){
 				index = 0;
 			}
 			else{
 				index = Character.toLowerCase(word.charAt(i)) - 'a' + 1;
 			}
 			if(trav.children[index] == null){
 				return false;
 			}
 			else{
 				trav = trav.children[index];
 				if(i  == word.length() - 1){
 					if(trav.isWord){
 						return true;
 					}
 					else{
 						return false;
 					}
 				}
 			}
 		}
 		return true;
 	}
 	public static boolean load(String dictionary)throws IOException{
 		BufferedReader br = new BufferedReader(new FileReader(dictionary));
 		if(br == null){
 			System.out.println("File could not be opened");
 		}
 		int index;
 		trie = new Node();
 		
 		if(trie == null){
 			System.out.println("NULL dereferencing1");
 		}
 		Node root = trie;
 		String zbor;
 		for(zbor = br.readLine(); zbor != null; zbor = br.readLine()){
 			root = trie;
 			for(int i = 0; i < zbor.length(); i++){
 				if(zbor.charAt(i) == '\''){
 					index = 0;
 				}
 				else{
 					index = zbor.charAt(i) - 'a' + 1;
 				}
 				if(root.children[index] == null){
 					root.children[index] = new Node();
 					if(root.children[index] == null){
 						System.out.println("NULL dereferencing2");
 						return false;
 					}
 					root = root.children[index];
 					if(i  == zbor.length() - 1){
 						root.isWord = true;
 					}
 				}
 				else{
 					root = root.children[index];
 					if(i  == zbor.length() - 1){
 						root.isWord = true;
 					}
 				}
 			}
 			num_words++;
 		}
 		loaded = true;
 		br.close();
 		return true;
 	}
 	public static int size(){
 		if(loaded){
 			return num_words;
 		}
 		else{
 			return 0;
 		}
 	}
 }
