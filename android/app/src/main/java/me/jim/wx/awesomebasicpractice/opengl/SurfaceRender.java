package me.jim.wx.awesomebasicpractice.opengl;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Date: 2019/8/25
 * Name: wx
 * Description:
 */
public class SurfaceRender implements GLSurfaceView.Renderer {

    private FloatBuffer floatBuffer;

    private String vertexCode =
            "attribute vec4 a_Position;" +
                    "void main(){" +
                    "gl_Position = a_Position;" +
                    "gl_PointSize = 30.0;" +
                    "}";

    private String fragCode =
            "precision medium float;" +
                    "uniform vec4 u_Color;" +
                    "void main(){" +
                    "gl_FragColor = uColor;" +
                    "}";

    public SurfaceRender() {

    }

    /**
     * 编译 OpenGL 程序基本流程如下：
     * <p>
     * 1、编译着色器
     * 2、创建 OpenGL 程序和着色器链接
     * 3、验证 OpenGL 程序
     * 4、确定使用 OpenGL 程序
     */

    public static int compileVertexShader(String shaderCode) {
        return compileShader(GLES20.GL_VERTEX_SHADER, shaderCode);
    }


    public static int compileFragShader(String shaderCode) {
        return compileShader(GLES20.GL_FRAGMENT_SHADER, shaderCode);
    }

    private static final String TAG = "SurfaceRender";

    //编译着色器
    private static int compileShader(int type, String shaderCode) {
        //创建id
        final int shaderObjectId = GLES20.glCreateShader(type);
        Log.e(TAG, "compileShader: "+ shaderObjectId);
        if (shaderObjectId == 0) {
            return 0;
        }
        //绑定id和代码
        GLES20.glShaderSource(shaderObjectId, shaderCode);
        //编译着色器
        GLES20.glCompileShader(shaderObjectId);
        //验证编译结果
        final int[] compileStatus = new int[1];
        GLES20.glGetShaderiv(shaderObjectId, GLES20.GL_COMPILE_STATUS, compileStatus, 0);
        if (compileStatus[0] == 0) {
            //失败则删除
            GLES20.glDeleteShader(shaderObjectId);
            return 0;
        }
        return shaderObjectId;
    }

    //创建program和链接着色器
    public static int linkProgram(int vertexShaderId, int fragmentShaderId) {
        //创建OpenGL程序ID
        final int programObjectId = GLES20.glCreateProgram();
        if (programObjectId == 0) {
            return 0;
        }
        //链接 顶点着色器
        GLES20.glAttachShader(programObjectId, vertexShaderId);
        //链接 片段着色器
        GLES20.glAttachShader(programObjectId, fragmentShaderId);
        // 链接着色器之后， 链接OpenGL程序
        GLES20.glLinkProgram(programObjectId);
        //验证链接结果
        final int[] linkStatus = new int[1];
        GLES20.glGetProgramiv(programObjectId, GLES20.GL_LINK_STATUS, linkStatus, 0);
        if (linkStatus[0] == 0) {
            //失败删除
            GLES20.glDeleteProgram(programObjectId);
        }
        return programObjectId;
    }

    //验证
    public static boolean validateProgram(int programObjectId) {
        GLES20.glValidateProgram(programObjectId);
        final int[] validateStatus = new int[1];
        GLES20.glGetProgramiv(programObjectId, GLES20.GL_VALIDATE_STATUS, validateStatus, 0);
        return validateStatus[0] != 0;
    }

    //确定使用
    public static int buildProgram(String vertexCode, String fragCode) {
        int vertexShader = compileVertexShader(vertexCode);
        int fragShader = compileFragShader(fragCode);
        int program = linkProgram(vertexShader, fragShader);
        validateProgram(program);
        return program;
    }


    private int aColorLocation;
    private int aPositionLocation;

    float[] pointVertex = {
            0f, 0f
    };


    float[] triangleVertex = {
            0.5f, 0f,
            0f, 1.0f,
            1.0f, 1.0f
    };

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
//        GLES20.glClearColor(1f, 1f, 1f, 1f);

        int program = buildProgram(vertexCode, fragCode);
        GLES20.glUseProgram(program);
        floatBuffer = ByteBuffer.allocateDirect(pointVertex.length * Constant.BYTES_PRE_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(pointVertex);

        aColorLocation = GLES20.glGetUniformLocation(program, "u_Color");
        aPositionLocation = GLES20.glGetAttribLocation(program, "a_Position");

        // 给绑定的值赋值，也就是从顶点数据那里开始读取，每次读取间隔是多少
        setVertexAttrPointer(0, aPositionLocation, 2, 0);
    }

    /**
     * 使顶点某个数据使能的
     *
     * @param dataOffset
     * @param attributeLocation
     * @param componentCount
     * @param stride
     */
    public void setVertexAttrPointer(int dataOffset, int attributeLocation, int componentCount, int stride) {
        floatBuffer.position(dataOffset);

        GLES20.glVertexAttribPointer(attributeLocation, componentCount, GLES20.GL_FLOAT, false, stride, floatBuffer);
        GLES20.glEnableVertexAttribArray(attributeLocation);

        floatBuffer.position(0);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        //确定视图大小
        GLES20.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        //清屏
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        //绘制
        GLES20.glUniform4f(aColorLocation, 0.0f, 0.0f, 1.0f, 1.0f);
        GLES20.glDrawArrays(GLES20.GL_POINTS, 0, 1);
    }
}
