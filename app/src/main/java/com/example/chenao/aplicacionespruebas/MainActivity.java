package com.example.chenao.aplicacionespruebas;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;


public class MainActivity extends Activity {

    private TextView etiquetaColor,etiquetaCantidad1,etiquetaCantidad2,etiquetaCronometro,etiquetaBuena,etiquetaMala,etiquetaCompara;
    int cant=0, cont1=0,cont2=0;
    int hora=0,minuto=0,segundo=0;
    private Handler miHandler=new Handler();
    private Handler miHandler2=new Handler();
    private String reloj="";
    private String arregloNombres[]={"Amarillo","Azul","Rojo","Verde","Negro"};
    private int arregloColores[]={Color.YELLOW,Color.BLUE,Color.RED,Color.GREEN,Color.BLACK};
    int aleatorio=0;
    int aleatorio2=0;
    int puntaje=0;
    int buenas=0;
    int malas=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etiquetaColor=(TextView) findViewById(R.id.etiColorAleatorio);
        etiquetaCantidad1=(TextView) findViewById(R.id.etiCantidad1);
        etiquetaCronometro=(TextView) findViewById(R.id.etiCronometro);
        etiquetaBuena=(TextView) findViewById(R.id.etiBuena);
        etiquetaMala=(TextView) findViewById(R.id.etiMala);
        etiquetaCompara=(TextView) findViewById(R.id.etiCompara);
/*
    inicializar un hilo solo una vez, por ejemplo para un splash
        miHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                cont2++;
                Toast.makeText(getApplicationContext(),"Hay un hilo Corriendo: "+cont2,Toast.LENGTH_SHORT).show();
                etiquetaCantidad1.setText("CANTIDAD1: " + cont2);
            }
        },5000);
*/      etiquetaCantidad1.setText("Puntos: " + puntaje);

        Random r=new Random();//permite la generacion de numeros aleatorios
        aleatorio=r.nextInt(5);
        aleatorio2=r.nextInt(5);
        etiquetaColor.setText(arregloNombres[aleatorio]);
        etiquetaColor.setTextColor(arregloColores[aleatorio2]);

        if (aleatorio==aleatorio2){
            etiquetaCompara.setText("SON IGUALES: "+aleatorio+" == "+aleatorio2);
        }else{
            etiquetaCompara.setText("SON DIFERENTES: "+aleatorio+" != "+aleatorio2);
        }

        iniciarHiloCronometro();
        iniciarHilo();

    }

    private void iniciarHiloCronometro() {
        final Thread miHilo2=new Thread(){
            public void run(){
                try {
                    while (true){
                        Thread.sleep(1000);
                        ejecutarHiloCronometro();
                    }
                }catch(Exception e){
                    System.out.println("Error: " + cont1);
                    System.out.println(e.getMessage());
                    // e.printStackTrace();
                }
            }


        };
        miHilo2.start();
    }

    private void ejecutarHiloCronometro() {

        miHandler2.post(new Runnable() {
            @Override
            public void run() {
                segundo++;
                if (segundo > 59) {
                    segundo = 0;
                    minuto++;
                    if (minuto > 59) {
                        minuto = 0;
                        hora++;
                    }
                }
                String textSeg="",textMin="",textHora="";
                if (segundo<10){
                    textSeg="0"+segundo;
                }else{
                    textSeg=""+segundo;
                }
                if (minuto<10){
                    textMin="0"+minuto;
                }else{
                    textMin=""+minuto;
                }
                if (hora<10){
                    textHora="0"+hora;
                }else{
                    textHora=""+hora;
                }

                reloj = textHora + ":" + textMin + ":" + textSeg;
//                Toast.makeText(getApplicationContext(), "Hilo Ejecutandose: " + cont2, Toast.LENGTH_SHORT).show();
                etiquetaCronometro.setText(reloj);
            }
        });
    }

    private void iniciarHilo() {
        final Thread miHilo=new Thread(){
            public void run(){
                try {

                    while (true){
                        Thread.sleep(3000);
                        cont1++;
                        System.out.println("ingresa: " + cont1);
                        //etiquetaCantidad1.setText("CANTIDAD1: " + cont1);
                        //miHandler.post(miRun);
                        ejecutarHilo();
                    }
                }catch(Exception e){
                    System.out.println("Error: " + cont1);
                    System.out.println(e.getMessage());
                   // e.printStackTrace();
                }
            }


        };
        miHilo.start();
    }

    private void ejecutarHilo() {

        miHandler.post(new Runnable() {
            @Override
            public void run() {
                //   Toast.makeText(getApplicationContext(), "Hilo corrienedo: " + cont2, Toast.LENGTH_SHORT).show();
                validaColores(aleatorio, aleatorio2, 3);
            }


        });
    }

    public void metodoBien(View view) {
        validaColores(aleatorio, aleatorio2, 1);
    }

    public void metodoMal(View view) {
        validaColores(aleatorio, aleatorio2, 2);
    }

     private void validaColores(int aleatorio1,int aleatorio2,int evento) {
        if (evento==3)
        {
            puntaje-=10;
            malas++;
        }else{
            if (evento==1){
                if (aleatorio1==aleatorio2){
                    System.out.println("BIEN: " + aleatorio1 + " Igual " + aleatorio2);
                    puntaje+=10;
                    buenas++;
                }else{
                    System.out.println("BIEN: " + aleatorio1 + " Diferente " + aleatorio2);
                    puntaje-=10;
                    malas++;
                }
            }else{
                if (evento==2){
                    if (aleatorio1!=aleatorio2){
                        System.out.println("MAL: " + aleatorio1 + " Diferente " + aleatorio2);
                        puntaje+=10;
                        buenas++;
                    }else{
                        System.out.println("MAL: " + aleatorio1 + " Iguales " + aleatorio2);
                        puntaje-=10;
                        malas++;
                    }
                }
            }
        }

        etiquetaCantidad1.setText("Puntos: " + puntaje);
        etiquetaBuena.setText(buenas+"");
        etiquetaMala.setText(malas + "");

        cambiaColor();
    }

    private void cambiaColor() {
        Random r=new Random();//permite la generacion de numeros aleatorios
        aleatorio=r.nextInt(5);
        aleatorio2=r.nextInt(5);

        if (aleatorio==aleatorio2){
            etiquetaCompara.setText("SON IGUALES: "+aleatorio+" == "+aleatorio2);
        }else{
            etiquetaCompara.setText("SON DIFERENTES: "+aleatorio+" != "+aleatorio2);
        }

        etiquetaColor.setText(arregloNombres[aleatorio]);
        etiquetaColor.setTextColor(arregloColores[aleatorio2]);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
