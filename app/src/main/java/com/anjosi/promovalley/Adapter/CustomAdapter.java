package com.anjosi.promovalley.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.anjosi.promovalley.R;
import com.anjosi.promovalley.vo.ItemProductVO;
import com.anjosi.promovalley.vo.MercadoVO;

import java.util.List;

/**
 * Created by Anjosi on 16/04/2016.
 */
public class CustomAdapter<T> extends ArrayAdapter<T>{

    private Context context;
    List<T> listaDados;
    private LayoutInflater inflater;
    int resorceId;

    public CustomAdapter(Context context, int resource, List<T> objects) {
        super(context, resource, objects);
        this.context = context;
        this.listaDados = objects;
        this.inflater = LayoutInflater.from(context);
        this.resorceId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if (convertView == null) {
            convertView = inflater.inflate(resorceId, null);

            holder = new ViewHolder();

            holder.row_01 = (TextView) convertView.findViewById(R.id.row_01);
            holder.row_02 = (TextView) convertView.findViewById(R.id.row_02);
            holder.row_03 = (TextView) convertView.findViewById(R.id.row_03);
            holder.row_04 = (TextView) convertView.findViewById(R.id.row_04);
            holder.row_05 = (TextView) convertView.findViewById(R.id.row_05);
            holder.row_06 = (TextView) convertView.findViewById(R.id.row_06);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        Object item = listaDados.get(position);

        if (item.getClass().equals(MercadoVO.class)) {
            holder.row_01.setText(String.valueOf (((MercadoVO) item).getId()) + " - ");
            holder.row_02.setText(((MercadoVO) item).getName());
            holder.row_03.setText(((MercadoVO) item).getEndereco());

        }else if (item.getClass().equals(ItemProductVO.class)){
            holder.row_01.setText(String.valueOf(((ItemProductVO) item).getId()) + " - ");
            holder.row_02.setText(((ItemProductVO) item).getMercado().getName());
            holder.row_03.setText(((ItemProductVO) item).getProduto().getName());
            holder.row_04.setText(" ");
            holder.row_05.setText(" R$ " + String.valueOf(((ItemProductVO) item).getPrice()));
        }

        return convertView;
    }

    static class ViewHolder {
        TextView row_01;
        TextView row_02;
        TextView row_03;
        TextView row_04;
        TextView row_05;
        TextView row_06;
    }
}
