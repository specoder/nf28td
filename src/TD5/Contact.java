package TD5;
public class Contact {

	private String m_nom;
	private String m_mail;
	private String m_icon;
	
/*	public void setNom(String m_nom) {
		this.m_nom = m_nom;
	}

	public void setMail(String m_mail) {
		this.m_mail = m_mail;
	}

	public void setIcon(String m_icon) {
		this.m_icon = m_icon;
	}*/
	
	public  void setContact(String pNom, String pMail, String pIcon){
		m_nom = pNom;
		m_mail = pMail;
		m_icon = pIcon;
	}

	public  Contact(String pNom, String pMail, String pIcon){
		m_nom = pNom;
		m_mail = pMail;
		m_icon = pIcon;
	}

	public String toString(){
		return m_nom;
	}

	public String getNom() {
		return m_nom;
	}

	public String getMail() {
		return m_mail;
	}

	public String getIconURL() {
		return m_icon;
	}
}
