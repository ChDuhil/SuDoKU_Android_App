/**
 * Created by christophe on 05/10/16.
 *
 */


package com.ubo.christophe.sudoku;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ChoixActivity extends AppCompatActivity {

    private ListView listChoice;
    private ArrayAdapter<String> listChoiceAdapter ;
    ArrayList<Integer> numberList = new ArrayList<Integer>() ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choix);
        listChoice = (ListView) findViewById(R.id.listNumber);

        // Populate the ArrayList
        for (int i=1; i<10; i++){
            numberList.add(new Integer(i));
        }

        // j'ai suivi le model de la best practice qui utilise des ArrayList pour allimenter les listView
        // Les Array list peuvent être utiles si nous voulons créer des listView dynamiques
        listChoiceAdapter = new ArrayAdapter(this,
                    android.R.layout.simple_list_item_1,
                    numberList);

        listChoice.setAdapter(listChoiceAdapter);

        listChoice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                setResult(numberList.get(position));

                finish();

            }
        });

    }


}
