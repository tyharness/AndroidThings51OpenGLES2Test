package example.androidthings.myopengles2Test;

import android.app.Activity;
import android.os.Bundle;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.os.Build;

import javax.microedition.khronos.egl.EGLConfig;



public class MainActivity extends Activity {
    private static final String TAG = MainActivity.class.getSimpleName();
    
    
  
    private GLSurfaceView mGLSurfaceView;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Log.d(TAG, "onCreate");
        
            
    mGLSurfaceView = new GLSurfaceView(this);
       
    
    
    final boolean isHardwareAccelerationSupported = mGLSurfaceView.isHardwareAccelerated();
    
    if (isHardwareAccelerationSupported)
    {  
       Log.d(TAG,  "hardware Acceleration is supported" ); 
    }else{
       Log.d(TAG,  "hardware Acceleration is not supported " + mGLSurfaceView.toString() );
    }
    
    
    
    
    // Check if the system supports OpenGL ES 2.0.
    final ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
    final ConfigurationInfo Info = activityManager.getDeviceConfigurationInfo();
   
    //Bypass but app may crash
    final boolean isGLES20supported = Info.reqGlEsVersion >= 0x20000  ||  Build.MODEL.contains("iot_rpi3");
    
   //final boolean isGLES20supported = Info.reqGlEsVersion >= 0x20000 ;
    
  
    
    
    if (isGLES20supported)
    {       
      
         mGLSurfaceView.setEGLConfigChooser(8, 8, 8, 8, 16, 0); //what does this work?????
         //mGLSurfaceView.setEGLConfigChooser(true); //why does this fail
         
         /*If no setEGLConfigChooser method is called, then by default the view will 
          * choose an RGB_888 surface with a depth buffer depth of at least 16 bits.
          * 
          * 
          * void setEGLConfigChooser (int redSize, 
                int greenSize, 
                int blueSize, 
                int alphaSize, 
                int depthSize, 
                int stencilSize)
          */
      
         
        mGLSurfaceView.setEGLContextClientVersion(2);
        mGLSurfaceView.setRenderer(new MyGLrenderer());
    }
    else
    {           
        Log.d(TAG, "OpenGLES 2 is not supported");       
    }

        Log.d(TAG,  Info.toString() );
        
        Log.d(TAG,  Integer.toBinaryString(Info.reqGlEsVersion) );
        Log.d(TAG,  Integer.toHexString(Info.reqGlEsVersion) );
        Log.d(TAG,  Info.getGlEsVersion ());
         
        Log.d(TAG,  Build.FINGERPRINT );       
        Log.d(TAG,  Build.BOARD );
        Log.d(TAG,  Build.BOOTLOADER );
        Log.d(TAG,  Build.MODEL );
    
       
    
    
       setContentView(mGLSurfaceView);
        
    }


   
    
@Override
protected void onResume()
{
    // The activity must call the GL surface view's onResume() on activity onResume().
    super.onResume();
    //mGLSurfaceView.onResume();
}
 
@Override
protected void onPause()
{
    // The activity must call the GL surface view's onPause() on activity onPause().
    super.onPause();
   // mGLSurfaceView.onPause();
}
   
    
 @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }
}
