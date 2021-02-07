package com.zzq.bitmap.util;

import android.graphics.Bitmap;
import android.util.LruCache;

import androidx.annotation.NonNull;

public class LruCacheUtil {
    private static final String KEY_TYPE = "Android_Bitmap";
    private static int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
    private LruCacheUtil() {
    }

    //使用最大内存的八分之一作为缓存大小
    private static final LruCache<String, Bitmap> memoryCache = new LruCache<String, Bitmap>(maxMemory / 8) {
        @Override
        protected int sizeOf(String key, Bitmap value) {
            // 重写此方法来衡量每张图片的大小，默认返回图片数量。
            return value.getByteCount() / 1024;
        }
    };

    public static void saveBitmap(@NonNull String key, @NonNull Bitmap bitmap) {
        if (getCacheBitmap(key) == null) {
            memoryCache.put(key, bitmap);
        }
    }

    public static Bitmap getCacheBitmap(@NonNull String key) {
        return memoryCache.get(key);
    }

    public static String createKey(String pictureKey) {
        return KEY_TYPE + "-" + pictureKey;
    }

    /**
     * 清理缓存
     * 比如下拉刷新，或者退出界面后，就清除某个特定类型的缓存
     *
     * 现在暂定为退出即删除全部
//     * @param type
     */
    public static void clearCache() {
        memoryCache.evictAll();
    }
}
