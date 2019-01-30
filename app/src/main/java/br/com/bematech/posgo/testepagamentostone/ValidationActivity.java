package br.com.bematech.posgo.testepagamentostone;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import stone.application.StoneStart;
import stone.application.SessionApplication;
import stone.application.interfaces.StoneCallbackInterface;
import stone.environment.Environment;
import stone.providers.ActiveApplicationProvider;
import stone.user.UserModel;
import stone.utils.Stone;

import static stone.environment.Environment.SANDBOX;

public class ValidationActivity extends AppCompatActivity {

    private static final String TAG = "ValidationActivity";
    EditText etCode;
    Button btActive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validation);

        etCode = findViewById(R.id.etStoneCode);
        btActive = findViewById(R.id.btAtivarStoneCode);
        btActive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Este deve ser, obrigatoriamente, o primeiro método a ser chamado
                List<UserModel> userList = StoneStart.init(ValidationActivity.this);

                Stone.setEnvironment(Environment.SANDBOX);

                // Se retornar nulo, o SDK não possui nenhum Stone Code ativo
                if (userList == null) {
                    final ActiveApplicationProvider activeApplicationProvider = new ActiveApplicationProvider(ValidationActivity.this);
                    activeApplicationProvider.setDialogMessage("Ativando o aplicativo");
                    activeApplicationProvider.setDialogTitle("Aguarde");
                    activeApplicationProvider.useDefaultUI(true);
                    activeApplicationProvider.setConnectionCallback(new StoneCallbackInterface() {

                        // Método chamado se for executado sem erros
                        public void onSuccess() {
                            Toast.makeText(getApplicationContext(), "Ativado com sucesso, iniciando o aplicativo", Toast.LENGTH_SHORT).show();
                        }

                        // Método chamado caso ocorra alguma exceção
                        public void onError() {
                            Toast.makeText(getApplicationContext(), "Erro na ativação do aplicativo, verifique a lista de erros do provider", Toast.LENGTH_SHORT).show();
                            // Chame o método abaixo para verificar a lista de erros
                            activeApplicationProvider.getListOfErrors();
                        }
                    });
                    activeApplicationProvider.activate(etCode.getText().toString());
                } else {
                    // Caso já tenha as informações do SDK sua aplicação poderá seguir o fluxo normal.
                    continueApplication();
                }
            }
        });
        //}
    }
    private void continueApplication() {
        Intent mainIntent = new Intent(ValidationActivity.this, MainActivity.class);
        startActivity(mainIntent);
        finish();
    }
}
