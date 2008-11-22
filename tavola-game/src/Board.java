import com.sun.tools.javac.util.Pair;

public class Board {
	private class Field {
		private boolean bonus;
		private boolean head;
		private boolean last;
		private boolean body;

		public Field() {
			this.bonus = false;
			this.head = false;
			this.last = false;
			this.body = false;
		}

		public void switchBonus() {
			this.bonus = !this.bonus;
		}

		public void switchHead() {
			this.head = !this.head;
		}

		public void switchLast() {
			this.last = !this.last;
		}

		public void switchBody() {
			this.body = !this.body;
		}

		public boolean collision() {
			return (this.head && (this.last || this.body));
		}

		public boolean bonusReached() {
			if (this.bonus && this.head) {
				switchBonus();
				return true;
			} else
				return false;
		}
	}

	Field[][] fields;
	Player player;
	final int width;
	final int height;

	public Board(int width, int height) {
		this.width = width;
		this.height = height;
		init();
	}

	private void init() {
		initPlayers();
		initFields();
	}

	private void initPlayers() {
		Pair<Byte, Byte> direction = new Pair<Byte, Byte>((byte) -1, (byte) 0);
		this.player = new Player(width / 4, height / 4, direction);
	}

	private void initFields() {

	}
}
