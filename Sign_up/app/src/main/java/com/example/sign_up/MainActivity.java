package com.example.sign_up;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {
    private Button bt1;

    private EditText edt_id, edt_pw, edt_pwcheck, edt_name, edt_year, edt_day, edt_email, edt_phone;
    private Spinner edt_month, edt_sex, edt_code;
    private SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void bt1(View view){
        //버튼을 눌렸을 때 값 불러오기
        edt_id = findViewById(R.id.edit_id); //id값 불러옴
        edt_pw = findViewById(R.id.edit_pw); //pw값 불러옴
        edt_pwcheck = findViewById(R.id.edit_pwcheck); //pwcheck값 불러옴
        edt_name = findViewById(R.id.edit_name); //name값 불러옴
        edt_year = findViewById(R.id.edit_year); //year값 불러옴
        edt_month =findViewById(R.id.edit_month); //스피너에서 month
        String month = edt_month.getSelectedItem().toString(); //스피너에서 선택한 값 문자열로 변환
        edt_day = findViewById(R.id.edit_day); //day값 불러옴
        edt_sex =findViewById(R.id.edit_sex); //스피너에서 sex
        String sex = edt_sex.getSelectedItem().toString(); //스피너에서 선택한 값 문자열로 변환
        edt_email = findViewById(R.id.edit_email); //email값 불러옴
        edt_code =findViewById(R.id.edit_code); //스피너에서 code
        String code = edt_code.getSelectedItem().toString(); //스피너에서 선택한 값 문자열로 변환
        edt_phone = findViewById(R.id.edit_phone); //phone값 불러옴


        preferences = getSharedPreferences("naver_UserInfo", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        
        //값 저장해주기
        editor.putString("userid",edt_id.getText().toString()); 
        editor.putString("userpw",edt_pw.getText().toString());
        editor.putString("userpwcheck",edt_pwcheck.getText().toString());
        editor.putString("username",edt_name.getText().toString());
        editor.putString("useryear",edt_year.getText().toString());
        editor.putString("usermonth",month);
        editor.putString("userday",edt_day.getText().toString());
        editor.putString("usersex",sex);
        editor.putString("useremail",edt_email.getText().toString());
        editor.putString("usercode",code);
        editor.putString("userphone",edt_phone.getText().toString());
        editor.commit();

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}