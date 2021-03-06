package example.androidthings.myopengles2Test;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;


/*I followed the approach outlined at:
 *  www.learnopengles.com/android-lesson-one-getting-started/
 */

public class  MyGLrenderer implements GLSurfaceView.Renderer 
{
 
 private float[] mModelMatrix      = new float[16];
 private float[] mViewMatrix       = new float[16];
 private float[] mProjectionMatrix = new float[16];
 private float[] mMVPMatrix        = new float[16];
 
 private int mMVPMatrixHandle;
 private int mPositionHandle;
 private int mColorHandle;
 
 private final int mBytesPerFloat = 4;
 private final int mStrideBytes = 7 * mBytesPerFloat; 
 
 
 private final int mPositionOffset = 0;
 private final int mPositionDataSize = 3;
 private final int mColorOffset = 3;
 private final int mColorDataSize = 4;  
 
 
 private float rot_angle = 0.0f;
 
 
 
///////////////////////////////////////////////////////

private final FloatBuffer mSquare1Vertices;


private final FloatBuffer mMaxwell0Vertices;
private final FloatBuffer mMaxwell1Vertices;
private final FloatBuffer mMaxwell2Vertices;
private final FloatBuffer mMaxwell3Vertices;
private final FloatBuffer mMaxwell4Vertices;
private final FloatBuffer mMaxwell5Vertices;
  


