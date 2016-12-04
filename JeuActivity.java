/**
 * Created by christophe on 05/10/16.
 *
 */


package com.ubo.christophe.sudoku;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class JeuActivity extends Activity {


    private static final int CODE_MON_ACTIVITE = 1;

    private int x;
    private int y;
    Grille gr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jeu);

        gr = (Grille)findViewById(R.id.Grille);
        gr.setOnTouchListener( new View.OnTouchListener() {

            // Méthode pour capter la selection de la case par l'utilisateur
            // et déclenche l'activité choix par appelle de la méthode goToChoix()
            @Override
            public boolean onTouch(View v, MotionEvent e){
                if (e.getAction()==MotionEvent.ACTION_UP){
                    x = (int)e.getX();
                    y = (int)e.getY();

                    //Condition autorisant l'appelle de la liste choix uniquement sur un touché à l'intérieur de la grille.
                    int max = Math.min(gr.getWidth(), gr.getHeight());
                    if(x<=max & y<=max){
                        // méthode pour l'appelle de la l'activitée ChoixActivity
                        goToChoix(v);
                    }

                }
                return true;
            }
        } );

    }



    /* Méthode de récupération du choix de la "listeChoix" et appelle de la méthode
    *  set() pour placer le numéro choisit dans la grille.
    */

    protected void onActivityResult(int requestCode, int resultCode , Intent intent) {
        System.out.println("result: " + resultCode);

        gr.set(x,y,resultCode);
    }


    // Lancement de l'activité Choix avec demande du résultat de la liste de choix.
    // la méthode est appelée par onTouchListener sur la grille
    public  void goToChoix (View view) {
        System.out.println("oui");

        Intent intent = new Intent(this, ChoixActivity.class);
        startActivityForResult(intent, CODE_MON_ACTIVITE);
    }


    // Sur appelle du bouton validation : donne le résultat du jeu et autorise le dessin d'un carré vert ou rouge en fonction du résultat.
    public void validation(View view){


        if (gr.isValid()){
            Toast.makeText(this, "Gagné", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Perdu", Toast.LENGTH_SHORT).show();
        }
        gr.gameResult(gr.isValid());
        gr.setDrawResult(true);
        gr.invalidate();
    }
}

