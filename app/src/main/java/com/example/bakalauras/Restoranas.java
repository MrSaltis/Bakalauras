package com.example.bakalauras;

import android.graphics.Bitmap;

public class Restoranas {
    public int RestoranoID;
    public String Pavadinimas;
    public String Miestas;
    public double Xcoord;
    public double Ycoord;
    public String Tipas;
    public String Adresas;
    public int TelNr;
    public String ElPastas;
    public String DarboLaikas;
    public String URL;
    public String Aprasymas;
    public Bitmap Paveiksliukas;

    public Restoranas(int newRestoranoID, String newPavadinimas, String newMiestas, double newXcoord, double newYcoord,
                      String newTipas, String newAdresas, int newTelNr, String newElPastas, String newDarboLaikas, String newURL,
                      String newAprasymas, Bitmap newPaveiksliukas)
    {
        this.RestoranoID = newRestoranoID;
        this.Pavadinimas = newPavadinimas;
        this.Miestas = newMiestas;
        this.Xcoord = newXcoord;
        this.Ycoord = newYcoord;
        this.Tipas = newTipas;
        this.Adresas = newAdresas;
        this.TelNr = newTelNr;
        this.ElPastas = newElPastas;
        this.DarboLaikas = newDarboLaikas;
        this.URL = newURL;
        this.Aprasymas = newAprasymas;
        this.Paveiksliukas = newPaveiksliukas;
    }


    public Restoranas() {

    }


    public void Redaguoti( String newPavadinimas, String newMiestas, double newXcoord, double newYcoord, String newTipas, String newAdresas, int newTelNr, String newElPastas, String newDarboLaikas, String newURL, String newAprasymas)
    {
        Pavadinimas = newPavadinimas;
        Miestas = newMiestas;
        Xcoord = newXcoord;
        Ycoord = newYcoord;
        Tipas = newTipas;
        Adresas = newAdresas;
        TelNr = newTelNr;
        ElPastas = newElPastas;
        DarboLaikas = newDarboLaikas;
        URL = newURL;
        Aprasymas = newAprasymas;
    }

    public String getPavadinimas(){
        return Pavadinimas;
    }

    public String getMiestas(){
        return Miestas;
    }

    public String getTipas(){
        return Tipas;
    }

    public int getRestoranoID() {
        return RestoranoID;
    }

    public double getXcoord() {
        return Xcoord;
    }

    public double getYcoord() {
        return Ycoord;
    }

    public int getTelNr() {
        return TelNr;
    }

    public String getElPastas() {
        return ElPastas;
    }

    public String getDarboLaikas() {
        return DarboLaikas;
    }

    public String getURL() {
        return URL;
    }

    public String getAprasymas() {
        return Aprasymas;
    }

    public String getAdresas() {
        return Adresas;
    }


    public Bitmap getPaveiksliukas() {
        return Paveiksliukas;
    }

    public void setPaveiksliukas(Bitmap paveiksliukas) {
        Paveiksliukas = paveiksliukas;
    }

    public void setRestoranoID(int restoranoID) {
        RestoranoID = restoranoID;
    }

    public void setPavadinimas(String pavadinimas) {
        Pavadinimas = pavadinimas;
    }

    public void setMiestas(String miestas) {
        Miestas = miestas;
    }

    public void setXcoord(double xcoord) {
        Xcoord = xcoord;
    }

    public void setYcoord(double ycoord) {
        Ycoord = ycoord;
    }

    public void setTipas(String tipas) {
        Tipas = tipas;
    }

    public void setAdresas(String adresas) {
        Adresas = adresas;
    }

    public void setTelNr(int telNr) {
        TelNr = telNr;
    }

    public void setElPastas(String elPastas) {
        ElPastas = elPastas;
    }

    public void setDarboLaikas(String darboLaikas) {
        DarboLaikas = darboLaikas;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public void setAprasymas(String aprasymas) {
        Aprasymas = aprasymas;
    }
}
