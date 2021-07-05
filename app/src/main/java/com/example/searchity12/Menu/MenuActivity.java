package com.example.searchity12.Menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.searchity12.Login.LoginActivity;
import com.example.searchity12.Menu.Fragments.HomeFragment;
import com.example.searchity12.Menu.Fragments.SettingsFragment;
import com.example.searchity12.R;
import com.google.android.material.navigation.NavigationView;

public class MenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;

    //Toolbar toolbar;
    NavigationView navigationView;

    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        setMenu();
    }

    private void setMenu(){
        setSupportActionBar(findViewById(R.id.toolbar_menu));
        drawerLayout = findViewById(R.id.drawer_menu);
        navigationView = findViewById(R.id.navigation_view_menu);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout, findViewById(R.id.toolbar_menu),R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerSlideAnimationEnabled(true);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        //Cargar menu_fragment
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frameLayout_menu, new HomeFragment());
        fragmentTransaction.commit();

        //Establecer evento onClick al navigationView
        navigationView.setNavigationItemSelectedListener(this);

        //TEXTVIEW CON EL EMAIL DEL USUARIO
        email = getIntent().getExtras().getString("email");
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.textView_email_menu);
        navUsername.setText(email);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);
        if(item.getItemId() == R.id.home){
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayout_menu, new HomeFragment());
            fragmentTransaction.commit();
        }
        if(item.getItemId() == R.id.settings){
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayout_menu, new SettingsFragment());
            fragmentTransaction.commit();
        }
        return false;
    }

    public void goToLogin(View v){
        Intent loginActivityIntent = new Intent(this, LoginActivity.class);
        startActivity(loginActivityIntent);
    }
}