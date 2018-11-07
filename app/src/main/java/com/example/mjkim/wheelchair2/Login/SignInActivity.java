package com.example.mjkim.wheelchair2.Login;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
public class SignInActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_sign_in);
        email = (EditText) findViewById(R.id.signupActivity_edittext_email);
        name = (EditText) findViewById(R.id.signupActivity_edittext_name);
        password = (EditText) findViewById(R.id.signupActivity_edittext_password);
        signup = (Button) findViewById(R.id.signupActivity_button_signup);
        mAuth = FirebaseAuth.getInstance();

        ImageButton back_button;

        back_button = (ImageButton)findViewById(R.id.back_b);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email1 = email.getText().toString();
                String name1 = name.getText().toString();
                String pw1 = password.getText().toString();
                createUser(email1,name1,pw1);
            }
        });
//        sendEmail();


    }
//    public void sendEmail(){
//        String url = "http://www.example.com/verify?uid=" + currentUser.getUid();
//        ActionCodeSettings actionCodeSettings = ActionCodeSettings.newBuilder()
//                .setUrl(url)
//                .setIOSBundleId("com.example.android")
//                // The default for this is populated with the current android package name.
//                .setAndroidPackageName("com.example.android", false, null)
//                .build();
//
//        currentUser.sendEmailVerification(actionCodeSettings)
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()) {
//                           Toast.makeText(EmailActivity.this,"링크 전송 성공",Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//    }

    private void createUser(String email,final String name, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()) {
                            try {
                                throw task.getException();
                            } catch(FirebaseAuthWeakPasswordException e) {
                                Toast.makeText(SignInActivity.this,"비밀번호가 간단합니다" ,Toast.LENGTH_SHORT).show();
                            } catch(FirebaseAuthInvalidCredentialsException e) {
                                Toast.makeText(SignInActivity.this,"이메일 형식에 맞지 않습니다" ,Toast.LENGTH_SHORT).show();
                            } catch(FirebaseAuthUserCollisionException e) {
                                Toast.makeText(SignInActivity.this,"이미 존재하는 계정 입니다." ,Toast.LENGTH_SHORT).show();
                            } catch(Exception e) {
                                Toast.makeText(SignInActivity.this,"다시 확인해주세요" ,Toast.LENGTH_SHORT).show();
                            }
                        }else {

                            currentUser = mAuth.getCurrentUser();

                            Toast.makeText(SignInActivity.this, "가입 성공  ", Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(SignInActivity.this, LoginScreen.class));
                            finish();
                        }
                    }
                });
    }

}
