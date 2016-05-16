package com.sfj.qrcode;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.google.zxing.Result;
import com.sfj.R;
import com.sfj.common.activity.BaseActivity;
import com.sfj.qrcode.camera.CameraManager;

import java.io.IOException;


/**
 * Created by sblau_000 on 2016/1/27.
 */
public class CaptureActivity extends BaseActivity implements SurfaceHolder.Callback {

    public static String SCAN_RESULT_KEY = "url";
    public static String TAG = "CaptureActivity";
    public static int VIBRATE_DURATION = 200;


    /**
     * view
     */
    private ViewfinderView mViewfinderView;
    private LinearLayout scanbar;
    private CaptureActivityHandler handler;
    private SurfaceHolder surfaceHolder;
    private CameraManager cameraManager;
    private boolean hasSurface = false;
    private boolean vibrate = true;
    private Result lastResult;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_scan);
        initView();
    }

    public void initView() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        mViewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
        scanbar = (LinearLayout) findViewById(R.id.capture_scanbar_view);
        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.surface_view);
        surfaceHolder = surfaceView.getHolder();
        mViewfinderView.post(new Runnable() {
            @Override
            public void run() {
                initScanBar();
            }
        });
    }

    /**
     * 没用
     */
    private void checkCameraPermission() {
        PackageManager pm = getPackageManager();
        boolean permission = pm.checkPermission("android.permission.CAMERA","com.qiatu.jihe")
                ==PackageManager.PERMISSION_GRANTED;
        Log.e("...","has permission?"+permission);

    }

    private void initScanBar(){
        Rect rect = mViewfinderView.getFrameRect();
        if(rect!=null){
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(rect.right-rect.left,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(rect.left,rect.top,0,0);
            scanbar.setLayoutParams(params);
            TranslateAnimation translateAnimation = new TranslateAnimation(0,0,0,rect.bottom-rect.top);
            translateAnimation.setRepeatCount(TranslateAnimation.INFINITE);
            translateAnimation.setDuration(2000);
            scanbar.startAnimation(translateAnimation);
        }
//        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams();
    }


    @Override
    protected void onResume() {
        super.onResume();

        cameraManager = new CameraManager(getApplicationContext());
        mViewfinderView.setCameraManager(cameraManager);
        handler = null;
        if (hasSurface) {
            initCamera(surfaceHolder);
        } else {
            // Install the callback and wait for surfaceCreated() to init the camera.
            surfaceHolder.addCallback(this);
        }
    }

    @Override
    protected void onPause() {
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        cameraManager.closeDriver();
        //historyManager = null; // Keep for onActivityResult
        if (!hasSurface) {
            SurfaceView surfaceView = (SurfaceView) findViewById(R.id.surface_view);
            SurfaceHolder surfaceHolder = surfaceView.getHolder();
            surfaceHolder.removeCallback(this);
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (holder == null) {
            Log.e(TAG, "*** WARNING *** surfaceCreated() gave us a null surface!");
        }
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    /**
     * A valid barcode has been found, so give an indication of success and show the results.
     *
     * @param rawResult   The contents of the barcode.
     * @param scaleFactor amount by which thumbnail was scaled
     * @param barcode     A greyscale bitmap of the camera data which was decoded.
     */
    public void handleDecode(Result rawResult, Bitmap barcode, float scaleFactor) {
        if (vibrate) {
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
        lastResult = rawResult;
        Log.d("...", "scan result = " + lastResult.getText());
        String result = lastResult.getText();
//        if (result.contains(ScanResultUtils.JIHE_SCAN)) {
//            //由于扫描的结果会在很多activity里面传递 所以在这里直接记录到application里
//            JiheApplication.setScanResult(result);
//            //如果是已登录的会员 则打开会员中心
//            Bundle bundle = new Bundle();
//            bundle.putString(SCAN_RESULT_KEY,result);
//            if(JiheApplication.isLogin
//                    && JiheMember.MEMBER.equals(JiheApplication.getUserInfo().getMemberFlag())){
//                openActivity(MemberCenterActivity.class,bundle);
//
//            }else{
//                openActivity(MemberLoginActivity.class,bundle);
//            }
//            finish();
//        } else if (result.startsWith("https://") || result.startsWith("http://")) {
//            //如果是网址 则打开网页
//            Bundle bundle = new Bundle();
//            bundle.putString(WebViewActivity.URL, result);
//            openActivity(WebViewActivity.class, bundle);
//            this.finish();
//        } else {
//            //无法识别的时候 显示出识别结果
//            CustomAlertDialog.Builder builder = new CustomAlertDialog.Builder(this);
//            builder.setMessage(result);
//            builder.setPositiveButton(getString(R.string.activity_member_login_ok), new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    dialog.dismiss();
//                    finish();
//                }
//            });
//
//            builder.create().show();
//        }
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        if (surfaceHolder == null) {
            throw new IllegalStateException("No SurfaceHolder provided");
        }
        if (cameraManager.isOpen()) {
            Log.w(TAG, "initCamera() while already open -- late SurfaceView callback?");
            return;
        }
        try {
            cameraManager.openDriver(surfaceHolder);
            if (handler == null) {
                handler = new CaptureActivityHandler(this, null, null, null, cameraManager);
            }
        } catch (IOException ioe) {
            Log.w(TAG, ioe);
        } catch (RuntimeException e) {
            Log.w(TAG, "Unexpected error initializing camera", e);
        }
    }

    public void drawViewfinder() {
        mViewfinderView.drawViewfinder();
    }

    CameraManager getCameraManager() {
        return cameraManager;
    }

    ViewfinderView getViewfinderView() {
        return mViewfinderView;
    }

    public Handler getHandler() {
        return handler;
    }

}













