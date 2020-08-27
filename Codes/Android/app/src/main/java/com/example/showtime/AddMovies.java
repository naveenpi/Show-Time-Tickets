package com.example.showtime;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;


import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.util.HashMap;
import java.util.Map;


public class AddMovies extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseStorage storage;
    private Uri filePath;
    StorageReference storageReference, ref;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    EditText movieName_EditText,genre_EditText,rating_EditText,ticket_price_EditText;
    String movieName_Text, genre_Text, rating_Text, ticketPrice_Text;
    ImageView movieImage;
    String profileImageUrl="";
    Button addMovie,uploadImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movies);

        movieName_EditText=findViewById(R.id.MovieName);
        genre_EditText=findViewById(R.id.Genre);
        rating_EditText=findViewById(R.id.Rating);
        ticket_price_EditText=findViewById(R.id.TicketPrice);
        movieImage=findViewById(R.id.movieImage);
        addMovie=findViewById(R.id.add_movie);
        uploadImg=findViewById(R.id.upload);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference("Images");

        movieImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FileChooser();


            }
        });

        uploadImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileUploader();

            }
        });


        addMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                movieName_Text = movieName_EditText.getText().toString();
                genre_Text = genre_EditText.getText().toString();
                rating_Text = rating_EditText.getText().toString();
                ticketPrice_Text = ticket_price_EditText.getText().toString();

                Map<String,Object> movies= new HashMap<>();
                movies.put("movieName",movieName_Text);
                movies.put("genre",genre_Text);
                movies.put("rating",rating_Text);
                movies.put("ticketPrice",ticketPrice_Text);
                movies.put("imgReference",profileImageUrl);
                Log.d("movie data",movies.toString());

                db.collection("movie")
                        .document(movieName_Text)
                        .set(movies)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("Successful registration", "movie is created for "+ movieName_Text);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("Failure registration", "movie is not created"+ movieName_EditText.getText().toString());
                            }
                        });

                startActivity(new Intent(getApplicationContext(),Admin.class));
                finish();

            }
        });
    }

    private String getExtension(Uri uri){

        ContentResolver cr = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
    }

    private void FileUploader() {

        ref=storageReference.child(System.currentTimeMillis()+"."+getExtension(filePath));
        ref.putFile(filePath)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Toast.makeText(AddMovies.this,"uploaded the image",Toast.LENGTH_LONG).show();

                        ref.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                profileImageUrl=task.getResult().toString();
                                Log.d("URL",profileImageUrl);
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {

                        Toast.makeText(AddMovies.this,"could not upload the image",Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void FileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null){
            filePath = data.getData();
            movieImage.setImageURI(filePath);

        }
    }

}