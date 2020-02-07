package logika;

import java.util.Random;

/**
 * Klasa u kojoj definisemo logiku igre.
 * 
 * @author Amina Sinanovic
 * */

public class Igra {
	
	public static final int DIREKCIJA_GORE = 0;
	public static final int DIREKCIJA_DOLE = 1;
	public static final int DIREKCIJA_LIJEVO = 2;
	public static final int DIREKCIJA_DESNO = 3;
	
	
	/**
	 * Racuna logiratam broja x po bazi 2. Koristi se kao pomocna funkcija za odredjivanje boje polja za GUI.
	 * @return int
	 * */
	public static int logoritam(int x){
		int brojac = 0;
		while(x>=2){
			x /= 2;
			brojac++;
		}
		return brojac;
	}
	
	/** Matrica brojeva u koju smjestamo brojeve koji predstavljaju vrijednost tog polja*/
	int[][] matrica;
	/** Broj slobodnih polja*/
	int brojSlobodnih;
	/** Atribut koji koristimo da odaberemo na koje polje cemo generisat novi broj i koji je to broj*/
	Random rnd;
	/** Atribut govori da li je doslo do pobjede u datom trenutku*/
	public boolean pobjeda = false;
	
	/** 
	 * Konstrutkor bez parametara inicijalizuje matricu dimenzije 4x4, broj slobodnih mejsta postavlja na 16
	 * i pozivom funkcije generise dva nasumicna broja na slucajno odabranim pozicijama.
	 * */
	
	public Igra(){
		
		matrica = new int[4][4];
		brojSlobodnih = 16;
		rnd = new Random();
		for(int i=0; i<2; i++){
			generisi();
		}
	}
	
	/** 
	 * Funkcija nasumicno bira koje po redu slobodno polje ce da zauzme i bira koji broj ce da stavi na tu poziciju 2 ili 4.
	 * Pojavljivanje broja 2 u odnosu na 4 je 9:1.
	 * */
	private void generisi(){
		
		int x = rnd.nextInt(brojSlobodnih);
		int  a = rnd.nextInt(10);
		if(a == 5) zauzmiPoziciju(x+1,4);
			else zauzmiPoziciju(x+1,2);
	}
	
	/** 
	 * Postavlja proslijedjenu vrijednost na proslijedjenu poziciju i umanjuje broj slobodnih mjesta za 1.
	 * 
	 * @param int pozicija nam govori na koje poredu slobodno mjesto u matrici treba postaviti vrijednost
	 * @param int vrijednost govori koju vrijednost treba da postavimo na poziciju
	 * */
	private void zauzmiPoziciju(int pozicija, int vrijednost){
		int brojac = 0;
		for(int i=0; i<4; i++)
			for(int j=0; j<4; j++)
				if(matrica[i][j] == 0){
					brojac++;
					if(brojac == pozicija){
						matrica[i][j] = vrijednost;
						brojSlobodnih--;
					}
				}
	}
	
