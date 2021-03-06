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

public class GraduatedActivity extends AppCompatActivity {

    //LAYOUT
    EditText editText_email_gra;
    EditText editText_password_gra;
    EditText editText_confirm_pass_gra;
    EditText editText_name_gra;
    EditText editText_lastname_gra;
    CheckBox conditions_gra;
    TextView textView_birthday_gra;
    Button button_birthday_gra;
    Spinner spinnerGender_gra;
    Spinner spinnerProvinces_gra;
    Spinner spinnerUniversity_gra;
    Spinner spinnerDegree_gra;
    EditText editText_graduationYear_gra;

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
        setContentView(R.layout.activity_graduated);

        editText_email_gra = findViewById(R.id.editText_email_graduated_signUp);
        editText_password_gra  = findViewById(R.id.editText_password_graduated_signUp);
        editText_confirm_pass_gra  = findViewById(R.id.editText_confirmPassword_graduated_signUp);
        editText_name_gra  = findViewById(R.id.editText_name_graduated_signUp);
        editText_lastname_gra  = findViewById(R.id.editText_lastName_graduated_signUp);
        textView_birthday_gra = findViewById(R.id.textView_birthday_graduated_signUp);
        button_birthday_gra = findViewById(R.id.button_birthday_graduated_signUp);
        conditions_gra = findViewById(R.id.checkBox_graduated_signUp);
        editText_graduationYear_gra = findViewById(R.id.editText_graduationYear_graduated_signUp);


