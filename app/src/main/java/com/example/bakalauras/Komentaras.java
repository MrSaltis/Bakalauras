package com.example.bakalauras;

public class Komentaras {
    public int KomentaroID;
    public int VartotojoID;
    public int RestoranoID;
    public String komentaras;

    public Komentaras(int komentaroID, int vartotojoID, int restoranoID, String komentaras) {
        KomentaroID = komentaroID;
        VartotojoID = vartotojoID;
        RestoranoID = restoranoID;
        this.komentaras = komentaras;
    }

    public Komentaras() {
    }

    public int getKomentaroID() {
        return KomentaroID;
    }

    public void setKomentaroID(int komentaroID) {
        KomentaroID = komentaroID;
    }

    public int getVartotojoID() {
        return VartotojoID;
    }

    public void setVartotojoID(int vartotojoID) {
        VartotojoID = vartotojoID;
    }

    public int getRestoranoID() {
        return RestoranoID;
    }

    public void setRestoranoID(int restoranoID) {
        RestoranoID = restoranoID;
    }

    public String getKomentaras() {
        return komentaras;
    }

    public void setKomentaras(String komentaras) {
        this.komentaras = komentaras;
    }
}