	/** 
	 * Vraca tabelu koja odgovara matrici igre, tj. trenutnom stanju igre.
	 * @return int[][]
	 * */
	public int[][] stanjeTabele() {
		int[][] tabela = new int[4][4];
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				tabela[i][j] = matrica[i][j];
			}
		}
		
		return tabela;
	}
	
	/** 
	 * Pomjera sve elemente u smjeru proslijedjene direkcije, zatim poziva funkciju za spajanje istih elemenata. Ako je doslo do spajanja
	 * tada je potrebno ponovo pomjeriti sva polja u istom pravcu.
	 * Ako je doslo do pomjeranja ili spajanja (tj. bilo je moguce napraviti promjenu u datom smjeru) tada se generise novi broj.
	 * @param int direkcija govori u kojem pravcu da ucinimo potez (gore, dole, lijevo ili desno)
	 * */
	public void potez(int direkcija){
		
		boolean pomjeranje = pomjeri(direkcija);
		boolean spajanje = spoji(direkcija);
		if(spajanje) pomjeri(direkcija);
		if(pomjeranje || spajanje) generisi();
	}
	
	
	/** 
	 * Ovisno od date direkcije funkcija pomjera sve elemente u tom pravcu. Krenemo od prvog polja do ivice, suprotno od smjera u kojem treba
	 * vrsiti pomjeranje i gledamo da li je mjesto slobodno, ako jeste zapamtito kao takvo, a ako je zauzeto provjeravamo da li ima 
	 * slobodnih mjesta prije njeg i ako da postavimo na to ranije slobodno.
	 * 
	 * @param int direkcija govori u kojem pravcu pomjeramo elemente
	 * @return boolean true ako je bilo pomjeranja, false ako su svi elementi ostali na istim poljima
	 * */
	private boolean pomjeri(int direkcija){
		boolean pomjeranje = false;
		if(direkcija == 0){
			for(int i=0; i<4; i++){
			  int slobodno = 4;
			  for(int j=0; j<4; j++)
				  if(matrica[j][i]==0){ if(j<slobodno) slobodno = j;}
				  else {
					  if(slobodno==4) continue;
					  pomjeranje = true;
					  matrica[slobodno][i] = matrica[j][i];
					  matrica[j][i] = 0;
					  slobodno = j;
					  if(matrica[j-1][i]==0) slobodno = j-1;
					  else slobodno = j;
				  }
			}
		} else if(direkcija == 1) {
			for(int i=0; i<4; i++){
			  int slobodno = -1;
			  for(int j=3; j>-1; j--)
				  if(matrica[j][i]==0){ if(j>slobodno) slobodno = j;}
				  else {
					  if(slobodno==-1) continue;
					  pomjeranje = true;
					  matrica[slobodno][i] = matrica[j][i]; 
					  matrica[j][i] = 0;
					  slobodno = j;
					  if(matrica[j+1][i]==0) slobodno = j+1;
					  else slobodno = j;
				  }
			}
		} else if(direkcija == 2) {
			for(int i=0; i<4; i++){
			  int slobodno = 4;
			  for(int j=0; j<4; j++)
				  if(matrica[i][j]==0){ if(j<slobodno) slobodno = j;}
				  else {
					  if(slobodno==4) continue;
					  pomjeranje = true;
					  matrica[i][slobodno] = matrica[i][j];
					  matrica[i][j] = 0;
					  slobodno = j;
					  if(matrica[i][j-1]==0) slobodno = j-1;
					  else slobodno = j;
				  }
			}
		} else if(direkcija == 3) {
			for(int i=0; i<4; i++){
			  int slobodno = -1;
			  for(int j=3; j>-1; j--)
				  if(matrica[i][j]==0){ if(j>slobodno) slobodno = j;}
				  else {
					  if(slobodno==-1) continue;
					  pomjeranje = true;
					  matrica[i][slobodno] = matrica[i][j];
					  matrica[i][j] = 0;
					  if(matrica[i][j+1]==0) slobodno = j+1;
					  else slobodno = j;
				  }
			}
		}
	  return pomjeranje;
	}
	
	/** 
	 * Ovisno o proslijedjene direkcije u tom smjeru vrsimo spajanje. Ako dva susjedna polja imaju istu vrijednost i razliciti su od nule
	 * tada da dva polja spajamo tako sto onom prvom (posmatrano u kontekstu zadatog smjera i pravca) uduplamo vrijednost i drugi postavimo
	 * na nulu. Kada dodje do spajanja provjeravamo da li je taj novi broj 2048 i ako jeste postavimo atribut pobjeda na true.
	 * Takodjer, pri svakom spajanju povecavamo broj slobodnih za 1. 
	 * 
	 * @param int direkcija govori u kojem smjeru vrsimo spajanje
	 * @return boolean true ako je doslo do spajanja
	 * */
	private boolean spoji(int direkcija){
	
		boolean promjena = false;
		
		if(direkcija == 0){
			for(int i=0; i<3; i++)
				for(int j=0; j<4; j++)
					if(matrica[i][j]==matrica[i+1][j] && matrica[i][j]!=0){
						promjena = true;
						matrica[i][j] *= 2;
						if(matrica[i][j]==2048) pobjeda = true;
						brojSlobodnih++;
						matrica[i+1][j]=0;
					}
		} else if(direkcija == 1){
			for(int i=3; i>0; i--)
				for(int j=0; j<4; j++)
					if(matrica[i][j]==matrica[i-1][j] && matrica[i][j]!=0){
						promjena = true;
						if(matrica[i][j]==2048) pobjeda = true;
						matrica[i][j] *= 2;
						brojSlobodnih++;
						matrica[i-1][j]=0;
					}
		} else if(direkcija == 2){
			for(int i=0; i<4; i++)
				for(int j=0; j<3; j++)
					if(matrica[i][j]==matrica[i][j+1] && matrica[i][j]!=0){
						promjena = true;
						if(matrica[i][j]==2048) pobjeda = true;
						matrica[i][j] *= 2;
						brojSlobodnih++;
						matrica[i][j+1]=0;
					}
		} else if(direkcija == 3){
			for(int i=0; i<4; i++)
				for(int j=3; j>0; j--)
					if(matrica[i][j]==matrica[i][j-1] && matrica[i][j]!=0){
						promjena = true;
						if(matrica[i][j]==2048) pobjeda = true;
						matrica[i][j] *= 2;
						brojSlobodnih++;
						matrica[i][j-1]=0;
					}
		}
		return promjena;
	}	
    
	/** 
	 * Najprije postavlja sve vrijednosti martice na nulu i broj slobondih mjesta n 16, zatim generise dva broja na slucajno odabranim
	 * pozicijama. Na taj nacin dovodi matricu u stanje kao sto treba biti na pocetku igre.
	 * */
	public void resetuj(){
		
	  for(int i=0; i<4; i++)
		  for(int j=0; j<4; j++)
			  matrica[i][j] = 0;
	  brojSlobodnih = 16;
	  for(int i=0; i<2; i++)
		  generisi();
	}
	
	/** 
	 * Provjerava da li je kraj tako sto prvo provjeri broj slobodnih mjesta. Ako je broj veci od nula to znaci da igrac moze odigrat jos bar
	 * jedan potez. A ako je taj broj jednak 0 onda se poziva funkcija mozeLiSpojiti jer ukoliko postoje dva broja koja se mogu spojiti
	 * tada ce doci i do oslobadjanja jednog mjesta te ce korisnik moci uciniti novi potez.
	 * */
	public boolean jeLiKraj(){
		
	  if(brojSlobodnih>0) return false;
	  if(mozeLiSpojiti()) return false;
	  return true;
	}
    
	/** 
	 * U svim smjerovima i pravcima provjerava da li postoji mogucnost za spajanje dva polja tako sto provjerava da li ima
	 * dva susjedna polja koja imaju istu vrijednost, a koja je razlicita od nule. Cim naidje na prvu takvu situaciju, vraca true.
	 * @return boolean true ako postoji mogucnost za spajanje
	 * */
	private boolean mozeLiSpojiti(){
	
		for(int i=0; i<3; i++)
			for(int j=0; j<4; j++)
				if(matrica[i][j]==matrica[i+1][j] && matrica[i][j]!=0)
					return true;
		for(int i=3; i>0; i--)
			for(int j=0; j<4; j++)
				if(matrica[i][j]==matrica[i-1][j] && matrica[i][j]!=0)
					return true;
		for(int i=0; i<4; i++)
			for(int j=0; j<3; j++)
				if(matrica[i][j]==matrica[i][j+1] && matrica[i][j]!=0)
					return true;
		for(int i=0; i<4; i++)
			for(int j=3; j>0; j--)
				if(matrica[i][j]==matrica[i][j-1] && matrica[i][j]!=0)
					return true;	
		return false;
	}
	
	/** 
	 * Provjerava da li je igrac pobijedio.
	 * @return boolean true ako je doslo do pobjede u datom trenutku.
	 * */
	public boolean jeLiPobijedio() {
		return pobjeda;
	}
  
}
