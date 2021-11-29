package ohtu;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Ostoskori {
    private ArrayList<Ostos> ostokset;
 
    public Ostoskori(){
        this.ostokset = new ArrayList<>();
    }
    public int tavaroitaKorissa() {
        // kertoo korissa olevien tavaroiden lukumäärän
        // eli jos koriin lisätty 2 kpl tuotetta "maito", 
        //   tulee metodin palauttaa 2 
        // jos korissa on 1 kpl tuotetta "maito" ja 1 kpl tuotetta "juusto", 
        //   tulee metodin palauttaa 2   

        return ostokset.stream().map(ostos -> ostos.lukumaara()).reduce(0, (ans,i) -> ans+i);
    }
 
    public int hinta() {
        // kertoo korissa olevien tuotteiden yhteenlasketun hinnan
        
        return ostokset.stream().map(ostos -> ostos.hinta()).reduce(0, (ans, i) -> ans+i);
    }
 
    public void lisaaTuote(Tuote lisattava) {
        Ostos ostos = new Ostos(lisattava);

        if(!this.ostokset.contains(ostos)){
            this.ostokset.add(ostos);
        }else{
            for(Ostos o : this.ostokset){
                if(o.equals(ostos)){
                    o.muutaLukumaaraa(1);
                }
            }
        }
    }
 
    public void poista(Tuote poistettava) {
        // poistaa tuotteen
        Ostos ostos = new Ostos(poistettava);
        Iterator<Ostos> iterator = this.ostokset.iterator();
        while(iterator.hasNext()){
            Ostos o = iterator.next();
            if(o.equals(ostos)){
                o.muutaLukumaaraa(-1);
                if(o.lukumaara() == 0){
                    iterator.remove();
                }
            }
        }
        
    }
 
    public List<Ostos> ostokset() {
        // palauttaa listan jossa on korissa olevat ostokset
 
        return this.ostokset;
    }
 
    public void tyhjenna() {
        this.ostokset = new ArrayList<>();
    }
}
