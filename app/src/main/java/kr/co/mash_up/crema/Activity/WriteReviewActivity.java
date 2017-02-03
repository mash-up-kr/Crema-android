package kr.co.mash_up.crema.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import kr.co.mash_up.crema.R;

/**
 * Created by sun on 2017. 1. 30..
 */

public class WriteReviewActivity extends AppCompatActivity  {
    Button btn_image;
    ImageView img1;

    private Uri mImageCaptureUri;
    private String absolutePath;

    private static final int PICK_FROM_CAMERA = 0;
    private static final int PICK_FROM_ALBUM = 1;
    private static final int CROP_FROM_IMAGE = 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.writereview);

        init();

        btn_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext())
                        .setTitle("업로드할 이미지 선택")
                        .setPositiveButton("사진촬영", cameraListener)
                        .setNeutralButton("앨범선택",albumListener)
                        .setNegativeButton("취소", cancelListener)
                        .show();

            }

            DialogInterface.OnClickListener cameraListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    doTakePhotoAction();
                }
            };
            DialogInterface.OnClickListener albumListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    doTakealbumAction();
                }
            };
            DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            };

        });

    }

    public void init(){
        btn_image=(Button)findViewById(R.id.btn_addimage);
        img1=(ImageView)findViewById(R.id.iv_img1);
    }

    public void doTakePhotoAction(){
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        String url = "tmp"+String.valueOf(System.currentTimeMillis()) +".jpg";
        mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),url));

        i.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
        startActivityForResult(i,PICK_FROM_CAMERA);
    }

    public void doTakealbumAction(){
        Intent i = new Intent(Intent.ACTION_PICK);
        i.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(i,PICK_FROM_ALBUM);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        Intent i;

        if(resultCode!=RESULT_OK)
            return;

        switch(requestCode){
            case PICK_FROM_ALBUM:
                mImageCaptureUri = data.getData();
                i = new Intent("com.android.camera.action.CROP");
                i.setDataAndType(mImageCaptureUri, "image/*");

                i.putExtra("outputX",200);
                i.putExtra("outputY",200);
                i.putExtra("aspectX",1);
                i.putExtra("aspectY",1);
                i.putExtra("scale", true);
                i.putExtra("return-data",true);
                startActivityForResult(i,CROP_FROM_IMAGE);
                break;
            case PICK_FROM_CAMERA:
                i = new Intent("com.android.camera.action.CROP");
                i.setDataAndType(mImageCaptureUri, "image/*");

                i.putExtra("outputX",200);
                i.putExtra("outputY",200);
                i.putExtra("aspectX",1);
                i.putExtra("aspectY",1);
                i.putExtra("scale", true);
                i.putExtra("return-data",true);
                startActivityForResult(i,CROP_FROM_IMAGE);
                break;
            case CROP_FROM_IMAGE:
                if(resultCode!=RESULT_OK)
                    return;
                final Bundle extras = data.getExtras();

                String filePath = Environment.getDownloadCacheDirectory().getAbsolutePath()
                        +"/Crema/"+System.currentTimeMillis()+".jpg";
                if(extras!=null){
                    Bitmap photo = extras.getParcelable("data");
                    img1.setImageBitmap(photo);

                    storeCropImage(photo, filePath);
                    absolutePath=filePath;
                    break;

                }

                File f = new File(mImageCaptureUri.getPath());
                if(f.exists())
                    f.delete();

        }

    }
    private void storeCropImage(Bitmap bitmap, String filePath){
        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/Crema";
        File directory_Crema = new File(dirPath);

        if(!directory_Crema.exists())
            directory_Crema.mkdir();

        File copyFile = new File(filePath);
        BufferedOutputStream out = null;

        try{
            copyFile.createNewFile();
            out= new BufferedOutputStream(new FileOutputStream(copyFile));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);

            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(copyFile)));

            out.flush();
            out.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
