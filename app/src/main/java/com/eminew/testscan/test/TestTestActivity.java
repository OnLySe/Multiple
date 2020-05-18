//package com.eminew.testscan.test;
//
//import android.annotation.SuppressLint;
//import android.content.BroadcastReceiver;
//import android.content.ContentUris;
//import android.content.Context;
//import android.content.Intent;
//import android.database.Cursor;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Environment;
//import android.provider.DocumentsContract;
//import android.provider.MediaStore;
//import android.text.TextUtils;
//import android.util.Log;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.eminew.testscan.R;
//
//import java.io.BufferedInputStream;
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//
//public class TestTestActivity extends AppCompatActivity {
//
//
//
//    private void getUpgradePackage() {
//        Intent intent = getIntent();
//        Uri uri = intent.getParcelableExtra("uri");
//        String path = null;
//        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
//            //4.4以后
//            path = getPath(this, uri);
//        } else {//4.4以下下系统调用方法
//            path = getRealPathFromURI(uri);
//        }
//
//        if (TextUtils.isEmpty(path)) {
//            return;
//        }
//
//        File file = new File(path);
//        if (!file.getName().endsWith(".bin")) {
//            if (hud.isShowing()) {
//                hud.dismiss();
//            }
//            Toast.makeText(SensorApp.getInstance(), R.string.only_support_bin,
//                    Toast.LENGTH_LONG).show();
//            mHandler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    finish();
//                }
//            },1000);
//
//            return;
//        }
//
//        if (file.length() == 0) {
//            //文件内容为空
//            Toast.makeText(this, R.string.file_is_empty, Toast.LENGTH_LONG).show();
//            return;
//        }
//        BufferedReader bufferedReader = null;
//        try {
//
//            byte[] allData = new byte[(int) file.length()];
//            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
//            byte[] buffer = new byte[1024];
//            int bytesRead = 0;
//            int tempLength = 0;
//            while ((bytesRead = bufferedInputStream.read(buffer)) != -1) {
//                System.arraycopy(buffer, 0, allData, tempLength, bytesRead);
//                tempLength += bytesRead;
//            }
//            Log.e("SensorSend", " file: " + file.getAbsolutePath() + " " + file.length());
//            sendUpgradePackage(allData);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (bufferedReader != null) {
//                    bufferedReader.close();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    public String getRealPathFromURI(Uri contentUri) {
//        String res = null;
//        String[] proj = {MediaStore.Images.Media.DATA};
//        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
//        if (null != cursor && cursor.moveToFirst()) {
//            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//            res = cursor.getString(column_index);
//            cursor.close();
//        }
//        return res;
//    }
//
//    /**
//     * 专为Android4.4设计的从Uri获取文件绝对路径，以前的方法已不好使
//     */
//    @SuppressLint("NewApi")
//    public String getPath(final Context context, final Uri uri) {
//
//        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
//
//        // DocumentProvider
//        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
//            // ExternalStorageProvider
//            if (isExternalStorageDocument(uri)) {
//                final String docId = DocumentsContract.getDocumentId(uri);
//                final String[] split = docId.split(":");
//                final String type = split[0];
//
//                if ("primary".equalsIgnoreCase(type)) {
//                    return Environment.getExternalStorageDirectory() + "/" + split[1];
//                }
//            }
//            // DownloadsProvider
//            else if (isDownloadsDocument(uri)) {
//
//                final String id = DocumentsContract.getDocumentId(uri);
//                final Uri contentUri = ContentUris.withAppendedId(
//                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
//
//                return getDataColumn(context, contentUri, null, null);
//            }
//            // MediaProvider
//            else if (isMediaDocument(uri)) {
//                final String docId = DocumentsContract.getDocumentId(uri);
//                final String[] split = docId.split(":");
//                final String type = split[0];
//
//                Uri contentUri = null;
//                if ("image".equals(type)) {
//                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
//                } else if ("video".equals(type)) {
//                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
//                } else if ("audio".equals(type)) {
//                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
//                }
//
//                final String selection = "_id=?";
//                final String[] selectionArgs = new String[]{split[1]};
//
//                return getDataColumn(context, contentUri, selection, selectionArgs);
//            }
//        }
//        // MediaStore (and general)
//        else if ("content".equalsIgnoreCase(uri.getScheme())) {
//            return getDataColumn(context, uri, null, null);
//        }
//        // File
//        else if ("file".equalsIgnoreCase(uri.getScheme())) {
//            return uri.getPath();
//        }
//        return null;
//    }
//
//    /**
//     * Get the value of the data column for this Uri. This is useful for
//     * MediaStore Uris, and other file-based ContentProviders.
//     *
//     * @param context       The context.
//     * @param uri           The Uri to query.
//     * @param selection     (Optional) Filter used in the query.
//     * @param selectionArgs (Optional) Selection arguments used in the query.
//     * @return The value of the _data column, which is typically a file path.
//     */
//    public String getDataColumn(Context context, Uri uri, String selection,
//                                String[] selectionArgs) {
//
//        Cursor cursor = null;
//        final String column = "_data";
//        final String[] projection = {column};
//
//        try {
//            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
//                    null);
//            if (cursor != null && cursor.moveToFirst()) {
//                final int column_index = cursor.getColumnIndexOrThrow(column);
//                return cursor.getString(column_index);
//            }
//        } catch (Exception e) {
//            Toast.makeText(this, getString(R.string.file_select_error), Toast.LENGTH_SHORT).show();
//        } finally {
//            if (cursor != null) {
//                cursor.close();
//            }
//        }
//        return null;
//    }
//
//    /**
//     * @param uri The Uri to check.
//     * @return Whether the Uri authority is ExternalStorageProvider.
//     */
//    public boolean isExternalStorageDocument(Uri uri) {
//        return "com.android.externalstorage.documents".equals(uri.getAuthority());
//    }
//
//    /**
//     * @param uri The Uri to check.
//     * @return Whether the Uri authority is DownloadsProvider.
//     */
//    public boolean isDownloadsDocument(Uri uri) {
//        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
//    }
//
//    /**
//     * @param uri The Uri to check.
//     * @return Whether the Uri authority is MediaProvider.
//     */
//    public boolean isMediaDocument(Uri uri) {
//        return "com.android.providers.media.documents".equals(uri.getAuthority());
//    }
//
//    class ConnStateBroadcastReceiver extends BroadcastReceiver {
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            Log.e("tetetete", "固件升级界面 onReceive 断开连接 " + isUpgradeSuccess);
//            if (!isUpgradeSuccess) {
//                Toast.makeText(SensorApp.getInstance(), R.string.disconnected, Toast.LENGTH_LONG).show();
//            }
//            finish();
//        }
//    }
//}
