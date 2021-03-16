package com.zzq.bitmap.util;

import android.graphics.Bitmap;

public class PicAlgorithm {
    private Bitmap bitmap, newRedBmp, BlackWhiteBmp, RedBmp;
    private int[] redBytes, greenBytes, blueBytes, greyBytes, pixels, red_Pixels, black_white_Pixels;
    private int width, height;

    public PicAlgorithm(Bitmap bitmap) {
        this.bitmap = bitmap;
        getColorBytes();
        getRedGrey();
    }

    //Gray = Red  单通道的红色
    public Bitmap getRedBitmap() {
        newRedBmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        newRedBmp.setPixels(pixels, 0, width, 0, 0, width, height);
        return newRedBmp;
    }

    public Bitmap getBlack_White_Bitmap() {
        BlackWhiteBmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        BlackWhiteBmp.setPixels(black_white_Pixels, 0, width, 0, 0, width, height);
        return BlackWhiteBmp;
    }

    public Bitmap getRed_Bitmap() {
        RedBmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        RedBmp.setPixels(red_Pixels, 0, width, 0, 0, width, height);
        return RedBmp;
    }


    public int[] getRedGrey() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int alpha = 255;
                int red = redBytes[width * i + j];
                int green = greenBytes[width * i + j];
                int blue = blueBytes[width * i + j];
                if (red < 100 & green < 100 & blue < 100) {
                    //黑色
                    red = 0;
                    green = 0;
                    blue = 0;
                    pixels[width * i + j] = alpha << 24 | red << 16 | green << 8
                            | blue;
                    black_white_Pixels[width * i + j] = pixels[width * i + j];
                    red_Pixels[width * i + j] = 255;
                } else if (red > 200 & green > 200 & blue > 200) {
                    //白色
                    red = 255;
                    green = 255;
                    blue = 255;
                    pixels[width * i + j] = alpha << 24 | red << 16 | green << 8
                            | blue;
                    black_white_Pixels[width * i + j] = pixels[width * i + j];
                    red_Pixels[width * i + j] = 255;
                } else {

                    pixels[width * i + j] = alpha << 24 | red << 16 | 0 << 8
                            | 0;
                    //  Log.v("getRedGrey2","red="+red);
                    black_white_Pixels[width * i + j] = alpha << 24 | 0 << 16 | 0 << 8
                            | 0;

                    red_Pixels[width * i + j] = pixels[width * i + j];
                }
                greyBytes[width * i + j] = red;
            }
        }
        return greyBytes;
    }

    private void getColorBytes() {
        width = bitmap.getWidth();
        height = bitmap.getHeight();
        pixels = new int[width * height];
        red_Pixels = new int[width * height];
        black_white_Pixels = new int[width * height];
        redBytes = new int[width * height];
        greenBytes = new int[width * height];
        blueBytes = new int[width * height];
        greyBytes = new int[width * height];
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
        for (int m = 0; m < height; m++) {
            for (int n = 0; n < width; n++) {
                int grey = pixels[width * m + n];
                int alpha = ((grey & 0xFF000000) >> 24); //透明度
                int red = ((grey & 0x00FF0000) >> 16);
                int green = ((grey & 0x0000FF00) >> 8);
                int blue = (grey & 0x000000FF);
                redBytes[width * m + n] = red;
                greenBytes[width * m + n] = green;
                blueBytes[width * m + n] = blue;
            }
        }
    }


    public int[] getRedGrey(int b, int a) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int alpha = 255;
                int red = redBytes[width * i + j];
                int green = greenBytes[width * i + j];
                int blue = blueBytes[width * i + j];
                if (red < a & green < a & blue < a) {
                    //黑色
                    red = 0;
                    green = 0;
                    blue = 0;
                    pixels[width * i + j] = alpha << 24 | red << 16 | green << 8
                            | blue;
                    black_white_Pixels[width * i + j] = pixels[width * i + j];
                    red_Pixels[width * i + j] = 255;
                } else if (red > b & green > b & blue > b) {
                    //白色
                    red = 255;
                    green = 255;
                    blue = 255;
                    pixels[width * i + j] = alpha << 24 | red << 16 | green << 8
                            | blue;
                    black_white_Pixels[width * i + j] = pixels[width * i + j];
                    red_Pixels[width * i + j] = 255;
                } else {

                    pixels[width * i + j] = alpha << 24 | red << 16 | 0 << 8
                            | 0;
                    //Log.v("getRedGrey2","red="+red);
                    black_white_Pixels[width * i + j] = 255;
                    red_Pixels[width * i + j] = pixels[width * i + j];
                }
                greyBytes[width * i + j] = red;
            }
        }
        return greyBytes;
    }


}