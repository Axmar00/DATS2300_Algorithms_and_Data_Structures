package notater;

public class Bubble {
    public static void main(String[] args){
        int[] a = {1, 9, 2, 4, 7, 6};
        //for(int i = 0; i<a.length; i++){
        //k = a[i]

        for(int k : a) {
            System.out.println(k);
        }
        bubble(a);
    }
    static void bubble(int[] a) {
        int begin = 0;
        int end = a.length - 1;

        for(int i=begin; i <end; ++i){
            if(a[i] > a[i+1]){
                //BYTT! - vi har funnet en inversjon
                int temp = a[i];
                a[i] = a[i+1];
                a[i+1] = temp;

            }else{
                //Ingenting
            }
            System.out.print(a[0]);
            for(int j = 1; j<a.length; ++j) {
                System.out.print(", " + a[j]);
            }
            System.out.println();
        }

    }
}


