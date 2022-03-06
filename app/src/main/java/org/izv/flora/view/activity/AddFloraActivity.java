package org.izv.flora.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.izv.flora.R;
import org.izv.flora.model.entity.Flora;
import org.izv.flora.viewmodel.AddFloraViewModel;

import java.util.ArrayList;

public class AddFloraActivity extends AppCompatActivity {

    private ArrayList<Flora> floras = new ArrayList<>();
    private AddFloraViewModel avm;
    private EditText etNombre, etFamilia, etIdentificacion,
            etAltitud, etHabitat, etFitosociologia,
            etBiotipo, etBioReproductiva, etFloracion,
            etFructificacion, etExpSexual, etPolinizacion,
            etDispersion, etNumCromosomatico, etRepAsexual,
            etDistribucion, etBiologia, etDemografia,
            etAmenazas, etMedPropuestas;

    private Button btAdd,btCancel;
    private Flora flora;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_flora);
        initialize();
    }

    private void initialize() {
        avm= new ViewModelProvider(this).get(AddFloraViewModel.class);
        avm.getAddFloraLiveData().observe(this, aLong -> {
            if(aLong>0){
                Toast.makeText(context, R.string.add, Toast.LENGTH_LONG).show();
                finish();
            }else {
                Toast.makeText(AddFloraActivity.this,"Error",Toast.LENGTH_LONG).show();
            }
        });

        initComponents();


    }
    private void initComponents(){
        context=this;
        etNombre=findViewById(R.id.etNombre);
        etFamilia = findViewById(R.id.etFamilia);
        etIdentificacion = findViewById(R.id.etIdentificacion);
        etAltitud = findViewById(R.id.etAltitud);
        etHabitat = findViewById(R.id.etHabitat);
        etFitosociologia = findViewById(R.id.etFitosociologia);
        etBiotipo = findViewById(R.id.etBiotipo);
        etBioReproductiva = findViewById(R.id.etBiologiaRepro);
        etFloracion = findViewById(R.id.etFloracion);
        etFructificacion = findViewById(R.id.etFructificacion);
        etExpSexual = findViewById(R.id.etExpresionSex);
        etPolinizacion = findViewById(R.id.etPolinizacion);
        etDispersion = findViewById(R.id.etDispersion);
        etNumCromosomatico = findViewById(R.id.etNumeroCromosoma);
        etRepAsexual = findViewById(R.id.etReproduccionAsex);
        etDistribucion = findViewById(R.id.etDistribucion);
        etBiologia = findViewById(R.id.etBiologia);
        etDemografia = findViewById(R.id.etDemografia);
        etAmenazas = findViewById(R.id.etAmenazas);
        etMedPropuestas = findViewById(R.id.etMedidasPropuestas);
        floras = (ArrayList<Flora>) getIntent().getSerializableExtra("idFloras");
        System.out.println(floras);
        buttons();



    }

    private void buttons(){
        btCancel=findViewById(R.id.btCancel);
        btCancel.setOnClickListener(view -> {
            new AlertDialog.Builder(context)
                    .setTitle("¿Volver a la página inicial?")
                    .setMessage("De esta manera perderás los datos introducidos")
                    .setPositiveButton("Aceptar", (dialog, which) -> {
                        finish();
                    })
                    .setNegativeButton("Cancelar", (dialog, which) -> {
                        dialog.cancel();
                    })
                    .show();
        });

        btAdd=findViewById(R.id.btAdd);
        btAdd.setOnClickListener(view -> {
            if (!etNombre.getText().toString().isEmpty()) {
                Boolean repitedFlora = false;
                for (Flora flora : floras) {
                    if ((getFlora().getNombre().equals(flora.getNombre()))) {
                        repitedFlora = true;
                    }
                }
                if (!repitedFlora) {
                    avm.createFlora(getFlora());

                }else{
                    Toast.makeText(context, "Ya existe una flora con ese nombre", Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(context, "Campo Nombre es obligatorio", Toast.LENGTH_LONG).show();
            }
        });

    }
    private Flora getFlora() {
        flora = new Flora();
        flora.setNombre(etNombre.getText().toString());
        flora.setFamilia(etFamilia.getText().toString());
        flora.setIdentificacion(etIdentificacion.getText().toString());
        flora.setAltitud(etAltitud.getText().toString());
        flora.setHabitat(etHabitat.getText().toString());
        flora.setFitosociologia(etFitosociologia.getText().toString());
        flora.setBiotipo(etBiotipo.getText().toString());
        flora.setBiologia_reproductiva(etBioReproductiva.getText().toString());
        flora.setFloracion(etFloracion.getText().toString());
        flora.setFructificacion(etFructificacion.getText().toString());
        flora.setExpresion_sexual(etExpSexual.getText().toString());
        flora.setPolinizacion(etPolinizacion.getText().toString());
        flora.setDispersion(etDispersion.getText().toString());
        flora.setNumero_cromosomatico(etNumCromosomatico.getText().toString());
        flora.setReproduccion_asexual(etRepAsexual.getText().toString());
        flora.setDistribucion(etDistribucion.getText().toString());
        flora.setBiologia(etBiologia.getText().toString());
        flora.setDemografia(etDemografia.getText().toString());
        flora.setAmenazas(etAmenazas.getText().toString());
        flora.setMedidas_propuestas(etMedPropuestas.getText().toString());
        return flora;
    }
}