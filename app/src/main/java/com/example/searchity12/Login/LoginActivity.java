package com.example.searchity12.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.example.searchity12.Menu.MenuActivity;
import com.example.searchity12.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {

    //LOGIN ACTIVITY
    TabLayout tabLayout;
    ViewPager2 viewPager;
    LoginAdapter adapter;

    //LOGIN TAB
    private FirebaseAuth mAuth;
    EditText email;
    EditText password;

    public LoginActivity() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setTabs();

        //LOGIN TAB
        mAuth = FirebaseAuth.getInstance();
    }

    ///////////////////////////////INICIALIZAR LOGIN ACTIVITY///////////////////////////////////////

    private void setTabs(){
        tabLayout = findViewById(R.id.tab_layout_login);
        viewPager = findViewById(R.id.viewPager_login);
        adapter = new LoginAdapter(getSupportFragmentManager(),getLifecycle());

        adapter.addFragment(new LoginTab());
        adapter.addFragment(new SignUpTab());

        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch(position){
                    case 0:
                        tab.setText("Iniciar Sesión");
                        break;
                    case 1:
                        tab.setText("Registrase");
                        break;
                }
            }
        }).attach();
    }

    ////////////////////////////////MOVIMIENTO POR ACTIVIDADES//////////////////////////////////////

    public void goToMenu(String email) {
        Intent menuActivityIntent = new Intent(this, MenuActivity.class);
        menuActivityIntent.putExtra("email",email);
        startActivity(menuActivityIntent);
    }

    public void goToBachillerSignUp(View v) {
        Intent bachillerActivity = new Intent(this, PrecollegeActivity.class);
        startActivity(bachillerActivity);
    }

    public void goToCollegeSignUp(View v) {
        Intent collegeActivity = new Intent(this, CollegeActivity.class);
        startActivity(collegeActivity);
    }

    public void goToGraduatedSignUp(View v) {
        Intent graduatedActivity = new Intent(this, GraduatedActivity.class);
        startActivity(graduatedActivity);
    }

    //////////////////////////////////INICIO LOGIN//////////////////////////////////////////////////

    public void checkLogin(View v){

        email = findViewById(R.id.editText_email_login);
        password = findViewById(R.id.editText_password_login);

        if(email.getText().toString().isEmpty()){
            Toast.makeText(this, "El campo email está vacío", Toast.LENGTH_LONG).show();
        }else if(password.getText().toString().isEmpty()){
            Toast.makeText(this, "El campo contraseña está vacío",Toast.LENGTH_LONG).show();
        }else{
            login(email.getText().toString(),password.getText().toString());
        }
    }


    private void login(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("Éxito", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            goToMenu(email);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("Error", "signInWithEmail:failure", task.getException());
                            showAlert();
                        }
                    }
                });

    }

    private void showAlert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Error");
        builder.setMessage("Se ha producido un error autenticando al usuario");
        Dialog dialog = builder.create();
        dialog.show();

    }

    ////////////////////////////////////FIN LOGIN///////////////////////////////////////////////////
}

