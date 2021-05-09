package com.example.bakalauras;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
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

public class VartotojaiAdapter extends RecyclerView.Adapter<VartotojaiAdapter.ViewHolder> {

    Context context;
    List<Vartotojas> vartotojas;
    String[] RestoranaiTitleList;
    String[] RestoranaiDescriptionList;
    Bitmap[] imagesList;
    RecyclerView recyclerView;
    int LogIn;
    public static final String EXTRA_VARTOTOJAS = "VARTOTOJAS";
    public static final String EXTRA_INT = "LogIn";
    public static final String EXTRA_TYPE = "TYPE";

    final View.OnClickListener onClickListener = new MyOnClickListener();

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {
        TextView rowName;
        TextView rowDescription;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rowName = itemView.findViewById(R.id.list_vardas);
            rowDescription = itemView.findViewById(R.id.list_pastas);
        }

        @Override
        public void onClick(View v) {
            showPopupMenu(v);
        }

        private void showPopupMenu(View view){
            PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
            popupMenu.inflate(R.menu.menu_restoranufunkcijos);
            popupMenu.setOnMenuItemClickListener(this);
            popupMenu.show();
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()){
                case R.id.menu_pridetiMarsrutuos:
                    Log.d(TAG, "onMenuItemClick: prideti Marsrutuos veikia ");
                    return true;
                default:
                    return false;
            }
        }


    }

    public VartotojaiAdapter(Context context, List<Vartotojas> vartotojas, RecyclerView recyclerView, int id) {
        this.context = context;
        this.vartotojas = vartotojas;
        this.recyclerView = recyclerView;
        this.LogIn = id;
    }

    @NonNull
    @Override
    public VartotojaiAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.single_item_vartotojas, parent, false);
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
    public void onBindViewHolder(@NonNull VartotojaiAdapter.ViewHolder holder, int position) {
        holder.rowName.setText(vartotojas.get(position).getVardas());
        holder.rowDescription.setText(vartotojas.get(position).getPastas());
    }

    @Override
    public int getItemCount() {
        return vartotojas.size();
    }

    private class MyOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int itemPosition = recyclerView.getChildLayoutPosition(v);
            //String item = restoranas.get(itemPosition).getPavadinimas();
            //Toast.makeText(context, item, Toast.LENGTH_SHORT).show();
            //OpenActivity_RestoranoInfo(itemPosition);
            OpenActivity_Profilis(itemPosition);
        }
    }

    public void OpenActivity_Profilis(int i){
        Intent intent = new Intent(context, Activity_Profilis.class);
        intent.putExtra(EXTRA_VARTOTOJAS, vartotojas.get(i).getID());
        intent.putExtra(EXTRA_INT, LogIn);
        intent.putExtra(EXTRA_TYPE, 1);
        context.startActivity(intent);
    }


}
