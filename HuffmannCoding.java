package HuffmannCoding;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class HuffmannCoding {
	PriorityQueue<Node> pq = new PriorityQueue<Node>();
	HashMap<Character, String> wordCode = new HashMap<Character, String>();
	ArrayList<Byte> bArray = new ArrayList<Byte>();
	HashMap<String, Character> codeWord = new HashMap<String, Character>();
	String remainBits = "";

	void addToQueue(HashMap<Character, Integer> wordFrequency) {
		for (@SuppressWarnings("rawtypes")
		Map.Entry m : wordFrequency.entrySet()) {
			char character = (char) m.getKey();
			int count = (int) m.getValue();
			Node nn = new Node(character, count);
			pq.offer(nn);

		}
	}

	void reverseMap() {
		for (@SuppressWarnings("rawtypes")
		Map.Entry m : wordCode.entrySet()) {
			char key = (char) m.getKey();
			String value = (String) m.getValue();
			codeWord.put(value, key);
		}
	}

	Node huffmannCodes() {
		while (pq.size() != 1) {
			Node a = pq.remove();
			Node b = pq.remove();
			int x = a.frequency;
			int y = b.frequency;
			Node nn = new Node('$', x + y);
			nn.left = a;
			nn.right = b;
			pq.add(nn);
		}
		Node ans = pq.remove();
		return ans;
	}

	void printCodes(Node root, String str) {
		if (root == null) {
			return;
		}
		if (root.value != '$') {
			System.out.println(root.value + ":" + str);
			wordCode.put(root.value, str);
		}
		printCodes(root.left, str + 0);
		printCodes(root.right, str + 1);

	}

	String codedSentence(String s) {

		String codedSentence = "";
		for (int i = 0; i < s.length(); i++) {
			if (wordCode.containsKey(s.charAt(i))) {
				codedSentence = codedSentence + (wordCode.get(s.charAt(i)));
			}
		}
		return codedSentence;
	}

	ArrayList<Byte> compress(String s) {
		int length = s.length();
		int ans = length / 8;
		int rem = length % 8;
		int num = 7;
		int total = 0;
		int k = 0;
		int j;
		int i;
		String sample = "";
		for (i = 1; i <= ans; i++) {
			if (k < (length - rem)) {
				for (j = k; j < k + 8; j++) {
					sample = sample + s.charAt(j);
					int x = Character.getNumericValue(s.charAt(j));
					total = total + (int) ((int) (x * (Math.pow(2, num))));
					num--;
				}
				k = j;
				num = 7;
				sample = "";
				bArray.add(((byte) total));
				total = 0;
			}
		}
		String last = "";
		sample = "";
		for (i = 0; i < (8 - rem); i++) {
			last = 0 + last;
		}
		for (i = length - rem; i < length; i++) {
			last = last + s.charAt(i);
			remainBits = remainBits + s.charAt(i);
		}
		total = 0;
		num = 7;
		for (i = 0; i < last.length(); i++) {
			int x = Character.getNumericValue(last.charAt(i));
			total = total + (int) ((int) (x * (Math.pow(2, num))));
			num--;
		}
		bArray.add(((byte) total));
		return bArray;
	}

	byte[] compressedFile(ArrayList<Byte> x) throws IOException {
		byte[] b = new byte[x.size()];
		for (int i = 0; i < x.size(); i++) {
			b[i] = x.get(i);
		}
		File outputFile = new File("/Users/pallavsaxena/Desktop/compressed.txt");
		try (FileOutputStream outputStream = new FileOutputStream(outputFile);) {
			outputStream.write(b);

			outputStream.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Compression Successful !!!");
		File file = new File("/Users/pallavsaxena/Desktop/compressed.txt");
		byte[] bytesArray = new byte[(int) file.length()];
		FileInputStream fis = new FileInputStream(file);
		fis.read(bytesArray);
		fis.close();
		return bytesArray;
	}

	String decompressedFile(byte[] b) {
		System.out.println("\n");
		String s = "";
		String x = "";
		String t = "";
		int i, k;
		for (i = 0; i < b.length - 1; i++) {
			if (b[i] < 0) {
				s = Integer.toBinaryString(b[i]);
				s = s.substring(24);
			} else {
				s = Integer.toBinaryString(b[i]);
				if (s.length() < 8) {
					for (k = 0; k < 8 - s.length(); k++) {
						t = t + 0;
					}
					s = t + s;
					t = "";
				}
			}
			x = x + s;
		}
		x = x + remainBits;
		String code = "";
		String sentence = "";
		for (i = 0; i < x.length(); i++) {
			code = code + x.charAt(i);
			if (codeWord.containsKey(code) == true) {
				sentence = sentence + codeWord.get(code);
				code = "";
			}
		}
		try {
			FileOutputStream fout = new FileOutputStream("/Users/pallavsaxena/Desktop/decompressed.txt");
			BufferedOutputStream bout = new BufferedOutputStream(fout);
			byte d[] = sentence.getBytes();
			bout.write(d);
			bout.flush();
			bout.close();
			fout.close();
			System.out.println("Decompression Successful !!!");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return x;
	}
}