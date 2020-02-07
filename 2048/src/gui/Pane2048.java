package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

import logika.Igra;

/**
 *  Klasa u kojoj kreiramo GUI igrice. Korisnik igra igru pomocu tipki na tastaturi koje predstavljaju strelice za gore, dole, lijevo i desno.
 *  
 *  @author Amina Sinanovic
 * */

public class Pane2048 {
	
	/** Atribut u kojem smjestamo samu igru i njenu logiku */
	Igra g;
	/** Okvir u kojem se prikazuje kompletna igra. */
	JFrame okvir;
	/** Okvir koji iskoci kada korisnik izgubi. */
	JFrame kraj;
	/** Okvir koji iskoci kada korisnik pobijedi.*/
	JFrame pobjeda;
	/** Panel u kojeg smjestamo glavni dio igre tj. polja koja korisnik pomjera.*/
	JPanel grid;
	/** Panel koji se nalazi iznad glavnog dijela igre. U njemu se nalazi naziv igre i dugme za Novu igru. */
	JPanel header;
	/** Matrica u kojoj su smjesteni dugmici na kojima se prikazuju brojevi i s kojim korisnik igra.*/
	JButton[][] polja;
	/** Dugme za ponovno pokretanje igre. Nalazi se u headeru.*/
	JButton novaigra_d1;
	/** Dugme za ponovno pokretanje igre. Pojavljuje se u okviru kraj ili pocetak, ovisno od ishoda igre.*/
	JButton novaigra_d2;
	/** Dugme za nastavak igre nakon stizanja do broja 2048. Nalazi se u okviru pobjeda.*/
	JButton nastavi;
	/** Niz boja koje se dodjeljuju brojevima.*/
	Color[] boje;
	/** Boja koja se koristi za polja koja ne nose nikakvu vrijednost.*/
	Color pozadina;
	/** Boja brojeva na svijetlim pozadinama, tj za brojeve 2 i 4.*/
	Color tamniBroj; 
	/** Boja brojeva na tamnijim pozadinama.*/
	Color svijetliBroj;
	
	/**
	 * Konstruktor bez parametara najprije incijalizuje novu igru u varijablu g. 
	 * Zatim postavlja odgavarajuce boje se koriste pri igranju. Za brojeve od 2 do 2048 koji se pojavljuju u igri boje su razlicite,
	 * a brojevi iznad 2048 imaju istu boju kao 2048.
	 * Zatim se inicijalizuje glavni okvir za koji koristimo BoxLayout kako bi sadrzaj tog okvira redali po Y osi.
	 * Dalje se kreira header koji je podijeljen u dva dijela: lijevi dio koji sadrzi pravougaonik na kojem je ispisan naziv igre
	 * i desni dio u kojem se nalazi dugme za ponovno pokretanje igre.
	 * Nakon toga se kreiraju poruke i okviri za kraj, odnosno pobjedu, dugme novaigra_d2 koje ponovo pokrece igru ako korisnik
	 * klikne na njeg a pojavaljuje se i u okviru pobjeda i kraj, dugme nastavi koje se pojavljuje u okviru pobjeda ukoliko korisnik
	 * zeli da igra i preko 2048.
	 * Zatim se kreira KeyListener koji ce se dodati na okvir. Te tri vrste ActionListenera za 3 vrste dugmadi koja postoje, a to su:
	 * za pokretanje nove igre (novaigra_d1, novaigra_d2), za nastavak igre (nastavi), te polja igre (matrica polja).
	 * Dalje se kreira grid u koji se smjesta glavni dio igre. U njeg se smjestaju dugmad koji su sacuvani u matrici polja.
	 * Boja polja i,j se doznaje na osnovu broja koji se nalazi u tabeli igre g na poziciji i,j te niza boje kojeg smo kreirali na pocetku
	 * konstruktora. Za boju se uzima boja na indeksu koja predstavlja vrijednost logoritma po bazi dva od vrijednosti koja se nalazi na polju i,j.
	 * Ukoliko je vrijednost polja 0, ne ispisujemo nista. Na svako polje dodajemo ActionListener.
	 * Nakon sto smo kreirali i header i grid, dodajemo ih u okvir.
	 **/
	
