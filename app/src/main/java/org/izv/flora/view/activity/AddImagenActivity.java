package org.izv.flora.view.activity;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.izv.flora.R;
import org.izv.flora.model.entity.Flora;
import org.izv.flora.model.entity.Imagen;
import org.izv.flora.viewmodel.AddFloraViewModel;
import org.izv.flora.viewmodel.AddImagenViewModel;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

public class AddImagenActivity extends AppCompatActivity {

    private ActivityResultLauncher<Intent> launcher;
    private Intent resultadoImagen = null;
    private EditText etNombre, etDescripcion, etIdFlora;
    private AddImagenViewModel aivm;
    private Spinner spinnerIdFlora;
    private ImageView imageView;
    private Uri uri;
    private FloatingActionButton btSelectImage;
    private Button btChangeImg;
    private ArrayList<Flora> arrayFlora;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_imagen);
        arrayFlora= (ArrayList<Flora>) getIntent().getSerializableExtra("arrayFlora");
        initialize();
    }

    private void initialize() {
        launcher = getLauncher();
        etDescripcion = findViewById(R.id.etDescripcion);
        etNombre = findViewById(R.id.etNombreImagen);
        spinnerIdFlora =findViewById(R.id.spinnerFloraId);
        fillSpinner();
        imageView = findViewById(R.id.imageView);
        btChangeImg = findViewById(R.id.btChangeImg);
        btSelectImage = findViewById(R.id.btSelectImage);
        btSelectImage.setOnClickListener(v -> {
            selectImage();
        });
        Button btAddImagen = findViewById(R.id.btAddImagen);
        btAddImagen.setOnClickListener(v -> {
            uploadDataImage();
        });
        Button btCancelImg = findViewById(R.id.btCancelImg);
        btCancelImg.setOnClickListener(view -> finish());
        aivm = new ViewModelProvider(this).get(AddImagenViewModel.class);
    }

    private void uploadDataImage() {

        long id = 0;
        String nombre = "";
        for (int i = 0; i < arrayFlora.size(); i++) {
            if (spinnerIdFlora.getSelectedItem().toString().equals(arrayFlora.get(i).getNombre())) {
                id = arrayFlora.get(i).getId();
            }
        }

        Random seed = new Random(30);
        Integer numero=-seed.nextInt();
        String idFlora = String.valueOf(id);
        nombre = etNombre.getText().toString() + "_" +numero;
        String descripcion =etDescripcion.getText().toString();
        if (!(nombre.trim().isEmpty() || idFlora.trim().isEmpty() || resultadoImagen == null)) {
            Imagen imagen = new Imagen();
            imagen.nombre = nombre;
            imagen.descripcion = descripcion;
            imagen.idflora = Long.parseLong(idFlora);
            aivm.saveImagen(resultadoImagen, imagen);
            Log.v("xyzyx", imagen.toString());
            finish();
        } else {
        }
    }

    ActivityResultLauncher<Intent> getLauncher() {
        return registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    //respuesta al resultado de haber seleccionado una imagen
                    if(result.getResultCode() == Activity.RESULT_OK) {
                        //copyData(result.getData());
                        resultadoImagen = result.getData();
                        uri = resultadoImagen.getData();
                        Glide.with(getApplicationContext())
                                .load(uri) // Uri of the picture
                                .into(imageView);
                        btSelectImage.setVisibility(View.GONE);
                        btChangeImg.setVisibility(View.VISIBLE);
                        btChangeImg.setOnClickListener(v -> {
                            selectImage();
                        });
                    }
                }
        );
    }

    Intent getContentIntent() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        return intent;
    }

    void selectImage() {
        Intent intent = getContentIntent();
        launcher.launch(intent);
    }

    void fillSpinner(){

        String[] selected = new String[arrayFlora.size()];
        for (int i = 0; i < arrayFlora.size(); i++) {
            selected[i] = arrayFlora.get(i).getNombre();
        }
        ArrayAdapter<String> content = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, selected);
        spinnerIdFlora.setAdapter(content);
    }
}