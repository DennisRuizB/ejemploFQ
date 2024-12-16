package com.example.restclientservweb;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.io.Console;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DenunciarActivity extends AppCompatActivity {
    private ApiService apiService;
    private ListView listViewProducts;
    private ListView listViewComprados;
    private TextView usernameDisplay;
    private TextView dineroDisplay;
    private String username;
    private String idUser;
    private int dinero;

    private String informer;
    private String message;
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_denunciar);

        EditText editTextInformer = findViewById(R.id.editTextName);
        EditText editTextMessage = findViewById(R.id.editTextDescription);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/dsaApp/") // Cambiado a 10.0.2.2 para el emulador
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);

//        username = getIntent().getStringExtra("username");
//        idUser = getIntent().getStringExtra("idUser");

        Button buttonBackToMain = findViewById(R.id.buttonGoToMainMenu);
        buttonBackToMain.setOnClickListener(v -> {
            Intent intent = new Intent(DenunciarActivity.this, MenuActivity.class);
            startActivity(intent);
        });

        Button buttonDenunciar = findViewById(R.id.buttonSubmitReport);
        buttonDenunciar.setOnClickListener(v -> {
            informer = editTextInformer.getText().toString();
            message = editTextMessage.getText().toString();
            date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            Issue issue = new Issue(date, informer, message);
            System.out.println(issue.getDate());
            System.out.println(issue.getInformer());
            System.out.println(issue.getMessage());
            enviarIssue(issue);
        });



    }
    private void enviarIssue(Issue issue) {
        Call<Issue> call = apiService.addIssue(issue);

        call.enqueue(new Callback<Issue>() {
            @Override
            public void onResponse(Call<Issue> call, Response<Issue> response) {
                // Manejar la respuesta exitosa aquí
                Toast.makeText(DenunciarActivity.this, "Denuncia enviada", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Issue> call, Throwable t) {
                Toast.makeText(DenunciarActivity.this, "Denuncia enviada", Toast.LENGTH_SHORT).show();
                // Manejar el error aquí
            }
        });
    }
}
