package com.example.interfazproyecto;

// Sketch.java


import controlP5.Button;
import controlP5.CallbackEvent;
import controlP5.CallbackListener;
import controlP5.ControlFont;
import controlP5.ControlP5;
import processing.core.PApplet;
import processing.core.PFont;


public class Sketch extends PApplet {
    MainActivity activity;
    //Interfaz************************************************

    ControlP5 cp5;

    String Cbutton;
    // Controladores de botones****
    boolean c_stop=false;
    //*****************************
    int celdasH;
    int celdasV;
    int SceldaH;
    int SceldaV;

    int BordeS;
    int BordeI;
    int BordeD;
    int TmatrizV;
    int TmatrizH;

    //*********************************************************
    float wc;
    float hc;

    // Tamaño de la matriz MxN
    int M=30;
    int N=30;


    // Numero de hormigas

    int NH=0;
    int StateA;
    int x0,y0,z0;
    int x1,y1,z1;
    float velocidad=200;
    String ParametrosH="\n";

    // Colores
    int blanco = color(255);
    int negro = color(0);
    int rojo = color(255,0,0);


    // Matriz de estados
    int[][] Mstate;
    // Arreglo de hormigas
    int[][] hormigas;
    // Reglas de direccion segun sea blanca o negra
    int[] dir;
    // Posicion de hormigas (x,y,z)
    int[] hormiga= new int[3];

    public Sketch(int NH, int[][] hormigas, int[] dir) {
        this.NH=NH;
        this.hormigas=hormigas;
        this.dir=dir;
    }

    public void settings() {
        size(width, 500);
        //fullScreen();
    }

    public void setup() {


        Mstate = new int[M][N];

        noStroke();
        // Configuracion de interfaz****************************************************************
        Cbutton="Default";
        celdasV= 14;
        celdasH=3;
        SceldaV = height/celdasV;
        SceldaH = width/celdasH;
        BordeD=30;
        BordeI=30;
        TmatrizV= 9*SceldaV-30;
        TmatrizH=width-BordeI-BordeD;
        BordeS= 3*SceldaV;


        cp5 = new ControlP5(this);

        PFont myFont1 = createFont("Georgia", 32);
        ControlFont TipoLetra = new ControlFont(myFont1);

        // Configuración de botones****************************




        Button Stop = cp5.addButton("JUGAR")
                .setColorBackground(color(98, 0, 238))
                .setValue(128)
                .setPosition(SceldaH,12*SceldaV)
                .setSize(SceldaH-5,SceldaV-5)
                .addCallback(new CallbackListener() {
                                 public void controlEvent(CallbackEvent theEvent) {
                                   if (theEvent.getAction()==ControlP5.ACTION_CLICK || theEvent.getAction()==ControlP5.ACTION_ENTER) {

                                         if(c_stop==false){
                                             c_stop=true;
                                         }else{
                                             c_stop=false;
                                         }
                                     }
                                 }
                             }
                )
                ;
        Stop.getCaptionLabel().setFont(TipoLetra);



        //******************************************************************************************
        background(128,128,128);
        frameRate(velocidad);

        wc=TmatrizH/M;
        hc=TmatrizV/N;

        // Matriz de estados: 1 celda blanca(Estado inicial), 2 celda negra(cuando es pisada una celda)
        for(int n=0; n < N; n = n+1){
            for(int m=0; m < M; m= m+1){
                Mstate[m][n]=1;
            }
        }
        // Asignación y dibujado de hormigas

        for(int x=0; x<NH; x=x+1){
            PintandoCeldaHormiga(hormigas[x][0],hormigas[x][1], hormigas[x][2],rojo);
        }

        // Dibujado de la cuadricula
        for (int n=0; n < N; n = n+1) {
            for(int m=0; m < M; m= m+1){
                PintandoCelda(m,n,blanco);
            }
        }
        drawTitle();
    }

    void PintandoCelda(int x, int y, int Color){
        noStroke();
        fill(Color);
        rect(x*wc+BordeI,y*hc+BordeS,wc,hc);
    }
    void PintandoCeldaHormiga(int x, int y,int z, int Color){
        noStroke();
        fill(160,50,0);
        int proporcionH=4;
        rect(x*wc+BordeI,y*hc+BordeS,wc,hc);
        fill(Color);
        if(z==0){
            rect(x*wc+BordeI,y*hc+(hc/proporcionH)+BordeS,wc,hc/proporcionH);
        }
        if(z==1){
            rect(x*wc+(2*wc/proporcionH)+BordeI,y*hc+BordeS,wc/proporcionH,hc);
        }
        if(z==2){
            rect(x*wc+BordeI,y*hc+(2*hc/proporcionH)+BordeS,wc,hc/proporcionH);
        }
        if(z==3){
            rect(x*wc+BordeI+(wc/proporcionH),y*hc+BordeS,wc/proporcionH,hc);
        }
    }
    public void drawTitle(){
        fill(128,128,128); // Color del fondo de la caja de texto
        rect(0,0,width,BordeS);
        fill(0);  // Define el color de la letra formato rgb
        textFont(createFont("Georgia", 32));
        textSize(50);    // tamaño de la letra
        textAlign(LEFT,CENTER); // Para alinear el texto en posicion horizontal y vertical respectivamente en la posicion que establece el siguiente comando
        ParametrosH= ParametrosH+" Langton Ant   ";

        text(ParametrosH, 5, SceldaV);
    }

    int[] DireccionH(int x, int y, int z,int dir){  // direcciones predeterminadas {0(arriba),1(derecha),2(abajo),3(izquierda)}
        int zr=0;
        int[] Punto = new int[3];

        zr= z+dir;   // Configurando ejes segun z*****
        if(zr>3){
            zr=zr-4;
        }
        if(zr==0){
            y=y-1;
        }
        if(zr==1){
            x=x+1;
        }
        if(zr==2){
            y=y+1;
        }
        if(zr==3){
            x=x-1;
        }
        Punto[0]=x;
        Punto[1]=y;
        Punto[2]=zr;
        return Punto;
    }
    public void draw() {
        if (c_stop == true ) {

// Paso de la hormiga, Guarda la posición del siguiente paso de la hormiga**************
            for (int x = 0; x < NH; x = x + 1) {
                x0 = hormigas[x][0];
                y0 = hormigas[x][1];
                z0 = hormigas[x][2];
                StateA = Mstate[x0][y0];

                if (StateA == 1) {                // Cuando la celda actual es blanca
                    hormiga = DireccionH(x0, y0, z0, dir[0]);
                } else {                        // Cuando la celda actual es negra
                    hormiga = DireccionH(x0, y0, z0, dir[1]);
                }
                x1 = hormiga[0];
                y1 = hormiga[1];
                z1 = hormiga[2];
//****************************************************************************************
                // Posiciones fuera de límite**************
                if (x1 >= M) {
                    x1 = 0;
                }
                if (x1 < 0) {
                    x1 = M - 1;
                }
                if (y1 >= N) {
                    y1 = 0;
                }
                if (y1 < 0) {
                    y1 = N - 1;
                }
                //*******************************************

                if (StateA == 1) {  // Cambia el estado actual de la celda
                    Mstate[x0][y0] = 2;
                    PintandoCelda(x0, y0, negro);
                } else {
                    Mstate[x0][y0] = 1;
                    PintandoCelda(x0, y0, blanco);
                }
                PintandoCeldaHormiga(x1, y1, z1, rojo);

                hormigas[x][0] = x1;
                hormigas[x][1] = y1;
                hormigas[x][2] = z1;
            }
        }
    }
}