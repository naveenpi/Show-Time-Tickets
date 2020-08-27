package com.example.showtime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import javax.annotation.Nullable;

public class HomeActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener
{

    TextView b1,b2,b3,b4;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        b1=findViewById(R.id.textView);
        b2=findViewById(R.id.textView2);
        b3=findViewById(R.id.textView3);
        b4=findViewById(R.id.textView4);

//        b1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent toDetails=new Intent(HomeActivity.this, MovieDetails.class);
//                toDetails.putExtra("movieId","bahubali2");
//                startActivity(toDetails);
//            }
//        });
//
//        b2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent toDetails=new Intent(HomeActivity.this, MovieDetails.class);
//                toDetails.putExtra("movieId","avengers");
//                startActivity(toDetails);
//
//            }
//        });
//
//        b3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent toDetails=new Intent(HomeActivity.this, MovieDetails.class);
//                toDetails.putExtra("movieId","thor");
//                startActivity(toDetails);
//
//            }
//        });
//
//        b4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent toDetails=new Intent(HomeActivity.this, MovieDetails.class);
//                toDetails.putExtra("movieId","ironman");
//                startActivity(toDetails);
//            }
//        });
    }

    public void menu(View v) {
        PopupMenu popup= new PopupMenu(this,v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.navigation_menu);
        popup.show();
    }
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item1:
                return true;
            case R.id.item2:
                Intent toLogin=new Intent(this,MainActivity.class);
                startActivity(toLogin);
                return true;
            case R.id.item3:
                Intent toForgotPassword=new Intent(this,ForgotPassword.class);
                startActivity(toForgotPassword);
                return true;
            default:
                Intent toSignUP=new Intent(this,SignUp.class);
                startActivity(toSignUP);
                return true;
        }
    }
    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finish();
    }
}
