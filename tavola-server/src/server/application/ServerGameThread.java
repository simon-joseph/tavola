package server.application;

import java.util.LinkedList;
import java.util.TreeMap;

import server.protocol.NextMoveRequest;
import server.protocol.ShowMovesRequest;
import data.game.Game;
import data.game.Player;
import data.network.ConnectionLostException;
import data.network.RequestSendingException;

/**
 * @author polchawa
 * 
 */
public class ServerGameThread extends Thread {
  private static final Object DEATH_MESSAGE = "DEATH";
  protected static final long SINGLE_TURN_TIME_MS = 200;
  private final Game game;

  public ServerGameThread(Game game) {
    super();
    this.game = game;

    System.out.println(game.getPlayers().size());
  }

  @Override
  public void run() {
    LinkedList<Thread> nextMoveThreads = new LinkedList<Thread>();
    final TreeMap<String, String> playersMoves = new TreeMap<String, String>();
    final LinkedList<Player> playersInformed = new LinkedList<Player>();

    for (final Player player : game.getPlayers()) {
      System.out.println(player.getId());
      nextMoveThreads.addLast(new Thread(new Runnable() {
        public void run() {
          while (game.isRunning()) {
            try {
              Long startTime = System.currentTimeMillis();
              String move = new NextMoveRequest()
                  .send(player.getMessagesPipe());
              synchronized (playersMoves) {
                playersMoves.put(player.getId(), move);
                if (playersMoves.size() < game.getPlayers().size()) {
                  while (playersMoves.size() < game.getPlayers().size()) {
                    // we have received all moves!
                    try {
                      playersMoves.wait();
                    } catch (InterruptedException e) {
                      // ignore
                    }
                  }
                } else {
                  playersMoves.notifyAll();
                }
              }
              if (move.equals(ServerGameThread.DEATH_MESSAGE)) {
                return;
              }
              new ShowMovesRequest(playersMoves).send(player.getMessagesPipe());

              synchronized (playersInformed) {
                playersInformed.addLast(player);
                if (playersInformed.size() < game.getPlayers().size()) {
                  while (playersInformed.size() < game.getPlayers().size()) {
                    try {
                      playersInformed.wait();
                    } catch (InterruptedException e) {
                      // ignore
                    }
                  }
                } else {
                  playersInformed.notifyAll();
                }
              }

              long sleep = ServerGameThread.SINGLE_TURN_TIME_MS
                  - (System.currentTimeMillis() - startTime);

              if (sleep > 0) {
                try {
                  Thread.sleep(sleep);
                } catch (InterruptedException e) {
                  // TODO Auto-generated catch block
                  e.printStackTrace();
                }
              }
            } catch (RequestSendingException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
            } catch (ConnectionLostException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
            }
          }
        }
      }));
    }
    try {
      // workaround na problem ze NEXT zostanie wyslane zanim klient bedzie
      // gotowy odbierac
      Thread.sleep(1000);
    } catch (InterruptedException e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    }

    for (Thread nextMoveThread : nextMoveThreads) {
      nextMoveThread.start();
    }

    for (Thread nextMoveThread : nextMoveThreads) {
      try {
        nextMoveThread.join();
      } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
  }

}
