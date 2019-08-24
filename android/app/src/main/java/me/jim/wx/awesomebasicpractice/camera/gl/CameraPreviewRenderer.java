package me.jim.wx.awesomebasicpractice.camera.gl;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.view.TextureView;


/**
 * Example renderer that changes colors and tones of camera feed
 * based on touch position.
 */
public class CameraPreviewRenderer extends CameraRenderer
{
    private static final String TAG = "CameraPreviewRenderer";

    private static final String mVetexShaderString = "//position\n" +
            "attribute vec4 position;\n" +
            "\n" +
            "//camera transform and texture\n" +
            "uniform mat4 camTextureTransform;\n" +
            "attribute vec4 camTexCoordinate;\n" +
            "\n" +
            "//tex coords\n" +
            "varying vec2 v_CamTexCoordinate;\n" +
            "\n" +
            "void main()\n" +
            "{\n" +
            "    //camera texcoord needs to be manipulated by the transform given back from the system\n" +
            "    v_CamTexCoordinate = (camTextureTransform * camTexCoordinate).xy;\n" +
            "\n" +
            "    gl_Position = position;\n" +
            "}";

    private static final String mFragShaderString = "#extension GL_OES_EGL_image_external : require\n" +
            "\n" +
            "//necessary\n" +
            "precision mediump float;\n" +
            "uniform samplerExternalOES camTexture;\n" +
            "varying vec2 v_CamTexCoordinate;\n" +
            "uniform float previewWH;\n"+
            "\n" +
            "\n" +
            "void main ()\n" +
            "{\n" +
            "       vec2 coord = v_CamTexCoordinate - vec2(0.5, 0.5);\n" +
            "       float factor = 0.49;\n" +
            "       float scale = 1.0/(0.5-factor);\n" +
            "       float radius = length(coord); //计算出圆的半径\n" +
            "\n" +
            "        //这句是关键 在采样的时候width不要完全采样，需要乘以0.75的系数，这样 width == height [针对摄像头出来的数据是 640 * 480]\n" +
            "       vec4 color = texture2D(camTexture, vec2(previewWH*v_CamTexCoordinate.x,v_CamTexCoordinate.y));\n" +
            "\n" +
            "       float stepA = 1.0-step(0.5, radius);\n" +
            "       float stepB = 1.0-step(factor, radius);\n" +
            "\n" +
            "       vec4 innerColor = stepB * color;\n" +
            "       vec4 midColor = (stepA-stepB) * (1.0-(radius-factor) * scale) * vec4(1.0, 1.0, 1.0, 1.0);\n" +
            "       gl_FragColor = innerColor + midColor;\n" +
            "}";


    /**
     * By not modifying anything, our default shaders will be used in the assets folder of shadercam.
     *
     * Base all shaders off those, since there are some default uniforms/textures that will
     * be passed every time for the camera coordinates and texture coordinates
     */
    public CameraPreviewRenderer(Context context, TextureView textureView, SurfaceTexture previewSurface, int width, int height)
    {
        super(context, previewSurface, textureView,width, height, mFragShaderString, mVetexShaderString,false);

        //other setup if need be done here
    }

    /**
     * we override {@link #setUniformsAndAttribs()} and make sure to call the super so we can add
     * our own uniforms to our shaders here. CameraRenderer handles the rest for us automatically
     */
    @Override
    protected void setUniformsAndAttribs()
    {
        super.setUniformsAndAttribs();
    }

}
