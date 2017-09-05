package HuffmannCoding;

import java.util.HashMap;

public class GeneratingFrequency {
	HashMap<Character, Integer> wordFrequency = new HashMap<Character, Integer>();
	String text;

	HashMap<Character, Integer> generateFrequency(String s) {
		for (int i = 0; i < s.length(); i++) {
		if (wordFrequency.containsKey(s.charAt(i)) == false) {
					wordFrequency.put(s.charAt(i), 1);
				} else {
					int value = wordFrequency.get(s.charAt(i));
					wordFrequency.put(s.charAt(i), value + 1);
				}
			}
		return wordFrequency;
	}
}