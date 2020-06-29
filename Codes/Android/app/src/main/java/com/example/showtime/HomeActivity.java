package com.example.showtime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
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
                Intent toSignUp=new Intent(this,SignUp.class);
                startActivity(toSignUp);
                return true;
            default:
                Intent toForgotPassword=new Intent(this,ForgotPassword.class);
                startActivity(toForgotPassword);
                return true;
        }
    }
    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finish();
    }
}
