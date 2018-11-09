package top.coolzhang;

import java.util.Random;


public class test {

    public static void main(String[] args) {
        int[] a = {9, 9, 12, 19, 20, 9, 5, 8, 5, 7};
        int result = randomSelect(a, 9);
        System.out.println(result);
    }

    public static int randomSelect(int[] A, int k){
        return randomSelectDo(A, 0, A.length-1, k);
    }

    private static int randomSelectDo(int[] A, int low, int high, int k){
        int i = randomPartition(A, low, high);
        //n is the number of < A[i]
        int n = i-low;
        if(n > k)
            return randomSelectDo(A, low, i-1, k);
        else if(n == k)
            return A[i];
        else
            return randomSelectDo(A, i+1, high, k-n-1);
    }


    private static void swap(int[] A, int i, int j){
        int temp = A[i];
        A[i] = A[j];
        A[j] = temp;
    }

    private static int randomPartition(int[] A, int low, int high){
        //random divide
        Random rand = new Random();
        int r = rand.nextInt(high-low+1) + low;
        swap(A, low, r);
        int i = low;
        int x = A[low];
        for(int j = low+1; j <= high; j ++){
            if(A[j] < x){
                i ++;
                if(i != j){
                    swap(A, i, j);
                }
            }
        }
        swap(A, low, i);
        return i;
    }
}
