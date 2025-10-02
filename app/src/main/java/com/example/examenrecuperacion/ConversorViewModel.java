package com.example.examenrecuperacion;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.Locale;

public class ConversorViewModel extends ViewModel {

    private final MutableLiveData<String> resultadoConversion = new MutableLiveData<>("");
    private final MutableLiveData<Boolean> esDolarHabilitado = new MutableLiveData<>(true);
    private final MutableLiveData<Boolean> esEuroHabilitado = new MutableLiveData<>(false);


    private MutableLiveData<String> mensajeToast;

    private final MutableLiveData<Void> limpiarCampoDolares = new MutableLiveData<>();
    private final MutableLiveData<Void> limpiarCampoEuros = new MutableLiveData<>();
    private final MutableLiveData<Void> solicitarFocoDolares = new MutableLiveData<>();
    private final MutableLiveData<Void> solicitarFocoEuros = new MutableLiveData<>();

    private static final double TASA_USD_EUR = 0.93;


    public LiveData<String> getResultadoConversion() { return resultadoConversion; }
    public LiveData<Boolean> getEsDolarHabilitado() { return esDolarHabilitado; }
    public LiveData<Boolean> getEsEuroHabilitado() { return esEuroHabilitado; }
    public LiveData<Void> getLimpiarCampoDolares() { return limpiarCampoDolares; }
    public LiveData<Void> getLimpiarCampoEuros() { return limpiarCampoEuros; }
    public LiveData<Void> getSolicitarFocoDolares() { return solicitarFocoDolares; }
    public LiveData<Void> getSolicitarFocoEuros() { return solicitarFocoEuros; }


    public LiveData<String> getMensajeToast() {
        if (mensajeToast == null) {

            mensajeToast = new MutableLiveData<>("");
        }
        return mensajeToast;
    }

    public void onRadioButtonChanged(int checkedId) {
        boolean esDolarAEuro = checkedId == R.id.rbDolarEuro;
        setTipoConversion(esDolarAEuro);
    }

    private void setTipoConversion(boolean esDolarAEuro) {
        esDolarHabilitado.setValue(esDolarAEuro);
        esEuroHabilitado.setValue(!esDolarAEuro);
        resultadoConversion.setValue("");

        getMensajeToast();
        mensajeToast.setValue("");


        if (esDolarAEuro) {

            limpiarCampoEuros.setValue(null);
            solicitarFocoDolares.setValue(null);
        } else {

            limpiarCampoDolares.setValue(null);
            solicitarFocoEuros.setValue(null);
        }

    }

    public void convertir(String valorDolar, String valorEuro) {
        Boolean esDolarAEuro = esDolarHabilitado.getValue();
        if (esDolarAEuro == null) return;

        getMensajeToast();

        String valorAConvertirStr = esDolarAEuro ? valorDolar : valorEuro;

        if (valorAConvertirStr == null || valorAConvertirStr.trim().isEmpty()) {
            mensajeToast.setValue("Por favor, ingrese un valor para convertir.");
            return;
        }

        try {
            double valorAConvertir = Double.parseDouble(valorAConvertirStr);
            double resultado;
            String resultadoFormateado;

            if (esDolarAEuro) {
                resultado = valorAConvertir * TASA_USD_EUR;
                resultadoFormateado = String.format(Locale.getDefault(), "%.2f USD son %.2f EUR", valorAConvertir, resultado);
            } else {
                resultado = valorAConvertir / TASA_USD_EUR;
                resultadoFormateado = String.format(Locale.getDefault(), "%.2f EUR son %.2f USD", valorAConvertir, resultado);
            }
            resultadoConversion.setValue(resultadoFormateado);
            mensajeToast.setValue("");

        } catch (NumberFormatException e) {
            mensajeToast.setValue("El valor ingresado no es un número válido.");
        }
    }
}
