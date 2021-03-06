package net.balbum.baby;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import net.balbum.baby.Util.Config;
import net.balbum.baby.Util.ConvertBitmapToFileUtil;
import net.balbum.baby.Util.GetExifOrientationUtil;
import net.balbum.baby.Util.GetRotatedBitmapUtil;
import net.balbum.baby.VO.BabyVo;
import net.balbum.baby.VO.ResponseVo;
import net.balbum.baby.lib.retrofit.ServiceGenerator;
import net.balbum.baby.lib.retrofit.TaskService;

import java.io.File;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;

/**
 * Created by hyes on 2015. 12. 11..
 */
public class AddBabyFragment extends Fragment {
    Context context;
    Button ok_btn;
    TextView register_later;
    EditText add_baby_name;
    EditText add_baby_birthday;
    ListView listView;
    RadioGroup radioGroup;
    ImageView add_baby_image;
    BabyVo.Gender baby_gender;
    int temp_gender;
    TypedFile a;
    Bitmap bitmap;
    String selectedImagePath;


    TaskService taskService;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.add_baby_fragment, container, false);
        context = this.getActivity();
        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        taskService = ServiceGenerator.createService(TaskService.class);

        ok_btn = (Button)this.getActivity().findViewById(R.id.add_baby_btn);
        register_later = (TextView)this.getActivity().findViewById(R.id.register_later);
        add_baby_name = (EditText)this.getActivity().findViewById(R.id.add_baby_name);
        add_baby_birthday = (EditText)this.getActivity().findViewById(R.id.add_baby_birthday);
        add_baby_image = (ImageView)this.getActivity().findViewById(R.id.add_baby_photo);
        radioGroup = (RadioGroup)this.getActivity().findViewById(R.id.add_baby_radiogroup);
        listView = (ListView)this.getActivity().findViewById(R.id.list);

        add_baby_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pictureActionIntent = null;

                pictureActionIntent = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(
                        pictureActionIntent,
                        Config.GALLERY_PICTURE);
            }
        });

        add_baby_birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogHandler pickerDialog = new DialogHandler(v);
                pickerDialog.show(getFragmentManager(), "date_picker");
            }
        });

        register_later.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMainActivity();
            }
        });
        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createBabyInfo();
            }
        });

    }

    private void createBabyInfo() {
        Log.d("test", "createBabyInfo");
        BabyVo babyVo = new BabyVo();
        babyVo.babyBirth = add_baby_birthday.getText().toString();
        babyVo.babyName = add_baby_name.getText().toString();

        temp_gender =radioGroup.getCheckedRadioButtonId();
        if(temp_gender == R.id.radio0){
            babyVo.babyGender = BabyVo.Gender.GIRL;
        }else if(temp_gender == R.id.radio1){
            babyVo.babyGender = BabyVo.Gender.BOY;
        }else if(temp_gender == R.id.radio2) {
            babyVo.babyGender = BabyVo.Gender.PREGNANCY;
        }
        Bitmap img = BitmapFactory.decodeResource(context.getResources(), R.drawable.img5);
        File ab = ConvertBitmapToFileUtil.convertFile(img);
        TypedFile a = new TypedFile("multipart/form-data", ab);
        if(babyVo.babyGender == null){
            babyVo.babyGender = BabyVo.Gender.UNDEFINED;
        }
        taskService.createBabyInfo(a, babyVo.babyName, babyVo.babyBirth, babyVo.babyGender.getValue(), new Callback<ResponseVo>() {
            @Override
            public void success(ResponseVo responseVo, Response response) {
                Log.d("test", "baby post success");
                goToMainActivity();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("test", "baby post fail");
            }
        });
    }

    private void goToMainActivity() {
        Intent intent = new Intent(context, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        bitmap = null;
        selectedImagePath = null;


        if (resultCode == getActivity().RESULT_OK && requestCode == Config.GALLERY_PICTURE) {
            if (data != null) {

                Uri selectedImage = data.getData();
                String[] filePath = {MediaStore.Images.Media.DATA};
                Cursor c = context.getContentResolver().query(selectedImage, filePath,
                        null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                selectedImagePath = c.getString(columnIndex);
                c.close();

                //                if (selectedImagePath != null) {
                //                    txt_image_path.setText(selectedImagePath);
                //                }

                bitmap = BitmapFactory.decodeFile(selectedImagePath); // load
                // preview cardImg
                // bitmap = Bitmap.createScaledBitmap(bitmap, 800, 800, false);

                bitmap = GetRotatedBitmapUtil.GetRotatedBitmap(bitmap, GetExifOrientationUtil.GetExifOrientation(selectedImagePath));
                add_baby_image.setImageBitmap(bitmap);

            } else {
                Toast.makeText(context, "Cancelled",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

}