 public MyGLrenderer()
 { 
  

 
  //Maxwell's Colour Triangle made up from 6 simple triangles
   
   
  /*   E
   * F /\ D
   *  /\/\
   * A  B C
   *
   */
  
  float h = (float) (0.5*Math.tan(60.0*0.01745));  //pi/3
  float h1 = (float) (0.25*Math.tan(60.0*0.01745)); //pi/6
  float ybar = (float) 0.33333*h;
  
  float Ax = -0.5f;   float Ay = -ybar;
  float Bx =  0.0f;   float By = -ybar;
  float Cx =  0.5f;   float Cy = -ybar;
  float Dx =  0.25f;  float Dy = -ybar+h1;
  float Ex =  0.0f;   float Ey =  2*ybar;
  float Fx =  -0.25f; float Fy = -ybar+h1;
  float Ox =  0.0f;   float Oy = 0.0f;
 
  
  
  ///////////////////////////////////////////////////////
  
  
  final float[] maxwell0VerticesData = {
 
             Ax, Ay, 0.0f, 
             0.0f, 0.0f, 1.0f, 1.0f,
             
             Bx, By, 0.0f,
             1.0f, 0.0f, 1.0f, 1.0f,
             
             Fx, Fy, 0.0f, 
             0.0f, 1.0f, 1.0f, 1.0f
   };
  
    mMaxwell0Vertices = ByteBuffer.allocateDirect(maxwell0VerticesData.length * mBytesPerFloat).order(ByteOrder.nativeOrder()).asFloatBuffer();
    mMaxwell0Vertices.put(maxwell0VerticesData).position(0);


    
    
    final float[] maxwell1VerticesData = {
 
             Bx, By, 0.0f, 
             1.0f, 0.0f, 1.0f, 1.0f,
             
             Cx, Cy, 0.0f,
             1.0f, 0.0f, 0.0f, 1.0f,
             
             Dx, Dy, 0.0f, 
             1.0f, 1.0f, 0.0f, 1.0f
   };
  
    mMaxwell1Vertices = ByteBuffer.allocateDirect(maxwell1VerticesData.length * mBytesPerFloat).order(ByteOrder.nativeOrder()).asFloatBuffer();
    mMaxwell1Vertices.put(maxwell1VerticesData).position(0);
   
    
    
   final float[] maxwell2VerticesData = {
 
             Fx, Fy, 0.0f, 
             0.0f, 1.0f, 1.0f, 1.0f,
             
             Dx, Dy, 0.0f,
             1.0f, 1.0f, 0.0f, 1.0f,
             
             Ex, Ey, 0.0f, 
             0.0f, 1.0f, 0.0f, 1.0f
   };
  
    mMaxwell2Vertices = ByteBuffer.allocateDirect(maxwell2VerticesData.length * mBytesPerFloat).order(ByteOrder.nativeOrder()).asFloatBuffer();
    mMaxwell2Vertices.put(maxwell2VerticesData).position(0);
      
    
    
 final float[] maxwell3VerticesData = {
 
             Fx, Fy, 0.0f, 
             0.0f, 1.0f, 1.0f, 1.0f,
             
             Bx, By, 0.0f,
             1.0f, 0.0f, 1.0f, 1.0f,
             
             Ox, Oy, 0.0f, 
             1.0f, 1.0f, 1.0f, 1.0f
   };
  
    mMaxwell3Vertices = ByteBuffer.allocateDirect(maxwell3VerticesData.length * mBytesPerFloat).order(ByteOrder.nativeOrder()).asFloatBuffer();
    mMaxwell3Vertices.put(maxwell3VerticesData).position(0);
      
 final float[] maxwell4VerticesData = {
 
   
             Bx, By, 0.0f,
             1.0f, 0.0f, 1.0f, 1.0f,
   
   
             Dx, Dy, 0.0f,
             1.0f, 1.0f, 0.0f, 1.0f,
             
             
             Ox, Oy, 0.0f, 
             1.0f, 1.0f, 1.0f, 1.0f
   };
  
    mMaxwell4Vertices = ByteBuffer.allocateDirect(maxwell4VerticesData.length * mBytesPerFloat).order(ByteOrder.nativeOrder()).asFloatBuffer();
    mMaxwell4Vertices.put(maxwell4VerticesData).position(0);
        
    
 final float[] maxwell5VerticesData = {
 
   
             Dx, Dy, 0.0f,
             1.0f, 1.0f, 0.0f, 1.0f,
   
             Fx, Fy, 0.0f, 
             0.0f, 1.0f, 1.0f, 1.0f,
          
             Ox, Oy, 0.0f, 
             1.0f, 1.0f, 1.0f, 1.0f
   };
  
    mMaxwell5Vertices = ByteBuffer.allocateDirect(maxwell5VerticesData.length * mBytesPerFloat).order(ByteOrder.nativeOrder()).asFloatBuffer();
    mMaxwell5Vertices.put(maxwell5VerticesData).position(0);
   
  
  
  // Make a square from 2 triangles
  final float[] square1VerticesData = {

             -0.5f, -0.5f, 0.0f, 
             1.0f, 0.0f, 0.0f, 1.0f,
             
             0.5f, -0.5f, 0.0f, 
             0.0f, 1.0f, 0.0f, 1.0f,
             
             0.5f, 0.5f, 0.0f, 
             0.0f, 0.0f, 1.0f, 1.0f,
  
  
             -0.5f, -0.5f, 0.0f, 
             1.0f, 0.0f, 0.0f, 1.0f,
             
             0.5f, 0.5f, 0.0f, 
             0.0f, 0.0f, 1.0f, 1.0f,
             
             -0.5f, 0.5f, 0.0f, 
             1.0f, 1.0f, 0.0f, 1.0f 
   
  };
  
  
 
  mSquare1Vertices = ByteBuffer.allocateDirect(square1VerticesData.length * mBytesPerFloat).order(ByteOrder.nativeOrder()).asFloatBuffer();   
  mSquare1Vertices.put(square1VerticesData).position(0);
   
 
  
 }
 
  
 
 
 
 
 
 ////////////////////////////////////////////////////////////////////////////
 
