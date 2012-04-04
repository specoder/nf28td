package TD3;

public class Contact {

	private String m_nom;
	private String m_mail;
	private String m_icon;

	public  Contact(String pNom, String pMail, String pIcon){
		m_nom = pNom;
		m_mail = pMail;
		m_icon = pIcon;
	}

	public String toString(){
		return m_nom;
	}

	public String getNom() {
		return m_mail;
	}

	public String getMail() {
		return m_mail;
	}

	public String getIcon() {
		return m_icon;
	}
}
