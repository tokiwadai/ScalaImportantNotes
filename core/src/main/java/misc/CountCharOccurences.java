package misc;

public class CountCharOccurences {
    public static void main(String[] args) {
        String str = "aaaccbbdcccccfffa";

        int[] count = new int[255];

        int length = str.length();

        for (int i = 0; i < length; i++) {
            count[str.charAt(i)]++;
            //System.out.println(str.charAt(i) + ": " + count[str.charAt(i)]);
        }
        System.out.println("count['a']: " + count[97]);

        char[] ch = new char[str.length()];
        for (int i = 0; i < length; i++) {
            ch[i] = str.charAt(i);
            int find = 0;
            for (int j = 0; j <= i; j++) {
                if (str.charAt(i) == ch[j]) {
                    find++;
                    //System.out.println("i: " + i + "-" + str.charAt(i) + ", j:" + j  + "-" + ch[j]);
                }
            }

            if (find == 1) {
                System.out.println("Number of Occurrence of " + str.charAt(i)
                        + " letter is: " + count[str.charAt(i)]);
            }
        }
    }
}