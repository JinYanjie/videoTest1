package com.example.myapplication.ep_video_use;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.myapplication.R;

import java.io.File;

import VideoHandle.EpDraw;
import VideoHandle.EpEditor;
import VideoHandle.EpVideo;
import VideoHandle.OnEditorListener;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class EditActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String[] parm = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    private static final String TAG = "EditActivity";
    private static final int CHOOSE_FILE = 10;
    private CheckBox cb_clip, cb_crop, cb_rotation, cb_mirror, cb_text;
    private EditText et_clip_start, et_clip_end, et_crop_x, et_crop_y, et_crop_w, et_crop_h, et_rotation, et_text_x, et_text_y, et_text;
    private TextView tv_file;
    private Button bt_file, bt_exec;
    private String videoUrl;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        initView();
        checkPermission();
    }

    private void initView() {
        cb_clip = (CheckBox) findViewById(R.id.cb_clip);
        cb_crop = (CheckBox) findViewById(R.id.cb_crop);
        cb_rotation = (CheckBox) findViewById(R.id.cb_rotation);
        cb_mirror = (CheckBox) findViewById(R.id.cb_mirror);
        cb_text = (CheckBox) findViewById(R.id.cb_text);
        et_clip_start = (EditText) findViewById(R.id.et_clip_start);
        et_clip_end = (EditText) findViewById(R.id.et_clip_end);
        et_crop_x = (EditText) findViewById(R.id.et_crop_x);
        et_crop_y = (EditText) findViewById(R.id.et_crop_y);
        et_crop_w = (EditText) findViewById(R.id.et_crop_w);
        et_crop_h = (EditText) findViewById(R.id.et_crop_h);
        et_rotation = (EditText) findViewById(R.id.et_rotation);
        et_text_x = (EditText) findViewById(R.id.et_text_x);
        et_text_y = (EditText) findViewById(R.id.et_text_y);
        et_text = (EditText) findViewById(R.id.et_text);
        tv_file = (TextView) findViewById(R.id.tv_file);
        bt_file = (Button) findViewById(R.id.bt_file);
        bt_exec = (Button) findViewById(R.id.bt_exec);
        bt_file.setOnClickListener(this);
        bt_exec.setOnClickListener(this);
        cb_mirror.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cb_rotation.setChecked(true);
                }
            }
        });
        cb_rotation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    cb_mirror.setChecked(false);
                }
            }
        });
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setMax(100);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setTitle("正在处理");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_file:
                chooseFile();
                break;
            case R.id.bt_exec:
                execVideo();
