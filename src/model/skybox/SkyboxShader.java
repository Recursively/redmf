package model.skybox;

import model.entities.Camera;
import model.shaders.ShaderProgram;
import org.lwjgl.util.vector.Matrix4f;
import model.toolbox.Maths;

public class SkyboxShader extends ShaderProgram {

    private static final String VERTEX_FILE = "src/model/skybox/skyboxVertexShader.glsl";
    private static final String FRAGMENT_FILE = "src/model/skybox/skyboxFragmentShader.glsl";

    private int location_projectionMatrix;
    private int location_viewMatrix;

    public SkyboxShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    public void loadProjectionMatrix(Matrix4f matrix){
        super.loadMatrix(location_projectionMatrix, matrix);
    }

    /*

        [ 1, 0, 0, x ]
        [ 0, 1, 0, y ]
        [ 0, 0, 1, z ]
        [ 0, 0, 0, 1 ]

        COMP261 paid off

        Last column of matrix determines the translation of skybox
    */
    public void loadViewMatrix(Camera camera){
        Matrix4f matrix = Maths.createViewMatrix(camera);
        matrix.m30 = 0;
        matrix.m31 = 0;
        matrix.m32 = 0;
        super.loadMatrix(location_viewMatrix, matrix);
    }

    @Override
    protected void getAllUniformLocations() {
        location_projectionMatrix = super.getUniformLocation("projectionMatrix");
        location_viewMatrix = super.getUniformLocation("viewMatrix");
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
    }

}