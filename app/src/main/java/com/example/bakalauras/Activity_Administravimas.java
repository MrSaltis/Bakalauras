package com.example.bakalauras;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.R.layout.simple_list_item_1;

public class Activity_Administravimas extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public EditText tekstas;
    public Spinner spinner, spinner2, spinner3;
    public Button button;
    public static final String EXTRA_INT = "LogIn";
    public static final String EXTRA_MIESTAS = "Miestas";
    public static final String EXTRA_RESTORANAS = "RESTORANAS";
    public static final String EXTRA_ZEMELAPIOTIPAS = "3";
    public static final String EXTRA_TIPAS = "Tipas";
    public int LogIn;
    List<Restoranas> restoranai;
    List<Vartotojas> vartotojai;
    String Miestas = "Vilnius";
    String Tipas = "Picerija";
    String Paieskos_tipas = "Restoranai";
    public int[] restoranoid;

    RecyclerView recyclerView;
    RecyclerView.Adapter programAdapter;
    RecyclerView.LayoutManager layoutmanager;

    DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__administravimas);

        Intent intent = getIntent();
        LogIn = intent.getIntExtra(Activity_Restoranai.EXTRA_INT, -1);


        tekstas = (EditText) findViewById(R.id.suvestine_tekstas);

        spinner = (Spinner) findViewById(R.id.spinner_paieskos_tipas);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Paieskos_tipas, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        spinner2 = (Spinner) findViewById(R.id.pasirinkt_miestas_suvestine);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.Miestai, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
        spinner2.setOnItemSelectedListener(this);

        spinner3 = (Spinner) findViewById(R.id.pasirinkt_tipas_suvestine);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this,
                R.array.Tipai, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(adapter3);
        spinner3.setOnItemSelectedListener(this);

        button = (Button) findViewById(R.id.button_filtruoti_suvestine);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Paieskos_tipas.equals("Restoranai")){
                    if(tekstas.getText().toString().equals("")) {
                        dataBaseHelper = new DataBaseHelper(Activity_Administravimas.this);
                        restoranai = dataBaseHelper.getRestoranaiCityType(Miestas, Tipas);
                    } else {
                        dataBaseHelper = new DataBaseHelper(Activity_Administravimas.this);
                        restoranai = dataBaseHelper.getRestoranasSearch(Miestas, Tipas, tekstas.getText().toString());
                    }
                    recyclerView = findViewById(R.id.RecyclerView_suvestine);
                    recyclerView.setHasFixedSize(true);
                    layoutmanager = new LinearLayoutManager(Activity_Administravimas.this);
                    recyclerView.setLayoutManager(layoutmanager);

                    programAdapter = new RestoranaiAdapter(Activity_Administravimas.this, restoranai, recyclerView, LogIn, 1);
                    recyclerView.setAdapter(programAdapter);
                }
                else if (Paieskos_tipas.equals("Vartotojai")){
                    if(tekstas.getText().toString().equals("")){
                        dataBaseHelper = new DataBaseHelper(Activity_Administravimas.this);
                        vartotojai = dataBaseHelper.getAllUsers();
                    } else {
                        dataBaseHelper = new DataBaseHelper(Activity_Administravimas.this);
                        vartotojai = dataBaseHelper.getAllUsersSearch(tekstas.getText().toString());
                    }

                    recyclerView = findViewById(R.id.RecyclerView_suvestine);
                    recyclerView.setHasFixedSize(true);
                    layoutmanager = new LinearLayoutManager(Activity_Administravimas.this);
                    recyclerView.setLayoutManager(layoutmanager);

                    programAdapter = new VartotojaiAdapter(Activity_Administravimas.this, vartotojai, recyclerView, LogIn);
                    recyclerView.setAdapter(programAdapter);
                }
            }
        });


    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent.getId() == R.id.spinner_paieskos_tipas){
            Paieskos_tipas = parent.getItemAtPosition(position).toString();

            if(Paieskos_tipas.equals("Vartotojai")){
                spinner2.setVisibility(View.GONE);
                spinner3.setVisibility(View.GONE);
            } else if (Paieskos_tipas.equals("Restoranai")) {
                spinner2.setVisibility(View.VISIBLE);
                spinner3.setVisibility(View.VISIBLE);
            }

        } else if (parent.getId() == R.id.pasirinkt_miestas_suvestine){
            Miestas = parent.getItemAtPosition(position).toString();
            //Toast.makeText(this, "Pasirinktas Miestas: " + Miestas, Toast.LENGTH_SHORT).show();
        }else if (parent.getId() == R.id.pasirinkt_tipas_suvestine){
            Tipas = parent.getItemAtPosition(position).toString();
            //Toast.makeText(this, "Pasirinktas Tipas: " + Tipas, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        dataBaseHelper = new DataBaseHelper(Activity_Administravimas.this);
        List<Vartotojas> vartotojas = dataBaseHelper.getUserFullbyID(LogIn);

        if(LogIn != -1){
            if (vartotojas.get(0).getRole() == 1) {
                getMenuInflater().inflate(R.menu.menu_main, menu);
                return true;
            } else {
                getMenuInflater().inflate(R.menu.menu_main_vartotojas, menu);
                return true;
            }
        } else {
            getMenuInflater().inflate(R.menu.menu_main_vartotojas, menu);
            return true;
        }
    }

    //menuSelect
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
                dataBaseHelper = new DataBaseHelper(Activity_Administravimas.this);
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
            case R.id.menu_suvestine:
                OpenActivity_Administravimas();
                return true;
            case R.id.menu_atsijungti:
                OpenActivity_MainActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void OpenActivity_Map(){
        Intent intent = new Intent(this, Activity_Zemelapis.class);

        intent.putExtra(EXTRA_ZEMELAPIOTIPAS, 3);
        intent.putExtra(EXTRA_MIESTAS, Miestas);
        intent.putExtra(EXTRA_TIPAS, Tipas);
        startActivity(intent);
    }

    public void OpenActivity_RestoranoInfo(int i){
        Intent intent = new Intent(this, Activity_RestoranoInformacija.class);
        intent.putExtra(EXTRA_INT, LogIn);
        intent.putExtra(EXTRA_RESTORANAS, restoranai.get(i).getRestoranoID());
        startActivity(intent);
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

    public void OpenActivity_MarsSarasas(){
        Intent intent = new Intent(this, Activity_MarsrutoSarasas.class);
        intent.putExtra(EXTRA_INT, LogIn);
        startActivity(intent);
    }

    public void OpenActivity_Isiminti(){
        Intent intent = new Intent(this, Activity_IsimintuSarasas.class);
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