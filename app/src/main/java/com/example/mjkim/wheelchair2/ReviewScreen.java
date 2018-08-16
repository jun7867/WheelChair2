package com.example.mjkim.wheelchair2;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mjkim.wheelchair2.Review.ReviewData;
import com.example.mjkim.wheelchair2.Review.ReviewList;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ReviewScreen extends AppCompatActivity {

    private static final int pick_from_camera = 0;
    private static final int pick_from_album = 1; // 갤러리 불러올때 요청 상수.
    private static final int REQUEST_IMAGE_CAPTURE = 672;
    private int id_view;
    private FirebaseStorage storage;
    StorageReference storageRef;
    public ReviewList reviewList = new ReviewList();
    ReviewData reviewData = new ReviewData();
    String imagePath1="";
    String imagePath2="";
    String imagePath3="";
    String imagePath4="";
    String imagePath5="";
    String imagePath6="";

    String phone ="";

    double rating;
    //Boolean tag[] = new Boolean[6];
    Boolean tag1,tag2,tag3,tag4,tag5,tag6;
    String review;

    ImageButton back_button;
    ImageButton menu_button;
    Button map_search;
    ImageView picture1,picture2,picture3,picture4,picture5,picture6;
    EditText review_text;
    RatingBar review_rating_bar;
    CheckBox chk[] = new CheckBox[6];
    TextView location_name, address_name, phone_number;
    static String downloadUrl;
    static String name;
    int pic1=0;
    int pic2=0;
    int pic3=0;
    int pic4=0;
    int pic5=0;
    int pic6=0;
    int mapx, mapy;
    private String imageFilePath1="";
    private String imageFilePath2="";
    private String imageFilePath3="";
    private String imageFilePath4="";
    private String imageFilePath5="";
    private String imageFilePath6="";

    private Uri photoUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},0);
        }
        setContentView(R.layout.activity_review_screen);

        storage=FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        Integer[] numChkIDs = { R.id.check_box_b1, R.id.check_box_b2, R.id.check_box_b3,
                R.id.check_box_b4, R.id.check_box_b5, R.id.check_box_b6};

        back_button = (ImageButton)findViewById(R.id.back_b);
        menu_button = (ImageButton)findViewById(R.id.menu_b);
        map_search = (Button)findViewById(R.id.current_b);
        picture1 = (ImageView)findViewById(R.id.picture1);
        picture2 = (ImageView)findViewById(R.id.picture2);
        picture3 = (ImageView)findViewById(R.id.picture3);
        picture4 = (ImageView)findViewById(R.id.picture4);
        picture5 = (ImageView)findViewById(R.id.picture5);
        picture6 = (ImageView)findViewById(R.id.picture6);

        review_text = (EditText) findViewById(R.id.review_text);
        review_rating_bar = (RatingBar) findViewById(R.id.ratingBar);
        location_name = (TextView) findViewById(R.id.location_name);
        address_name = (TextView)findViewById(R.id.address_name);
        phone_number = (TextView)findViewById(R.id.telephone_number);


        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        menu_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){ openMenuTab(); }});
        map_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { openMapSearch();
            }
        });

        //시설정보
        Intent intent = getIntent();
        location_name.setText(intent.getExtras().getString("NAME"));
        name = intent.getExtras().getString("NAME");
        address_name.setText(intent.getExtras().getString("ROAD_ADDRESS"));
        phone_number.setText(intent.getExtras().getString("TELEPHONE"));
        phone = intent.getExtras().getString("TELEPHONE");
        mapx = intent.getExtras().getInt("MAPX");
        mapy = intent.getExtras().getInt("MAPY");

        phone_number.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent1 = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
                startActivity(intent1);
            }
        });

        //별점
        review_rating_bar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float temp_rating, boolean fromUser) { rating = temp_rating; }});

        //체크박스
        for(int i = 0; i < 6; i++){ chk[i] = (CheckBox)findViewById(numChkIDs[i]); }
    }

    public void openMenuTab(){
        Intent intent = new Intent(this, MenuScreen.class);
        startActivity(intent);
    }

    public void onClick(View view){

        id_view = view.getId();
        if(view.getId() == R.id.picture1 || view.getId() == R.id.picture2 || view.getId() == R.id.picture3
                || view.getId() == R.id.picture4 || view.getId() == R.id.picture5 || view.getId() == R.id.picture6){
            DialogInterface.OnClickListener cameraListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) { doTakePhotoAction(); }
            };

            DialogInterface.OnClickListener albumListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) { doTakeAlbumAction(); }
            };
            DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) { dialogInterface.dismiss(); }
            };

            new AlertDialog.Builder(this)
                    .setTitle("업로드할 이미지 선택")
                    .setPositiveButton("앨범선택", albumListener)
                    .setNeutralButton("취소", cancelListener)
                    .setNegativeButton("사진촬영", cameraListener)
                    .show();
        }
    }
    public void doTakePhotoAction(){  //카메라에서 사진 가져오기.
        int permissioncheck= ContextCompat.checkSelfPermission(ReviewScreen.this,Manifest.permission.CAMERA);
        if(permissioncheck== PackageManager.PERMISSION_DENIED){
            //권한없음
            ActivityCompat.requestPermissions(ReviewScreen.this,new String[]{Manifest.permission.CAMERA},0);
            Toast.makeText(this,"권한없음",Toast.LENGTH_SHORT).show();
        }
        else{ // 권한있
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException ex) {
                    // Error occurred while creating the File
                }

                if (photoFile != null) {
                    photoUri = FileProvider.getUriForFile(this, getPackageName(), photoFile);
                    System.out.println("포토 유알이"+photoUri);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,@NonNull String[] permissions,@NonNull int[] grantResults){
        if(requestCode==0){
            if(grantResults[0]==0){
                Toast.makeText(this,"카메라 권한 승인",Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this,"권한 승인이 거절되었습니다. 카메라를 이용하려면 권한을 승인해야 합니다.",Toast.LENGTH_SHORT).show();
            }
        }
    }
    private int exifOrientationToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }
        return 0;
    }

    private Bitmap rotate(Bitmap bitmap, float degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }
    public void doTakeAlbumAction(){ //앨범에서 이미지 가져오기
        //앨범 호출
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, pick_from_album );
    }
    public String getPath(Uri uri){
        String [] proj={MediaStore.Images.Media.DATA};
        CursorLoader cursorLoader=new CursorLoader(this,uri,proj,null,null,null);
        Cursor cursor=cursorLoader.loadInBackground();
        int index=cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(index);
    }
    @Override
    protected  void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if(resultCode == RESULT_OK){
            if(requestCode == REQUEST_IMAGE_CAPTURE){
                if(id_view == R.id.picture1){
                    Bitmap bitmap = BitmapFactory.decodeFile(imageFilePath1);
                    ExifInterface exif = null;
                    try {
                        exif = new ExifInterface(imageFilePath1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    int exifOrientation;
                    int exifDegree;

                    if (exif != null) {
                        exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                        exifDegree = exifOrientationToDegrees(exifOrientation);
                    } else {
                        exifDegree = 0;
                    }
                    picture1.setImageBitmap(rotate(bitmap, exifDegree));
                    pic1=2;
                }
                if(id_view == R.id.picture2){
                    Bitmap bitmap = BitmapFactory.decodeFile(imageFilePath2);
                    ExifInterface exif = null;

                    try {
                        exif = new ExifInterface(imageFilePath2);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    int exifOrientation;
                    int exifDegree;
                    if (exif != null) {
                        exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                        exifDegree = exifOrientationToDegrees(exifOrientation);
                    } else {
                        exifDegree = 0;
                    }
                    picture2.setImageBitmap(rotate(bitmap, exifDegree));
                    pic2=2;
                }
                if(id_view == R.id.picture3){
                    Bitmap bitmap = BitmapFactory.decodeFile(imageFilePath3);
                    ExifInterface exif = null;

                    try {
                        exif = new ExifInterface(imageFilePath3);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    int exifOrientation;
                    int exifDegree;

                    if (exif != null) {
                        exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                        exifDegree = exifOrientationToDegrees(exifOrientation);
                    } else {
                        exifDegree = 0;
                    }
                    picture3.setImageBitmap(rotate(bitmap, exifDegree));
                    pic3=2;
                }
                if(id_view == R.id.picture4){
                    Bitmap bitmap = BitmapFactory.decodeFile(imageFilePath4);
                    ExifInterface exif = null;

                    try {
                        exif = new ExifInterface(imageFilePath4);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    int exifOrientation;
                    int exifDegree;

                    if (exif != null) {
                        exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                        exifDegree = exifOrientationToDegrees(exifOrientation);
                    } else {
                        exifDegree = 0;
                    }
                    picture4.setImageBitmap(rotate(bitmap, exifDegree));
                    pic4=2;
                }
                if(id_view == R.id.picture5){
                    Bitmap bitmap = BitmapFactory.decodeFile(imageFilePath5);
                    ExifInterface exif = null;

                    try {
                        exif = new ExifInterface(imageFilePath5);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    int exifOrientation;
                    int exifDegree;

                    if (exif != null) {
                        exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                        exifDegree = exifOrientationToDegrees(exifOrientation);
                    } else {
                        exifDegree = 0;
                    }
                    picture5.setImageBitmap(rotate(bitmap, exifDegree));
                    pic5=2;
                }
                if(id_view == R.id.picture6){
                    Bitmap bitmap = BitmapFactory.decodeFile(imageFilePath6);
                    ExifInterface exif = null;

                    try {
                        exif = new ExifInterface(imageFilePath6);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    int exifOrientation;
                    int exifDegree;

                    if (exif != null) {
                        exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                        exifDegree = exifOrientationToDegrees(exifOrientation);
                    } else {
                        exifDegree = 0;
                    }
                    picture6.setImageBitmap(rotate(bitmap, exifDegree));
                    pic6=2;
                }
            }

            }
            if(requestCode==pick_from_album) {
                if (id_view == R.id.picture1) {
                    imagePath1 = getPath(data.getData());
                    File f1 = new File(imagePath1);
                    picture1.setImageURI(Uri.fromFile(f1));
                    pic1=1;
                }
                if (id_view == R.id.picture2) {
                    imagePath2 = getPath(data.getData());
                    File f2 = new File(imagePath2);
                    picture2.setImageURI(Uri.fromFile(f2));
                    pic2=1;
                }
                if (id_view == R.id.picture3) {
                    imagePath3 = getPath(data.getData());
                    File f3 = new File(imagePath3);
                    picture3.setImageURI(Uri.fromFile(f3));
                    pic3=1;
                }
                if (id_view == R.id.picture4) {
                    imagePath4 = getPath(data.getData());
                    File f4 = new File(imagePath4);
                    picture4.setImageURI(Uri.fromFile(f4));
                    pic4=1;
                }
                if (id_view == R.id.picture5) {
                    imagePath5 = getPath(data.getData());
                    File f5 = new File(imagePath5);
                    picture5.setImageURI(Uri.fromFile(f5));
                    pic5=1;
                }
                if (id_view == R.id.picture6) {
                    imagePath6 = getPath(data.getData());
                    File f6 = new File(imagePath6);
                    picture6.setImageURI(Uri.fromFile(f6));
                    pic6=1;
                }
            }
        }
    private File createImageFile() throws IOException {  //이미지가 저장될 파일을 만드는 함수
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "TEST_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,      /* prefix */
                ".jpg",         /* suffix */
                storageDir          /* directory */
        );
        if(id_view==R.id.picture1){
            imageFilePath1 = image.getAbsolutePath();
        }
        if(id_view==R.id.picture2){
            imageFilePath2 = image.getAbsolutePath();
        }
        if(id_view==R.id.picture3){
            imageFilePath3 = image.getAbsolutePath();
        }
        if(id_view==R.id.picture4){
            imageFilePath4 = image.getAbsolutePath();
        }
        if(id_view==R.id.picture5){
            imageFilePath5 = image.getAbsolutePath();
        }
        if(id_view==R.id.picture6){
            imageFilePath6 = image.getAbsolutePath();
        }

        return image;
    }
   // 사진 링크를 저장하는 함수.
   private void upload(final String uri, final String uri2, final String uri3,final String uri4,final String uri5,final String uri6){
        System.out.println("시작");

           Uri file = Uri.fromFile(new File(uri));
           if(pic1==2){ // 사진으로 찍은거면 file을 바꾸기.
               file = Uri.fromFile(new File(imageFilePath1));
            }
           StorageReference riverRef=storageRef.child("images/"+file.getLastPathSegment());
           UploadTask uploadTask = riverRef.putFile(file);
           uploadTask.addOnFailureListener(new OnFailureListener() {
               @Override
               public void onFailure(@NonNull Exception e) {
                   upload2(uri2,uri3,uri4,uri5,uri6);
                   System.out.println("실패1");
               }
           }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
               @Override
               public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                   downloadUrl=taskSnapshot.toString();
                   System.out.println("다운1: "+downloadUrl);
                   reviewList.setImageUrl1(downloadUrl);
                   if(pic2!=0){ upload2(uri2,uri3,uri4,uri5,uri6); }
                   else if(pic3!=0){ upload3(uri3,uri4,uri5,uri6); }
                   else if(pic4!=0){ upload4(uri4,uri5,uri6); }
                   else if(pic5!=0){ upload5(uri5,uri6); }
                   else if (pic6 != 0) { upload6(uri6); }
                   else{ reviewData.saveData(name, reviewList); }
               }
           });
   }
   public void upload2(String uri2, final String uri3, final String uri4, final String uri5, final String uri6){
       Uri file2 = Uri.fromFile(new File(uri2));
       if(pic2==2){ // 사진으로 찍은거면 file을 바꾸기.
           file2 = Uri.fromFile(new File(imageFilePath2));
       }
       StorageReference riverRef2=storageRef.child("images/"+file2.getLastPathSegment());
       UploadTask uploadTask2 = riverRef2.putFile(file2);
       uploadTask2.addOnFailureListener(new OnFailureListener() {
           @Override
           public void onFailure(@NonNull Exception e) {
                upload3(uri3,uri4,uri5,uri6);
               System.out.println("실패2");
           }
       }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
           @Override
           public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
               downloadUrl=taskSnapshot.toString();
               System.out.println("다운2: "+downloadUrl);
               reviewList.setImageUrl2(downloadUrl);
               if(pic3!=0){ upload3(uri3,uri4,uri5,uri6); }
               else if(pic4!=0){ upload4(uri4,uri5,uri6); }
               else if(pic5!=0){ upload5(uri5,uri6); }
               else if (pic6 != 0) { upload6(uri6); }
               else{ reviewData.saveData(name, reviewList); }
           }
       });
   }
   public void upload3(String uri3, final String uri4, final String uri5, final String uri6){
       Uri file3 = Uri.fromFile(new File(imagePath3));
       if(pic3==2){ // 사진으로 찍은거면 file을 바꾸기.
           file3 = Uri.fromFile(new File(imageFilePath3));
       }
       StorageReference riverRef3=storageRef.child("images/"+file3.getLastPathSegment());
       UploadTask uploadTask3 = riverRef3.putFile(file3);
       uploadTask3.addOnFailureListener(new OnFailureListener() {
           @Override
           public void onFailure(@NonNull Exception e) {
                    upload4(uri4,uri5,uri6);
               System.out.println("실패3");
           }
       }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
           @Override
           public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
               downloadUrl=taskSnapshot.toString();
               System.out.println("다운3: "+downloadUrl);
               reviewList.setImageUrl3(downloadUrl);
               if(pic4!=0){ upload4(uri4,uri5,uri6); }
               else if(pic5!=0){ upload5(uri5, uri6); }
               else if (pic6 != 0) { upload6(uri6); }
               else{ reviewData.saveData(name, reviewList); }
           }
       });
   }
   public void upload4(String uri4, final String uri5, final String uri6){
       Uri file4 = Uri.fromFile(new File(imagePath4));
       if(pic4==2){ // 사진으로 찍은거면 file을 바꾸기.
           file4 = Uri.fromFile(new File(imageFilePath4));
       }
       StorageReference riverRef4=storageRef.child("images/"+file4.getLastPathSegment());
       UploadTask uploadTask4 = riverRef4.putFile(file4);
       uploadTask4.addOnFailureListener(new OnFailureListener() {
           @Override
           public void onFailure(@NonNull Exception e) {
                System.out.println("실패4");
                upload5(uri5,uri6);
           }
       }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
           @Override
           public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
               downloadUrl=taskSnapshot.toString();
               System.out.println("다운4: "+downloadUrl);
               reviewList.setImageUrl4(downloadUrl);
               if(pic5!=0){ upload5(uri5,uri6); }
               else if (pic6 != 0) { upload6(uri6); }
               else{ reviewData.saveData(name, reviewList); }
           }
       });
   }
         public void upload5(String uri5, final String uri6){
          Uri file5 = Uri.fromFile(new File(imagePath5));
       if(pic5==2){ // 사진으로 찍은거면 file을 바꾸기.
           file5 = Uri.fromFile(new File(imageFilePath5));
       }
       StorageReference riverRef5=storageRef.child("images/"+file5.getLastPathSegment());
       UploadTask uploadTask5 = riverRef5.putFile(file5);
       uploadTask5.addOnFailureListener(new OnFailureListener() {
           @Override
           public void onFailure(@NonNull Exception e) {
               System.out.println("실패5");
               upload6(uri6);
           }
       }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
           @Override
           public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
               downloadUrl=taskSnapshot.toString();
               System.out.println("다운5: "+downloadUrl);
               reviewList.setImageUrl5(downloadUrl);
               if (pic6 != 0) { upload6(uri6); }
               else{ reviewData.saveData(name, reviewList); }
           }
       });
   }
   public void upload6(String uri6){
       Uri file6 = Uri.fromFile(new File(imagePath6));
       if(pic6==2){ // 사진으로 찍은거면 file을 바꾸기.
           file6 = Uri.fromFile(new File(imageFilePath6));
       }
       StorageReference riverRef6=storageRef.child("images/"+file6.getLastPathSegment());
       UploadTask uploadTask6 = riverRef6.putFile(file6);
       uploadTask6.addOnFailureListener(new OnFailureListener() {
           @Override
           public void onFailure(@NonNull Exception e) {
               System.out.println("실패6");
           }
       }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
           @Override
           public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
               downloadUrl=taskSnapshot.toString();
               System.out.println("다운6: "+downloadUrl);
               reviewList.setImageUrl6(downloadUrl);
               reviewData.saveData(name,reviewList);
           }
       });
   }
    public void openMapSearch(){
        Intent intent = new Intent(this, WatchLocationActivity.class);
        intent.putExtra("NAME", name);
        intent.putExtra("MAPX", mapx);
        intent.putExtra("MAPY", mapy);
        startActivity(intent);
    }
    public void onFinish(View view){ //등록눌렀을때.
        Intent review_intent = getIntent();

        name = review_intent.getExtras().getString("NAME");
        double mapx = (double)review_intent.getExtras().getInt("MAPX");
        double mapy = (double)review_intent.getExtras().getInt("MAPY");

        //리뷰 문장 받아오기
        review = review_text.getText().toString();

        if(chk[0].isChecked() == true) tag1 = true; else tag1 = false;
        if(chk[1].isChecked() == true) tag2 = true; else tag2 = false;
        if(chk[2].isChecked() == true) tag3 = true; else tag3 = false;
        if(chk[3].isChecked() == true) tag4 = true; else tag4 = false;
        if(chk[4].isChecked() == true) tag5 = true; else tag5 = false;
        if(chk[5].isChecked() == true) tag6 = true; else tag6 = false;

        reviewList = new ReviewList(rating, tag1,tag2,tag3,tag4,tag5,tag6, review,mapx,mapy);

        if(pic1==1 || pic2==1 || pic3==1 || pic4==1 || pic5==1 || pic6==1 ||
        pic1==2 || pic2==2 || pic3==2 || pic4==2 || pic5==2 || pic6==2) {  // 사진이 하나라도 있으면.
            upload(imagePath1, imagePath2, imagePath3,imagePath4,imagePath5,imagePath6);
        }
        if(pic1 !=1 && pic1 !=2 && pic2 !=1 && pic2 !=2 && pic3 !=1 && pic3 !=2 && pic4 !=1 && pic4 !=2 &&
               pic5!=1 && pic5!=2 && pic6 !=1 && pic6 !=2 ) {
            reviewData.saveData(name, reviewList);  //사진 없을때
        }
        Toast.makeText(this, "리뷰가 등록되었습니다 ", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, FirstScreen.class);
        startActivity(intent);
    }
}