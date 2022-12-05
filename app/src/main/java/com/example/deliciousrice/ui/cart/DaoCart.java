package com.example.deliciousrice.ui.cart;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.deliciousrice.Model.Cart;

import java.util.ArrayList;
import java.util.List;

public class DaoCart {
    initData initData;
    private  SQLiteDatabase db;
    public DaoCart(Context context){
        initData=new initData(context);
        db=initData.getWritableDatabase();
    }
    public List<Cart> getall(){
        String sql="select * from tbcart";
        return getdata(sql);
    }
    @SuppressLint("Range")
    @NonNull
    private  List<Cart> getdata(String sql, String... id) {
        List<Cart> list=new ArrayList<>();
        Cursor c=db.rawQuery(sql,id);
        while (c.moveToNext()){
            Cart obj=new Cart();
            obj.id_product=Integer.parseInt(c.getString(c.getColumnIndex("id_product")));
            obj.name=c.getString(c.getColumnIndex("name"));
            obj.price=Integer.parseInt(c.getString(c.getColumnIndex("price")));
            obj.image=c.getString(c.getColumnIndex("image"));
            obj.amount=Integer.parseInt(c.getString(c.getColumnIndex("amount")));
            list.add(obj);
        }
        return list;
    }
    public long InsertData(int id_product, String name, int price, String image, int amount) {
        ContentValues values=new ContentValues();
        values.put("id_product",id_product);
        values.put("name",name);
        values.put("price",price);
        values.put("image",image);
        values.put("amount",amount);
        return db.insert("tbcart",null,values);
    }
    public int DeleteCart(int id) {
        return db.delete("tbcart", "id_product=?", new String[]{String.valueOf(id)});
    }
    public int UpdateCart(int id, int price,int amount){
        ContentValues values=new ContentValues();
        values.put("price",price);
        values.put("amount",amount);
        return db.update("tbcart",values,"id_product=?",new String[]{String.valueOf(id)});

    }
    public void DeleteData(){
        String sql = "DELETE FROM tbcart";
        db.execSQL(sql);
    }
    public class initData extends SQLiteOpenHelper {

        public initData(@Nullable Context context) {
            super(context,"Cartcustomer.db", null, 1);
        }


        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            String sql="CREATE TABLE tbcart(id_product integer,name text, price integer, image text, amount integer)";
            sqLiteDatabase.execSQL(sql);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL("drop table if exists tbcart");
            onCreate(sqLiteDatabase);
        }

    }
}