//				test();
                break;
        }
    }

    /**
     * 选择文件
     */
    private void chooseFile() {
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, CHOOSE_FILE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CHOOSE_FILE:
                if (resultCode == RESULT_OK) {
                    Log.e(TAG, "onActivityResult: " + data.getData());
//                    content://com.android.fileexplorer.myprovider/external_files/tencent/MicroMsg/WeiXin/wx_camera_1556684671699.mp4
//                     /storage/emulated/0/                                        tencent/MicroMsg/WeiXin/wx_camera_1556684671699.mp4
                    videoUrl = UriUtils.getPath(EditActivity.this, data.getData());

                    Log.e(TAG, "onActivityResult: " + videoUrl);
                    tv_file.setText(videoUrl);
                    break;
                }
        }
    }

    /**
     * 开始编辑
     */
    private void execVideo() {

        File file1 = new File("/storage/emulated/0/DCIM/Camera/VID_20190506_175616.mp4");
        Log.e(TAG, "onActivityResult: 2  " + file1.length());


        if (videoUrl != null && !"".equals(videoUrl)) {
            EpVideo epVideo = new EpVideo(videoUrl);
            if (cb_clip.isChecked())
                epVideo.clip(Float.parseFloat(et_clip_start.getText().toString().trim()), Float.parseFloat(et_clip_end.getText().toString().trim()));
            if (cb_crop.isChecked())
                epVideo.crop(Integer.parseInt(et_crop_w.getText().toString().trim()), Integer.parseInt(et_crop_h.getText().toString().trim()), Integer.parseInt(et_crop_x.getText().toString().trim()), Integer.parseInt(et_crop_y.getText().toString().trim()));
            if (cb_rotation.isChecked())
                epVideo.rotation(Integer.parseInt(et_rotation.getText().toString().trim()), cb_mirror.isChecked());
            if (cb_text.isChecked()) {
                epVideo.addText(Integer.parseInt(et_text_x.getText().toString().trim()), Integer.parseInt(et_text_y.getText().toString().trim()), 30, "red", "/storage/emulated/0/Download/music.mp4" + "msyh.ttf", et_text.getText().toString().trim());
            }
            File externalCacheDir = getExternalCacheDir();
            Log.e(TAG, "execVideo: "+externalCacheDir.getAbsolutePath() );
            String imgPath = "/storage/emulated/0/DCIM/Camera/IMG_20161106_133015.jpg";
            epVideo.addDraw(new EpDraw(imgPath, 800, 1600, 200, 200, false));

            mProgressDialog.setProgress(0);
            mProgressDialog.show();
            final String outPath = "/storage/emulated/0/DCIM/Camera/33333.mp4";

            /**
             * 在外部缓存目录 创建文件夹
             * /storage/emulated/0/Android/data/com.example.myapplication/cache/vimg/
             */
            File file = new File(externalCacheDir.getAbsolutePath() + "/vimg");
            boolean mkdirs = file.mkdirs();
            final String outPath2 = externalCacheDir.getAbsolutePath()+"/vimg/pic%03d.jpg";
            EpEditor.video2pic(videoUrl, outPath2, 1080, 1920, 1, new OnEditorListener() {
                @Override
                public void onSuccess() {
                    Log.e(TAG, "onSuccess: " + "图片处理成功" + outPath2);
                    mProgressDialog.dismiss();
                }

                @Override
                public void onFailure() {

                }

                @Override
                public void onProgress(float progress) {

                }
            });

//            EpEditor.exec(epVideo, new EpEditor.OutputOption(outPath), new OnEditorListener() {
//                @Override
//                public void onSuccess() {
////                    Toast.makeText(EditActivity.this, "编辑完成:" + outPath, Toast.LENGTH_SHORT).show();
//                    mProgressDialog.dismiss();
//
//                    Intent v = new Intent(Intent.ACTION_VIEW);
//                    v.setDataAndType(Uri.parse(outPath), "video/mp4");
//                    startActivity(v);
//                }
//
//                @Override
//                public void onFailure() {
////					Toast.makeText(EditActivity.this, "编辑失败", Toast.LENGTH_SHORT).show();
//                    mProgressDialog.dismiss();
//                }
//
//                @Override
//                public void onProgress(float v) {
//                    mProgressDialog.setProgress((int) (v * 100));
//                }
//            });
//        } else {
//            Toast.makeText(this, "选择一个视频", Toast.LENGTH_SHORT).show();
        }
    }

    private void test() {
        final String outPath = "/storage/emulated/0/Download/music.mp4";
        EpEditor.music(videoUrl, "/storage/emulated/0/DownLoad/huluwa.aac", outPath, 1.0f, 1.0f, new OnEditorListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(EditActivity.this, "编辑完成:" + outPath, Toast.LENGTH_SHORT).show();

                Intent v = new Intent(Intent.ACTION_VIEW);
                v.setDataAndType(Uri.parse(outPath), "video/mp4");
                startActivity(v);
            }

            @Override
            public void onFailure() {
                Toast.makeText(EditActivity.this, "编辑失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onProgress(float v) {

            }
        });
    }

    @AfterPermissionGranted(100)
    public void checkPermission() {
        if (EasyPermissions.hasPermissions(this, parm)) {

        } else {
            EasyPermissions.requestPermissions(this, "权限", 100, parm);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}
