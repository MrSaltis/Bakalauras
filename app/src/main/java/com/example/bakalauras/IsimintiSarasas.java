package com.example.bakalauras;

public class IsimintiSarasas {
    int IsimintiSarasoID;
    int VartotojoID;
    int RestoranoID;

    public IsimintiSarasas(int isimintiSarasoID, int vartotojoID, int restoranoID) {
        IsimintiSarasoID = isimintiSarasoID;
        VartotojoID = vartotojoID;
        RestoranoID = restoranoID;
    }

    public IsimintiSarasas() {
    }

    public int getIsimintiSarasoID() {
        return IsimintiSarasoID;
    }

    public void setIsimintiSarasoID(int isimintiSarasoID) {
        IsimintiSarasoID = isimintiSarasoID;
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
