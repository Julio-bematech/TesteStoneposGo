package br.com.bematech.posgo.testepagamentostone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import stone.application.interfaces.StoneCallbackInterface;
import stone.environment.Environment;
import stone.providers.ActiveApplicationProvider;
import stone.user.UserModel;
import stone.utils.Stone;

public class MainActivity extends AppCompatActivity {

    TextView tvNome;
    TextView tvCnpj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UserModel userModel = new UserModel();
        String code = userModel.getStoneCode();

        tvNome = findViewById(R.id.tvNome);
        tvNome.setText(userModel.getMerchantName());

        tvCnpj = findViewById(R.id.tvCnpj);
        tvCnpj.setText(userModel.getMerchantDocumentNumber());

        Toast.makeText(this,"Stone Code ativo: "+userModel.getStoneCode(),Toast.LENGTH_LONG).show();
    }

}
