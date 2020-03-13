package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(10);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti != null);
    }

    @Test
    public void konstruktoriAsettaaSaldonOikein() {
        assertEquals("saldo: 0.10", kortti.toString());
    }

    @Test
    public void rahanLataaminenKasvattaaSaldoa() {
        kortti.lataaRahaa(90);
        assertEquals("saldo: 1.0", kortti.toString());
    }

    @Test
    public void saldoVaheneeJosTarpeeksiRahaa() {
        assertEquals(true, kortti.otaRahaa(10));
    }

    @Test
    public void saldoEiMuutuJosEiTarpeeksiRahaa() {
        assertEquals(false, kortti.otaRahaa(100));
    }
    
    @Test
    public void saldonTulostusToimii(){
        assertEquals(10, kortti.saldo());
    }
}
