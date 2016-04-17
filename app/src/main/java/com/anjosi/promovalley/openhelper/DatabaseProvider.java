package com.anjosi.promovalley.openhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.anjosi.promovalley.vo.ItemProductVO;
import com.anjosi.promovalley.vo.MercadoVO;
import com.anjosi.promovalley.vo.ProductVO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anjosi on 16/04/2016.
 */
public class DatabaseProvider {

    public static DataBaseHelper helper;
    public static SQLiteDatabase db;

    public DatabaseProvider(Context context){

        helper = new DataBaseHelper(context);
/*
        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                if(listProd().size() == 0){
                    AutoInserProdutos();
                    AutoInserMercados();
                    AutoInserItens();
                }
            }
        }, 2000, 2000);
*/
    }

    public static int insertProdutos(ProductVO vo){
        ContentValues cv = new ContentValues();

        cv.put(DataBaseHelper.COLUMN_NAME_COMP, vo.getName());

        db = helper.getWritableDatabase();

        long id = db.insert(DataBaseHelper.TABLE_COMP, null, cv);

        db.close();

        return  Integer.parseInt(String.valueOf(id));
    }

    public static int insertItens(ItemProductVO item){
        ContentValues cv = new ContentValues();

        cv.put(DataBaseHelper.COLUMN_PRIC_IT, item.getPrice());
        cv.put(DataBaseHelper.COLUMN_FK_MER, item.getMercado().getId());
        cv.put(DataBaseHelper.COLUMN_FK_PROD, item.getProduto().getId());

        db = helper.getWritableDatabase();

        long id = db.insert(DataBaseHelper.TABLE_ITEM, null, cv);

        db.close();

        return  Integer.parseInt(String.valueOf(id));
    }

    public static int insertMercado(MercadoVO vo){
        ContentValues cv = new ContentValues();
        cv.put(DataBaseHelper.COLUMN_NAME_MERC, vo.getName());
        cv.put(DataBaseHelper.COLUMN_ENDE_MERC, vo.getEndereco());

        db = helper.getWritableDatabase();

        long id = db.insert(DataBaseHelper.TABLE_MERCADO, null, cv);

        db.close();

        return  Integer.parseInt(String.valueOf(id));
    }

    public static List<ItemProductVO> listItens(){
        return  listItens(0, 0, false);
    }

    public static List<ItemProductVO> listItens(int codigoPro, int codigoMercado){
        return  listItens(codigoPro, codigoMercado, false);
    }

    public static List<ItemProductVO> listItens(int codigoPro, int codigoMercado, boolean orderDesc){
        db = helper.getReadableDatabase();

        StringBuffer bf = new StringBuffer();
        bf.append("SELECT * FROM ");
        bf.append(DataBaseHelper.TABLE_ITEM);

        String[] argumentos = null;
        if ((codigoPro != 0) || (codigoMercado != 0)) {
            bf.append(" WHERE ");

            if(codigoPro != 0){
                bf.append(DataBaseHelper.COLUMN_FK_PROD);
                bf.append(" = ? ");

                if(codigoMercado != 0){
                    bf.append(",");
                    bf.append(DataBaseHelper.COLUMN_FK_MER);
                    bf.append(" = ? ");
                }

                argumentos = new String[]{String.valueOf(codigoPro), String.valueOf(codigoMercado)};

            }else{
                if(codigoMercado != 0){
                    bf.append(DataBaseHelper.COLUMN_FK_MER);
                    bf.append(" = ? ");

                    argumentos = new String[]{String.valueOf(codigoMercado)};
                }
            }
        }

        bf.append(" ORDER BY ");
        bf.append(DataBaseHelper.COLUMN_PRIC_IT);

        if(orderDesc)
            bf.append(" DESC ");

        Cursor cursor = db.rawQuery(bf.toString(), argumentos);

        List<ItemProductVO> listItens = new ArrayList<ItemProductVO>();

        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex(DataBaseHelper.COLUMN_ID));
            Double price = cursor.getDouble(cursor.getColumnIndex(DataBaseHelper.COLUMN_PRIC_IT));

            int codPr = cursor.getInt(cursor.getColumnIndex(DataBaseHelper.COLUMN_FK_PROD));
            int codMr = cursor.getInt(cursor.getColumnIndex(DataBaseHelper.COLUMN_FK_MER));

            ProductVO prod = ItemProduto(codPr);
            MercadoVO merc = ItemMercado(codMr);


            ItemProductVO itemVo = new ItemProductVO();
            itemVo.setProduto(prod);
            itemVo.setMercado(merc);
            itemVo.setId(id);
            itemVo.setPrice(price);

