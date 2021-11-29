package ohtu;

import java.util.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class OstoskoriTest {

    Ostoskori kori;
    Tuote tuote;

    @Before
    public void setUp() {
        kori = new Ostoskori();
        tuote = mock(Tuote.class);
        
    }

    // step 1
    @Test
    public void ostoskorinHintaJaTavaroidenMaaraAlussa() { 
        assertEquals(0, kori.hinta());
 
        assertEquals(0, kori.tavaroitaKorissa());
    }
    @Test
    public void yhdenTuotteenLisaamisenJalkeenKorissaYksiTuote() {
        when(tuote.getHinta()).thenReturn(5);
        when(tuote.getNimi()).thenReturn("maito");
        kori.lisaaTuote(tuote);
        assertEquals(1, kori.tavaroitaKorissa());
    }
    @Test
    public void yhdenTuotteenLisaamisenJalkeenKorinHintaOikein() {
        when(tuote.getHinta()).thenReturn(5);
        when(tuote.getNimi()).thenReturn("maito");
        kori.lisaaTuote(tuote);
        assertEquals(5, kori.hinta());
    }
    @Test
    public void kahdenTuotteenLisaamisenJalkeenKorinHintaOikein() {
        when(tuote.getHinta()).thenReturn(5);
        when(tuote.getNimi()).thenReturn("maito");
        kori.lisaaTuote(tuote);
        kori.lisaaTuote(tuote);
        assertEquals(10, kori.hinta());
    }
    @Test
    public void kahdenTuotteenLisaamisenJalkeenKorissaKaksiTuotetta() {
        when(tuote.getHinta()).thenReturn(5);
        when(tuote.getNimi()).thenReturn("maito");
        kori.lisaaTuote(tuote);
        kori.lisaaTuote(tuote);
        assertEquals(2, kori.tavaroitaKorissa());
    }
    @Test
    public void lisaaKaksiEriTuotettaOstoskoriinKasvattaaTuotteidenLkm() {
        when(tuote.getHinta()).thenReturn(5);
        when(tuote.getNimi()).thenReturn("maito");
        kori.lisaaTuote(tuote);
        assertEquals(5, kori.hinta());
    }
    @Test
    public void lisaaKaksiEriTuotettaOstoskoriinKasvattaaTuotteidenHintaa() {
        when(tuote.getHinta()).thenReturn(5);
        when(tuote.getNimi()).thenReturn("maito");
        kori.lisaaTuote(tuote);
        kori.lisaaTuote(tuote);
        assertEquals(10, kori.hinta());
    }
    @Test
    public void lisaamallaKaksikertaaSamaaTuotettaOstoskoriSisaltaaVainYhdenOstoksen() {
        when(tuote.getHinta()).thenReturn(5);
        when(tuote.getNimi()).thenReturn("maito");
        kori.lisaaTuote(tuote);
        kori.lisaaTuote(tuote);
        assertEquals(1, kori.ostokset().size());
    }
    @Test
    public void lisaaKaksiEriTuotettaOstoskoriinKasvattaaOstoksienLukumaaraaKahteen() {
        Tuote tuote2 = mock(Tuote.class);
        when(tuote.getHinta()).thenReturn(5);
        when(tuote.getNimi()).thenReturn("maito");
        when(tuote2.getHinta()).thenReturn(5);
        when(tuote2.getNimi()).thenReturn("piima");
        kori.lisaaTuote(tuote);
        kori.lisaaTuote(tuote2);
        assertEquals(2, kori.ostokset().size());
    }
    @Test
    public void yhdenTuotteenLisaamisenJalkeenOstoskorissaOnOikeaTuote(){
        when(tuote.getHinta()).thenReturn(5);
        when(tuote.getNimi()).thenReturn("maito");
        
        kori.lisaaTuote(tuote);

        Ostos ostos = kori.ostokset().get(0);
        assertEquals("maito", ostos.tuotteenNimi());
        assertEquals(1, ostos.lukumaara());
    }
    @Test
    public void lisaamallaYhdenTuotteenKorissaOnYksiOstos() {
        when(tuote.getHinta()).thenReturn(5);
        when(tuote.getNimi()).thenReturn("maito");
        kori.lisaaTuote(tuote);
        assertEquals(1, kori.ostokset().size());
    }
    @Test
    public void poistamallaKoristaTuotteenJotaOnUseampiPieneneeTuotteenLkm(){
        when(tuote.getHinta()).thenReturn(5);
        when(tuote.getNimi()).thenReturn("maito");

        kori.lisaaTuote(tuote);
        kori.lisaaTuote(tuote);

        kori.poista(tuote);
        Ostos ostos = kori.ostokset().get(0);
        assertEquals(1, ostos.lukumaara());
    }
    @Test
    public void poistamallaKoristaTuotteenOstosKatoaa(){
        when(tuote.getHinta()).thenReturn(5);
        when(tuote.getNimi()).thenReturn("maito");

        kori.lisaaTuote(tuote);

        kori.poista(tuote);
        assertEquals(0, kori.ostokset().size());
    }
    @Test
    public void tyhjetamallaKorinKoriTuleeTyhjaksiOstoksista(){
        when(tuote.getHinta()).thenReturn(5);
        when(tuote.getNimi()).thenReturn("maito");

        kori.lisaaTuote(tuote);
        kori.tyhjenna();
        assertEquals(0, kori.ostokset().size());
    }

}
