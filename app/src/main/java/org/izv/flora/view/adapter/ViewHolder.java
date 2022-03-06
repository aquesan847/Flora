package org.izv.flora.view.adapter;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.izv.flora.R;

public class ViewHolder extends RecyclerView.ViewHolder {

    public ImageView ivFlora;
    public EditText etNombre, etFamilia;

    public ViewHolder(@NonNull View item) {
        super(item);
        ivFlora = item.findViewById(R.id.ivFloraItem);
        etNombre = item.findViewById(R.id.etNombreItem);
        etFamilia = item.findViewById(R.id.etFamiliaItem);
    }
}

