/*
 * Copyright (C) 2021 dreamn(dream@dreamn.cn)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package cn.dreamn.qianji_auto.core.hook.app.qianji;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "qianjiapp";
    ;
    //SQLiteDatabase
    private final SQLiteDatabase db;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, 86);
        db = getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //
    }


    public ArrayList<Data> getCategory() {
        Cursor cursor = db.rawQuery("select * from category", null);
        ArrayList<Data> data = new ArrayList<>();
        while (cursor.moveToNext()) {
            Bundle bundle = new Bundle();
            bundle.putString("name", cursor.getString(cursor.getColumnIndex("NAME")));
            bundle.putString("type", cursor.getString(cursor.getColumnIndex("TYPE")));
            bundle.putString("id", cursor.getString(cursor.getColumnIndex("_id")));
            bundle.putString("parent", cursor.getString(cursor.getColumnIndex("PARENT_ID")));
            bundle.putString("level", cursor.getString(cursor.getColumnIndex("LEVEL")));
            bundle.putString("icon", cursor.getString(cursor.getColumnIndex("ICON")));
            Data data1 = new Data();
            data1.set(bundle);
            data.add(data1);
        }
        cursor.close();
        return data;
    }

    public ArrayList<Data> getAsset() {
        Cursor cursor = db.rawQuery("select * from user_asset where TYPE <> 5", null);
        ArrayList<Data> data = new ArrayList<>();
        while (cursor.moveToNext()) {
            Bundle bundle = new Bundle();
            bundle.putString("name", cursor.getString(cursor.getColumnIndex("NAME")));
            bundle.putString("icon", cursor.getString(cursor.getColumnIndex("ICON")));
            Data data1 = new Data();
            data1.set(bundle);
            data.add(data1);
        }
        cursor.close();
        return data;
    }

    public ArrayList<Data> getUserBook() {
        Cursor cursor = db.rawQuery("select * from user_book ", null);
        ArrayList<Data> data = new ArrayList<>();
        while (cursor.moveToNext()) {
            Bundle bundle = new Bundle();
            bundle.putString("name", cursor.getString(cursor.getColumnIndex("NAME")));
            bundle.putString("cover", cursor.getString(cursor.getColumnIndex("COVER")));
            Data data1 = new Data();
            data1.set(bundle);
            data.add(data1);
        }
        cursor.close();
        return data;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        if (db != null) {
            db.close();
        }
    }


}