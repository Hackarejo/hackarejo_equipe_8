package com.anjosi.promovalley;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.anjosi.promovalley.openhelper.DatabaseProvider;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseProvider provider = new DatabaseProvider(this);
    }

    public void BtnMercado(View v) {
        Intent intent = new Intent(this, ListaMercados.class);
        startActivity(intent);
    }

    public void BtnCompara(View v){
        Intent intent = new Intent(this, ListaCompra.class);
        startActivity(intent);
    }
}
