package misc;

public class BubleSort {
    public static void main(String[] args) {
        //int[] arr = {3, 8, 5, 2, 7, 1};
        int[] arr = {6, 5, 4, 3, 2, 1};
        int n = arr.length;
        int counter = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 1; j < n; j++) {
                counter = counter + 1;
                if (arr[j-1] > arr[j]) {
                    int temp = arr[j-1];
                    arr[j-1] = arr[j];
                    arr[j] = temp;
                }
            }
        }
        StringBuffer sb = new StringBuffer();
        String separator = ", ";
        for (int i = 0; i < n; i++) {
            if (i == n-1) separator = "]";
            sb.append(arr[i] + separator);
        }
        System.out.print("[" + counter + "] -- [" + sb.toString());
    }
}