 @Override
 public void onSurfaceCreated(GL10 glUnused, EGLConfig config) 
 {
 
  GLES20.glClearColor(1.0f, 1.0f, 1.0f, 0.0f);
  
  GLES20.glCullFace(GLES20.GL_FRONT_AND_BACK);
 
 
  final float eyeX = 0.0f;
  final float eyeY = 0.0f;
  final float eyeZ = 1.5f;

 
  final float lookX = 0.0f;
  final float lookY = 0.0f;
  final float lookZ = -6.0f;

 
  final float upX = 0.0f;
  final float upY = 1.0f;
  final float upZ = 0.0f;

 
  Matrix.setLookAtM(mViewMatrix, 0, eyeX, eyeY, eyeZ, lookX, lookY, lookZ, upX, upY, upZ);
  
  final String vertexShader =

    // This matrix member variable provides a hook to manipulate
    // the coordinates of objects that use this vertex shader.
    "uniform mat4 uMVPMatrix;   \n"  +

    "attribute vec4 a_Position;  \n" +
    "attribute vec4 a_Color;    \n"  +
    "varying vec4 v_Color;      \n"  +
    
    "void main(){               \n"  +
    "v_Color = a_Color;         \n"  +
    // The matrix must be included as part of gl_Position
    // Note that the uMVPMatrix factor *must be first* in order
    // for the matrix multiplication product to be correct.
    " gl_Position = uMVPMatrix * a_Position; \n" +
    "}  \n";
  
  
  
  final String fragmentShader =
   "precision mediump float;       \n"  // Set the default precision to medium. We don't need as high of a 
             // precision in the fragment shader.    
    + "varying vec4 v_Color;          \n"  // This is the color from the vertex shader interpolated across the 
               // triangle per fragment.     
    + "void main()                    \n"  // The entry point for our fragment shader.
    + "{                              \n"
    + "   gl_FragColor = v_Color;     \n"  // Pass the color directly through the pipeline.    
    + "}                              \n";            
  
  // Load in the vertex shader.
  int vertexShaderHandle = GLES20.glCreateShader(GLES20.GL_VERTEX_SHADER);

  if (vertexShaderHandle != 0) 
  {
   // Pass in the shader source.
   GLES20.glShaderSource(vertexShaderHandle, vertexShader);

   // Compile the shader.
   GLES20.glCompileShader(vertexShaderHandle);

   // Get the compilation status.
   final int[] compileStatus = new int[1];
   GLES20.glGetShaderiv(vertexShaderHandle, GLES20.GL_COMPILE_STATUS, compileStatus, 0);

   // If the compilation failed, delete the shader.
   if (compileStatus[0] == 0) 
   {    
    GLES20.glDeleteShader(vertexShaderHandle);
    vertexShaderHandle = 0;
   }
  }

  if (vertexShaderHandle == 0)
  {
   throw new RuntimeException("Error creating vertex shader.");
  }
  
  // Load in the fragment shader shader.
  int fragmentShaderHandle = GLES20.glCreateShader(GLES20.GL_FRAGMENT_SHADER);

  if (fragmentShaderHandle != 0) 
  {
   // Pass in the shader source.
   GLES20.glShaderSource(fragmentShaderHandle, fragmentShader);

   // Compile the shader.
   GLES20.glCompileShader(fragmentShaderHandle);

   // Get the compilation status.
   final int[] compileStatus = new int[1];
   GLES20.glGetShaderiv(fragmentShaderHandle, GLES20.GL_COMPILE_STATUS, compileStatus, 0);

   // If the compilation failed, delete the shader.
   if (compileStatus[0] == 0) 
   {    
    GLES20.glDeleteShader(fragmentShaderHandle);
    fragmentShaderHandle = 0;
   }
  }

  if (fragmentShaderHandle == 0)
  {
   throw new RuntimeException("Error creating fragment shader.");
  }
  
  // Create a program object and store the handle to it.
  int programHandle = GLES20.glCreateProgram();
  
  if (programHandle != 0) 
  {
   // Bind the vertex shader to the program.
   GLES20.glAttachShader(programHandle, vertexShaderHandle);   

   // Bind the fragment shader to the program.
   GLES20.glAttachShader(programHandle, fragmentShaderHandle);
   
   // Bind attributes
   GLES20.glBindAttribLocation(programHandle, 0, "a_Position");
   GLES20.glBindAttribLocation(programHandle, 1, "a_Color");
   
   // Link the two shaders together into a program.
   GLES20.glLinkProgram(programHandle);

   // Get the link status.
   final int[] linkStatus = new int[1];
   GLES20.glGetProgramiv(programHandle, GLES20.GL_LINK_STATUS, linkStatus, 0);

   // If the link failed, delete the program.
   if (linkStatus[0] == 0) 
   {    
    GLES20.glDeleteProgram(programHandle);
    programHandle = 0;
   }
  }
  
  if (programHandle == 0)
  {
   throw new RuntimeException("Error creating program.");
  }
        
      
        mMVPMatrixHandle = GLES20.glGetUniformLocation(programHandle, "uMVPMatrix");        
        mPositionHandle  = GLES20.glGetAttribLocation(programHandle, "a_Position");
        mColorHandle     = GLES20.glGetAttribLocation(programHandle, "a_Color");        
        GLES20.glUseProgram(programHandle);   
        
       
        
 } 
 
 
 
