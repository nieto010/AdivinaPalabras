package com.example.examen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


public class MostrarPalabrasActivity extends Activity {

    private ListView lista;
    private Partida partida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view);

        //recibirDatos();

        Intent i = getIntent();
        partida = (Partida) i.getSerializableExtra("partida");
        lista = findViewById(R.id.listView);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, android.R.id.text1, partida.getPalabras());

    }


    public void recibirDatos() {
        Bundle datos = getIntent().getExtras();
        String[] palabras = new String[6];
        palabras = datos.getStringArray("palabras");
        lista = findViewById(R.id.listView);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,android.R.id.text1, palabras);
        lista.setAdapter(adapter);
    }

    public void volver(View vista) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }



}