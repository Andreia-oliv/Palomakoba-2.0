package com.example.projetofinalcep.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.projetofinalcep.R;
import com.example.projetofinalcep.helper.EnderecoDAO;
import com.example.projetofinalcep.interfaces.CEPService;
import com.example.projetofinalcep.modelos.CEP;
import com.example.projetofinalcep.modelos.Endereco;
import com.example.projetofinalcep.modelos.MyReceiver;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private Button btnListar;
    private Button btnBuscar;
    private EditText cepResultado;
    private Retrofit retrofit;
    private BroadcastReceiver myReceiver;
    private SensorManager sensorManager;
    Sensor acelerometro;
    SensorEventListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnListar = findViewById(R.id.btnListar);
        btnBuscar = findViewById(R.id.btnBuscar);
        cepResultado = findViewById(R.id.edtCEP);

        //exemplo do uso de retrofrit
        String urlCep = "https://viacep.com.br/ws/";
        retrofit = new Retrofit.Builder()
                .baseUrl(urlCep)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //inicializa o broadcast da bateria
        myReceiver = new MyReceiver();

        //capta os eventos de sensores
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        acelerometro = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        listener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                if (sensorEvent.values[0] > 15) {
                    finish();
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {
            }
        };

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    recuperarCep();
            }
        });

        btnListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EnderecoActivity.class);
                startActivity(intent);
            }
        });
    }

    public void recuperarCep() {
        if(cepResultado.length() == 8){
        CEPService cepService = retrofit.create(CEPService.class);
        String cep = cepResultado.getText().toString();
        Call<CEP> call = cepService.consultarCEP(cep);
        call.enqueue(new Callback<CEP>() {
            @Override
            public void onResponse(Call<CEP> call, Response<CEP> response) {
                if (response.isSuccessful() && response.body().getCep() != null) {
                    CEP cep = response.body();
                    AlertDialog.Builder cepResult = new AlertDialog.Builder(MainActivity.this);
                    cepResult.setTitle("Informações do CEP: " + cep.getCep());
                    cepResult.setMessage(cep.getLogradouro() + " " + cep.getComplemento() + "\nBairro: " + cep.getBairro() + "\nCidade: " + cep.getLocalidade() + "\nEstado: " + cep.getUf());
                    cepResult.setNegativeButton("Fechar", null);
                    cepResult.setPositiveButton("Salvar Endereço?", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            CEP cep = response.body();
                            EnderecoDAO enderecoDAO = new EnderecoDAO(getApplicationContext());
                            String nomeCep = cep.getCep();
                            String rua = cep.getLogradouro();
                            String numero = cep.getComplemento();
                            String bairro = cep.getBairro();
                            String cidade = cep.getLocalidade();
                            String uf = cep.getUf();
                            Endereco endereco = new Endereco();
                            endereco.setCep(nomeCep);
                            endereco.setLogradouro(rua);
                            endereco.setComplemento(numero);
                            endereco.setBairro(bairro);
                            endereco.setCidade(cidade);
                            endereco.setUF(uf);
                            if (enderecoDAO.salvar(endereco)) {
                                Toast.makeText(getApplicationContext(), "Salvo com sucesso!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "Erro ao salvar endereco", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                    cepResult.show();
                } else {
                    Toast.makeText(MainActivity.this, "O CEP não existe", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CEP> call, Throwable t) {

            }
        });
        }
        else {
            Toast.makeText(this, "Digite um CEP válido", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.registerReceiver(this.myReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        sensorManager.registerListener(listener , acelerometro, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause(){
        super.onPause();
        sensorManager.unregisterListener(listener);
        this.unregisterReceiver(this.myReceiver);
    }

}