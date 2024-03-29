package oblig1;

public class Oppgave3 {

    public static int antallUlikeUsortert(int[] a) {
        if(a.length==0){
            return 0;
        }
        int ulikeTall = 1;
        for(int i = 1; i < a.length; ++i){
            boolean check = false;
            for(int j = i; j > 0; j--){
                if(check){
                    break;
                }
                if(a[i] == a[j-1]){
                    check = true;
                }
            }
            if(!check){
                ulikeTall++;
            }
        }
        return ulikeTall;
    }

    public static int antallUlikeUsortert2(int[] a) {
        if(a.length==0){
            return 0;
        }
        int unikeElementer = 0;
        for(int i = 0; i < a.length; i++){
            boolean unik = false;

            for (int j = 0; j < i; j++){
                if(a[i] == a[j]){
                    unik = true;
                    break;
                }
            }
            if(!unik){
                unikeElementer++;
            }
        }
        return unikeElementer;
    }

}
