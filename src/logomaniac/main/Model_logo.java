package logomaniac.main;

import java.io.Serializable;

public class Model_logo implements Serializable {

		private static final long serialVersionUID = -556449800181279594L;
		
		private int name;
		private int imageResource;
		private Model_categorie categorie;
		private int score;

		public Model_logo(int caterpillar, int imageResource, Model_categorie categorie) {
			this.imageResource = imageResource;
			this.setName(caterpillar);
			this.setCategorie(categorie);
			score = 0;
		}
		
		public Model_logo(int name, int imageResource, int score, Model_categorie categorie) {
			this.imageResource = imageResource;
			this.setName(name);
			this.setCategorie(categorie);
			this.score = score;
		}

		public int getImageResource() {
			return imageResource;
		}

		public void setImageResource(int imageResource) {
			this.imageResource = imageResource;
		}

		public int getName() {
			return name;
		}

		public void setName(int name) {
			this.name = name;
		}

		public Model_categorie getCategorie() {
			return categorie;
		}

		public void setCategorie(Model_categorie categorie) {
			this.categorie = categorie;
		}

		public int getScore() {
			return score;
		}

		public void setScore(int score) {
			this.score = score;
		}		
}
