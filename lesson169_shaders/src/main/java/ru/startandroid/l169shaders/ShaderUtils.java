package ru.startandroid.l169shaders;

import android.content.Context;
import android.content.res.Resources.NotFoundException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static android.opengl.GLES20.GL_VERTEX_SHADER;
import static android.opengl.GLES20.GL_FRAGMENT_SHADER;
import static android.opengl.GLES20.GL_COMPILE_STATUS;
import static android.opengl.GLES20.GL_LINK_STATUS;

import static android.opengl.GLES20.glCreateShader;
import static android.opengl.GLES20.glShaderSource;
import static android.opengl.GLES20.glCompileShader;
import static android.opengl.GLES20.glGetShaderiv;
import static android.opengl.GLES20.glDeleteShader;
import static android.opengl.GLES20.glCreateProgram;
import static android.opengl.GLES20.glAttachShader;
import static android.opengl.GLES20.glLinkProgram;
import static android.opengl.GLES20.glGetProgramiv;
import static android.opengl.GLES20.glDeleteProgram;
import static android.opengl.GLES20.glUseProgram;


public class ShaderUtils {

    public static int createProgram(Context context, int vertexShaderRawId, int fragmentShaderRawId) {

        int vertexShaderId = ShaderUtils.createShader(GL_VERTEX_SHADER, context, vertexShaderRawId);
        int fragmentShaderId = ShaderUtils.createShader(GL_FRAGMENT_SHADER, context, fragmentShaderRawId);

        final int programId = glCreateProgram();
        if (programId == 0) {
            return 0;
        }

        glAttachShader(programId, vertexShaderId);
        glAttachShader(programId, fragmentShaderId);

        glLinkProgram(programId);
        final int[] linkStatus = new int[1];
        glGetProgramiv(programId, GL_LINK_STATUS, linkStatus, 0);
        if (linkStatus[0] == 0) {
            glDeleteProgram(programId);
            return 0;
        }

        glUseProgram(programId);
        return programId;

    }

    private static int createShader(int type, Context context, int shaderRawId) {
        String shaderText = FileUtils
                .readTextFromRaw(context, shaderRawId);
        return ShaderUtils.createShader(type, shaderText);
    }

    private static int createShader(int type, String shaderText) {
        final int shaderId = glCreateShader(type);
        if (shaderId == 0) {
            return 0;
        }
        glShaderSource(shaderId, shaderText);
        glCompileShader(shaderId);
        final int[] compileStatus = new int[1];
        glGetShaderiv(shaderId, GL_COMPILE_STATUS, compileStatus, 0);
        if (compileStatus[0] == 0) {
            glDeleteShader(shaderId);
            return 0;
        }
        return shaderId;
    }


}
