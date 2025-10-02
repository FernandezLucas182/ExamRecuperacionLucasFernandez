package com.example.examenrecuperacion;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.example.examenrecuperacion.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ConversorViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(ConversorViewModel.class);

        setupObservers();
        setupListeners();
    }

    private void setupObservers() {
        viewModel.getResultadoConversion().observe(this, resultado -> {
            binding.tvResultado.setText(resultado);
        });

        viewModel.getEsDolarHabilitado().observe(this, habilitado -> {
            binding.etDolares.setEnabled(habilitado);
        });

        viewModel.getEsEuroHabilitado().observe(this, habilitado -> {
            binding.etEuros.setEnabled(habilitado);
        });

        viewModel.getLimpiarCampoDolares().observe(this, aVoid -> {
            binding.etDolares.setText("");
        });

        viewModel.getLimpiarCampoEuros().observe(this, aVoid -> {
            binding.etEuros.setText("");
        });

        viewModel.getSolicitarFocoDolares().observe(this, aVoid -> {
            binding.etDolares.requestFocus();
        });

        viewModel.getSolicitarFocoEuros().observe(this, aVoid -> {
            binding.etEuros.requestFocus();
        });


        viewModel.getMensajeToast().observe(this, mensaje -> {
            Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
        });
    }

    private void setupListeners() {
        binding.btnConvertir.setOnClickListener(v -> {
            String valorDolares = binding.etDolares.getText().toString();
            String valorEuros = binding.etEuros.getText().toString();
            viewModel.convertir(valorDolares, valorEuros);
        });

        binding.rgConversion.setOnCheckedChangeListener((group, checkedId) -> {
            viewModel.onRadioButtonChanged(checkedId);
        });
    }
}