        setSpinners();
    }

    ///////////////////////////////INICIALIZAR COMPONENTES//////////////////////////////////////////

    private void setSpinners(){
        spinnerGender_gra = findViewById(R.id.spinner_gender_graduated);
        spinnerProvinces_gra = findViewById(R.id.spinner_province_graduated);
        spinnerUniversity_gra = findViewById(R.id.spinner_university_graduated);
        spinnerDegree_gra = findViewById(R.id.spinner_degree_graduated);

        //SPINNER G??NERO
        String[] gender = {"Hombre", "Mujer" , "No Binario"};
        ArrayAdapter<String> arrayAdapter_gender = new ArrayAdapter<String>(this,R.layout.spinner_text_view,gender);
        spinnerGender_gra.setAdapter(arrayAdapter_gender);


        //SPINNER PROVINCIA
        String[] provinces = {"Alicante", "Castell??n" , "Valencia"};
        ArrayAdapter<String> arrayAdapter_provinces = new ArrayAdapter<String>(this,R.layout.spinner_text_view,provinces);
        spinnerProvinces_gra.setAdapter(arrayAdapter_provinces);

        //SPINNER UNIVERSIDAD
        String[] universities = {"Universidad Polit??cnica de Valencia"};
        ArrayAdapter<String> arrayAdapter_universities = new ArrayAdapter<String>(this,R.layout.spinner_text_view,universities);
        spinnerUniversity_gra.setAdapter(arrayAdapter_universities);

        //SPINNER GRADO
        String[] degrees = {"Doble grado en Administraci??n y Direcci??n de Empresas + Ingenier??a Inform??tica"};
        ArrayAdapter<String> arrayAdapter_degrees = new ArrayAdapter<String>(this,R.layout.spinner_text_view,degrees);
        spinnerDegree_gra.setAdapter(arrayAdapter_degrees);
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
                textView_birthday_gra.setText(dayOfMonth+"/"+month+"/"+year);
            }
        },year,month,day);
        datePickerDialog.show();
    }

    ////////////////////////////////MOVIMIENTO POR ACTIVIDADES//////////////////////////////////////

    public void goToLogin(View v) {
        Intent loginActivityIntent = new Intent(this, LoginActivity.class);
        startActivity(loginActivityIntent);
    }

    public void goToMenu(String email_gra) {
        Intent menuActivityIntent = new Intent(this, MenuActivity.class);
        menuActivityIntent.putExtra("email", email_gra);
        startActivity(menuActivityIntent);
    }
    ////////////////////////////////COMPROBACIONES//////////////////////////////////////////////////

    private boolean checkIsEmpty(){
        if(editText_email_gra.getText().toString().isEmpty()){
            Toast.makeText(this, "El campo email est?? vac??o", Toast.LENGTH_LONG).show();
            return false;
        }
        else if(editText_password_gra.getText().toString().isEmpty()){
            Toast.makeText(this, "El campo contrase??a est?? vac??o", Toast.LENGTH_LONG).show();
            return false;
        }
        else if(editText_confirm_pass_gra.getText().toString().isEmpty()){
            Toast.makeText(this, "El campo confirmar contrase??a est?? vac??o", Toast.LENGTH_LONG).show();
            return false;
        }
        else if(editText_name_gra.getText().toString().isEmpty()){
            Toast.makeText(this, "El campo nombre est?? vac??o", Toast.LENGTH_LONG).show();
            return false;
        }
        else if(editText_lastname_gra.getText().toString().isEmpty()){
            Toast.makeText(this, "El campo apellidos est?? vac??o", Toast.LENGTH_LONG).show();
            return false;
        }else if(textView_birthday_gra.getText().toString().isEmpty()){
            Toast.makeText(this, "El campo fecha de nacimiento est?? vac??o", Toast.LENGTH_LONG).show();
            return false;
        }else if(editText_graduationYear_gra.getText().toString().isEmpty()){
            Toast.makeText(this, "El campo a??o de graduaci??n est?? vac??o", Toast.LENGTH_LONG).show();
            return false;
        } else if(!conditions_gra.isChecked()) {
            Toast.makeText(this, "Tiene que aceptar las condiciones y pol??ticas", Toast.LENGTH_LONG).show();
            return false;
        }else{ return true;}
    }

    private boolean checkPassword(){
        if(!editText_password_gra.getText().toString().equals(editText_confirm_pass_gra.getText().toString())){
            Toast.makeText(this, "La contrase??a no coincide", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean checkYear(){
        String textViewBirthdayText = textView_birthday_gra.getText().toString();
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean checkGraduationYear(){
        String graduationYear_string = editText_graduationYear_gra.getText().toString();
        if(graduationYear_string.length() != 4){
            Toast.makeText(getApplicationContext(),"El campo a??o de graduaci??n debe de tener 4 n??meros" ,Toast.LENGTH_LONG).show();
            return false;
        }
        int graduationYear_int;
        try{
            graduationYear_int = Integer.parseInt(graduationYear_string);
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage() ,Toast.LENGTH_LONG).show();
            return false;
        }
        Date date = new Date();
        ZoneId timeZone = ZoneId.systemDefault();
        int validYear = date.toInstant().atZone(timeZone).getYear() - 10;
        if(graduationYear_int < validYear){
            Toast.makeText(getApplicationContext(),"Lo sentimos, no te puedes registrar. Te graduaste hace m??s de 10 a??os" ,Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    ///////////////////////////////////INICIO REGISTRARSE///////////////////////////////////////////

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void graduatedSignUp(View v){
        String email_gra = editText_email_gra.getText().toString();
        String password_gra = editText_password_gra.getText().toString();

        if(checkIsEmpty() && checkPassword() && checkYear() && checkGraduationYear()){
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email_gra, password_gra)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d("??xito", "signInWithEmail:success");
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                graduatedDB(email_gra,password_gra);
                                goToMenu(email_gra);
                            } else {
                                Log.w("Error", "signInWithEmail:failure", task.getException());
                                showAlert(task.getException().getMessage().toString());
                            }
                        }
                    });
        }

    }

    private void graduatedDB(String email_gra, String password_gra){
        String name_gra = editText_name_gra.getText().toString();
        String lastname_gra = editText_lastname_gra.getText().toString();
        String birthday_gra = textView_birthday_gra.getText().toString();
        String gender_gra = (String) spinnerGender_gra.getSelectedItem();
        String province_gra = (String) spinnerProvinces_gra.getSelectedItem();
        String university_gra = (String) spinnerUniversity_gra.getSelectedItem();
        String degree_gra = (String) spinnerDegree_gra.getSelectedItem();
        String graduationYear_gra = editText_graduationYear_gra.getText().toString();
        System.out.println("*************************************************************************** "+graduationYear_gra);

        DocumentReference reference = db.collection("graduated").document(email_gra);
        Map<String,Object> data = new HashMap<>();
        data.put("password",password_gra);
        data.put("name",name_gra);
        data.put("lastname", lastname_gra);
        data.put("birthday", birthday_gra);
        data.put("gender", gender_gra);
        data.put("province",province_gra);
        data.put("university",university_gra);
        data.put("degree",degree_gra);
        data.put("graduationYear", graduationYear_gra);

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