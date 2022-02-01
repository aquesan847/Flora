package org.izv.flora.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.izv.flora.R;
import org.izv.flora.model.entity.Flora;
import org.izv.flora.viewmodel.AddFloraViewModel;
import org.izv.flora.viewmodel.MainActivityViewModel;

public class AddFloraActivity extends AppCompatActivity {

    private EditText etNombre;
    private Button btAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_flora);
        initialize();
    }

    private void initialize() {
        AddFloraViewModel avm = new ViewModelProvider(this).get(AddFloraViewModel.class);
        avm.getAddFloraLiveData().observe(this, new Observer<Long>() {
            @Override
            public void onChanged(Long aLong) {
                if(aLong > 0) {
                    finish();
                } else {
                    Toast.makeText(AddFloraActivity.this, "Error", Toast.LENGTH_LONG).show();
                }
            }
        });
        etNombre = findViewById(R.id.etNombre);
        btAdd = findViewById(R.id.btAdd);
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Flora flora = new Flora();
                flora.setNombre(etNombre.getText().toString());
                avm.createFlora(flora);
            }
        });
    }
}