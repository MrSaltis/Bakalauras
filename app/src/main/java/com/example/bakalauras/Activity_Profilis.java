package com.example.bakalauras;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class Activity_Profilis extends AppCompatActivity {
    public EditText editText;
    public static final String EXTRA_INT = "LogIn";
    TextView name, email, role;
    EditText newName, newPastas, newPass, oldPass;
    Button btn_redaguoti;

    DataBaseHelper dataBaseHelper;
    List<Vartotojas> vartotojas;
    public int LogIn = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__profilis);

        Intent intent = getIntent();
        LogIn = intent.getIntExtra(Activity_Restoranai.EXTRA_INT, -1);

        dataBaseHelper = new DataBaseHelper(Activity_Profilis.this);
        vartotojas = dataBaseHelper.getUserFullbyID(LogIn);

        Toast.makeText(this, "Test: " + LogIn, Toast.LENGTH_SHORT).show();

        name = (TextView) findViewById(R.id.profilis_vardas);
        email = (TextView) findViewById(R.id.profilis_pastas);
        role = (TextView) findViewById(R.id.profilis_role);
        newName = (EditText) findViewById(R.id.profilis_newVardas);
        newPastas = (EditText) findViewById(R.id.profilis_newPastas);

        name.setText(vartotojas.get(0).getVardas());
        email.setText(vartotojas.get(0).getPastas());
        if(vartotojas.get(0).getRole() == 1){
            role.setText("Administratorius");
        } else {
            role.setText("Vartotojas");
        }

        newName.setText(vartotojas.get(0).getVardas());
        newPastas.setText(vartotojas.get(0).getPastas());

        btn_redaguoti = (Button) findViewById(R.id.btn_profilis_redaguoti);
        btn_redaguoti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newPass = (EditText) findViewById(R.id.profilis_newSlaptazodis);
                oldPass = (EditText) findViewById(R.id.profilis_oldSlaptazodis);

                if(newPass != null) {
                    if (vartotojas.get(0).getSlaptazodis().equals(oldPass.getText().toString())) {
                        dataBaseHelper = new DataBaseHelper(Activity_Profilis.this);

                        Vartotojas vartotojai;

                        vartotojai = new Vartotojas(vartotojas.get(0).getID(), newName.getText().toString(),
                                newPass.getText().toString(), newPastas.getText().toString(), vartotojas.get(0).getRole());

                        dataBaseHelper.updateUser(vartotojai);

                        Toast.makeText(Activity_Profilis.this, "Slaptažodis pakeistas", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(Activity_Profilis.this, "Neteisingas senas slaptažodis", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    dataBaseHelper = new DataBaseHelper(Activity_Profilis.this);

                    Vartotojas vartotojai;

                    vartotojai = new Vartotojas(vartotojas.get(0).getID(), newName.getText().toString(), vartotojas.get(0).getSlaptazodis(),
                            newPastas.getText().toString(), vartotojas.get(0).getRole());

                    dataBaseHelper.updateUser(vartotojai);

                    Toast.makeText(Activity_Profilis.this, "Duomenys atnaujinti", Toast.LENGTH_SHORT).show();
                }

            }

        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        dataBaseHelper = new DataBaseHelper(Activity_Profilis.this);
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
                DataBaseHelper dataBaseHelper = new DataBaseHelper(Activity_Profilis.this);
                List<Vartotojas> vartotojas = dataBaseHelper.getUserFullbyID(LogIn);
                if(vartotojas.get(0).getRole() == 1){
                    OpenActivity_PridetiRestorana();
                    return true;
                } else {
                    Toast.makeText(this, "Šiai paslaugai teisės neturite", Toast.LENGTH_SHORT).show();
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

    public void OpenActivity_PridetiRestorana(){
        Intent intent = new Intent(this, Activity_PridetiRestorana.class);
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

    public void OpenActivity_Administravimas(){
        Intent intent = new Intent(this, Activity_Administravimas.class);
        intent.putExtra(EXTRA_INT, LogIn);
        startActivity(intent);
    }
}
