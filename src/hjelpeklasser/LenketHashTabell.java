package hjelpeklasser;

import java.util.Iterator;
import java.util.Objects;
import java.util.StringJoiner;

public class LenketHashTabell<T> implements Beholder<T> {
    private static class Node<T>      // en indre nodeklasse
    {
        private final T verdi;          // nodens verdi
        private final int hashverdi;    // lagrer hashverdien
        private Node<T> neste;          // peker til neste node

        private Node(T verdi, int hashverdi, Node<T> neste)  // konstruktør
        {
            this.verdi = verdi;
            this.hashverdi = hashverdi;
            this.neste = neste;
        }
    } // class Node

    private Node<T>[] hash;           // en nodetabell
    private final float tetthet;      // eng: loadfactor
    private int grense;               // eng: threshold (norsk: terskel)
    private int antall;               // antall verdier


    @SuppressWarnings({"rawtypes", "unchecked"})  // en annotasjon
    public LenketHashTabell(int dimensjon)       // konstruktør
    {
        if (dimensjon < 0) throw new IllegalArgumentException("Negativ dimensjon!");

        hash = new Node[dimensjon];                // bruker raw type
        tetthet = 0.75f;                           // maksimalt 75% full
        grense = (int) (tetthet * hash.length);     // gjør om til int
        antall = 0;                                // foreløpig ingen verdier
    }

    public LenketHashTabell()  // standardkonstruktør
    {
        this(13);  // velger 13 som startdimensjon inntil videre
    }

    private void utvid()                               // hører til LenketHashTabell
    {
        @SuppressWarnings({"rawtypes", "unchecked"})      // bruker raw type
                Node<T>[] nyhash = new Node[2 * hash.length + 1];  // dobling + 1

        for (int i = 0; i < hash.length; i++)            // den gamle tabellen
        {
            Node<T> p = hash[i];                           // listen til hash[i]

            while (p != null)                              // går nedover
            {
                Node<T> q = p.neste;                         // hjelpevariabel
                int nyindeks = p.hashverdi % nyhash.length;  // indeks i ny tabell

                p.neste = nyhash[nyindeks];                  // p skal legges først

                nyhash[nyindeks] = p;
                p = q;                                       // flytter p til den neste
            }

            hash[i] = null;                                // nuller i den gamle
        }

        hash = nyhash;                                   // bytter tabell
        grense = (int) (tetthet * hash.length);           // ny grense
    }

    public int antall() {
        return antall;
    }

    public boolean tom() {
        return antall == 0;
    }

    public boolean leggInn(T verdi) {
        Objects.requireNonNull(verdi, "verdi er null!");

        if (antall >= grense) utvid();

        int hashverdi = verdi.hashCode() & 0x7fffffff;  // fjerner fortegn
        int indeks = hashverdi % hash.length;           // finner indeksen

        // legger inn først i listen som hører til indeks
        hash[indeks] = new Node<>(verdi, hashverdi, hash[indeks]);  // lagrer hashverdi

        antall++;        // en ny verdi
        return true;     // vellykket innlegging
    }

    public String toString() {
        StringJoiner s = new StringJoiner(", ", "[", "]");

        for (Node<T> p : hash)              // går gjennom tabellen
        {
            for (; p != null; p = p.neste)    // går gjennom listen
            {
                s.add(p.verdi.toString());
            }
        }
        return s.toString();
    }

    @Override
    public boolean inneholder(T verdi) {
        if (verdi == null) return false;

        int hashverdi = verdi.hashCode() & 0x7fffffff;
        int indeks = hashverdi % hash.length;

        Node<T> p = hash[indeks];

        while (p != null) {
            if (p.verdi.equals(verdi)) {
                return true;
            }
            p = p.neste;
        }
        return false;
    }

    @Override
    public boolean fjern(T verdi) {
        if (verdi == null) return false;

        int hashverdi = verdi.hashCode() & 0x7fffffff;
        int indeks = hashverdi % hash.length;

        Node<T> p = hash[indeks];
        Node<T> q = null;

        while (p != null) {

            if (p.verdi.equals(verdi)) {
                if (q == null) {
                    hash[indeks] = p.neste;
                } else {
                    q.neste = p.neste;
                }
                antall--;
                return true;
            }
            q = p;
            p = p.neste;
        }
        return false;
    }

    @Override
    public void nullstill() {

        for(int i = 0; i < hash.length; i++) hash[i] = null;
        antall = 0;
    }

    @Override
    public Iterator<T> iterator() {
        throw new UnsupportedOperationException();
    }

    public static void main(String[] args) {

        String[] navn = {"Olga", "Basir", "Ali", "Per", "Elin", "Siri",
                "Ole", "Mette", "Anne", "Åse", "Leif", "Mona", "Lise"};

        LenketHashTabell<String> hashtabell = new LenketHashTabell<>(11);

        for (String n : navn) {
            hashtabell.leggInn(n);
            System.out.println(hashtabell);
        }

    }
}  // class LenketHashTabell