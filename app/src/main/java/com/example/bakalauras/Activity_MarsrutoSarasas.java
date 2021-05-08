package com.example.bakalauras;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.bakalauras.Activity_Profilis;
import com.example.bakalauras.Activity_RestoranoInformacija;
import com.example.bakalauras.Activity_Zemelapis;
import com.example.bakalauras.MainActivity;
import com.example.bakalauras.R;

import java.util.ArrayList;
import java.util.List;

import static android.R.layout.simple_list_item_1;

public class Activity_MarsrutoSarasas extends AppCompatActivity {
    int LogIn;
    public static final String EXTRA_INT = "LogIn";
    ListView listView;
    public static final String EXTRA_RESTORANAS = "RESTORANAS";
    public static final String EXTRA_ZEMELAPIOTIPAS = "2";
    Button button, button2;
    int ViewType = 2;

    RecyclerView recyclerView;
    RecyclerView.Adapter programAdapter;
    RecyclerView.LayoutManager layoutmanager;

    List<Restoranas> restoranai;
    DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__marsruto_sarasas);

        Intent intent = getIntent();
        LogIn = intent.getIntExtra(Activity_Restoranai.EXTRA_INT, -1);


        dataBaseHelper = new DataBaseHelper(Activity_MarsrutoSarasas.this);
        restoranai = dataBaseHelper.getMarsrutas(LogIn);

        recyclerView = findViewById(R.id.RecyclerView_MarsSarasas);
        recyclerView.setHasFixedSize(true);
        layoutmanager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutmanager);

        programAdapter = new RestoranaiAdapter(this, restoranai, recyclerView, LogIn, ViewType);
        recyclerView.setAdapter(programAdapter);





        button = (Button) findViewById(R.id.button_matytiMarsruta);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (restoranai.size() >= 2){
                    OpenActivity_Map();
                } else {
                    Toast.makeText(Activity_MarsrutoSarasas.this, "Šiai funkcijai reikalingi bent 2 restoranai", Toast.LENGTH_SHORT).show();
                }
            }
        });

        button = (Button) findViewById(R.id.button_refresh_mars);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restoranai = dataBaseHelper.getMarsrutas(LogIn);
                programAdapter = new RestoranaiAdapter(Activity_MarsrutoSarasas.this, restoranai, recyclerView, LogIn, ViewType);
                recyclerView.setAdapter(programAdapter);
            }
        });

    }







    public void OpenActivity_RestoranoInfo(int i){
        Intent intent = new Intent(this, Activity_RestoranoInformacija.class);
        intent.putExtra(EXTRA_RESTORANAS, restoranai.get(i).getRestoranoID());
        startActivity(intent);
    }

    public void OpenActivity_Map(){
        Intent intent = new Intent(this, Activity_Zemelapis.class);
        intent.putExtra(EXTRA_ZEMELAPIOTIPAS, 2);
        intent.putExtra(EXTRA_INT, LogIn);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
                dataBaseHelper = new DataBaseHelper(Activity_MarsrutoSarasas.this);
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
