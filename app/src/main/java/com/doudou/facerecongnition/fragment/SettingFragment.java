package com.doudou.facerecongnition.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.*;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.alibaba.fastjson.JSONObject;
import com.doudou.facerecongnition.R;
import com.doudou.facerecongnition.activity.LoginActivity;
import com.doudou.facerecongnition.activity.stack.ActivityController;
import com.doudou.facerecongnition.constant.GlobalVar;
import com.doudou.facerecongnition.entity.Teacher;
import com.doudou.facerecongnition.net.HttpCallBackListener;
import com.doudou.facerecongnition.net.HttpUtil;
import com.doudou.facerecongnition.util.FileUtil;
import com.doudou.facerecongnition.util.MyHttpUtil;

import java.io.*;
import java.util.LinkedHashMap;

import static android.app.Activity.RESULT_OK;

public class SettingFragment extends Fragment implements View.OnClickListener {

    /**
     * 头像
     **/
    private ImageView headPhoto;
    private Bitmap head;
    private static String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/faceRecognition/myHead/";
    /**
     * 帐号
     */
    private TextView account;
    private TextView name;
    private TextView phone;
    private Button btExit;
    private Teacher teacher = null;

    RelativeLayout rlChangePassword;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        btExit = view.findViewById(R.id.btnExit);
        headPhoto = view.findViewById(R.id.ivHead);
        account = view.findViewById(R.id.tvUserName);
        name = view.findViewById(R.id.tvFullName);
        phone = view.findViewById(R.id.tvPhone);
        rlChangePassword = view.findViewById(R.id.rlChangePassword);
        teacher = getManagerInfo();

        if (getHead(teacher) != null){
            headPhoto.setImageDrawable(getHead(teacher));
        }
        account.setText(teacher.getAccount());
        name.setText(teacher.getName());
        phone.setText(teacher.getPhoneNumber());
        btExit.setOnClickListener(this);
        headPhoto.setOnClickListener(this);
        rlChangePassword.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnExit:
                //退出登录
                startActivity(new Intent().setClass(getActivity(), LoginActivity.class));
                ActivityController.getInstance().outSign();
                break;
            case R.id.ivHead:
                //更换头像选择
                showTypeDialog();
                break;
            case R.id.rlChangePassword:
                changePassword();
                break;

        }

    }

    private Teacher getManagerInfo() {
        return (Teacher) getArguments().getSerializable("teacher");
    }

    private Drawable getHead(Teacher teacher) {
        Bitmap bt = BitmapFactory.decodeFile(path + teacher.getImageName());
        Drawable drawable = null;
        if (bt != null) {
            drawable = new BitmapDrawable(bt);
        } else {
            //从服务器获取头像,保存头像到本地
            new ChangeHeadImageRequest().execute(teacher);
        }
        return drawable;
    }

    /**
     * 保存头像到sd卡
     *
     * @param bitmap   头像
     * @param headName 头像名
     */
    private void saveHead(Bitmap bitmap, String headName) {
        String sdStatus = Environment.getExternalStorageState();
        if (sdStatus.equals(Environment.MEDIA_MOUNTED)) {
            //sd卡可用
            FileOutputStream fos = null;
            File file = new File(path);
            //创建文件夹
            file.mkdirs();
            String fileName = path + "/" + headName;
            try {
                fos = new FileOutputStream(fileName);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }finally {

                try {
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();

                }
            }
        }
    }

    private void showTypeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final AlertDialog dialog = builder.create();
        View view = View.inflate(getActivity(), R.layout.dialog_select_photo, null);
        TextView tv_select_gallery = view.findViewById(R.id.tv_select_gallery);
        TextView tv_select_camera = view.findViewById(R.id.tv_select_camera);
        tv_select_gallery.setOnClickListener(new View.OnClickListener() {// 在相册中选取
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Intent.ACTION_PICK, null);
                intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent1, 1);
                dialog.dismiss();
            }
        });
        tv_select_camera.setOnClickListener(new View.OnClickListener() {// 调用照相机
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent2.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(new File(path, teacher.getImageName())));
                startActivityForResult(intent2, 2);// 采用ForResult打开
                dialog.dismiss();
            }
        });
        dialog.setView(view);
        dialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    cropPhoto(data.getData());// 裁剪图片
                }

                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    File temp = new File(path + teacher.getImageName());
                    cropPhoto(Uri.fromFile(temp));// 裁剪图片
                }

                break;
            case 3:
                if (data != null) {
                    Bundle extras = data.getExtras();
                    head = extras.getParcelable("data");
                    if (head != null) {
                        //头像上传服务器代码
                        LinkedHashMap<String, String> para = new LinkedHashMap<>();
                        saveHead(head, teacher.getImageName());// 保存在SD卡中
                        para.put("teacherId", teacher.getId().toString());
                        byte[] dataFile = FileUtil.getBytes(path + "/" + teacher.getImageName());
                        HttpUtil.post4file(GlobalVar.myServer + "/teacher/modifyImage", dataFile, "imageFile", teacher.getImageName(), para, new HttpCallBackListener() {
                            @Override
                            public void onFinish(JSONObject response) {

                                handler.sendEmptyMessage(0);
                            }

                            @Override
                            public void onError(Exception e) {
                                handler.sendEmptyMessage(1);
                            }
                        });

                    }
                }
                break;
            default:
                break;

        }
    }

    /**
     * 调用系统的裁剪功能
     *
     * @param uri
     */
    public void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 3);
    }

    /**
     * 修改密码
     */
    private void changePassword(){

    }


    //内部类执行异步网络任务
    class ChangeHeadImageRequest extends AsyncTask {


        @Override
        protected Object doInBackground(Object[] objects) {

            Teacher teacher = (Teacher) objects[0];
            Bitmap bitmap = MyHttpUtil.getBitmapByUrl(teacher.getImagePath());
            if (bitmap != null) {
                //保存头像到本地
                saveHead(bitmap, teacher.getImageName());
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Object o) {
            headPhoto.setImageDrawable(new BitmapDrawable((Bitmap) o));
        }


    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0){
                headPhoto.setImageBitmap(head);
                Toast.makeText(getContext(), "修改成功", Toast.LENGTH_SHORT).show();
            }else if (msg.what == 1){
                Toast.makeText(getContext(), "网络出错", Toast.LENGTH_SHORT).show();
            }
        }
    };

}
