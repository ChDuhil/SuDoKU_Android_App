/**
 * Created by christophe on 05/10/16.
 *
 */

package com.ubo.christophe.sudoku;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class AProposActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_propos);
    }
    /** called when user clicks "jouer" button*/
    public void backToMain (View view) {
        Intent intent = new Intent(this,MainActivity.class );
        startActivity(intent);
    }
}
