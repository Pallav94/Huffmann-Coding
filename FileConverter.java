package HuffmannCoding;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class FileConverter {
	String readFile(String fileName) throws IOException {
		String sentence="";
		 
		try{    
			 
			 	FileInputStream fin=new FileInputStream(fileName);    
	            int i=0;    
	            while((i=fin.read())!=-1){     
	             sentence=sentence+(char)i;
	            }    
	            fin.close();    
	          }catch(Exception e){System.out.println(e);}
		 		System.out.println("\n");
		 	    return sentence;    
	     }    
	public static void main(String[] args) {
		FileConverter FC = new FileConverter();

		try {
			String sentence = FC.readFile("/Users/pallavsaxena/Desktop/sampletext.txt");
			GeneratingFrequency GF = new GeneratingFrequency();
			HuffmannCoding HC = new HuffmannCoding();
			HashMap<Character, Integer> wordFrequency = GF.generateFrequency(sentence);
			HC.addToQueue(wordFrequency);
			
			Node root = HC.huffmannCodes();
			
			HC.printCodes(root, "");
			HC.reverseMap();
			String s=HC.codedSentence(sentence);
			ArrayList<Byte> codedWord=HC.compress(s);
			byte[] x=HC.compressedFile(codedWord);
			HC.decompressedFile(x);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}