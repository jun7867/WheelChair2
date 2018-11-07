package com.example.mjkim.wheelchair2.Login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.mjkim.wheelchair2.BackPressCloseHandler;
import com.example.mjkim.wheelchair2.FirstScreen;
import com.example.mjkim.wheelchair2.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.internal.api.FirebaseNoSignedInUserException;

public class LoginScreen extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{


    private GoogleSignInClient mGoogleSignInClient;
    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;
    private FirebaseAuth mAuth;
    private GoogleApiClient mGoogleApiClient;
    private FirebaseUser currentUser;
    FirebaseUser user;
    private BackPressCloseHandler backPressCloseHandler;
    private FirebaseAuth.AuthStateListener mAuthListener;
    Button pass_btn;  // 로그인없이 이동.
    Button login_btn_google; //구글 로그인 버튼
    Button createUser;  // 회원가입버튼
    Button findId;
    Button findPass;
    EditText emailTxt; //이메일 텍스트
    EditText pwTxt; // 비밀번호 텍스트
    ImageButton loginEmail;
//    LoginButton loginButton;
    ImageButton loginButton;
    CallbackManager mCallbackManager;
    public static int save = 0;
    private CheckBox checkBox;
    private boolean saveLoginData;
    private SharedPreferences appData;
    private String id;
    private String pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        pass_btn=(Button)findViewById(R.id.no_login);
        backPressCloseHandler = new BackPressCloseHandler(this);
        createUser=(Button)findViewById(R.id.create_User);
        emailTxt=(EditText)findViewById(R.id.id_text);
        pwTxt=(EditText)findViewById(R.id.pass_text);
        loginEmail=(ImageButton)findViewById(R.id.login_b2);
        mAuth = FirebaseAuth.getInstance(); // 로그인 작업의 onCreate 메소드에서 FirebaseAuth 개체의 공유 인스턴스를 가져옵니다
        login_btn_google= (Button) findViewById(R.id.login_btn_google);
        findId=(Button)findViewById(R.id.findid_b);
        findPass=(Button)findViewById(R.id.findpass_b);
//        SignInButton googleSign=(SignInButton)findViewById(R.id.google_sign);

        // 설정값 불러오기
        appData = getSharedPreferences("appData", MODE_PRIVATE);
        load();

        emailTxt = (EditText) findViewById(R.id.id_text);
        pwTxt = (EditText) findViewById(R.id.pass_text);
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        loginButton = (ImageButton) findViewById(R.id.login_b2);

        // 이전에 로그인 정보를 저장시킨 기록이 있다면
        if (saveLoginData) {
            emailTxt.setText(id);
            pwTxt.setText(pwd);
            checkBox.setChecked(saveLoginData);
        }

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 로그인 성공시 저장 처리, 예제는 무조건 저장
                save();
            }
        });

        //로그인 버튼을 눌렀을때.
        loginEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=emailTxt.getText().toString();
                String password=pwTxt.getText().toString();
                loginStart(email,password); // 성공했을때 메인페이지로 보냄.
            }
        });
        //구글 로그인 버튼을 눌렀을때
        login_btn_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("알림", "구글 LOGIN");

                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });
        //로그인없이 이동 눌렀을때
        pass_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save = 0;
                Intent intent=new Intent(LoginScreen.this,FirstScreen.class);
                startActivity(intent);
            }
        });
        //회원가입 버튼을 눌렀을때
        createUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginScreen.this,SignInActivity.class);
                startActivity(intent);
            }
        });
        findId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginScreen.this,"준비중입니다",Toast.LENGTH_SHORT).show();
            }
        });
        findPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginScreen.this,"준비중입니다",Toast.LENGTH_SHORT).show();
            }
        });
        mCallbackManager = CallbackManager.Factory.create();
      //  loginButton =(LoginButton)findViewById(R.id.facebook);
