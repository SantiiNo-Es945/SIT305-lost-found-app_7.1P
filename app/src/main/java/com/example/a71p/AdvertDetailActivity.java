package com.example.a71p;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;


import androidx.appcompat.app.AppCompatActivity;

public class AdvertDetailActivity extends AppCompatActivity {

    ImageView detailImageView;
    TextView detailTitleTextView, detailInfoTextView;
    Button removeAdvertButton;

    DatabaseHelper databaseHelper;
    int advertId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advert_detail);

        databaseHelper = new DatabaseHelper(this);

        detailImageView = findViewById(R.id.detailImageView);
        detailTitleTextView = findViewById(R.id.detailTitleTextView);
        detailInfoTextView = findViewById(R.id.detailInfoTextView);
        removeAdvertButton = findViewById(R.id.removeAdvertButton);

        advertId = getIntent().getIntExtra("id", -1);
        String type = getIntent().getStringExtra("type");
        String name = getIntent().getStringExtra("name");
        String phone = getIntent().getStringExtra("phone");
        String description = getIntent().getStringExtra("description");
        String category = getIntent().getStringExtra("category");
        String location = getIntent().getStringExtra("location");
        String date = getIntent().getStringExtra("date");
        String imagePath = getIntent().getStringExtra("imagePath");

        detailTitleTextView.setText(type + ": " + name);

        String info = "Phone: " + phone +
                "\nCategory: " + category +
                "\nLocation: " + location +
                "\nPosted: " + date +
                "\n\nDescription:\n" + description;

        detailInfoTextView.setText(info);

        if (imagePath != null && !imagePath.isEmpty()) {
            if (imagePath.startsWith("content://")) {
                detailImageView.setImageURI(Uri.parse(imagePath));
            } else {
                detailImageView.setImageURI(Uri.fromFile(new java.io.File(imagePath)));
            }
        }

        removeAdvertButton.setOnClickListener(v -> {
            databaseHelper.deleteAdvert(advertId);
            Toast.makeText(this, "Advert removed", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}