            listItens.add(itemVo);
        }

        cursor.close();
        db.close();

        return listItens;
    }

    public static ProductVO ItemProduto(int codiPro){
        return listProd(codiPro).get(0);
    }

    public static List<ProductVO> listProd(){
        return  listProd(null);
    }

    public static List<ProductVO> listProd(Object where){
        db = helper.getReadableDatabase();

        StringBuffer bf = new StringBuffer();
        bf.append("SELECT * FROM ");
        bf.append(DataBaseHelper.TABLE_COMP);

        String[] argumentos = null;
        if (where != null) {
            bf.append(" WHERE ");

            if(where instanceof String){
                bf.append(DataBaseHelper.COLUMN_NAME_COMP);
                bf.append(" LIKE ? ");
            }else{
                bf.append(DataBaseHelper.COLUMN_ID);
                bf.append(" = ?");
            }

            argumentos = new String[]{String.valueOf(where)};
        }
        bf.append(" ORDER BY ");
        bf.append(DataBaseHelper.COLUMN_NAME_COMP);

        Cursor cursor = db.rawQuery(bf.toString(), argumentos);

        List<ProductVO> produtos = new ArrayList<ProductVO>();

        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex(DataBaseHelper.COLUMN_ID));
            String nome = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_NAME_COMP));

            ProductVO pr = new ProductVO();
            pr.setId(id);
            pr.setName(nome);

            produtos.add(pr);
        }

        cursor.close();
        db.close();

        return produtos;
    }

    public static MercadoVO ItemMercado(int codigo){
        return  listMercados(codigo).get(0);
    }

    public static List<MercadoVO> listMercados(){
        return  listMercados(null);
    }

    public static List<MercadoVO> listMercados(Object where){
        db = helper.getReadableDatabase();

        StringBuffer bf = new StringBuffer();
        bf.append("SELECT * FROM ");
        bf.append(DataBaseHelper.TABLE_MERCADO);

        String[] argumentos = null;
        if (where != null) {
            bf.append(" WHERE ");

            if(where instanceof String){
                bf.append(DataBaseHelper.COLUMN_NAME_MERC);
                bf.append(" LIKE ? ");
            }else{
                bf.append(DataBaseHelper.COLUMN_ID);
                bf.append(" = ?");
            }

            argumentos = new String[]{String.valueOf(where)};

        }

        bf.append(" ORDER BY ");
        bf.append(DataBaseHelper.COLUMN_ID);

        Cursor cursor = db.rawQuery(bf.toString(), argumentos);

        List<MercadoVO> mercados = new ArrayList<MercadoVO>();

        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex(DataBaseHelper.COLUMN_ID));
            String nome = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_NAME_MERC));
            String endereco = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_ENDE_MERC));

            MercadoVO vo = new MercadoVO();
            vo.setId(id);
            vo.setName(nome);
            vo.setEndereco(endereco);

            mercados.add(vo);
        }

        cursor.close();
        db.close();

        return mercados;
    }

    private static void AutoInserItens(){
        ItemProductVO vo = new ItemProductVO();

        MercadoVO mercadoVO = new MercadoVO();
        mercadoVO.setId(1);
        vo.setMercado(mercadoVO);

        itens(vo, 1, 10.00);
        insertItens(vo);

        itens(vo, 2, 7.80);
        insertItens(vo);

        itens(vo, 3, 6.00);
        insertItens(vo);

        itens(vo, 4, 12.00);
        insertItens(vo);

        itens(vo, 5, 2.70);
        insertItens(vo);

        itens(vo, 6, 8.95);
        insertItens(vo);

        itens(vo, 7, 15.22);
        insertItens(vo);

        itens(vo, 8, 8.75);
        insertItens(vo);

        itens(vo, 9, 9.99);
        insertItens(vo);

        itens(vo, 10, 7.30);
        insertItens(vo);

        itens(vo, 11, 8.82);
        insertItens(vo);


        //mercado 2

        mercadoVO.setId(2);
        vo.setMercado(mercadoVO);

        itens(vo, 1, 8.90);
        insertItens(vo);

        itens(vo, 2, 8.80);
        insertItens(vo);

        itens(vo, 3, 12.00);
        insertItens(vo);

        itens(vo, 4, 5.00);
        insertItens(vo);

        itens(vo, 5, 6.70);
        insertItens(vo);

        itens(vo, 6, 9.95);
        insertItens(vo);

        itens(vo, 7, 1.22);
        insertItens(vo);

        itens(vo, 8, 6.75);
        insertItens(vo);

        itens(vo, 9, 2.99);
        insertItens(vo);

        itens(vo, 10, 17.30);
        insertItens(vo);

        itens(vo, 11, 12.82);
        insertItens(vo);
    }

    private static ItemProductVO itens(ItemProductVO vo, int codigoPro, double valor){
        ProductVO productVO = new ProductVO();
        productVO.setId(codigoPro);
        vo.setProduto(productVO);
        vo.setPrice(valor);

        return vo;
    }

    private static void AutoInserMercados(){
        MercadoVO mercado = new MercadoVO();
        mercado.setName("SuperMercado SuperPao");
        mercado.setEndereco("Av. Antônio Paiva Cantelmo 77");
        insertMercado(mercado);

        mercado.setName("MSuperMercado Vipi");
        mercado.setEndereco("Rua Antonina, 320");
        insertMercado(mercado);
    }

    private static void AutoInserProdutos(){
        ProductVO produto = new ProductVO();
        produto.setName("ARROZ");
        insertProdutos(produto);
        produto.setName("ACUCAR");
        insertProdutos(produto);
        produto.setName("CAFÉ MOÍDO");
        insertProdutos(produto);
        produto.setName("FARINHA DE MANDIOCA");
        insertProdutos(produto);
        produto.setName("FARINHA DE TRIGO");
        insertProdutos(produto);
        produto.setName("FEIJÃO CARIOCA TIPO");
        insertProdutos(produto);
        produto.setName("FUBÁ");
        insertProdutos(produto);
        produto.setName("MACARRÃO ESPAGUETE");
        insertProdutos(produto);
        produto.setName("ÓLEO DE SOJA");
        insertProdutos(produto);
        produto.setName("SAL");
        insertProdutos(produto);
        produto.setName("SARDINHA EM ÓLEO");
        insertProdutos(produto);
    }
}