//        loginButton.setReadPermissions("email", "public_profile");
//        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(LoginResult loginResult) {
//                Log.d(TAG, "facebook:onSuccess:" + loginResult);
//                handleFacebookAccessToken(loginResult.getAccessToken());
//            }
//
//            @Override
//            public void onCancel() {
//                Log.d(TAG, "facebook:onCancel");
//                // ...
//            }
//
//            @Override
//            public void onError(FacebookException error) {
//                Log.d(TAG, "facebook:onError", error);
//                // ...
//            }
//        });
        // Configure Google Sign In 구글 인증하기
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)

                .enableAutoManage(this, this )

                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)

                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        login_btn_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });


        mAuthListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user= firebaseAuth.getCurrentUser();
                if(user!=null){  //

                }else{

                }
            }
        };
    }

    private void save() {
        // SharedPreferences 객체만으론 저장 불가능 Editor 사용
        SharedPreferences.Editor editor = appData.edit();

        // 에디터객체.put타입( 저장시킬 이름, 저장시킬 값 )
        // 저장시킬 이름이 이미 존재하면 덮어씌움
        editor.putBoolean("SAVE_LOGIN_DATA", checkBox.isChecked());
        editor.putString("ID", emailTxt.getText().toString().trim());
        editor.putString("PWD", pwTxt.getText().toString().trim());

        // apply, commit 을 안하면 변경된 내용이 저장되지 않음
        editor.apply();
    }

    // 설정값을 불러오는 함수
    private void load() {
        // SharedPreferences 객체.get타입( 저장된 이름, 기본값 )
        // 저장된 이름이 존재하지 않을 시 기본값
        saveLoginData = appData.getBoolean("SAVE_LOGIN_DATA", false);
        id = appData.getString("ID", "");
        pwd = appData.getString("PWD", "");
    }

    public void loginStart(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthInvalidUserException e) {
                        Toast.makeText(LoginScreen.this, "존재하지 않는 계정입니다 ", Toast.LENGTH_SHORT).show();
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        Toast.makeText(LoginScreen.this, "이메일 형식이 맞지 않습니다", Toast.LENGTH_SHORT).show();
                    } catch (FirebaseNetworkException e) {
                        Toast.makeText(LoginScreen.this, "네트워크 문제가 발생했습니다", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(LoginScreen.this, "Exception", Toast.LENGTH_SHORT).show();
                    }


                } else {

                    save = 1;
                    currentUser = mAuth.getCurrentUser();
                    Toast.makeText(LoginScreen.this, "로그인 성공", Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(LoginScreen.this, FirstScreen.class));
                    finish();
                }

            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            user = mAuth.getCurrentUser();
                            Toast.makeText(LoginScreen.this,"구글 로그인완료",Toast.LENGTH_SHORT).show();
                            save = 1;
                            Intent intent=new Intent(LoginScreen.this, FirstScreen.class);
                            startActivity(intent);
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginScreen.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });


    }
//    private void handleFacebookAccessToken(AccessToken token) {
//        Log.d(TAG, "handleFacebookAccessToken:" + token);
//
//        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
//        mAuth.signInWithCredential(credential)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//                            Toast.makeText(LoginScreen.this,"페이스북 로그인 성공",Toast.LENGTH_SHORT).show();
//                            save = 1;
//                            FirebaseUser user = mAuth.getCurrentUser();
//                            updateUI(user);
//                            Intent intent=new Intent(LoginScreen.this,FirstScreen.class); // 로그인 성공시 페이지 이동.
//                            startActivity(intent);
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Log.w(TAG, "signInWithCredential:failure", task.getException());
//                            Toast.makeText(LoginScreen.this, "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();
//                            updateUI(null);
//                        }
//
//                        // ...
//                    }
//                });
//    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(LoginScreen.this,"연결이 해제되었습니다",Toast.LENGTH_SHORT).show();
    }
    private void updateUI(FirebaseUser user) {

    }


    public void onClick(View view) {

    }

    public void onBackPressed(){
        backPressCloseHandler.onBackPressed();
    }
//    로그아웃 안했으면, 즉 로그인 되어있으면 자동으로 메인페이지로 이동시키기
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            save = 1;
            startActivity(new Intent(LoginScreen.this, FirstScreen.class));
            finish();
        }
    }

}
