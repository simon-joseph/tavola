package client.protocol;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import data.gamePawel.Game;
import data.network.TavolaProtocol;

public class TavolaPreGameProtocol implements TavolaProtocol {

  public ArrayList<Game> listGames(BufferedReader br, PrintWriter pw)
      throws InvalidProtocolException, IOException {
    pw.println("LIST_GAMES");
    ArrayList<Game> games = new ArrayList<Game>();
    String line;
    while ((line = br.readLine()) != null) {
      if (line.equals("END")) {
        return games;
      }
      String[] args = line.split(" ");
      if (args.length != 6) {
        throw new InvalidProtocolException();
      }
      // gameId:
      int[] numbers = new int[5];
      for (int i = 0; i < 5; ++i) {
        try {
          numbers[i] = Integer.parseInt(args[i]);
        } catch (NumberFormatException e) {
          throw new InvalidProtocolException();
        }
      }
      // gameId, levelId, playersCount, maxPlayersCount, bonusesCount,
      // creatorName
      games.add(new Game(args[0], args[1], numbers[2], numbers[3], numbers[4],
          args[5]));
    }
    throw new InvalidProtocolException();
  }

  public String processInput(String input) {
    return null;
  }

}
