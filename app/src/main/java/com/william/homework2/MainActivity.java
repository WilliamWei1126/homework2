package com.william.homework2;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.opencv.android.InstallCallbackInterface;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.QRCodeDetector;

public class MainActivity extends AppCompatActivity {
    TextView textview;

    LoaderCallbackInterface loaderCallbackInterface = new LoaderCallbackInterface() {
        @Override
        public void onManagerConnected(int status) {

        }

        @Override
        public void onPackageInstall(int operation, InstallCallbackInterface callback) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final TextView text = (TextView) findViewById(R.id.textView);
        if (!OpenCVLoader.initDebug()) {
            boolean success =
                    OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_4_0
                            , this, loaderCallbackInterface
                    );


        } else {
            loaderCallbackInterface.onManagerConnected(LoaderCallbackInterface.SUCCESS);


        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ImageView imageview;



        textview = findViewById(R.id.textView);
        Button draw = findViewById(R.id.button);

        draw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ImageView imgView = findViewById(R.id.qr);
                Bitmap orig = ((BitmapDrawable) imgView.getDrawable()).getBitmap();
                Mat img = new Mat(orig.getHeight(), orig.getWidth(), CvType.CV_8UC4);
                Utils.bitmapToMat(orig, img);
                QRCodeDetector qrCodeDetector = new QRCodeDetector();
                String result =  qrCodeDetector.detectAndDecode(img);

                String [] lines = result.split(";");
                Scalar LineColor = new Scalar(255, 0, 0, 255);
                int linewidth = 3;
                //coursera java for android week 2 array part 2
                for (String currline : lines){
                    String [] line = currline.split(" ");
                    String p1 = line[0];
                    String p2 = line[1];
                    String [] p1xy = p1.split(",");
                    String [] p2xy = p2.split(",");
                    String p1x = p1xy[0];
                    String p1y = p1xy[1];
                    String p2x = p2xy[0];
                    String p2y = p2xy[1];
                    int point1x = Integer.parseInt(p1x);
                    int point1y = Integer.parseInt(p1y);
                    int point2x = Integer.parseInt(p2x);
                    int point2y = Integer.parseInt(p2y);
                    Point firstPoint = new Point(point1x, point1y);
                    Point secondPoint = new Point(point2x, point2y);
                    Imgproc.line(img, firstPoint, secondPoint, LineColor, linewidth);
                    Bitmap resultBitmap = Bitmap.createBitmap(img.cols(),img.rows(),Bitmap.Config.ARGB_8888);
                    Utils.matToBitmap(img, resultBitmap);

                    imgView.setImageBitmap(resultBitmap);
                    imgView.requestLayout();
                    imgView.getLayoutParams().height += 222;
                    imgView.getLayoutParams().width += 216;

                }




            }
        });
    }
}
