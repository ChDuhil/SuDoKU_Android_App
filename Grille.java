package com.ubo.christophe.sudoku;

/**
 * Created by christophe on 05/10/16.
 *
 */

// package fr.ubo.smartsudoku;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import static android.icu.text.DisplayContext.LENGTH_SHORT;

public class Grille extends View {

    private int screenWidth;
    private int screenHeight;
    private int n;
    private boolean drawResult = false;

    private Paint paint1;
    private Paint paint2;
    private Paint paint3;

    private   int[][] matrix = new int[9][9];
    private  boolean[][] fixIdx = new boolean[9][9];

    public Grille(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public Grille(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Grille(Context context) {
        super(context);
        init();
    }

    private void init() {
        set("000105000140000670080002400063070010900000003010090520007200080026000035000409000");
        // set("672145398145983672389762451263574819958621743714398526597236184426817935831459267");
        // set("123456789912345678891234567789123456678912345567891234456789123345678912234567891");
        // set("000400870", 0);
        // set("047092050", 1);
        // set("200600030", 2);
        // set("970500203", 3);
        // set("508024706", 4);
        // set("604007085", 5);
        // set("090308007", 6);
        // set("003240160", 7);
        // set("012000090", 8);

        paint1 = new Paint();
        paint1.setAntiAlias(true);
        paint1.setColor(Color.BLACK);
        paint2 = new Paint();
        paint2.setAntiAlias(true);
        paint2.setColor(Color.RED);
        paint2.setStrokeWidth(5);
        paint3 = new Paint();
        paint3.setAntiAlias(true);
        paint3.setStrokeWidth(10);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        screenWidth = getWidth();
        screenHeight = getHeight();
        int x = Math.min(screenWidth, screenHeight);
        n = (x / 9) - (1 - (x % 2));
        paint1.setTextSize(x/12);
        paint2.setTextSize(x/12);





        // Dessiner les lignes noir
        for (int i  =0;  i<x ;i+=n) {
            canvas.drawLine(i, 0, i, 9 * n, paint1);
            //System.out.println("coordonnée i noir" + i);
        }
        for (int i = 0; i<x; i+=n){
            canvas.drawLine(0,i,9*n,i,paint1);
        }

        // Dessiner les lignes rouges

        for (int i  =3*n;  i<9*n; i+=(3*n)){
            canvas.drawLine(i,0,i,9*n, paint2);

        }

        for (int i = 3*n; i<9*n; i+=(3*n)){
            canvas.drawLine(0,i,9*n,i,paint2);

        }
        // Le contenu d'une case
        String s;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                s = "" + (matrix[j][i] == 0 ? "" : matrix[j][i]);
                if (fixIdx[j][i])
                    // j'ai redéfini la hauteur des nombres pour qu'ils soient lisibles
                    // quelque soit la taille de l'écran :
                    canvas.drawText(s, i * n + (n / 3) , j * n
                            + (n / 2) + (n / 3), paint2);
                else
                    canvas.drawText(s, i * n + (n / 3) , j * n
                            + (n / 2) + (n / 3), paint1);
            }
        }

        // dessine le carré résultat :
        if(getDrawResult()){
            canvas.drawLine(0,0,9*n,0, paint3);
            canvas.drawLine(9*n,0,9*n,9*n,paint3);
            canvas.drawLine(9*n,9*n,0,9*n,paint3);
            canvas.drawLine(0,9*n,0,0,paint3);
            setDrawResult(false);
        }

    }



    public int getXFromMatrix(int x) {
        // Renvoie l'indice d'une case à partir du pixel x de sa position h
        return (x / n);
    }

    public int getYFromMatrix(int y) {
        // Renvoie l'indice d'une case à partir du pixel y de sa position v
        return (y / n);
    }

    public void set(String s, int i) {
        // Remplir la ième ligne de la matrice matrix avec un vecteur String s
        int v;
        for (int j = 0; j < 9; j++) {
            v = s.charAt(j) - '0';
            matrix[i][j] = v;
            if (v == 0)
                fixIdx[i][j] = false;
            else
                fixIdx[i][j] = true;
        }
    }

    public void set(String s) {
        // Remplir la matrice matrix à partir d'un vecteur String s
        for (int i = 0; i < 9; i++) {
            set(s.substring(i * 9, i * 9 + 9), i);
        }
    }

    public  void set(int x, int y, int v) {
        // Affecte la valeur v à la case (y,x)
        int X = getXFromMatrix(x);
        int Y = getYFromMatrix(y);
       // if pour sécuriser le isNotFix
        if (X < 9 & Y < 9){
            if (isNotFix(Y, X)) {
                // On affecte la valeur dans la matrice
                matrix[Y][X] = v;
                // MàJ de l'affichage:
                invalidate();
            }
        }
    }

    public  boolean isNotFix(int x, int y) {
        // Renvoie true si la case (y,x) n'est pas fixe
        return !fixIdx [x][y];
    }

    public boolean isValid() {
        // 1. Vérifie l'existence de chaque numéro (de 1 à 9) dans chaque
        // ligne et chaque colonne
        boolean[] rl = { true, true, true, true, true, true, true, true, true };
        boolean[] rc = { true, true, true, true, true, true, true, true, true };
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (matrix[i][j] == 0)
                    return false;
                if (rl[j] && rc[j])
                    rl[j] = rc[j] = false;
                else
                    return false;
            }
            for (int j = 0; j < 9; j++) {
                rl[matrix[i][j] - 1] = true;
                rc[matrix[i][j] - 1] = true;
            }
        }

        // ------
        // 2. Vérifie l'existence de chaque numéro (de 1 à 9) dans chacun
        // des 9 carrés
        boolean[] r = { true, true, true, true, true, true, true, true, true };
        int w;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                w = 0;
                for (int k = i * 3; k < i * 3 + 3; k++) {
                    for (int l = j * 3; l < j * 3 + 3; l++) {
                        if (matrix[k][l] == 0)
                            return false;
                        if (r[w])
                            r[w++] = false;
                        else
                            return false;
                    }
                }
                for (int k = i * 3; k < i * 3 + 3; k++) {
                    for (int l = j * 3; l < j * 3 + 3; l++) {
                        r[matrix[k][l] - 1] = true;
                    }
                }
            }
        }
        // ------
        // Gagné
        return true;
    }

    //Helper configure la couleur du carré resultat
    // retourn True quand elle est appelée pour autoriser le dessin du carré.
    public boolean gameResult( boolean result){

        if (result){
            paint3.setColor(Color.GREEN);
        } else {
            paint3.setColor((Color.RED));
        }

        return true ;
    }

    // setter pour la variable drawResult.
    public void setDrawResult (boolean d){
        drawResult = d ;
    }

    // getter pour la variable drawResult
    public boolean getDrawResult(){
        return drawResult;
    }
}
