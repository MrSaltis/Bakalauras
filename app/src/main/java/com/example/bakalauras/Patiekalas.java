package com.example.bakalauras;

public class Patiekalas {
    public int PatiekaloID;
    public String Pavadinimas;
    public int Kaina;
    public String Tipas;
    public String Aprasymas;
    public int RestoranoID;
    public int Ivertinimas = 0;
    public int Balsai = 0;

    public void PridetiPatiekala(int ID, String Pav, int newKaina, String newTipas, String newAprasymas, int newRestoranoID, int newIvert, int newBal){
        PatiekaloID = ID;
        Pavadinimas = Pav;
        Kaina = newKaina;
        Tipas = newTipas;
        Aprasymas = newAprasymas;
        RestoranoID = newRestoranoID;
        Ivertinimas = newIvert;
        Balsai = newBal;
    }

    public int getBalsai() {
        return Balsai;
    }

    public void setBalsai() {
        Balsai = Balsai+1;
    }

    public int getIvertinimas() {
        return Ivertinimas;
    }

    public void setIvertinimas(int ivertinimas) {
        Ivertinimas = Ivertinimas + ivertinimas;
    }

    public int getPatiekaloID() {
        return PatiekaloID;
    }

    public String getPavadinimas() {
        return Pavadinimas;
    }

    public int getKaina() {
        return Kaina;
    }

    public String getTipas() {
        return Tipas;
    }

    public String getAprasymas() {
        return Aprasymas;
    }

    public void setPavadinimas(String pavadinimas) {
        Pavadinimas = pavadinimas;
    }

    public void setKaina(int kaina) {
        Kaina = kaina;
    }

    public void setTipas(String tipas) {
        Tipas = tipas;
    }

    public void setAprasymas(String aprasymas) {
        Aprasymas = aprasymas;
    }

    public int getRestoranoID() {
        return RestoranoID;
    }

    public void setRestoranoID(int restoranoID) {
        RestoranoID = restoranoID;
    }
}
