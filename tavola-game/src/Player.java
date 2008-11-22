import java.util.Vector;

import com.sun.tools.javac.util.Pair;

public class Player {
	private Pair<Integer, Integer> head;
	private Pair<Integer, Integer> last;
	private byte delay;
	private Vector<Pair<Integer, Integer>> body;
	private Pair<Byte, Byte> direction;
	private Pair<Byte, Byte> newDirection;

	public Player(int w, int h, Pair<Byte, Byte> direction) {
		this.delay = 5;
		this.head = new Pair<Integer, Integer>(w, h);
		this.last = new Pair<Integer, Integer>(w, h);
		this.body = new Vector<Pair<Integer, Integer>>();
		this.direction = direction;
	}

	public void update() {
		this.updateDirection();
		this.move();
	}

	private void updateDirection() {
		if (direction.fst != newDirection.fst
				&& direction.snd != newDirection.snd) {
			this.direction = newDirection;
			body.add(head);
		}
	}

	private void move() {
		this.head = new Pair<Integer, Integer>(this.head.fst
				+ this.direction.fst, this.head.snd + this.direction.snd);
		if (delay == 0) {
			changeLast();
		} else {
			delay--;
		}
	}

	private void changeLast() {
		if (this.last.fst == this.body.lastElement().fst) {
			if (this.last.snd < this.body.lastElement().snd) {
				this.last = new Pair<Integer, Integer>(this.last.fst,
						this.last.snd + 1);
			} else {
				this.last = new Pair<Integer, Integer>(this.last.fst,
						this.last.snd - 1);
			}
		} else {
			if (this.last.fst < this.body.lastElement().fst) {
				this.last = new Pair<Integer, Integer>(this.last.fst + 1,
						this.last.snd);
			} else {
				this.last = new Pair<Integer, Integer>(this.last.fst - 1,
						this.last.snd);
			}
		}
		if (this.last == this.body.lastElement())
			this.body.removeElementAt(this.body.size() - 1);
	}

	public Pair<Integer, Integer> getHead() {
		return head;
	}

	public Pair<Integer, Integer> getLast() {
		return last;
	}

	public Vector<Pair<Integer, Integer>> getBody() {
		return body;
	}

	public void setDelay(byte delay) {
		this.delay = delay;
	}

	public void setNewDirection(Pair<Byte, Byte> newDirection) {
		this.newDirection = newDirection;
	}
}
