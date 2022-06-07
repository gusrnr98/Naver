package com.example.sign_up;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.view.View;
import android.os.Bundle;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private Button login;
    private EditText edit_id, edit_pw;
    private SharedPreferences preferences;
    private LinearLayout id_pw_view,id_pw_view2, id_pw_view3;
    private String userid, userpw, id, pw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
    }
    public void init(){
        id_pw_view = findViewById(R.id.id_pw_view);
        id_pw_view2 = findViewById(R.id.id_pw_view2);
        id_pw_view3 = findViewById(R.id.id_pw_view3);
    }
    public void onclick(View v) {
        switch(v.getId()){
            case R.id.view1:
                id_pw_view.setVisibility(View.VISIBLE);
                id_pw_view2.setVisibility(View.INVISIBLE);
                id_pw_view3.setVisibility(View.INVISIBLE);
                break;
            case R.id.view2:
                id_pw_view2.setVisibility(View.VISIBLE);
                id_pw_view.setVisibility(View.INVISIBLE);
                id_pw_view3.setVisibility(View.INVISIBLE);
                break;
            case R.id.view3:
                id_pw_view3.setVisibility(View.VISIBLE);
                id_pw_view2.setVisibility(View.INVISIBLE);
                id_pw_view.setVisibility(View.INVISIBLE);
                break;
        }
    }

    public void login(View v){
        preferences = getSharedPreferences("naver_UserInfo", MODE_PRIVATE);
        edit_id = findViewById(R.id.edit_id);
        id=edit_id.getText().toString();
        edit_pw = findViewById(R.id.edit_pw);
        pw=edit_pw.getText().toString();

        getPreferences();

        if(id.equals(userid) && pw.equals(userpw)){
           showok();
        }
        else{
            showf();
        }

    }
    private void getPreferences(){
        userid= preferences.getString("userid", "");
        userpw= preferences.getString("userpw", "");
    }
    void showok(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("로그인성공");
        builder.setMessage("id = "+id+" userid="+userid+"\n pw = "+pw+" userpw="+userpw);
        Toast toast = Toast.makeText(getApplicationContext(), "성공",Toast.LENGTH_SHORT);

        toast.show();
        builder.show();
    }
    void showf(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("로그인실패");
        builder.setMessage("아이디와 비밀번호를 다시 확인하세요");

        builder.show();
    }
    public void sign_upclick(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}