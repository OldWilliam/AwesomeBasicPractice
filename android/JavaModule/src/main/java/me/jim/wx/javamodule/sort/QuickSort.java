package me.jim.wx.javamodule.sort;

import java.lang.reflect.Proxy;
import java.util.Collections;
import java.util.Locale;

/**
 * 思想：冒泡排序扫描一趟最多才调整一个逆序，可以想法让一趟多调整些逆序数字
 * 方法；找个数，小的放这数前面，大的放后面，就可以一趟调整多个逆序了
 * 关键词：递归、枢轴、子序列
 */
class QuickSort implements SortMain.ISort {

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
    @Override
    public void sort(int[] array) {

        innerSort(array, 0, array.length - 1);
        System.out.println(mVetexShaderString);
        System.out.println(mFragShaderString);
    }

    private void innerSort(int[] array, int left, int right) {
        if (left < right) {//不是while，递归的退出条件，不可再分子列表
            int pivot = QuickPass(array, left, right);
            innerSort(array, left, pivot - 1);
            innerSort(array, pivot + 1, right);
        }


    }

    int QuickPass(int[] array, int left, int right) {
        int low = left;
        int high = right;
        int pivot = array[low];

        while (low < high) {
            while (array[high] > pivot && high > low) {
                high--;
            }
            if (low < high) {//调整完成了，应当退出，不然会影响后面逻辑
                array[low] = array[high];
                low++;
            } else {
                break;
            }

            while (array[low] < pivot && high > low) {
                low++;
            }
            if (low < high) {
                array[high] = array[low];
                high--;
            } else {
                break;
            }
        }
        array[low] = pivot;
        for (int anArray : array) {
            System.out.print(String.format(Locale.US, "%2d ", anArray));
        }
        System.out.println();
        return low;
    }
}
