package com.example.bakalauras;


public class MarsrutoSarasas {

    int MarsrutoSarasoID;
    int VartotojoID;
    int RestoranoID;

    public MarsrutoSarasas(int marsrutoSarasoID, int vartotojoID, int restoranoID) {
        MarsrutoSarasoID = marsrutoSarasoID;
        VartotojoID = vartotojoID;
        RestoranoID = restoranoID;
    }

    public MarsrutoSarasas() {
    }

    public int getMarsrutoSarasoID() {
        return MarsrutoSarasoID;
    }

    public void setMarsrutoSarasoID(int marsrutoSarasoID) {
        MarsrutoSarasoID = marsrutoSarasoID;
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
}
