/**
 * Created by christophe on 05/10/16.
 *
 */


package com.ubo.christophe.sudoku;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /** called when user clicks "jouer" button*/
    public void goToPlayground (View  view) {

        System.out.println("y: " + "okGo");
        Intent intent = new Intent(this,JeuActivity.class );
        startActivity(intent);

    }


    /** called when user clicks "A propos" button*/
    public void goToInformation (View view){
        Intent intent = new Intent(this,AProposActivity.class);
        startActivity(intent);

    }

}
