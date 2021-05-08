package com.example.bakalauras;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import static android.content.ContentValues.TAG;

public class RestoranaiAdapter extends RecyclerView.Adapter<RestoranaiAdapter.ViewHolder> {

    static Context context;
    static List<Restoranas> restoranas;
    String[] RestoranaiTitleList;
    String[] RestoranaiDescriptionList;
    Bitmap[] imagesList;
    RecyclerView recyclerView;
    static int LogIn, type;
    public static final String EXTRA_RESTORANAS = "RESTORANAS";
    public static final String EXTRA_INT = "LogIn";


    final View.OnClickListener onClickListener = new MyOnClickListener();

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {
        TextView rowName;
        TextView rowDescription;
        ImageView rowImage, menuImage;




        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rowName = itemView.findViewById(R.id.list_title);
            rowDescription = itemView.findViewById(R.id.list_description);
            rowImage = itemView.findViewById(R.id.imageView);
            menuImage = itemView.findViewById(R.id.single_item_image);
            menuImage.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            showPopupMenu(v);
        }

        private void showPopupMenu(View view){
            if (type == 1) {
                PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
                popupMenu.inflate(R.menu.menu_restoranufunkcijos);
                popupMenu.setOnMenuItemClickListener(this);
                popupMenu.show();
            } else {
                PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
                popupMenu.inflate(R.menu.menu_marsrut);
                popupMenu.setOnMenuItemClickListener(this);
                popupMenu.show();
            }
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            DataBaseHelper dataBaseHelper;
            switch (item.getItemId()){
                case R.id.menu_pridetiMarsrutuos:
                    Log.d(TAG, "onMenuItemClick: prideti Marsrutuos veikia ");
                    dataBaseHelper = new DataBaseHelper(context);
                    MarsrutoSarasas marsrutoSarasas = new MarsrutoSarasas(-1, LogIn , restoranas.get(getAdapterPosition()).getRestoranoID());
                    dataBaseHelper.addMarsrutoSarasas(marsrutoSarasas);
                    Toast.makeText(context, "Restoranas pridėtas į maršruto sąrašą", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.menu_isiminti:
                    Log.d(TAG, "onMenuItemClick: Įsiminti veikia ");
                    dataBaseHelper = new DataBaseHelper(context);
                    IsimintiSarasas isimintiSarasas = new IsimintiSarasas(-1, LogIn , restoranas.get(getAdapterPosition()).getRestoranoID());
                    dataBaseHelper.addIsimintiSarasas(isimintiSarasas);
                    Toast.makeText(context, "Restoranas Įsimintas", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.menu_panaikinti:
                    if (type == 2){
                        dataBaseHelper = new DataBaseHelper(context);
                        dataBaseHelper.deleteMarsrutas(restoranas.get(getAdapterPosition()).getRestoranoID(), LogIn);
                    } else if (type == 3) {
                        dataBaseHelper = new DataBaseHelper(context);
                        dataBaseHelper.deleteIsimintas(restoranas.get(getAdapterPosition()).getRestoranoID(), LogIn);
                    }
                    return true;
                default:
                    return false;
            }
        }

    }

    /* Senas paduodant ne list
    public RestoranaiAdapter(Context context, String[] RestoranaiTitleList, String[] RestoranaiDescriptionList, Bitmap[] imagesList){
        this.context = context;
        this.RestoranaiTitleList = RestoranaiTitleList;
        this.RestoranaiDescriptionList = RestoranaiDescriptionList;
        this.imagesList = imagesList;
    }*/

    public RestoranaiAdapter(Context context, List<Restoranas> restoranas, RecyclerView recyclerView, int id, int type) {
        this.context = context;
        this.restoranas = restoranas;
        this.recyclerView = recyclerView;
        this.LogIn = id;
        this.type = type;
    }

    @NonNull
    @Override
    public RestoranaiAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.single_item, parent, false);
        view.setOnClickListener(onClickListener);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    /* Paduodant ne List
    @Override
    public void onBindViewHolder(@NonNull RestoranaiAdapter.ViewHolder holder, int position) {
        holder.rowName.setText(RestoranaiTitleList[position]);
        holder.rowDescription.setText(RestoranaiDescriptionList[position]);
        holder.rowImage.setImageBitmap(imagesList[position]);
    }*/

    @Override
    public void onBindViewHolder(@NonNull RestoranaiAdapter.ViewHolder holder, int position) {
        holder.rowName.setText(restoranas.get(position).getPavadinimas());
        holder.rowDescription.setText(restoranas.get(position).getDarboLaikas());
        holder.rowImage.setImageBitmap(restoranas.get(position).getPaveiksliukas());
    }

    @Override
    public int getItemCount() {
        return restoranas.size();
    }

    private class MyOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int itemPosition = recyclerView.getChildLayoutPosition(v);
            //String item = restoranas.get(itemPosition).getPavadinimas();
            //Toast.makeText(context, item, Toast.LENGTH_SHORT).show();
            OpenActivity_RestoranoInfo(itemPosition);
        }
    }

    public void OpenDatabase(){
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
    }

    public void OpenActivity_RestoranoInfo(int i){
        Intent intent = new Intent(context, Activity_RestoranoInformacija.class);
        intent.putExtra(EXTRA_RESTORANAS, restoranas.get(i).getRestoranoID());
        intent.putExtra(EXTRA_INT, LogIn);
        context.startActivity(intent);
    }


}

