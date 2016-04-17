package com.anjosi.promovalley;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.anjosi.promovalley.Adapter.CustomAdapter;
import com.anjosi.promovalley.openhelper.DatabaseProvider;
import com.anjosi.promovalley.vo.ItemProductVO;

import java.util.List;

public class ListaCompra extends Activity {

    private ListView listView;
    private CustomAdapter adapter;

    private int codigo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_lista);

        this.listView = (ListView) findViewById(R.id.list_produtos);

        this.codigo = getIntent().getIntExtra("codigo", 0);

        ListaItens();
    }

    private void ListaItens(){
        List<ItemProductVO> listItens =DatabaseProvider.listItens(0, codigo);

        this.adapter = new CustomAdapter(this, R.layout.layout_itens, listItens);

        this.listView.setAdapter(adapter);
    }
}
