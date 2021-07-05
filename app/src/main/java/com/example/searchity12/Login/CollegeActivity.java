package com.example.searchity12.Login;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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

import com.example.searchity12.Menu.MenuActivity;
import com.example.searchity12.R;
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

public class CollegeActivity extends AppCompatActivity {

    //LAYOUT
    EditText editText_email_co;
    EditText editText_password_co;
    EditText editText_confirm_pass_co;
    EditText editText_name_co;
    EditText editText_lastname_co;
    CheckBox conditions_co;
    TextView textView_birthday_co;
    Button button_birthday_co;
    Spinner spinnerGender_co;
    Spinner spinnerProvinces_co;
    Spinner spinnerUniversity_co;
    Spinner spinnerDegree_co;
    Spinner spinnerCourse_co;

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
        setContentView(R.layout.activity_college);

        editText_email_co = findViewById(R.id.editText_email_college_signUp);
        editText_password_co  = findViewById(R.id.editText_password_college_signUp);
        editText_confirm_pass_co  = findViewById(R.id.editText_confirmPassword_college_signUp);
        editText_name_co  = findViewById(R.id.editText_name_college_signUp);
        editText_lastname_co  = findViewById(R.id.editText_lastName_college_signUp);
        textView_birthday_co = findViewById(R.id.textView_birthday_college_signUp);
        button_birthday_co = findViewById(R.id.button_birthday_college_signUp);
        conditions_co = findViewById(R.id.checkBox_college_signUp);

