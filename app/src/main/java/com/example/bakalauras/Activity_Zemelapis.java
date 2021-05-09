package com.example.bakalauras;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.bakalauras.directionhelpers.FetchURL;
import com.example.bakalauras.directionhelpers.TaskLoadedCallback;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

public class Activity_Zemelapis extends AppCompatActivity implements OnMapReadyCallback, TaskLoadedCallback {
    GoogleMap map;
    List<Restoranas> restoranai;
    private MarkerOptions place1, place2;
    public String Miestas = "";
    public String Tipas = "";
    public int Map_Tipas = 0;
    public int LogIn = -1;
    public int restoranoid;
    private Polyline currentPolyline;

    DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zemelapis);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        dataBaseHelper = new DataBaseHelper(Activity_Zemelapis.this);
        restoranai = dataBaseHelper.getRestoranasSingle(restoranoid);

        Intent intent = getIntent();
        Miestas = intent.getStringExtra(Activity_Restoranai.EXTRA_MIESTAS);
        Tipas = intent.getStringExtra(Activity_Restoranai.EXTRA_TIPAS);
        if (Map_Tipas == 0){
            Map_Tipas = intent.getIntExtra(Activity_Restoranai.EXTRA_ZEMELAPIOTIPAS, 0);
        }
        if (Map_Tipas == 0){
            Map_Tipas = intent.getIntExtra(Activity_RestoranoInformacija.EXTRA_ZEMELAPIOTIPAS, 0);
        }
        if (Map_Tipas == 0){
            Map_Tipas = intent.getIntExtra(Activity_MarsrutoSarasas.EXTRA_ZEMELAPIOTIPAS, 0);
        }
        LogIn = intent.getIntExtra(Activity_MarsrutoSarasas.EXTRA_INT, -1);

        restoranoid = intent.getIntExtra(Activity_RestoranoInformacija.EXTRA_RESTORANAS, 0);

/*
        Restoranas restoranas = new Restoranas();
        restoranas = new Restoranas(0, "Cili", "Vilnius", 54.710779, 25.262604,
                "Picerija", "Ozo g. 25, Vilnius 08217" , 860000001, "info@cili.lt", "8:00-19:00", "https://www.cili.lt", "Skaniausios ir pigios picos čia" );
        restoranai.add(restoranas);
        MarsSarasas.add(restoranas);
        Restoranas restoranas2 = new Restoranas();
        restoranas2.Prideti(1, "Can Can", "Vilnius", 54.713797, 25.273032,
                "Picerija", "Ozo g. 18, Vilnius 08243" , 860000002, "info@cancan.lt", "8:00-19:00", "https://www.cancan.lt", "Skaniausios ir pigios picos čia" );
        restoranai.add(restoranas2);
        MarsSarasas.add(restoranas2);
        Restoranas restoranas3 = new Restoranas();
        restoranas3.Prideti(2, "Grill London", "Vilnius", 54.718938, 25.284848,
                "Restoranas", "J. Balčikonio g. 3, Vilnius 08200" , 860000005, "info@grilllondon.lt", "9:00-23:00", "https://www.grilllondon.lt", "Grilintas maistas" );
        MarsSarasas.add(restoranas3);
        Restoranas restoranas4 = new Restoranas();
        restoranas4.Prideti(3, "Kauno pica", "Kaunas", 54.891602, 23.918543,
                "Picerija", "Karaliaus Mindaugo pr. 49, Kaunas 44333" , 860000003, "info@kaunopica.lt", "8:00-19:00", "https://www.kaunopica.lt", "Skaniausios kaunietiškos picos" );
        restoranai.add(restoranas4);
*/
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        Log.d("mylog", "Added Markers");
        //Toast.makeText(this, Map_Tipas + " " +Miestas + " " + Tipas, Toast.LENGTH_SHORT).show();
        if(Map_Tipas == 1){
            // 1 restoranas from info
            dataBaseHelper = new DataBaseHelper(Activity_Zemelapis.this);
            restoranai = dataBaseHelper.getRestoranasSingle(restoranoid);

            LatLng location = new LatLng(restoranai.get(0).Xcoord, restoranai.get(0).Ycoord);
            map.addMarker(new MarkerOptions().position(location).title(restoranai.get(0).getPavadinimas() + " " + restoranai.get(0).getDarboLaikas()));
            map.moveCamera(CameraUpdateFactory.newLatLng(location));
            moveToCurrentLocation(location);
        } else if (Map_Tipas == 2){
            // restoranu maršrutas, from marsrutu sąrašas
            dataBaseHelper = new DataBaseHelper(Activity_Zemelapis.this);
            restoranai = dataBaseHelper.getMarsrutas(LogIn);
            int nr = 0;
            for (int i = 1; i < restoranai.size(); i++){
                place1 = new MarkerOptions().position(new LatLng(restoranai.get(i-1).getXcoord(), restoranai.get(i-1).getYcoord())).title(restoranai.get(i-1).getPavadinimas());
                place2 = new MarkerOptions().position(new LatLng(restoranai.get(i).getXcoord(), restoranai.get(i).getYcoord())).title(restoranai.get(i).getPavadinimas());
                if(nr == 0) {
                    map.addMarker(place1);
                    map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(restoranai.get(i-1).getXcoord(), restoranai.get(i-1).getYcoord())));
                    nr++;
                    LatLng location = new LatLng(restoranai.get(i-1).getXcoord(), restoranai.get(i-1).getYcoord());
                    moveToCurrentLocation(location);
                }
                map.addMarker(place2);
                new FetchURL(Activity_Zemelapis.this).execute(getUrl(place1.getPosition(), place2.getPosition(), "walking"), "walking");
                }
        }
        else if (Map_Tipas == 3){
            //Atfiltruoti rodomi restoranai, from Restoranai
            dataBaseHelper = new DataBaseHelper(Activity_Zemelapis.this);
            restoranai = dataBaseHelper.getRestoranaiCityType(Miestas, Tipas);
            int nr = 0;
            for (int i = 0; i < restoranai.size(); i++) {
                LatLng location = new LatLng(restoranai.get(i).Xcoord, restoranai.get(i).Ycoord);
                map.addMarker(new MarkerOptions().position(location).title(restoranai.get(i).getPavadinimas() + "\n" + restoranai.get(i).getTipas() + "\n" + restoranai.get(i).getDarboLaikas()));
                if (nr == 0) {
                    nr = 1;
                    map.moveCamera(CameraUpdateFactory.newLatLng(location));
                    moveToCurrentLocation(location);
                }

            }

        }
    }


    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Mode
        String mode = "mode=" + directionMode;
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getString(R.string.map_key);
        return url;
    }

    @Override
    public void onTaskDone(Object... values) {
        if (currentPolyline != null)
            currentPolyline.remove();
        currentPolyline = map.addPolyline((PolylineOptions) values[0]);
    }

    private void moveToCurrentLocation(LatLng currentLocation)
    {
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation,15));
        // Zoom in, animating the camera.
        map.animateCamera(CameraUpdateFactory.zoomTo(13));
    }

}
