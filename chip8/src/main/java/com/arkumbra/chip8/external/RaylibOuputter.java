package com.arkumbra.chip8.external;

import static com.raylib.Jaylib.RAYWHITE;
import static com.raylib.Jaylib.RED;
import static com.raylib.Jaylib.VIOLET;
import static com.raylib.Raylib.BeginDrawing;
import static com.raylib.Raylib.BeginMode3D;
import static com.raylib.Raylib.CAMERA_FREE;
import static com.raylib.Raylib.CAMERA_ORBITAL;
import static com.raylib.Raylib.ClearBackground;
import static com.raylib.Raylib.DrawCube;
import static com.raylib.Raylib.DrawFPS;
import static com.raylib.Raylib.DrawGrid;
import static com.raylib.Raylib.DrawText;
import static com.raylib.Raylib.EndDrawing;
import static com.raylib.Raylib.EndMode3D;
import static com.raylib.Raylib.InitWindow;
import static com.raylib.Raylib.SetCameraMode;
import static com.raylib.Raylib.SetTargetFPS;
import static com.raylib.Raylib.UpdateCamera;
import static com.raylib.Raylib.WindowShouldClose;

import com.arkumbra.chip8.machine.KeyPressListener;
import com.arkumbra.chip8.machine.Keys;
import com.arkumbra.chip8.machine.ScreenMemory;
import com.raylib.Jaylib.Camera;
import com.raylib.Jaylib.Vector3;
import com.raylib.Raylib;
import java.awt.Color;
import java.awt.event.KeyListener;

public class RaylibOuputter implements ScreenOutputter {

  private Vector3 pixel = new Vector3(10,10, 10);
  private Camera camera;

  private ScreenMemory screenMemory;

  @Override
  public void init(ScreenMemory screenMemory, KeyPressListener keys) {
    this.screenMemory = screenMemory;


    InitWindow(800, 450, "Demo");
    SetTargetFPS(60);
    camera = new Camera(new Vector3(18,16,18),new Vector3(), new Vector3(0,1,0), 45, 0);
//    SetCameraMode(camera, CAMERA_FREE);
    SetCameraMode(camera, CAMERA_ORBITAL);

    while(!WindowShouldClose()){
      UpdateCamera(camera);
      BeginDrawing();
      ClearBackground(RAYWHITE);
      BeginMode3D(camera);
      DrawGrid(20, 1.0f);

      DrawCube(pixel,  1, 2, 4, RED);

      drawPixels();
      EndMode3D();
      DrawText("Hello world", 190, 200, 20, VIOLET);
      DrawFPS(20, 20);
      EndDrawing();
    }
    Raylib.CloseWindow();

//    new Thread(new GameLoop()).start();
  }


  private void drawPixels() {
    boolean[][] pixels = screenMemory.getPixels();


    for (int y = 0; y < pixels[0].length; y++) {
      for (int x = 0; x < pixels.length; x++) {
        if (pixels[x][y]) {
          Vector3 pixel = new Vector3(x,1, y);

          DrawCube(pixel, 1, 1, 1, VIOLET);
        }
      }
    }
  }


  class GameLoop implements Runnable {

    @Override
    public void run() {
      InitWindow(800, 450, "Demo");
      SetTargetFPS(60);
      camera = new Camera(new Vector3(18,16,18),new Vector3(), new Vector3(0,1,0), 45, 0);
//    SetCameraMode(camera, CAMERA_FREE);
      SetCameraMode(camera, CAMERA_ORBITAL);

      while(!WindowShouldClose()){
        UpdateCamera(camera);
        BeginDrawing();
        ClearBackground(RAYWHITE);
        BeginMode3D(camera);
        DrawGrid(20, 1.0f);

        DrawCube(pixel,  1, 2, 4, RED);

        drawPixels();
        EndMode3D();
        DrawText("Hello world", 190, 200, 20, VIOLET);
        DrawFPS(20, 20);
        EndDrawing();
      }
      Raylib.CloseWindow();
    }
  }
}