        setSpinners();
    }

    ///////////////////////////////INICIALIZAR COMPONENTES//////////////////////////////////////////

    private void setSpinners(){
        spinnerGender_co = findViewById(R.id.spinner_gender_college);
        spinnerProvinces_co = findViewById(R.id.spinner_province_college);
        spinnerUniversity_co = findViewById(R.id.spinner_university_college);
        spinnerDegree_co = findViewById(R.id.spinner_degree_college);
        spinnerCourse_co = findViewById(R.id.spinner_course_college);

        //SPINNER GÉNERO
        String[] gender = {"Hombre", "Mujer" , "No Binario"};
        ArrayAdapter<String> arrayAdapter_gender = new ArrayAdapter<String>(this,R.layout.spinner_text_view,gender);
        spinnerGender_co.setAdapter(arrayAdapter_gender);


        //SPINNER PROVINCIA
        String[] provinces = {"Alicante", "Castellón" , "Valencia"};
        ArrayAdapter<String> arrayAdapter_provinces = new ArrayAdapter<String>(this,R.layout.spinner_text_view,provinces);
        spinnerProvinces_co.setAdapter(arrayAdapter_provinces);

        //SPINNER UNIVERSIDAD
        String[] universities = {"Universidad Politécnica de Valencia"};
        ArrayAdapter<String> arrayAdapter_universities = new ArrayAdapter<String>(this,R.layout.spinner_text_view,universities);
        spinnerUniversity_co.setAdapter(arrayAdapter_universities);

        //SPINNER GRADO
        String[] degrees = {"Doble grado en Administración y Dirección de Empresas + Ingeniería Informática"};
        ArrayAdapter<String> arrayAdapter_degrees = new ArrayAdapter<String>(this,R.layout.spinner_text_view,degrees);
        spinnerDegree_co.setAdapter(arrayAdapter_degrees);

        //SPINNER CURSO
        String[] courses = {"Primero", "Segundo" , "Tercero", "Cuarto", "Quinto"};
        ArrayAdapter<String> arrayAdapter_courses = new ArrayAdapter<String>(this,R.layout.spinner_text_view,courses);
        spinnerCourse_co.setAdapter(arrayAdapter_courses);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setBirthday(View v){
        final Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day =calendar.get(Calendar.DAY_OF_MONTH);
        datePickerDialog = new DatePickerDialog(this, R.style.datePickerTheme,new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month++;
                textView_birthday_co.setText(dayOfMonth+"/"+month+"/"+year);
            }
        },year,month,day);
        datePickerDialog.show();
    }

    ////////////////////////////////MOVIMIENTO POR ACTIVIDADES//////////////////////////////////////

    public void goToLogin(View v) {
        Intent loginActivityIntent = new Intent(this, LoginActivity.class);
        startActivity(loginActivityIntent);
    }

    public void goToMenu(String email_co) {
        Intent menuActivityIntent = new Intent(this, MenuActivity.class);
        menuActivityIntent.putExtra("email", email_co);
        startActivity(menuActivityIntent);
    }
    ////////////////////////////////COMPROBACIONES//////////////////////////////////////////////////

    private boolean checkIsEmpty(){
        if(editText_email_co.getText().toString().isEmpty()){
            Toast.makeText(this, "El campo email está vacío", Toast.LENGTH_LONG).show();
            return false;
        }
        else if(editText_password_co.getText().toString().isEmpty()){
            Toast.makeText(this, "El campo contraseña está vacío", Toast.LENGTH_LONG).show();
            return false;
        }
        else if(editText_confirm_pass_co.getText().toString().isEmpty()){
            Toast.makeText(this, "El campo confirmar contraseña está vacío", Toast.LENGTH_LONG).show();
            return false;
        }
        else if(editText_name_co.getText().toString().isEmpty()){
            Toast.makeText(this, "El campo nombre está vacío", Toast.LENGTH_LONG).show();
            return false;
        }
        else if(editText_lastname_co.getText().toString().isEmpty()){
            Toast.makeText(this, "El campo apellidos está vacío", Toast.LENGTH_LONG).show();
            return false;
        }else if(textView_birthday_co.getText().toString().isEmpty()){
            Toast.makeText(this, "El campo fecha de nacimiento está vacío", Toast.LENGTH_LONG).show();
            return false;
        } else if(!conditions_co.isChecked()) {
            Toast.makeText(this, "Tiene que aceptar las condiciones y políticas", Toast.LENGTH_LONG).show();
            return false;
        }else{ return true;}
    }

    private boolean checkPassword(){
        if(!editText_password_co.getText().toString().equals(editText_confirm_pass_co.getText().toString())){
            Toast.makeText(this, "La contraseña no coincide", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean checkYear(){
        String textViewBirthdayText = textView_birthday_co.getText().toString();
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
    public void collegeSignUp(View v){
        String email_co = editText_email_co.getText().toString();
        String password_co = editText_password_co.getText().toString();

        if(checkIsEmpty() && checkPassword() && checkYear()){
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email_co, password_co)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d("Éxito", "signInWithEmail:success");
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                collegeDB(email_co,password_co);
                                goToMenu(email_co);
                            } else {
                                Log.w("Error", "signInWithEmail:failure", task.getException());
                                showAlert(task.getException().getMessage().toString());
                            }
                        }
                    });
        }

    }

    private void collegeDB(String email_co, String password_co){
        String name_co = editText_name_co.getText().toString();
        String lastname_co = editText_lastname_co.getText().toString();
        String birthday_co = textView_birthday_co.getText().toString();
        String gender_co = (String) spinnerGender_co.getSelectedItem();
        String province_co = (String) spinnerProvinces_co.getSelectedItem();
        String university_co = (String) spinnerUniversity_co.getSelectedItem();
        String degree_co = (String) spinnerDegree_co.getSelectedItem();
        String couse_co = (String) spinnerCourse_co.getSelectedItem();

        DocumentReference reference = db.collection("college").document(email_co);
        Map<String,Object> data = new HashMap<>();
        data.put("password",password_co);
        data.put("name",name_co);
        data.put("lastname", lastname_co);
        data.put("birthday", birthday_co);
        data.put("gender", gender_co);
        data.put("province",province_co);
        data.put("university",university_co);
        data.put("degree",degree_co);
        data.put("course",couse_co);

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