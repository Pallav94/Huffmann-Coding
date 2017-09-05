package HuffmannCoding;

class Node implements Comparable<Node> {
	char value;
	int frequency;
	Node left;
	Node right;

	Node(char x, int y) {
		value = x;
		frequency = y;
		left = null;
		right = null;
	}

	@Override
	public int compareTo(Node n) {
		return (this.frequency - n.frequency);
	}
}
