package com.anjosi.promovalley;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.anjosi.promovalley.Adapter.CustomAdapter;
import com.anjosi.promovalley.openhelper.DatabaseProvider;
import com.anjosi.promovalley.vo.MercadoVO;

import java.text.DecimalFormat;
import java.util.List;

public class ListaMercados extends Activity {

    private ListView listaDados;
    CustomAdapter adapter;
    private List<MercadoVO> listMercado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_lista);

        this.listaDados = (ListView) findViewById(R.id.list_produtos);

        ListaMercados();
    }

    private void ListaMercados() {
        listMercado = DatabaseProvider.listMercados();

        adapter = new CustomAdapter(this, R.layout.layout_itens, listMercado);

        listaDados.setAdapter(adapter);

        listaDados.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DecimalFormat df = new DecimalFormat("0.##");

                double valor = DatabaseProvider.valores(listMercado.get(position).getId());

                Toast.makeText(ListaMercados.this, "Media de valores e de: " + df.format(valor), Toast.LENGTH_SHORT).show();

                ChamaActivity(listMercado.get(position).getId());
            }
        });
    }

    private void ChamaActivity(int id){
        Intent intent = new Intent(this, ListaCompra.class);
        intent.putExtra("codigo", id);

        startActivity(intent);
    }

}
