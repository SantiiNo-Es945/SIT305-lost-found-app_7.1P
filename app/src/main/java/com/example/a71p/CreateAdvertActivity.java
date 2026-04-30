package com.example.a71p;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CreateAdvertActivity extends AppCompatActivity {

    RadioGroup typeRadioGroup;
    EditText nameEditText, phoneEditText, descriptionEditText, categoryEditText, locationEditText;
    Button selectImageButton, saveAdvertButton;
    ImageView itemImageView;

    DatabaseHelper databaseHelper;
    String imagePath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_advert);
        //connects screen with SQLite
        databaseHelper = new DatabaseHelper(this);

        typeRadioGroup = findViewById(R.id.typeRadioGroup);
        nameEditText = findViewById(R.id.nameEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        categoryEditText = findViewById(R.id.categoryEditText);
        locationEditText = findViewById(R.id.locationEditText);
        selectImageButton = findViewById(R.id.selectImageButton);
        saveAdvertButton = findViewById(R.id.saveAdvertButton);
        itemImageView = findViewById(R.id.itemImageView);

        selectImageButton.setOnClickListener(v -> openGallery());

        saveAdvertButton.setOnClickListener(v -> saveAdvert());
    }
    //open phone gallery
    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        imagePickerLauncher.launch(intent);
    }
    //receive selected image
    private final androidx.activity.result.ActivityResultLauncher<Intent> imagePickerLauncher =
            registerForActivityResult(
                    new androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                            Uri selectedImageUri = result.getData().getData();

                            getContentResolver().takePersistableUriPermission(
                                    selectedImageUri,
                                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                            );

                            //save image location as text
                            imagePath = selectedImageUri.toString();
                            itemImageView.setImageURI(selectedImageUri);
                        }
                    }
            );
    //read all form fields
    private void saveAdvert() {
        int selectedId = typeRadioGroup.getCheckedRadioButtonId();
        RadioButton selectedRadioButton = findViewById(selectedId);

        String type = selectedRadioButton.getText().toString();
        String name = nameEditText.getText().toString();
        String phone = phoneEditText.getText().toString();
        String description = descriptionEditText.getText().toString();
        String category = categoryEditText.getText().toString();
        String location = locationEditText.getText().toString();
        //creates date/time stamp
        String date = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(new Date());

        if (name.isEmpty() || phone.isEmpty() || description.isEmpty()
                || category.isEmpty() || location.isEmpty() || imagePath.isEmpty()) {
            Toast.makeText(this, "Please fill all fields and upload an image", Toast.LENGTH_SHORT).show();
            return;
        }
        //save advert into SQLite
        boolean inserted = databaseHelper.insertAdvert(
                type, name, phone, description, category, location, date, imagePath
        );

        if (inserted) {
            Toast.makeText(this, "Advert saved", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Error saving advert", Toast.LENGTH_SHORT).show();
        }
    }
}