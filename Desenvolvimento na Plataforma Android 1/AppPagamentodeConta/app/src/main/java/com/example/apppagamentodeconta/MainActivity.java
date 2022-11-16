package com.example.apppagamentodeconta;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button btnTotal, btnPagar;
    private EditText edtValor;
    private TextView txtValor;
    private RadioGroup rdGrupo, cbGrupo;
    private RadioButton rdSemDesc, rd5, rd10, rd15;
    private CheckBox cbArroz, cbLeite, cbOvos, cbCarne, cbPao;

    double valor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnTotal = findViewById(R.id.btnTotal);
        btnPagar = findViewById(R.id.btnPagar);
        //EditTexts
        edtValor = findViewById(R.id.edtValor);
        //TextViews
        txtValor = findViewById(R.id.txtValor);
        //RadioGroups
        rdGrupo = findViewById(R.id.rdGrupo);
        cbGrupo = findViewById(R.id.cbGrupo);
        //RadioButtons
        rdSemDesc = findViewById(R.id.rdSemDesc);
        rd5 = findViewById(R.id.rd5);
        rd10 = findViewById(R.id.rd10);
        rd15 = findViewById(R.id.rd15);
        //CheckBoxes
        cbArroz = findViewById(R.id.cbArroz);
        cbLeite = findViewById(R.id.cbLeite);
        cbOvos = findViewById(R.id.cbOvos);
        cbCarne = findViewById(R.id.cbCarne);
        cbPao = findViewById(R.id.cbPao);

        btnTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valor = 0;
                if(cbArroz.isChecked())
                    valor += 3.5;
                if(cbLeite.isChecked())
                    valor += 5.5;
                if(cbCarne.isChecked())
                    valor += 12.3;
                if(cbPao.isChecked())
                    valor += 2.2;
                if(cbOvos.isChecked())
                    valor += 7.5;
                txtValor.setText("Valor: " + valor);
            }
        });

        btnPagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double valorDesc = 0, valorPago = 0, troco = 0;
                if(rdGrupo.getCheckedRadioButtonId() == R.id.rd5)
                    valorDesc = valor * 0.95;
                else if(rdGrupo.getCheckedRadioButtonId() == R.id.rd10)
                    valorDesc = valor * 0.90;
                else if(rdGrupo.getCheckedRadioButtonId() == R.id.rd15)
                    valorDesc = valor * 0.85;
                else valorDesc = valor;

                if(edtValor.getText().toString().isEmpty()) {
                    AlertDialog.Builder aviso2 = new AlertDialog.Builder(MainActivity.this);
                    aviso2.setTitle("Erro!");
                    aviso2.setMessage("Por favor, insira o valor pago pelo cliente.");
                    aviso2.setNeutralButton("Ok", null);
                    aviso2.show();
                }else {
                    valorPago = Double.parseDouble(edtValor.getText().toString());
                    troco = valorPago - valorDesc;
                    if (valorPago < valorDesc) {
                        AlertDialog.Builder aviso1 = new AlertDialog.Builder(MainActivity.this);
                        aviso1.setTitle("Atenção!");
                        aviso1.setMessage("Valor insuficiente para pagamento da compra!");
                        aviso1.setNeutralButton("Ok", null);
                        aviso1.show();
                    } else {
                        String saida = String.format("Valor total da compra: %5.2f" +
                                "\nValor com desconto: %5.2f" +
                                "\nValor pago: %5.2f" +
                                "\nTroco: %5.2f", valor, valorDesc, valorPago, troco);
                        AlertDialog.Builder paga = new AlertDialog.Builder(MainActivity.this);
                        paga.setTitle("Recibo da Compra");
                        paga.setMessage(saida);
                        paga.setNeutralButton("Ok", null);
                        paga.show();
                    }
                }
            }
        });
    }
}