package com.example.examen;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private String palabra;
    private final ArrayList<String> palabras = new ArrayList<String>(6) {{
        add("ANDROID");
        add("JAVA");
        add("WINDOWS");
        add("AVE");
        add("HOLD");
        add("ADIOS");
    }};
    private char[] caracterPalabra = new char[20];
    private int intentos = 5;
    private TextView palabraSinResolver;
    private final Random generador = new Random();
    private final ArrayList<String> posiciones = new ArrayList<>();
    private TextView numIntentos;
    private final Partida p = new Partida();
    private EditText nuevaPalabra;
    private TextView palabrasDisponibles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iniciarJuego();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.verPalabra:
                verPalabraActual();
                return true;
            case R.id.agregarPalabra:
                crearPalabranueva();
                return true;
            case R.id.mostrarPalabras:
                mostrarPalabras();
                return true;
            case R.id.importarPalabras:
                leerArchivo();
                return true;
            case R.id.exportarPalabras:
                escribirArchivo();
                return true;
            case R.id.salir:
                salir();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void salir() {
        finish();
    }


    private void verPalabraActual() {
        Toast.makeText(this,"La palabra es: " + palabra,Toast.LENGTH_LONG).show();
    }
    @SuppressLint("SetTextI18n")
    private void crearPalabranueva() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Clear nueva Palabra")
                .setMessage("Mensaje")
                .setView(nuevaPalabra = new EditText(this))
                .setPositiveButton("Aceptar", (dialog, which) -> {
                    palabras.add(nuevaPalabra.getText().toString());
                    palabrasDisponibles.setText("Palabras Disponibles: " + palabras.size());
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void mostrarPalabras() {
        Intent i = new Intent(this,MostrarPalabrasActivity.class);
        i.putExtra("palabra", p);

        startActivity(i);
    }
    @SuppressLint("SetTextI18n")
    public void iniciarJuego() {
        palabraSinResolver = findViewById(R.id.palabra);
        numIntentos = findViewById(R.id.intentos);
        palabrasDisponibles = findViewById(R.id.palabrasDisponibles);
        intentos = 5;
        numIntentos.setText("Intentos: " + intentos);
        palabrasDisponibles.setText("Palabras disponibles: " + palabras.size());
        palabra = palabras.get(generador.nextInt(palabras.size()));
        caracterPalabra = palabra.toCharArray();
        palabraSinResolver.setText("");
        for (int i = 0; i < caracterPalabra.length; i++) {
            posiciones.add(i, " _");
            palabraSinResolver.append(posiciones.get(i));
        }
    }

    public void obtenerLetra(View vista) {
        EditText letra = findViewById(R.id.letra);
        String letraAProbar = letra.getText().toString().toUpperCase();
        probarLetra(letraAProbar);
        letra.setText("");
    }

    @SuppressLint("SetTextI18n")
    public void probarLetra(String letraAProbar) {
        palabraSinResolver = findViewById(R.id.palabra);
        numIntentos = findViewById(R.id.intentos);
        boolean letraEncontrada = false;

        if (intentos > 0) {
            for (int i = 0; i < caracterPalabra.length; i++) {
                if (letraAProbar.equalsIgnoreCase(String.valueOf(caracterPalabra[i]))) {
                    posiciones.set(i, letraAProbar);
                    letraEncontrada = true;
                    palabraSinResolver.setText("");
                }
            }
            if (letraEncontrada) {
                for (int i = 0; i < posiciones.size(); i++) {
                    palabraSinResolver.append(posiciones.get(i));
                }
            } else {
                intentos--;
            }
            numIntentos.setText("Intentos: " + intentos);
            if (!posiciones.contains(" _")) {
                Toast.makeText(this, "Has completado la palabra!", Toast.LENGTH_LONG).show();
            }
            if (intentos == 0) {
                Toast.makeText(this, "Te has quedado sin intentos", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void iniciarNuevoJuego(View vista) {
        iniciarJuego();
    }

    public void escribirArchivo() {
        String nombreArchivo = "palabras.txt";
        try {
            OutputStreamWriter archivo = new OutputStreamWriter(openFileOutput(nombreArchivo, Context.MODE_PRIVATE));
            archivo.write("codigo\n");
            archivo.write("android\n");
            archivo.write("programacion\n");
            archivo.flush();
            archivo.close();
            Toast.makeText(getApplicationContext(), "Datos guardados", Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            Toast.makeText(getApplicationContext(), "No se pudo crear el archivo", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @SuppressLint("SetTextI18n")
    public void leerArchivo(){
        String nombreArchivo = "palabras.txt";
        try{
            InputStreamReader archivo = new InputStreamReader(openFileInput(nombreArchivo));
            BufferedReader br = new BufferedReader(archivo);
            String linea = br.readLine();
            while (linea != null){
                for (int i = 0; i < palabras.size(); i++) {
                        linea = br.readLine();
                    if (!palabras.get(i).equalsIgnoreCase(linea) ) {
                        linea = br.readLine();
                        palabras.add(linea);
                    }
                }
                palabrasDisponibles.setText("Palabras disponibles: " + palabras.size());
            }
            br.close();
            archivo.close();
        } catch (FileNotFoundException e) {
            Toast.makeText(getApplicationContext(), "No exist el archivo", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}