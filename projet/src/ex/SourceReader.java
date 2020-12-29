package ex;


/**
 * Gere la lecture d'une chaine de caractere
 * @author geo02
 *
 */
public class SourceReader {

	private String src;
	private int index=0;

	public SourceReader(String contenu) {
		this.src = contenu;
	}

	/**
	 * Renvoie un caractère ou null si fin de fichier
	 * 
	 */
	public Character lectureSymbole() {
		if (index >= src.length()) return null;
		char c = src.charAt(index);
		index++;
		return c;
	}
	
	
	public void goBack() {
		if (index == 0) {
			System.out.println("Appel de goBack interdit");
		} else {
			index--;
		}
	}
	
}
