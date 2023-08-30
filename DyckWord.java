/**
 * @author Ritwik Banerjee
 */
public class DyckWord {
    
    private final String word;
    
    public DyckWord(String word) {
        if (isDyckWord(word))
            this.word = word;
        else
            throw new IllegalArgumentException(String.format("%s is not a valid Dyck word.", word));
    }
    
    private static boolean isDyckWord(String word) {
        int leftCount = 0;
        int rightCount = 0;
        for (char c: word.toCharArray()){
            switch (c) {
                case '(' -> leftCount++;
                case ')' -> rightCount++;
            }
        }

        return leftCount == rightCount;
    }
    
    public String getWord() {
        return word;
    }

    public static void main(String[] args){
        System.out.println(DyckWord.isDyckWord("1-4)"));
        System.out.println(DyckWord.isDyckWord("(2+(3*7)"));
    }
    
}
