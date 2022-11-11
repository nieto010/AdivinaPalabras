package com.example.examen;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Partida extends Activity implements Serializable {


    private String palabra;
    private String[] palabras = new String[] {"ANDROID", "JAVA", "WINDOWS", "AVE", "HOLA", "ADIOS"};
    private char[] caracterPalabra = new char[7];
    private int intentos = 5;
    private boolean finPartida = false;
    private EditText letra;
    private TextView palabraSinResolver;
    private Random generador = new Random();
    private ArrayList<String> posiciones = new ArrayList<String>();
    private Boolean[] posicionesBoolean;
    private TextView numIntentos;
    private boolean letraEncontrada = false;

    public void iniciarJuego() {
        palabraSinResolver = (TextView) findViewById(R.id.palabra);
        numIntentos = (TextView) findViewById(R.id.intentos);
        intentos = 5;
        numIntentos.setText("Intentos: " + intentos);
        palabra = palabras[generador.nextInt(palabras.length)];
        caracterPalabra = palabra.toCharArray();
        posicionesBoolean = new Boolean[caracterPalabra.length];
        palabraSinResolver.setText("");
        for (int i = 0; i < caracterPalabra.length; i++) {
            posiciones.add(i, " _");
            palabraSinResolver.append(posiciones.get(i));
        }
    }
    public void probarLetra(String letraAProbar) {
        palabraSinResolver = (TextView) findViewById(R.id.palabra);
        numIntentos = (TextView) findViewById(R.id.intentos);
        finPartida = false;
        letraEncontrada = false;

        if (!finPartida) {
            for (int i = 0; i < caracterPalabra.length; i++) {
                if (letraAProbar.equalsIgnoreCase(String.valueOf(caracterPalabra[i]))) {
                    posiciones.set(i, letraAProbar);
                    posicionesBoolean[i] = true;
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
                finPartida = true;
                Toast.makeText(this, "Has completado la palabra!", Toast.LENGTH_LONG).show();
            }
            if (intentos == 0) {
                finPartida = true;
                Toast.makeText(this, "Te has quedado sin intentos", Toast.LENGTH_LONG).show();
            }
        }

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
    public void leerArchivo(){
        String nombreArchivo = "palabras.txt";
        try{
            InputStreamReader archivo = new InputStreamReader(openFileInput(nombreArchivo));
            BufferedReader br = new BufferedReader(archivo);
            String linea = br.readLine();
            while (linea != null){
                //p.insertarPalabra(linea);
                linea = br.readLine();
            }
            br.close();
            archivo.close();
        } catch (FileNotFoundException e) {
            Toast.makeText(getApplicationContext(), "No existe el archivo", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getPalabra() {
        return palabra;
    }

    public void setPalabra(String palabra) {
        this.palabra = palabra;
    }

    public String[] getPalabras() {
        return palabras;
    }

    public void setPalabras(String[] palabras) {
        this.palabras = palabras;
    }

    public char[] getCaracterPalabra() {
        return caracterPalabra;
    }

    public void setCaracterPalabra(char[] caracterPalabra) {
        this.caracterPalabra = caracterPalabra;
    }

    public int getIntentos() {
        return intentos;
    }

    public void setIntentos(int intentos) {
        this.intentos = intentos;
    }

    public boolean isFinPartida() {
        return finPartida;
    }

    public void setFinPartida(boolean finPartida) {
        this.finPartida = finPartida;
    }

    public EditText getLetra() {
        return letra;
    }

    public void setLetra(EditText letra) {
        this.letra = letra;
    }

    public TextView getPalabraSinResolver() {
        return palabraSinResolver;
    }

    public void setPalabraSinResolver(TextView palabraSinResolver) {
        this.palabraSinResolver = palabraSinResolver;
    }

    public Random getGenerador() {
        return generador;
    }

    public void setGenerador(Random generador) {
        this.generador = generador;
    }

    public ArrayList<String> getPosiciones() {
        return posiciones;
    }

    public void setPosiciones(ArrayList<String> posiciones) {
        this.posiciones = posiciones;
    }

    public Boolean[] getPosicionesBoolean() {
        return posicionesBoolean;
    }

    public void setPosicionesBoolean(Boolean[] posicionesBoolean) {
        this.posicionesBoolean = posicionesBoolean;
    }

    public TextView getNumIntentos() {
        return numIntentos;
    }

    public void setNumIntentos(TextView numIntentos) {
        this.numIntentos = numIntentos;
    }

    public boolean isLetraEncontrada() {
        return letraEncontrada;
    }

    public void setLetraEncontrada(boolean letraEncontrada) {
        this.letraEncontrada = letraEncontrada;
    }
}
