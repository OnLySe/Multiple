package com.eminew.testscan;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.eminew.testscan.databinding.ActivityMainBinding;
import com.minewtech.sensorKit.bean.history.DoorHistoryData;
import com.minewtech.sensorKit.interfaces.outside.door.OnReceiveDoorDataListener;
import com.minewtech.sensorKit.manager.MinewSensorCenterManager;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        final Switch switchHtStore = binding.switchHtStore;
        switchHtStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = switchHtStore.isChecked();
                Log.e("tetetetete", "switch onClick " + checked);
            }
        });
        switchHtStore.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                boolean checked = switchHtStore.isChecked();
                Log.e("tetetetete", "switch onLongClick " + checked);
                switchHtStore.setChecked(!checked);
                return true;
            }
        });
        switchHtStore.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                boolean checked = switchHtStore.isChecked();
                Log.e("tetetetete", "switch onCheckedChanged " + isChecked + " " + checked);
            }
        });

        MinewSensorCenterManager.getInstance(this).readDoorHistoryData("fd",  (byte)11,  (byte)11, new OnReceiveDoorDataListener() {
            @Override
            public void receiverDoorData(String s, List<DoorHistoryData> list) {

            }
        });
    }

    public void restart(View view) {
        openFile();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null) {
            Uri fileUri = data.getData();
            Log.e("SensorSend", "" + fileUri.getPath());
            File file = new File(fileUri.getPath());
            getData(fileUri);
        }
    }

    private void openFile() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        //intent.setType("text/plain");//设置类型
        intent.setType("*/*");//设置类型
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, 133);

    }

    private void getData(Uri uri) {
        File file = new File(uri.getPath());
        String path = file.getPath();
        Log.e("SensorSend", " file: " + path);
        if (path.contains(".") && !path.endsWith(".bin")) {
            Toast.makeText(this, "only_support_bin", Toast.LENGTH_SHORT).show();
            return;
        }
        BufferedInputStream bufferedInputStream;

        try {
            if (path.contains("/external/file/")) {
                bufferedInputStream = new BufferedInputStream(new FileInputStream(getRealPathFromUri(this, uri)));
            } else {
                bufferedInputStream = new BufferedInputStream(getContentResolver().openInputStream(uri));
            }
            ArrayList<Byte> allData = new ArrayList<>();
            //每次读取4k
            byte[] buffer = new byte[1024 * 4];
            int bytesRead = 0;
            int tempLength = 0;
            while ((bytesRead = bufferedInputStream.read(buffer)) != -1) {
                //System.arraycopy(buffer, 0, allData, tempLength, bytesRead);
                for (int i = 0; i < bytesRead; i++) {
                    byte by = buffer[i];
                    allData.add(by);
                }
                tempLength += bytesRead;
            }
            Log.e("SensorSend", " file size: " + allData.size());
            byte[] fileData = new byte[allData.size()];
            for (int i = 0; i < allData.size(); i++) {
                fileData[i] = allData.get(i);
            }
            bufferedInputStream.close();
            Log.e("SensorSend", "end, file: " + fileData.length + " " + fileData[0] + " " + fileData[1]);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this, "file_select_error", Toast.LENGTH_SHORT).show();
            finish();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "file_select_error", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    public static String getRealPathFromUri(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    private void readFile(File file, Uri uri) {
        try {
            ArrayList<Byte> allData = new ArrayList<>();
            Log.e("tetete", " file: " + file.getName());
            InputStream in = getContentResolver().openInputStream(uri);
            if (in == null) {
                Log.e("tetete", " file: InputStream null");
                return;
            }
            BufferedInputStream bufferedInputStream = new BufferedInputStream(in);
            byte[] buffer = new byte[1024];
            int bytesRead = 0;
            int tempLength = 0;
            while ((bytesRead = bufferedInputStream.read(buffer)) != -1) {
//                System.arraycopy(buffer, 0, allData, tempLength, bytesRead);
                for (int i = 0; i < bytesRead; i++) {
                    byte by = buffer[i];
                    allData.add(by);
                }
                tempLength += bytesRead;
            }
            Log.e("tetete", " file: " + file.getAbsolutePath() + " " + tempLength + " " + allData.size());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
