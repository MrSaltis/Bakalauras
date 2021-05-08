package com.example.bakalauras;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.R.layout.simple_list_item_1;

public class Activity_Meniu extends AppCompatActivity {
    ListView listView;
    List<Patiekalas> patiekalai = new ArrayList<>();
    int Restoranoid;
    double Ivertinimas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__meniu);

        Intent intent = getIntent();
        Restoranoid = intent.getIntExtra(Activity_RestoranoInformacija.EXTRA_RESTORANAS, 0);

        Patiekalas patiekalas = new Patiekalas();
        patiekalas.PridetiPatiekala(0,"Margarita", 12, "Pica", "Padas, pomidorų pasta, margarita sūris", 0, 45, 10);
        patiekalai.add(patiekalas);
        Patiekalas patiekalas2 = new Patiekalas();
        patiekalas2.PridetiPatiekala(1,"Cappri", 13, "Pica", "Padas, pomidorų pasta, margarita sūris, kumpis, pievagrybiai", 0, 30, 10);
        patiekalai.add(patiekalas2);
        Patiekalas patiekalas3 = new Patiekalas();
        patiekalas3.PridetiPatiekala(2,"Skanioji", 13, "Pica", "Padas, pomidorų pasta, margarita sūris, kumpis, šoninė, rūkla, česnakinis padažas", 0, 35, 10 );
        patiekalai.add(patiekalas3);
        Patiekalas patiekalas4 = new Patiekalas();
        patiekalas4.PridetiPatiekala(3,"Skanioji", 13, "Pica", "Padas, pomidorų pasta, margarita sūris, kumpis, šoninė, rūkla, česnakinis padažas", 1, 48, 10 );
        patiekalai.add(patiekalas4);

        listView = (ListView) findViewById(R.id.list_meniu);
        ArrayList<String> list = new ArrayList<>();

        for(int i=0; i<patiekalai.size() ;i++) {
            if (patiekalai.get(i).getRestoranoID() == Restoranoid) {
                if(patiekalai.get(i).getBalsai()>0) {
                    Ivertinimas = (patiekalai.get(i).getIvertinimas()*1.0)/(patiekalai.get(i).getBalsai()*1.0);
                }
                String stars = String.format("%.02f", Ivertinimas);
                list.add("Įvertinimas: "+stars+"☆ balsai: "+patiekalai.get(i).getBalsai()+"\nPavadinimas: "+patiekalai.get(i).getPavadinimas()+"    Kaina: "
                        +patiekalai.get(i).getKaina()+"€\nAprašymas: "+patiekalai.get(i).getAprasymas());
            }
        }

        ArrayAdapter arrayAdapter=new ArrayAdapter<>(this, simple_list_item_1,list);
        listView.setAdapter(arrayAdapter);

        registerForContextMenu(listView);

    }

    public void Atnaujinti(){
        listView = (ListView) findViewById(R.id.list_meniu);
        ArrayList<String> list = new ArrayList<>();

        for(int i=0; i<patiekalai.size() ;i++) {
            if (patiekalai.get(i).getRestoranoID() == Restoranoid) {
                if(patiekalai.get(i).getBalsai()>0) {
                    Ivertinimas = (patiekalai.get(i).getIvertinimas()*1.0)/(patiekalai.get(i).getBalsai()*1.0);
                }
                String stars = String.format("%.02f", Ivertinimas);
                list.add("Įvertinimas: "+stars+"☆ balsai: "+patiekalai.get(i).getBalsai()+"\nPavadinimas: "+patiekalai.get(i).getPavadinimas()+"    Kaina: "
                        +patiekalai.get(i).getKaina()+"€\nAprašymas: "+patiekalai.get(i).getAprasymas());
            }
        }

        ArrayAdapter arrayAdapter=new ArrayAdapter<>(this, simple_list_item_1,list);
        listView.setAdapter(arrayAdapter);
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_ivertinimas, menu);
        menu.setHeaderTitle("Pasirinkite įvertinimą");
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        if(item.getItemId() == R.id.Ivertinimas_vienas){
            Toast.makeText(this, "Patiekalą įvertinote 1 žvaigždute", Toast.LENGTH_SHORT).show();
            patiekalai.get(info.position).setBalsai();
            patiekalai.get(info.position).setIvertinimas(1);
            Atnaujinti();
            return true;
        }else if (item.getItemId() == R.id.Ivertinimas_du){
            Toast.makeText(this, "Patiekalą įvertinote 2 žvaigždutėmis", Toast.LENGTH_SHORT).show();
            patiekalai.get(info.position).setBalsai();
            patiekalai.get(info.position).setIvertinimas(2);
            Atnaujinti();
            return true;
        }else if (item.getItemId() == R.id.Ivertinimas_trys){
            Toast.makeText(this, "Patiekalą įvertinote 3 žvaigždutėmis", Toast.LENGTH_SHORT).show();
            patiekalai.get(info.position).setBalsai();
            patiekalai.get(info.position).setIvertinimas(3);
            Atnaujinti();
            return true;
        }else if (item.getItemId() == R.id.Ivertinimas_keturi){
            Toast.makeText(this, "Patiekalą įvertinote 4 žvaigždutėmis", Toast.LENGTH_SHORT).show();
            patiekalai.get(info.position).setBalsai();
            patiekalai.get(info.position).setIvertinimas(4);
            Atnaujinti();
            return true;
        }else if (item.getItemId() == R.id.Ivertinimas_penki){
            Toast.makeText(this, "Patiekalą įvertinote 5 žvaigždutėmis", Toast.LENGTH_SHORT).show();
            patiekalai.get(info.position).setBalsai();
            patiekalai.get(info.position).setIvertinimas(5);
            Atnaujinti();
            return true;
        }
        return true;
    }


}
