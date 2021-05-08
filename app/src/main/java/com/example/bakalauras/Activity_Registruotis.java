package com.example.bakalauras;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Activity_Registruotis extends AppCompatActivity {
    Button button;
    EditText reg_vardas, reg_pass, reg_pastas;
    String vard;
    String past;
    String slapt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__registruotis);


        button = (Button) findViewById(R.id.button_registruotis);
        //reg_vardas = (EditText) findViewById(R.id.reg_vardas);
        //reg_pastas = (EditText) findViewById(R.id.reg_pastas);
        //reg_pass = (EditText) findViewById(R.id.reg_slaptazodis);

        button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                reg_vardas = (EditText) findViewById(R.id.reg_vardas);
                reg_pastas = (EditText) findViewById(R.id.reg_pastas);
                reg_pass = (EditText) findViewById(R.id.reg_slaptazodis);

                if(reg_pass.length() >= 8){
                    Vartotojas vartotojas;

                    try {
                        vartotojas = new Vartotojas(0 ,reg_vardas.getText().toString(), reg_pass.getText().toString() , reg_pastas.getText().toString(), 2);
                        Toast.makeText(Activity_Registruotis.this, vartotojas.toString(), Toast.LENGTH_SHORT).show();
                    }
                    catch (Exception e){
                        vartotojas = new Vartotojas(-1, "error", "pass" , "pastas", 0);
                        Toast.makeText(Activity_Registruotis.this, "Naujas vartotojas nesukurtas", Toast.LENGTH_SHORT).show();
                    }

                    DataBaseHelper dataBaseHelper = new DataBaseHelper(Activity_Registruotis.this);
                    boolean sucess = dataBaseHelper.addUser(vartotojas);
                    if(sucess == true){
                        Toast.makeText(Activity_Registruotis.this, "Vartotojas užgistruotas", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Activity_Registruotis.this, "Įvyko klaida", Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(Activity_Registruotis.this, "Slaptažodį turi sudaryti bent 8 simboliai", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }




}
