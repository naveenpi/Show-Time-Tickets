package com.example.showtime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

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
   
    }
}
