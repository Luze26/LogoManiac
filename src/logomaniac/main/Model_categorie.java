package logomaniac.main;

import java.io.Serializable;
import java.util.ArrayList;

public class Model_categorie implements Serializable {
	
	private static final long serialVersionUID = 707925728817381300L;
	
	private int titre;
	private int score;
	private int trouve;
	private int total;
	private int lock_titre;
	private int lock_score;
	private boolean lock = false;
	
	public Model_categorie(int voitures, int score, int trouve, int total, int lock_score, int lock_titre) {
		this.titre = voitures;
		this.score = score;
		this.setTrouve(trouve);
		this.setTotal(total);
		this.setLock_score(lock_score);
		this.setLock_titre(lock_titre);
	}

	public int getTitre() {
		return titre;
	}
	
	public void setTitre(int titre) {
		this.titre = titre;
	}
	
	public int getScore() {
		return score;
	}
	
	public void setScore(int score) {
		this.score = score;
	}

	public int getTrouve() {
		return trouve;
	}

	public void setTrouve(int trouve) {
		this.trouve = trouve;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getLock_titre() {
		return lock_titre;
	}

	public void setLock_titre(int lock_titre) {
		this.lock_titre = lock_titre;
	}

	public int getLock_score() {
		return lock_score;
	}

	public void setLock_score(int lock_score) {
		this.lock_score = lock_score;
	}

	public boolean isLock() {
		return lock;
	}

	public void setLock(boolean lock) {
		this.lock = lock;
	}
}
