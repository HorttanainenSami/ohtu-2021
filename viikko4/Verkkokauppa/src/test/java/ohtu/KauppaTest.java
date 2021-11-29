package ohtu.verkkokauppa;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.google.common.annotations.VisibleForTesting;

public class KauppaTest {
    Pankki pankki;
    Viitegeneraattori viite;
    Varasto varasto;

    @Before
    public void setUp(){
        pankki = mock(Pankki.class);
        viite = mock(Viitegeneraattori.class);        
        varasto = mock(Varasto.class);
    }
    @Test
    public void ostoksenPaaytyttyaPankinMetodiaTilisiirtoKutsutaan() {
        // määritellään että viitegeneraattori palauttaa viitten 42
        when(viite.uusi()).thenReturn(42);

        // määritellään että tuote numero 1 on maito jonka hinta on 5 ja saldo 10
        when(varasto.saldo(1)).thenReturn(10); 
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));

        // sitten testattava kauppa 
        Kauppa k = new Kauppa(varasto, pankki, viite);              

        // tehdään ostokset
        k.aloitaAsiointi();
        k.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa
        k.tilimaksu("pekka", "12345");

        // sitten suoritetaan varmistus, että pankin metodia tilisiirto on kutsuttu
        verify(pankki).tilisiirto(anyString(), anyInt(), anyString(), anyString(),anyInt());   
        // toistaiseksi ei välitetty kutsussa käytetyistä parametreista
    }
    @Test
    public void ostoksenPaaytyttyaPankinMetodiaTilisiirtoKutsutaanOikeallaAsiakkaallaTilinumeroillaJaSummalla() {

        // määritellään että viitegeneraattori palauttaa viitten 42
        when(viite.uusi()).thenReturn(42);

        // määritellään että tuote numero 1 on maito jonka hinta on 5 ja saldo 10
        when(varasto.saldo(1)).thenReturn(10); 
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));

        // sitten testattava kauppa 
        Kauppa k = new Kauppa(varasto, pankki, viite);              

        // tehdään ostokset
        k.aloitaAsiointi();
        k.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa
        k.tilimaksu("pekka", "12345");

        // sitten suoritetaan varmistus, että pankin metodia tilisiirto on kutsuttu
        verify(pankki).tilisiirto(eq("pekka"), eq(42), eq("12345"), eq("33333-44455"),eq(5));   
        // toistaiseksi ei välitetty kutsussa käytetyistä parametreista
    }
    @Test
    public void kahdenOstoksenPaaytyttyaPankinMetodiaTilisiirtoKutsutaan(){

        when(viite.uusi()).thenReturn(42);


        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));
        when(varasto.saldo(2)).thenReturn(10);
        when(varasto.haeTuote(2)).thenReturn(new Tuote(2, "piima", 3));

        Kauppa k = new Kauppa(varasto, pankki, viite);

        k.aloitaAsiointi();
        k.lisaaKoriin(1);
        k.lisaaKoriin(2);
        k.tilimaksu("sami", "123456");

        verify(pankki).tilisiirto(eq("sami"), eq(42), eq("123456"), anyString(), eq(8));
    }
    @Test
    public void kahdenSamanTuotteenOstoksenPaaytyttyaPankinMetodiaTilisiirtoKutsutaanOikeillaParametreilla(){

        when(viite.uusi()).thenReturn(42);

        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));

        Kauppa k = new Kauppa(varasto, pankki, viite);

        k.aloitaAsiointi();
        k.lisaaKoriin(1);
        k.lisaaKoriin(1);
        k.tilimaksu("sami", "123456");

        verify(pankki).tilisiirto(eq("sami"), eq(42), eq("123456"), anyString(), eq(10));
    }
    @Test
    public void kahdenTuotteenJoistaToistaEiOleVarastossaPankinMetodiaTilisiirtoKutsutaanOikeillaParametreilla(){

        when(viite.uusi()).thenReturn(42);

        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));
        when(varasto.saldo(2)).thenReturn(0);
        when(varasto.haeTuote(2)).thenReturn(new Tuote(2, "piima", 3));

        Kauppa k = new Kauppa(varasto, pankki, viite);

        k.aloitaAsiointi();
        k.lisaaKoriin(1);
        k.lisaaKoriin(2);
        k.tilimaksu("sami", "123456");

        verify(pankki).tilisiirto(eq("sami"), eq(42), eq("123456"), anyString(), eq(5));
    }

    @Test
    public void aloitaAsiointiNollaaEdellisenIstunnon(){
        when(viite.uusi()).thenReturn(42);
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));
        
        Kauppa k = new Kauppa(varasto, pankki, viite);
        
        k.aloitaAsiointi();
        k.lisaaKoriin(1);
        k.lisaaKoriin(1);
        k.aloitaAsiointi();
        k.tilimaksu("sami", "123456");
        verify(pankki).tilisiirto(eq("sami"), eq(42), eq("123456"), eq("33333-44455"), eq(0));
    }
    @Test
    public void maksutapahtumaHakeeUudenViitenumeronOstoksille() {
        Kauppa k = new Kauppa(varasto, pankki, viite);

        k.aloitaAsiointi();
        k.tilimaksu("sami", "123");
        verify(viite, times(1)).uusi();
        k.aloitaAsiointi();
        k.tilimaksu("sami", "123");
        verify(viite, times(2)).uusi();
        k.aloitaAsiointi();
        k.tilimaksu("sami", "123");
        verify(viite, times(3)).uusi();

    }
    @Test
    public void viitegeneraattoriKayttaaPerakkaisiaNumeroita() {
        viite = new Viitegeneraattori();

        Kauppa k = new Kauppa(varasto, pankki, viite);
        
        k.aloitaAsiointi();
        k.tilimaksu("sami", "123");
        verify(pankki).tilisiirto(anyString(),eq(1),anyString(),anyString(),anyInt());
        k.aloitaAsiointi();
        k.tilimaksu("sami", "123");
        verify(pankki).tilisiirto(anyString(),eq(2),anyString(),anyString(),anyInt());
        k.aloitaAsiointi();
        k.tilimaksu("sami", "123");
        verify(pankki).tilisiirto(anyString(),eq(3),anyString(),anyString(),anyInt());

    }
    @Test
    public void poistaKoristaToimiiOikein() {
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));
        Kauppa k = new Kauppa(varasto, pankki, viite);

        k.aloitaAsiointi();
        k.lisaaKoriin(1);
        k.lisaaKoriin(1);
        k.poistaKorista(1);
        k.tilimaksu("sami", "123");
        verify(pankki).tilisiirto(anyString(), anyInt(), anyString(), anyString(), eq(5));

    }
}

