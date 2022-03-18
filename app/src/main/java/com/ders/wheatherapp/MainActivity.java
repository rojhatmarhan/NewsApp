package com.ders.wheatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ders.wheatherapp.Models.NewsApiResponse;
import com.ders.wheatherapp.Models.NewsHeadLines;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SelectListener, View.OnClickListener {

    RecyclerView recyclerView;
    CustomAdapter adapter;
    ProgressDialog dialog;
    Button b1, b2, b3, b4, b5, b6, b7;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dialog = new ProgressDialog(this);
        dialog.setTitle("Haberler Yükleniyor ..!");
        dialog.show();

        searchView = findViewById(R.id.search_view);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                dialog.setTitle(query + " ile ilgili haberler alınıyor ..!");
                dialog.show();
                RequestManager manager = new RequestManager(MainActivity.this);
                manager.getNewsHeadLines(listener, "general", query);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        b1 = findViewById(R.id.btn_1);
        b1.setOnClickListener(this::onClick);
        b2 = findViewById(R.id.btn_2);
        b2.setOnClickListener(this::onClick);
        b3 = findViewById(R.id.btn_3);
        b3.setOnClickListener(this::onClick);
        b4 = findViewById(R.id.btn_4);
        b4.setOnClickListener(this::onClick);
        b5 = findViewById(R.id.btn_5);
        b5.setOnClickListener(this::onClick);
        b6 = findViewById(R.id.btn_6);
        b6.setOnClickListener(this::onClick);
        b7 = findViewById(R.id.btn_7);
        b7.setOnClickListener(this::onClick);

        RequestManager manager = new RequestManager(this);
        manager.getNewsHeadLines(listener, "general", null);
    }

    private final OnFetchDataListener<NewsApiResponse> listener = new OnFetchDataListener<NewsApiResponse>() {
        @Override
        public void onFetchData(List<NewsHeadLines> list, String message) {
            if(list.isEmpty()){
                Toast.makeText(MainActivity.this, "Veri bulunamadı !!!", Toast.LENGTH_SHORT).show();
            }
            else{
                showNews(list);
            }
            dialog.dismiss();
        }

        @Override
        public void onError(String message) {
            Toast.makeText(MainActivity.this, "Hata oluştu !", Toast.LENGTH_SHORT).show();
        }
    };

    private void showNews(List<NewsHeadLines> list) {
        recyclerView = findViewById(R.id.rv_main);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        adapter = new CustomAdapter(this,  list, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void OnNewsClicked(NewsHeadLines headLines) {
        startActivity(new Intent(MainActivity.this, DetailsActivity.class)
        .putExtra("data", headLines));
    }

    @Override
    public void onClick(View v) {
        Button button = (Button) v;
        String category;

        switch (button.getText().toString()){
            case "İş":
                category = "business";
                break;
            case "Eğlence":
                category = "entertainment";
                break;
            case "Genel":
                category = "general";
                break;
            case "Sağlık":
                category = "health";
                break;
            case "Bilim":
                category = "science";
                break;
            case "Spor":
                category = "sport";
                break;
            case "Teknoloji":
                category = "technology";
                break;
            default:
                category = "general";
                break;
        }

        dialog.setTitle(button.getText().toString() + " kategorisi için veriler alınıyor ..!");
        dialog.show();

        RequestManager manager = new RequestManager(this);
        manager.getNewsHeadLines(listener, category, null);
    }
}