 @Override
 public void onSurfaceChanged(GL10 glUnused, int width, int height) 
 {
 
  GLES20.glViewport(0, 0, width, height);

 
  final float ratio = (float) width / height;
  final float left = -ratio;
  final float right = ratio;
  final float bottom = -1.0f;
  final float top = 1.0f;
  final float near = 0.8f;
  final float far = 10.0f;
  
  Matrix.frustumM(mProjectionMatrix, 0, left, right, bottom, top, near, far);
 } 

 
 
 
 
 
 
 
 @Override
 public void onDrawFrame(GL10 glUnused) 
 {
  GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);           
                
      
         rot_angle +=  1.0f;

        // Draw the Maxwell Colour Triangle made up from 6 triangles
        Matrix.setIdentityM(mModelMatrix, 0);       
                
        drawTriangle(mMaxwell0Vertices); 
        drawTriangle(mMaxwell1Vertices); 
        drawTriangle(mMaxwell2Vertices); 
        drawTriangle(mMaxwell3Vertices); 
        drawTriangle(mMaxwell4Vertices); 
        drawTriangle(mMaxwell5Vertices); 
        
        
        
        //Draw a Square
        Matrix.setIdentityM(mModelMatrix, 0);       
        Matrix.translateM(mModelMatrix, 0, 0.0f, -0.5f, 0.0f);        
        Matrix.rotateM(mModelMatrix, 0, 90.0f, 1.0f, 0.0f, 0.0f);     //rotated to be flat on the ground.
        
        Matrix.rotateM(mModelMatrix, 0, rot_angle, 0.0f, 0.0f, 1.0f); //dynamic rotation about y axis       
        drawSquare(mSquare1Vertices);
        
       
        
 } 
 
 
 
 //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
 
 private void drawTriangle(final FloatBuffer aTriangleBuffer)
 {  
         
         aTriangleBuffer.position(mPositionOffset);
         GLES20.glVertexAttribPointer(mPositionHandle, mPositionDataSize, GLES20.GL_FLOAT, false,
         mStrideBytes, aTriangleBuffer);        
                
         GLES20.glEnableVertexAttribArray(mPositionHandle);        
        
      
         aTriangleBuffer.position(mColorOffset);
         GLES20.glVertexAttribPointer(mColorHandle, mColorDataSize, GLES20.GL_FLOAT, false,
         mStrideBytes, aTriangleBuffer);        
        
         GLES20.glEnableVertexAttribArray(mColorHandle);
       
         Matrix.multiplyMM(mMVPMatrix, 0, mViewMatrix, 0, mModelMatrix, 0);
        
        
         Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mMVPMatrix, 0);

         GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mMVPMatrix, 0);
         GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 3);                               
 }
 
 
 
 
 
private void drawSquare(final FloatBuffer aSquareBuffer)
 {  
  
         aSquareBuffer.position(mPositionOffset);
         GLES20.glVertexAttribPointer(mPositionHandle, mPositionDataSize, GLES20.GL_FLOAT, false, mStrideBytes, aSquareBuffer);        
                
         GLES20.glEnableVertexAttribArray(mPositionHandle);        
        
         aSquareBuffer.position(mColorOffset);
         GLES20.glVertexAttribPointer(mColorHandle, mColorDataSize, GLES20.GL_FLOAT, false, mStrideBytes, aSquareBuffer);        
        
         GLES20.glEnableVertexAttribArray(mColorHandle);
                
         Matrix.multiplyMM(mMVPMatrix, 0, mViewMatrix, 0, mModelMatrix, 0);
              
         Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mMVPMatrix, 0);

         GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mMVPMatrix, 0);
         
         GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 6); 
   }

}