	public Pane2048(){
		
		g = new Igra();

		pozadina = Color.decode("#bcad9f");
		tamniBroj = Color.decode("#786f61");
		svijetliBroj = Color.decode("#fcf1e2");
		boje = new Color[]{pozadina,Color.decode("#eee4da"),Color.decode("#ece0c8"),Color.decode("#f1b078"),
				Color.decode("#ec8c52"),Color.decode("#f67b60"),Color.decode("#ea5837"),Color.decode("#f2d86a"),
				Color.decode("#f2d86a"),Color.decode("#e4c02c"),Color.decode("#e2b913"),Color.decode("#5eda91")};
		
		
		okvir = new JFrame("2048");
		okvir.setLayout(new BoxLayout(okvir.getContentPane(), BoxLayout.Y_AXIS));
		okvir.setBackground(pozadina);
			
		header = new JPanel();
		header.setLayout(new GridLayout(1,2));
		header.setPreferredSize(new Dimension(620,200));
		header.setBackground(pozadina);
		
		JPanel header_lijevo = new JPanel(){
			public void paintComponent(Graphics g1){
				int x = header.getWidth();
				int y = header.getHeight();
		        Graphics2D g = (Graphics2D) g1;
				g.setColor(boje[7]);
				g.fillRect(x/12,y/4,x/3, y/2);
		        g.setColor(Color.WHITE);
		        g.setFont(new Font("Cambria", Font.BOLD, x/35+y/7)); 
		        g.drawString("2048", x/6, 4*y/7);
			}
		};
		
		JPanel header_desno = new JPanel();
		header_desno.setLayout(null);
		header_desno.setBackground(pozadina);
		
		novaigra_d1 = new JButton("NOVA IGRA");
		novaigra_d1.setFont(new Font("Cambria", Font.BOLD, 30));
		novaigra_d1.setBackground(boje[3]);
		novaigra_d1.setBounds(50,50,200,100);
		header_desno.add(novaigra_d1);
		
		header.add(header_lijevo);
		header.add(header_desno);
		
		JLabel poruka_kraj = new JLabel("Izgubili ste.");
		poruka_kraj.setFont(new Font("Cambria", Font.BOLD, 30));
		poruka_kraj.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JLabel poruka_pobjeda = new JLabel("Pobijedili ste.");
		poruka_pobjeda.setFont(new Font("Cambria", Font.BOLD, 30));
		poruka_pobjeda.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		novaigra_d2 = new JButton("NOVA IGRA");
		novaigra_d2.setFont(new Font("Cambria", Font.BOLD, 30));
		novaigra_d2.setBackground(boje[3]);
		novaigra_d2.setBounds(50,50,200,100);
		novaigra_d2.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		nastavi = new JButton("NASTAVI");
		nastavi.setFont(new Font("Cambria", Font.BOLD, 30));
		nastavi.setBackground(boje[3]);
		nastavi.setBounds(50,50,200,100);
		nastavi.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		kraj = new JFrame("KRAJ");
		kraj.setLayout(new BoxLayout(kraj.getContentPane(), BoxLayout.Y_AXIS));
		kraj.add(Box.createRigidArea(new Dimension(0,50)));
		kraj.add(poruka_kraj);
		kraj.add(Box.createRigidArea(new Dimension(0,10)));
		
		pobjeda = new JFrame("POBJEDA");
		pobjeda.setLayout(new BoxLayout(pobjeda.getContentPane(), BoxLayout.Y_AXIS));
		pobjeda.add(Box.createRigidArea(new Dimension(0,40)));
		pobjeda.add(poruka_pobjeda);
		pobjeda.add(Box.createRigidArea(new Dimension(0,10)));
		
		pobjeda.add(nastavi);
		pobjeda.add(Box.createRigidArea(new Dimension(0,10)));
		
		KeyListener klik = napraviKeyListener();
		ActionListener dugme = napraviActionListenerDugme();
		ActionListener dugme_novaigra = napraviActionListenerNovaIgra();
		ActionListener dugme_nastavi = napraviActionListenerNastavi();
		
		novaigra_d1.addActionListener(dugme_novaigra);
		novaigra_d2.addActionListener(dugme_novaigra);
		nastavi.addActionListener(dugme_nastavi);
		
		grid = new JPanel();
		grid.setLayout(new GridLayout(4,4,4,4));
		grid.setBackground(tamniBroj);
		grid.setBorder(BorderFactory.createEmptyBorder(4,4,4,4));
		
		polja = new JButton[4][4];
		int[][] tabela = g.stanjeTabele();
		
		for(int i=0; i<4; i++)
			for(int j=0; j<4; j++){
				polja[i][j] = new JButton();
				polja[i][j].setPreferredSize(new Dimension(150,150));
				if(tabela[i][j]==2 || tabela[i][j]==4)
					polja[i][j].setForeground(tamniBroj);
				else polja[i][j].setForeground(svijetliBroj);
				if(tabela[i][j]==0) polja[i][j].setText("");
				else polja[i][j].setText(Integer.toString(tabela[i][j]));
				polja[i][j].setFont(new Font("Cambria", Font.BOLD, 35));
				if(tabela[i][j]>=2048) polja[i][j].setBackground(boje[11]);
				else polja[i][j].setBackground(boje[Igra.logoritam(tabela[i][j])]);
				polja[i][j].addActionListener(dugme);
				grid.add(polja[i][j]);
			}
		
		okvir.addKeyListener(klik);
		okvir.setFocusable(true);
		
		okvir.add(header);
		okvir.add(grid);
		
	}
	
