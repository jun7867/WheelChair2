package com.example.mjkim.wheelchair2.Login;

import android.app.DialogFragment;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mjkim.wheelchair2.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
// 회원가입하는 액티비티
public class EmailActivity extends AppCompatActivity {
    private EditText email;
    private EditText name;
    private EditText password;
    private Button signup;
 //   private DialogFragment progressDialog;
    FirebaseAuth mAuth; //로그인 변수
    FirebaseUser currentUser;  // 정보 담을거.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);
        email = (EditText) findViewById(R.id.signupActivity_edittext_email);
        name = (EditText) findViewById(R.id.signupActivity_edittext_name);
        password = (EditText) findViewById(R.id.signupActivity_edittext_password);
        signup = (Button) findViewById(R.id.signupActivity_button_signup);
        mAuth = FirebaseAuth.getInstance();
//        FirebaseAuth.getInstance()
//                .createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
//                .addOnCompleteListener(EmailActivity.this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        final String uid = task.getResult().getUser().getUid();
//                        progressDialog.dismiss();
//                        Toast.makeText(getApplicationContext(), "회원가입을 성공하셨습니다.", Toast.LENGTH_SHORT).show();
//                        finish();
//                    }
//                });


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email1 = email.getText().toString();
                String name1 = name.getText().toString();
                String pw1 = password.getText().toString();
                createUser(email1,name1,pw1);
            }
        });

    }
    private void createUser(String email,final String name, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()) {
                            try {
                                throw task.getException();
                            } catch(FirebaseAuthWeakPasswordException e) {
                                Toast.makeText(EmailActivity.this,"비밀번호가 간단해요.." ,Toast.LENGTH_SHORT).show();
                            } catch(FirebaseAuthInvalidCredentialsException e) {
                                Toast.makeText(EmailActivity.this,"email 형식에 맞지 않습니다." ,Toast.LENGTH_SHORT).show();
                            } catch(FirebaseAuthUserCollisionException e) {
                                Toast.makeText(EmailActivity.this,"이미존재하는 email 입니다." ,Toast.LENGTH_SHORT).show();
                            } catch(Exception e) {
                                Toast.makeText(EmailActivity.this,"다시 확인해주세요.." ,Toast.LENGTH_SHORT).show();
                            }
                        }else {

                            currentUser = mAuth.getCurrentUser();

                            Toast.makeText(EmailActivity.this, "가입 성공  ", Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(EmailActivity.this, LoginScreen.class));
                            finish();
                        }
                    }
                });
    }
}
