package com.example.bakalauras;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.R.layout.simple_list_item_1;

public class Activity_Restoranai extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public EditText editText;
    public Spinner spinner, spinner2;
    public Button button;
    public Button button2;
    public static final String EXTRA_INT = "LogIn";
    public static final String EXTRA_MIESTAS = "Miestas";
    public static final String EXTRA_RESTORANAS = "RESTORANAS";
    public static final String EXTRA_ZEMELAPIOTIPAS = "3";
    public static final String EXTRA_TIPAS = "Tipas";
    int ViewType = 1;
    public int LogIn;
    List<Restoranas> restoranai;
    List<Patiekalas> patiekalai;
    String Miestas = "Vilnius";
    String Tipas = "Picerija";
    public int[] restoranoid;

    RecyclerView recyclerView;
    RecyclerView.Adapter programAdapter;
    RecyclerView.LayoutManager layoutmanager;

    DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restoranai);

        Intent intent = getIntent();
        LogIn = intent.getIntExtra(MainActivity.EXTRA_INT, -1);

        dataBaseHelper = new DataBaseHelper(Activity_Restoranai.this);
        restoranai = dataBaseHelper.getRestoranai();

        recyclerView = findViewById(R.id.RecyclerView);
        recyclerView.setHasFixedSize(true);
        layoutmanager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutmanager);

        programAdapter = new RestoranaiAdapter(this, restoranai, recyclerView, LogIn, 1);
        recyclerView.setAdapter(programAdapter);

        restoranoid = new int[1000];

        /*
        Patiekalas patiekalas = new Patiekalas();
        patiekalas.PridetiPatiekala(0,"Margarita", 12, "Pica", "Padas, pomidorų pasta, margarita sūris", 0, 45, 10);
        patiekalai.add(patiekalas);
        Patiekalas patiekalas2 = new Patiekalas();
        patiekalas2.PridetiPatiekala(1,"Cappri", 13, "Pica", "Padas, pomidorų pasta, margarita sūris, kumpis, pievagrybiai", 0, 35, 10);
        patiekalai.add(patiekalas2);
        Patiekalas patiekalas3 = new Patiekalas();
        patiekalas3.PridetiPatiekala(2,"Skanioji", 13, "Pica", "Padas, pomidorų pasta, margarita sūris, kumpis, šoninė, rūkla, česnakinis padažas", 1, 48, 10 );
        patiekalai.add(patiekalas3);*/




        /*Restoranas restoranas = new Restoranas();
        restoranas.Prideti(0, "Cili", "Vilnius", 54.710779, 25.262604,
                "Picerija", "Ozo g. 25, Vilnius 08217" , 860000001, "info@cili.lt", "8:00-19:00", "https://www.cili.lt", "Skaniausios ir pigios picos čia" );
        restoranai.add(restoranas);
        Restoranas restoranas2 = new Restoranas();
        restoranas2.Prideti(1, "Can Can", "Vilnius", 54.713797, 25.273032,
                "Picerija", "Ozo g. 18, Vilnius 08243" , 860000002, "info@cancan.lt", "8:00-19:00", "https://www.cancan.lt", "Skaniausios ir pigios picos čia" );
        restoranai.add(restoranas2);
        Restoranas restoranas3 = new Restoranas();
        restoranas3.Prideti(2, "Grill London", "Vilnius", 54.718938, 25.284848,
                "Restoranas", "J. Balčikonio g. 3, Vilnius 08200" , 860000005, "info@grilllondon.lt", "9:00-23:00", "https://www.grilllondon.lt", "Grilintas maistas" );
        restoranai.add(restoranas3);
        Restoranas restoranas4 = new Restoranas();
        restoranas4.Prideti(3, "Kauno pica", "Kaunas", 54.891602, 23.918543,
                "Picerija", "Karaliaus Mindaugo pr. 49, Kaunas 44333" , 860000003, "info@kaunopica.lt", "8:00-19:00", "https://www.kaunopica.lt", "Skaniausios kaunietiškos picos" );
        restoranai.add(restoranas4);*/


        spinner = (Spinner) findViewById(R.id.pasirinkt_miestas);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Miestai, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        spinner2 = (Spinner) findViewById(R.id.pasirinkt_tipas);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.Tipai, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
        spinner2.setOnItemSelectedListener(this);

        //listView = (ListView) findViewById(R.id.restoranai_sarasas);
        //ArrayList<String> list = new ArrayList<>();


        //old listview užpildymas
        /*for(int i=0; i<restoranai.size() ;i++){
            int best = 0;
            double stars = 0;
            if(restoranai.get(i).getMiestas().equals(Miestas) && restoranai.get(i).getTipas().equals(Tipas)) {
                for (int a=0; a<patiekalai.size(); a++) {
                    if(restoranai.get(i).getRestoranoID() == patiekalai.get(a).getRestoranoID()) {
                        double Ivertinimas = (patiekalai.get(a).getIvertinimas()*1.0)/(patiekalai.get(a).getBalsai()*1.0);
                        if(Ivertinimas>stars){
                            stars = Ivertinimas;
                            best = a;
                        }
                    }
                }
                list.add(restoranai.get(i).getPavadinimas()+" "+ restoranai.get(i).getDarboLaikas()+"\nPopuliariausias patiekalas: "+patiekalai.get(best).getPavadinimas()+" "
                +String.format("%.02f", stars)+"☆ balsai: "+patiekalai.get(best).getBalsai());
                restoranoid[i] = restoranai.get(i).getRestoranoID();
            }
        }
        if(list.size()==0){
            Toast.makeText(this, "Restoranų nerasta", Toast.LENGTH_SHORT).show();
        }
        ArrayAdapter arrayAdapter=new ArrayAdapter<>(this, simple_list_item_1,list);
        listView.setAdapter(arrayAdapter);*/

        // old listview filtravimas
        button = (Button) findViewById(R.id.button_filtruoti);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataBaseHelper = new DataBaseHelper(Activity_Restoranai.this);
                restoranai = dataBaseHelper.getRestoranaiCityType(Miestas, Tipas);

                programAdapter = new RestoranaiAdapter(Activity_Restoranai.this, restoranai, recyclerView, LogIn, ViewType);
                recyclerView.setAdapter(programAdapter);
            }
        });

        button2 = (Button) findViewById(R.id.button_rodytiMap);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenActivity_Map();
            }
        });


        //old listview
        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                OpenActivity_RestoranoInfo(restoranoid[position]);
            }
        });

        registerForContextMenu(listView);*/

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_restoranufunkcijos, menu);
        menu.setHeaderTitle("Pasirinkite veiksmą");
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.menu_pridetiMarsrutuos){
            Toast.makeText(this, "Restoranas pridėtas", Toast.LENGTH_SHORT).show();
            return true;
        }
        return true;
    }

    //Old listview
    public void ArrayAdapter(ArrayList list){
        ArrayAdapter arrayAdapter=new ArrayAdapter<>(this, simple_list_item_1,list);
        //listView.setAdapter(arrayAdapter);
        if(list.size()==0){
            Toast.makeText(this, "Restoranų nerasta", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.pasirinkt_miestas){
            Miestas = parent.getItemAtPosition(position).toString();
            //Toast.makeText(this, "Pasirinktas Miestas: " + Miestas, Toast.LENGTH_SHORT).show();
        }else if (parent.getId() == R.id.pasirinkt_tipas){
            Tipas = parent.getItemAtPosition(position).toString();
            //Toast.makeText(this, "Pasirinktas Tipas: " + Tipas, Toast.LENGTH_SHORT).show();
        }
        /*
        switch(parent.getId()) {
            case R.id.pasirinkt_miestas:
                Miestas = parent.getItemAtPosition(position).toString();
                Toast.makeText(this, "Pasirinktas Miestas: " + Miestas, Toast.LENGTH_SHORT).show();
            case R.id.pasirinkt_tipas:
                Tipas = parent.getItemAtPosition(position).toString();
                Toast.makeText(this, "Pasirinktas Tipas: " + Tipas, Toast.LENGTH_SHORT).show();
        }*/
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        dataBaseHelper = new DataBaseHelper(Activity_Restoranai.this);
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
                dataBaseHelper = new DataBaseHelper(Activity_Restoranai.this);
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
