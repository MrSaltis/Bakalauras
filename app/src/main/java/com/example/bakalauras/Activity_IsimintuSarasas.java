package com.example.bakalauras;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

public class Activity_IsimintuSarasas extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    int LogIn;
    public static final String EXTRA_INT = "LogIn";
    ListView listView;
    public static final String EXTRA_RESTORANAS = "RESTORANAS";
    public static final String EXTRA_ZEMELAPIOTIPAS = "2";
    public Spinner spinner, spinner2;
    public Button button, button2;
    int ViewType = 3;
    String Miestas = "";
    String Tipas = "";

    RecyclerView recyclerView;
    RecyclerView.Adapter programAdapter;
    RecyclerView.LayoutManager layoutmanager;

    List<Restoranas> restoranai;
    DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__isimintu_sarasas);

        dataBaseHelper = new DataBaseHelper(Activity_IsimintuSarasas.this);
        restoranai = dataBaseHelper.getIsiminti(LogIn);

        recyclerView = findViewById(R.id.RecyclerView_IsimSarasas);
        recyclerView.setHasFixedSize(true);
        layoutmanager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutmanager);

        programAdapter = new RestoranaiAdapter(this, restoranai, recyclerView, LogIn, ViewType);
        recyclerView.setAdapter(programAdapter);

        spinner = (Spinner) findViewById(R.id.pasirinkt_miestas_isiminti);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Miestai2, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        spinner2 = (Spinner) findViewById(R.id.pasirinkt_tipas_isiminti);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.Tipai2, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
        spinner2.setOnItemSelectedListener(this);

        button = (Button) findViewById(R.id.button_filtruoti_isiminti);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Miestas.isEmpty() && Tipas.isEmpty()){
                    restoranai = dataBaseHelper.getIsiminti(LogIn);
                    programAdapter = new RestoranaiAdapter(Activity_IsimintuSarasas.this, restoranai, recyclerView, LogIn, ViewType);
                    recyclerView.setAdapter(programAdapter);
                    if(restoranai.size() == 0){
                        Toast.makeText(Activity_IsimintuSarasas.this, "Restoranų sąrašas tuščias", Toast.LENGTH_SHORT).show();
                    }
                } else if (Miestas.isEmpty()){
                    restoranai = dataBaseHelper.getIsimintiTipas(LogIn, Tipas);
                    programAdapter = new RestoranaiAdapter(Activity_IsimintuSarasas.this, restoranai, recyclerView, LogIn, ViewType);
                    recyclerView.setAdapter(programAdapter);
                    if(restoranai.size() == 0){
                        Toast.makeText(Activity_IsimintuSarasas.this, "Restoranų sąrašas tuščias", Toast.LENGTH_SHORT).show();
                    }
                } else if (Tipas.isEmpty()){
                    restoranai = dataBaseHelper.getIsimintiMiestas(LogIn, Miestas);
                    programAdapter = new RestoranaiAdapter(Activity_IsimintuSarasas.this, restoranai, recyclerView, LogIn, ViewType);
                    recyclerView.setAdapter(programAdapter);
                    if(restoranai.size() == 0){
                        Toast.makeText(Activity_IsimintuSarasas.this, "Restoranų sąrašas tuščias", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    restoranai = dataBaseHelper.getIsimintiMiestasTipas(LogIn, Miestas, Tipas);
                    programAdapter = new RestoranaiAdapter(Activity_IsimintuSarasas.this, restoranai, recyclerView, LogIn, ViewType);
                    recyclerView.setAdapter(programAdapter);
                    if(restoranai.size() == 0){
                        Toast.makeText(Activity_IsimintuSarasas.this, "Restoranų sąrašas tuščias", Toast.LENGTH_SHORT).show();
                    }
                }
                dataBaseHelper = new DataBaseHelper(Activity_IsimintuSarasas.this);
                restoranai = dataBaseHelper.getRestoranaiCityType(Miestas, Tipas);

                programAdapter = new RestoranaiAdapter(Activity_IsimintuSarasas.this, restoranai, recyclerView, LogIn, ViewType);
                recyclerView.setAdapter(programAdapter);
            }
        });



    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.pasirinkt_miestas_isiminti){
            Miestas = parent.getItemAtPosition(position).toString();
            //Toast.makeText(this, "Pasirinktas Miestas: " + Miestas, Toast.LENGTH_SHORT).show();
        }else if (parent.getId() == R.id.pasirinkt_tipas_isiminti){
            Tipas = parent.getItemAtPosition(position).toString();
            //Toast.makeText(this, "Pasirinktas Tipas: " + Tipas, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_profilis:
                if(LogIn > -1) {
                    OpenActivity_Profilis();
                    return true;
                } else {
                    Toast.makeText(this,"Šiai funkcijai turite būti prisijungęs",Toast.LENGTH_SHORT).show();
                    return true;
                }
            case R.id.menu_pridetiRestorana: {
                dataBaseHelper = new DataBaseHelper(Activity_IsimintuSarasas.this);
                List<Vartotojas> vartotojas = dataBaseHelper.getUserFullbyID(LogIn);
                if(LogIn != -1){
                    if(vartotojas.get(0).getRole() == 1){
                        OpenActivity_PridetiRestorana();
                        return true;
                    } else {
                        Toast.makeText(this, "Šiai paslaugai teisės neturite", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                } else {
                    Toast.makeText(this, "Šiai funkcijai turite būti administratorius", Toast.LENGTH_SHORT).show();
                    return true;
                }
            }
            case R.id.menu_isimintiSarasas:
                if(LogIn > -1) {
                    OpenActivity_Isiminti();
                    return true;
                } else {
                    Toast.makeText(this,"Šiai funkcijai turite būti prisijungęs",Toast.LENGTH_SHORT).show();
                    return true;
                }
            case R.id.menu_marsSarasas:
                if(LogIn > -1) {
                    OpenActivity_MarsSarasas();
                    return true;
                } else {
                    Toast.makeText(this,"Šiai funkcijai turite būti prisijungęs",Toast.LENGTH_SHORT).show();
                    return true;
                }
            case R.id.menu_atsijungti:
                OpenActivity_MainActivity();
                return true;
            case R.id.menu_suvestine:
                OpenActivity_Administravimas();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // menu bendras
    public void OpenActivity_Profilis(){
        Intent intent = new Intent(this, Activity_Profilis.class);
        intent.putExtra(EXTRA_INT, LogIn);
        startActivity(intent);
    }

    public void OpenActivity_MainActivity(){
        LogIn = -1;
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(EXTRA_INT, LogIn);
        startActivity(intent);
    }

    public void OpenActivity_Isiminti(){
        Intent intent = new Intent(this, Activity_IsimintuSarasas.class);
        intent.putExtra(EXTRA_INT, LogIn);
        startActivity(intent);
    }

    public void OpenActivity_MarsSarasas(){
        Intent intent = new Intent(this, Activity_MarsrutoSarasas.class);
        intent.putExtra(EXTRA_INT, LogIn);
        startActivity(intent);
    }

    public void OpenActivity_PridetiRestorana(){
        Intent intent = new Intent(this, Activity_PridetiRestorana.class);
        intent.putExtra(EXTRA_INT, LogIn);
        startActivity(intent);
    }

    public void OpenActivity_Administravimas(){
        Intent intent = new Intent(this, Activity_Administravimas.class);
        intent.putExtra(EXTRA_INT, LogIn);
        startActivity(intent);
    }
}