import java.awt.*;

public class GameCycle extends Canvas implements Runnable {
  public static final long NS_PER_SECOND = 1000000000;
  public static final double TPS = 60.0;
  public static int WIDTH = 800, HEIGHT = 600;
  public String title = "Example";

  private Thread thread;
  private boolean isRunning = false;

  public GameCycle() {
//    new Window(WIDTH, HEIGHT, title, this);
  }

  public static void main(String[] args) {
    GameCycle game = new GameCycle();
    game.start();
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
    double NS_PER_TICK = NS_PER_SECOND / TPS;
    double delta = 0;
    long timer = System.currentTimeMillis();
    int frames = 0;
    int ticks = 0;
    boolean render;

    while (isRunning) {
      render = false;
      long now = System.nanoTime();
      delta += now - lastTime;
      lastTime = now;

      while (delta >= NS_PER_SECOND) {
        tick();
        ticks++;
        delta -= NS_PER_TICK;
        render = true;
      }

      if (render) {
        render();
        frames++;
      } else {
        try {
          //noinspection BusyWait
          Thread.sleep(1);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }

      if (System.currentTimeMillis() - timer > 1000) {
        timer += 1000;
        System.out.println(ticks + " | " + frames);
        ticks = 0;
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
