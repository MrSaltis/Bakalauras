package com.example.bakalauras;

import android.os.Build;

//import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.util.Date;

public class Vartotojas {
    public int VartotojoID;
    public String VartotojoVardas;
    public String ElPastas;
    public String Slaptazodis;
    //public LocalDate RegistracijosData;
    public int Role;

    //@RequiresApi(api = Build.VERSION_CODES.O)

    public Vartotojas(int newID, String newVartotjoVardas, String newSlaptazodis, String newElPastas, int newRole)
    {
        this.VartotojoID = newID;
        this.VartotojoVardas = newVartotjoVardas;
        this.Slaptazodis = newSlaptazodis;
        this.ElPastas = newElPastas;
        //RegistracijosData = LocalDate.now();
        this.Role = newRole;
    }

    public Vartotojas(){

    }

    public void setVardas(String newVardas) {
        VartotojoVardas = newVardas;
    }

    public void setPastas(String newPastas) {
        ElPastas = newPastas;
    }

    public void setSlaptazodis(String newSlaptazodis) {
        Slaptazodis = newSlaptazodis;
    }

    public void setRole(int role) {
        Role = role;
    }

    public int getID() {
        return VartotojoID;
    }

    public void setID(int vartotojoID) {
        VartotojoID = vartotojoID;
    }

    public String getVardas() {
        return VartotojoVardas;
    }

    public String getPastas() {
        return ElPastas;
    }

    public String getSlaptazodis() {
        return Slaptazodis;
    }

    public int getRole() {
        return Role;
    }


    public void RedaguotiVartotoja(String newVardas, String newPastas, String newPass) {
        setVardas(newVardas);
        setPastas(newPastas);
        setSlaptazodis(newPass);
    }

}

