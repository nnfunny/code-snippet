//  Description: The game cycle implementation with Canvas
//  Source: https://youtu.be/SKFNouTFgto
//  Date: 09/03/2021

import java.awt.*;

public class GameCycle extends Canvas implements Runnable {
  public static int WIDTH = 800, HEIGHT = 600;
  public String title = "Example";

  private Thread thread;
  private boolean isRunning = false;

  public GameCycle() {
    new Window(WIDTH, HEIGHT, title, this);
  }

  public static void main(String[] args) {
    new GameCycle();
  }

  private synchronized void start() {
    if (isRunning) return;

    thread = new Thread(this);
    thread.start();
    isRunning = true;
  }

  @Override
  public void run() {
    this.requestFocus();
    long lastTime = System.nanoTime();
    double amountOfTicks = 60.0;
    double ns = 1000000000 / amountOfTicks;
    double delta = 0;
    long timer = System.currentTimeMillis();
    int frames = 0;

    while(isRunning) {
      long now = System.nanoTime();
      delta += (now - lastTime) / ns;
      lastTime = now;

      while(delta >= 1) {
        tick();
        delta--;
      }
      render();
      frames++;

      if(System.currentTimeMillis() - timer > 1000) {
        timer += 1000;
        frames = 0;
      }
    }
    stop();
  }

  private void tick() {
    // Updates the game
  }

  private void render() {
    // Renders the game
  }

  private synchronized void stop() {
    if (!isRunning) return;

    try {
      thread.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