	/** 
	 * Kreira KeyListener koji detektuje 4 tipke s tastature, a to su strelice za gore, dole, lijevo i desno.
	 * Na osnovu pritisnute tipke vrsi potez u tom smjeru, a zatim poziva funkciju osvjezi koja prikazuje novo stanje igre.
	 * @return KeyListener
	 * */
	private KeyListener napraviKeyListener(){
		
		KeyListener x = new KeyListener(){ 
		
			public void keyPressed(KeyEvent e) {
			    int key = e.getKeyCode();
			    if (key == KeyEvent.VK_LEFT) g.potez(g.DIREKCIJA_LIJEVO);
			    else if (key == KeyEvent.VK_RIGHT) g.potez(g.DIREKCIJA_DESNO);
			    else if (key == KeyEvent.VK_UP) g.potez(g.DIREKCIJA_GORE);
			    else if (key == KeyEvent.VK_DOWN) g.potez(g.DIREKCIJA_DOLE);
			    osvjezi();
		    }
		
			public void keyReleased(KeyEvent e) {}
	
			public void keyTyped(KeyEvent arg0) {}
		};
		return x;
	}
	
	/** 
	 * Kreira ActionListener za dugmad koja predstavljaju polja igrice. Naime, ako korisnik klikne na neko od tih dugmadi
	 * gubi se fokus sa okvira za registrovanje pritisnute tipke na tastaturi. Neophodno je da kada korisnik klikna neko dugme
	 * vratimo fokus na okvir.
	 * @return ActionListener
	 * */
	private ActionListener napraviActionListenerDugme(){
		
		ActionListener x = new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				okvir.requestFocus();
			}
		};
		return x;
	}
	
	/**
	 * Kreira ActionListener za dugmad s natpisom Nova igra a kojim ponovo pokrecemo igru. Resetuje igricu, pozivom funkcije osvjezi
	 * prikazuje novo stanje igrice, vraca fokus na okvir za registrovanje pritisnute tipke na tastaturi, te postavlja vidljivost
	 * okvira pobjeda i kraj na false u slucaju da rijec o dugmetu novaigra_d2 koji se pojavljuje u tim prozorima.
	 * @return ActionListener
	 * */
	private ActionListener napraviActionListenerNovaIgra(){
		
		ActionListener x = new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				g.resetuj();
				okvir.requestFocus();
				osvjezi();
				pobjeda.setVisible(false);
				kraj.setVisible(false);
			}
		};
		return x;
	}
	
	/**
	 * Kreira ActionListener za dugme nastavi koje se pojavljuje u okviru pobjeda nakon sto korisnik pobijedi.
	 * Vraca fokus na okvir i zatvara okvir pobjeda.
	 * @return ActionListener
	 * */
	private ActionListener napraviActionListenerNastavi(){
		
		ActionListener x = new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				okvir.requestFocus();
				pobjeda.setVisible(false);
			}
		};
		return x;
	}
	
	/**
	 * Uzima stanje tabele igrice i mijenja izgled polja na osnovu trenutnog stanja. Postavlja vrijednost na polje, boju pozadine i boju teksta.
	 * Provjerava da li je u tom stanju doslo do pobjede ili poraza korisnika te ukoliko jeste prikazuje odgovorajuci novi okvir.
	 * */
	private void osvjezi(){
		int[][] tabela = g.stanjeTabele();
		for(int i=0; i<4; i++)
			for(int j=0; j<4; j++){
				if(tabela[i][j]==2 || tabela[i][j]==4)
					polja[i][j].setForeground(tamniBroj);
				else polja[i][j].setForeground(svijetliBroj);
				if(tabela[i][j]==0) polja[i][j].setText("");
				else polja[i][j].setText(Integer.toString(tabela[i][j]));
				if(tabela[i][j]>=2048) polja[i][j].setBackground(boje[11]);
				else polja[i][j].setBackground(boje[Igra.logoritam(tabela[i][j])]);
			}
		if(g.jeLiKraj())
			prikaziKraj();
		if(g.jeLiPobijedio()){
			g.pobjeda = false;
			prikaziPobjeda();
		}
	}
	
	/**
	 * Poziva se iz main klase za prikazivanje okvira. Postavlja velicinu, vidljivost i poziciju okvira, te postavi da
	 * se program prekine pri zatvaranju okvira.
	 * */
	public void prikazi(){
		
		okvir.pack();
		okvir.setLocationRelativeTo(null);
		okvir.setVisible(true);
		okvir.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	/** 
	 * Prikazuje okvir kraj. Prije toga u taj okvir dodaje dugme novaigra_d2. Postavlja dimenziju, boju pozadine, lokaciju okvira.
	 * */
	private void prikaziKraj() {
		
		kraj.add(novaigra_d2);
		
		kraj.setSize(300, 300);
		kraj.getContentPane().setBackground(pozadina);
		kraj.setLocationRelativeTo(null);
		kraj.setVisible(true);
	}
	/** 
	 * Prikazuje okvir pobjeda. Prije toga u taj okvir dodaje dugme novaigra_d2. Postavlja dimenziju, boju pozadine, lokaciju okvira.
	 * */
	private void prikaziPobjeda(){
		
		pobjeda.add(novaigra_d2);
		
		pobjeda.setSize(300, 300);
		pobjeda.getContentPane().setBackground(pozadina);
		pobjeda.setLocationRelativeTo(null);
		pobjeda.setVisible(true);
	}
	
}
