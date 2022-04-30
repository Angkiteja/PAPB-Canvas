package com.example.simplecanvas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView mImageView;
    private Canvas mCanvas;
    //untuk mewarnai kotak
    private Paint mPaint = new Paint();
    //untuk mewarnai tulisannya
    private Paint mPaintText = new Paint(Paint.UNDERLINE_TEXT_FLAG);
    private Bitmap mBitmap;

    private int mColorBackground;
    private int mColorRectangle;
    private int mColorAccent;

    //set batasan
    private final static int OFFSET = 120;
    //current offset
    private int mOffset = OFFSET;

//    variable rectangle
    private Rect mRect = new Rect();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageView = findViewById(R.id.myImageView);

        //di initialize dulu
        mColorBackground = ResourcesCompat.getColor(getResources(), R.color.colorBackground, null);
        mColorRectangle = ResourcesCompat.getColor(getResources(), R.color.colorRectangle, null);
        mColorAccent = ResourcesCompat.getColor(getResources(), R.color.teal_200, null);

        mPaintText.setColor(ResourcesCompat.getColor(getResources(), R.color.black, null));
        mPaintText.setTextSize(70);

        //buat canvas ketika image view di klik, di image view harus ditambahkan onClickListener
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //disini nambahkan canvas dan  bitmap, dan coding imageview nya
                //ambil ukuran lebar n tinggi imageview
                int vWidth = view.getWidth();
                int vHeight = view.getHeight();

                //ngambil tengahnya berapa, untuk threshold, untuk menentukan kapan harus tambahkan
                //rectangle nya, kapan bulet2 nya
                int halfWidth = vWidth/2;
                int halfHeight = vHeight/2;


                if(mOffset == OFFSET){
                    //karena bitmap seukuran image view, make variable width sama height
                    mBitmap = Bitmap.createBitmap(vWidth, vHeight, Bitmap.Config.ARGB_8888);
                    //bitmap diasosiasi ke image view
                    mImageView.setImageBitmap(mBitmap);
                    //selain diasosiasi ke image view, diasosiasikan juga dengan canvas
                    mCanvas = new Canvas(mBitmap);
                    mCanvas.drawColor(mColorBackground);
                    mCanvas.drawText("Keep Tapping", 100, 100, mPaintText);

                    //rubah mOffset supaya baris diatas tidak dieksekusi berkali2
                    mOffset += OFFSET;
                }
                else{
                    if (mOffset < halfWidth && mOffset < halfHeight){
                        //warna nya berubah2
                        mPaint.setColor(mColorRectangle - 100*mOffset);
                        mRect.set(mOffset, mOffset, vWidth-mOffset, vHeight-mOffset);
                        //ditempel di canvas
                        mCanvas.drawRect(mRect, mPaint);

                        mOffset += OFFSET;

                    }
                    else {
                        //tambahkan circle nya
                        mPaint.setColor(mColorAccent);
                        mCanvas.drawCircle(halfWidth, halfHeight, halfWidth/3, mPaint);

                        //tambahkan text
                        String text = "Done!";
                        Rect mBounds = new Rect();
                        mPaintText.getTextBounds(text, 0, text.length(),mBounds);

                        int x = mBounds.centerX();
                        int y = mBounds.centerY();


                        mCanvas.drawText(text, halfWidth - x, halfHeight - y, mPaintText);

                    }
                }
                //supaya lebih efektif
                view.invalidate();

            }
        });
    }
}