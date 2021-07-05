package com.example.searchity12.Login;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.searchity12.Menu.MenuActivity;
import com.example.searchity12.Model.Precollege;
import com.example.searchity12.R;
import com.example.searchity12.Tools;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PrecollegeActivity extends AppCompatActivity {

    //LAYOUT
    EditText editText_email_pre;
    EditText editText_password_pre;
    EditText editText_confirm_pass_pre;
    EditText editText_name_pre;
    EditText editText_lastname_pre;
    TextView textView_birthday_pre;
    Button button_birthday_pre;
    CheckBox conditions_pre;
    Spinner spinnerGender_pre;
    /* Spinner spinnerCommunities_pre;*/

    //CALENDARIO
    private int year;
    private int month;
    private int day;
    DatePickerDialog datePickerDialog;
    static final int MINIMUM_AGE  = 14;

    private static  final FirebaseFirestore db = FirebaseFirestore.getInstance();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_precollege);

        editText_email_pre = findViewById(R.id.editText_email_precollege_signUp);
        editText_password_pre = findViewById(R.id.editText_password_precollege_signUp);
        editText_confirm_pass_pre = findViewById(R.id.editText_confirmPassword_precollege_signUp);
        editText_name_pre = findViewById(R.id.editText_name_precollege_signUp);
        editText_lastname_pre = findViewById(R.id.editText_lastname_precollege_signUp);
        textView_birthday_pre = findViewById(R.id.textView_birthday_precollege_signUp);
        button_birthday_pre = findViewById(R.id.button_birthday_precollege_signUp);
        conditions_pre = findViewById(R.id.checkBox_precollege_signUp);

        setSpinners();
    }

    ///////////////////////////////INICIALIZAR COMPONENTES//////////////////////////////////////////

    private void setSpinners(){
        spinnerGender_pre = findViewById(R.id.spinner_gender_precollege);
        /*spinnerCommunities = findViewById(R.id.spinner_community_precollege);*/

        //SPINNER GÉNERO
        String[] gender = {"Hombre", "Mujer" , "No Binario"};
        ArrayAdapter<String> arrayAdapter_gender = new ArrayAdapter<String>(this,R.layout.spinner_text_view,gender);
        spinnerGender_pre.setAdapter(arrayAdapter_gender);

        //SPINNER COMUNIDAD
/*        String[] communities = {"Andalucía","Aragón", "Asturias", "Cantabria", "Castilla-La Mancha", "Castilla y León", "Cataluña",
                "Extremadura", "Galicia", "Islas Baleares", "Canarias", "La Rioja", "Madrid", "Murcia", "Navarra", "País Vasco", "Valencia"};
        ArrayAdapter<String> arrayAdapter_communities = new ArrayAdapter<String>(this,R.layout.spinner_text_view,communities);
        spinnerCommunities.setAdapter(arrayAdapter_communities);*/
    }

    public void setBirthday(View v){
        final Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day =calendar.get(Calendar.DAY_OF_MONTH);
        datePickerDialog = new DatePickerDialog(this, R.style.datePickerTheme,new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month++;
                textView_birthday_pre.setText(dayOfMonth+"/"+month+"/"+year);
            }
        },year,month,day);
        datePickerDialog.show();
    }

    ////////////////////////////////MOVIMIENTO POR ACTIVIDADES//////////////////////////////////////

    public void goToLogin(View v) {
        Intent loginActivityIntent = new Intent(this, LoginActivity.class);
        startActivity(loginActivityIntent);
    }

    public void goToMenu(String email_pre) {
        Intent menuActivityIntent = new Intent(this, MenuActivity.class);
        menuActivityIntent.putExtra("email", email_pre);
        startActivity(menuActivityIntent);
    }

    ////////////////////////////////COMPROBACIONES//////////////////////////////////////////////////

    private boolean checkIsEmpty(){
        if(editText_email_pre.getText().toString().isEmpty()){
            Toast.makeText(this, "El campo email está vacío", Toast.LENGTH_LONG).show();
            return false;
        }
        else if(editText_password_pre.getText().toString().isEmpty()){
            Toast.makeText(this, "El campo contraseña está vacío", Toast.LENGTH_LONG).show();
            return false;
        }
        else if(editText_confirm_pass_pre.getText().toString().isEmpty()){
            Toast.makeText(this, "El campo confirmar contraseña está vacío", Toast.LENGTH_LONG).show();
            return false;
        }
        else if(editText_name_pre.getText().toString().isEmpty()){
            Toast.makeText(this, "El campo nombre está vacío", Toast.LENGTH_LONG).show();
            return false;
        }
        else if(editText_lastname_pre.getText().toString().isEmpty()){
            Toast.makeText(this, "El campo apellidos está vacío", Toast.LENGTH_LONG).show();
            return false;
        }else if(textView_birthday_pre.getText().toString().isEmpty()){
            Toast.makeText(this, "El campo fecha de nacimiento está vacío", Toast.LENGTH_LONG).show();
            return false;
        } else if(!conditions_pre.isChecked()) {
            Toast.makeText(this, "Tiene que aceptar las condiciones y políticas", Toast.LENGTH_LONG).show();
            return false;
        }else{ return true;}
    }

    private boolean checkPassword(){
        if(!editText_password_pre.getText().toString().equals(editText_confirm_pass_pre.getText().toString())){
            Toast.makeText(this, "La contraseña no coincide", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean checkYear(){
        String textViewBirthdayText = textView_birthday_pre.getText().toString();
        if(!textViewBirthdayText.isEmpty()){
            int yearOfBirth = 0;
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            try {
                Date birthday = format.parse(textViewBirthdayText);
                yearOfBirth = birthday.getYear() + 1900;
            } catch (ParseException e) { e.printStackTrace();}
            //Date actual
            Date date = new Date();
            ZoneId timeZone = ZoneId.systemDefault();
            int validYear = date.toInstant().atZone(timeZone).getYear() - MINIMUM_AGE;
            if(yearOfBirth > validYear){
                Toast.makeText(getApplicationContext()," Eres demasiado joven" ,Toast.LENGTH_SHORT).show();
                return false;
            }
            return true;
        }
        else{
            return false;
        }
    }

    ///////////////////////////////////INICIO REGISTRARSE///////////////////////////////////////////

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void precollegeSignUp(View v){
        String email_pre = editText_email_pre.getText().toString();
        String password_pre = editText_password_pre.getText().toString();

        if(checkIsEmpty() && checkPassword() && checkYear()){
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email_pre, password_pre)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d("Éxito", "signInWithEmail:success");
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                precollegeDB(email_pre,password_pre);
                                goToMenu(email_pre);
                            } else {
                                Log.w("Error", "signInWithEmail:failure", task.getException());
                                showAlert(task.getException().getMessage().toString());
                            }
                        }
                    });
        }

    }

    private void precollegeDB(String email_pre, String password_pre){
        String name_pre = editText_name_pre.getText().toString();
        String lastname_pre = editText_lastname_pre.getText().toString();
        String birthday_pre = textView_birthday_pre.getText().toString();
        String gender_pre = (String) spinnerGender_pre.getSelectedItem();
        //Precollege newPrecollege = new Precollege(email_pre,password_pre,name_pre,lastname_pre,birthday_pre,gender_pre);

        DocumentReference reference = db.collection("precollege").document(email_pre);
        Map<String,Object> data = new HashMap<>();
        data.put("password",password_pre);
        data.put("name",name_pre);
        data.put("lastname", lastname_pre);
        data.put("birthday", birthday_pre);
        data.put("gender", gender_pre);

        reference.set(data);
    }

    private void showAlert(String error){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Error");
        builder.setMessage(error);
        Dialog dialog = builder.create();
        dialog.show();

    }

    ////////////////////////////////////FIN REGISTRARSE/////////////////////////////////////////////
}