package com.mycompany.unicafe;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class KassapaateTest {

    Kassapaate kassapaate;
    Maksukortti maksukortti;

    @Before
    public void setUp() {
        kassapaate = new Kassapaate();
        maksukortti = new Maksukortti(300);
    }

    @Test
    public void rahamaara() {
        assertEquals(100000, kassapaate.kassassaRahaa());
    }

    @Test
    public void myydytLounaat() {
        assertEquals(0, kassapaate.maukkaitaLounaitaMyyty());
        assertEquals(0, kassapaate.edullisiaLounaitaMyyty());
    }

    @Test
    public void edullisenLounaanOstoKunMaksuTarpeeksiSuuri() {
        assertEquals(10, kassapaate.syoEdullisesti(250));
        assertEquals(100240, kassapaate.kassassaRahaa());
        assertEquals(1, kassapaate.edullisiaLounaitaMyyty());
    }

    @Test
    public void edullisenLounaanOstoKunMaksiEiTarpeeksiSuuri() {
        assertEquals(200, kassapaate.syoEdullisesti(200));
        assertEquals(100000, kassapaate.kassassaRahaa());
        assertEquals(0, kassapaate.edullisiaLounaitaMyyty());
    }

    @Test
    public void maukkaanLounaanOstoKunMaksuTarpeeksiSuuri() {
        assertEquals(10, kassapaate.syoMaukkaasti(410));
        assertEquals(100400, kassapaate.kassassaRahaa());
        assertEquals(1, kassapaate.maukkaitaLounaitaMyyty());
    }

    @Test
    public void maukkaanLounaanOstoKunMaksuEiTarpeeksiSuuri() {
        assertEquals(300, kassapaate.syoMaukkaasti(300));
        assertEquals(100000, kassapaate.kassassaRahaa());
        assertEquals(0, kassapaate.maukkaitaLounaitaMyyty());
    }

    @Test
    public void edullisenLounaanOstoKunMaksukortillaTarpeeksiRahaa() {
        assertEquals(true, kassapaate.syoEdullisesti(maksukortti));
        assertEquals(1, kassapaate.edullisiaLounaitaMyyty());
        assertEquals(100000, kassapaate.kassassaRahaa());
    }

    @Test
    public void edullisenLounaanOstoKunMaksukortilleEiTarpeeksiRahaa() {
        maksukortti.otaRahaa(100);
        assertEquals(false, kassapaate.syoEdullisesti(maksukortti));
        assertEquals(0, kassapaate.edullisiaLounaitaMyyty());
    }

    @Test
    public void maukkaanLounaanOstoKunMaksukortillaTarpeeksiRahaa() {
        maksukortti.lataaRahaa(150);
        assertEquals(true, kassapaate.syoMaukkaasti(maksukortti));
        assertEquals(1, kassapaate.maukkaitaLounaitaMyyty());
    }

    @Test
    public void maukkaanLounaanOstoKunMaksukortillaEiTarpeeksiRahaa() {
        assertEquals(false, kassapaate.syoMaukkaasti(maksukortti));
        assertEquals(0, kassapaate.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void rahanLatausKortille(){
        kassapaate.lataaRahaaKortille(maksukortti, 300);
        assertEquals(600, maksukortti.saldo());
        assertEquals(100300, kassapaate.kassassaRahaa());
    }
    
    @Test
    public void negatiivisenSummanLatausKortille(){
        kassapaate.lataaRahaaKortille(maksukortti, -10);
        assertEquals(300, maksukortti.saldo());
        assertEquals(100000, kassapaate.kassassaRahaa());
    }
}
