package client.protocol;

import java.io.IOException;
import java.util.ArrayList;

import client.application.Pipe;

public abstract class GameMessage<T> {
	protected abstract String getMessageString();
	
	protected abstract T parseAnswerStrings(String[] answerStrings) throws InvalidProtocolException;
	
	protected abstract boolean endOfAnswer(String s);
	
	public T send(Pipe pipe) throws IOException, InvalidProtocolException {
		pipe.println(getMessageString());
		ArrayList<String> answer = new ArrayList<String>();
		String s = null;
		while (!endOfAnswer(s)) {
			s = pipe.readln();
			if (s == null) {
				throw new InvalidProtocolException();
			}
			answer.add(s);
		}
		return parseAnswerStrings(answer.toArray(new String[]{}));
	}
	
	
}
