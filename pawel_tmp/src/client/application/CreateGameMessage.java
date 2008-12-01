package client.application;

import client.protocol.GameMessage;

public class CreateGameMessage extends GameMessage<String> {
	private String levelId;
	private int maxPlayersCount;
	private int bonusesCount;
	private String theme;
	
	public CreateGameMessage(String levelId, int maxPlayersCount, int bonusesCount, String theme) {
		// TODO Auto-generated constructor stub
		this.levelId = levelId;
		this.maxPlayersCount = maxPlayersCount;
		this.bonusesCount = bonusesCount;
		this.theme = theme;
	}

	@Override
	protected boolean endOfAnswer(String s) {
		// TODO Auto-generated method stub
		return s != null;
	}

	@Override
	protected String getMessageString() {
		// TODO Auto-generated method stub
		return "CREATE_GAME " + levelId + " " + maxPlayersCount + " " + bonusesCount + " " + theme;
	}

	@Override
	protected String parseAnswerStrings(String[] answerStrings) {
		// TODO Auto-generated method stub
		if (answerStrings[0].matches("^OK [0-9]+$")) {
			return answerStrings[0].substring(3);
		} else {
			return null;
		}
	}

}
