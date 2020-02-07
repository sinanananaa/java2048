package konzola;

import java.util.Scanner;

import logika.Igra;

/**
 * Klasa u kojoj definisemo igranje igre preko konzole.
 * 
 * @author Amina Sinanovic
 * */
public class Igraj2048 {
	
	/** 
	 * Ispisuje proslijedjenu matricu intova red po red.
	 * @param tabela matrica koju zelimo ispisati
	 * */
	public static void ispisiTabelu(int[][] tabela){
		for(int i=0; i<4; i++){
			for(int j=0; j<4; j++)
				System.out.print(tabela[i][j]+" ");
			System.out.println();
		}
	}
    /** 
     * Najprije se ispisuje pocetno stanje tabele.
     * Zatim se zahtjeva da korisnik unese direkciju ili da odluci igrati ispocetka.
     * Nakon toga se poziva odgovorajuca funkcija, tj. ucini potez u datoj direkciji ili resetuj igru.
     * Ispisuje se novo stanje tabele.
     * Zatim se vrsi provjera da li je kraj ili pobjeda. Ako je kraj igrac bira da li da igra ponovo ili prekine program.
     * Ako je pobjeda igrac bira da li da nastavi igru preko 2048, igra ispocetka ili prekine program.
     * */
	public static void main(String[] args) { 
		
		Igra g = new Igra();
		
		int[][] tabela = g.stanjeTabele();
		System.out.println("Igra 2048");
		System.out.println();
		ispisiTabelu(tabela);
		System.out.println();
		
		Scanner ulaz = new Scanner(System.in);
		int potez;
		char x;
		while(true){
			System.out.println("Unesite potez: 8-gore, 2-dole, 6-desno, 4-lijevo ili 0-nova igra");
			potez = ulaz.nextInt();
			if(potez == 8) g.potez(g.DIREKCIJA_GORE);
			else if(potez == 2) g.potez(g.DIREKCIJA_DOLE);
			else if(potez == 4) g.potez(g.DIREKCIJA_LIJEVO);
			else if(potez == 6) g.potez(g.DIREKCIJA_DESNO);
			else if(potez == 0) {g.resetuj(); System.out.println("NOVA IGRA");}
			tabela = g.stanjeTabele();
			ispisiTabelu(tabela);
			System.out.println();
			if(g.jeLiKraj()){
				System.out.println("Izgubili ste.");
				System.out.println("Da li želite igrati ponovo? (D/N) ");
				x = ulaz.next().charAt(0);
				if(x == 'D') {
					g.resetuj();
					ispisiTabelu(tabela);
					System.out.println();
					continue;
				}
				else {
					System.out.println("Kraj.");
					break;
				}
				
			} else if(g.jeLiPobijedio()){
				System.out.println("Pobijedili ste.");
				System.out.println("Da li želite nastaviti, ponovo igrati ili zavrsiti? (N/P/Z) ");
				g.pobjeda = false;
				x = ulaz.next().charAt(0);
				if(x == 'N') continue;
				else if(x == 'P'){
					g.resetuj();
					ispisiTabelu(tabela);
					System.out.println();
					continue;
				} else if( x == 'Z'){
					System.out.println("Kraj.");
					break;
				}
			}
			
		}
	}

}
