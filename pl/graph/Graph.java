package pl.graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Set;

public class Graph {
	private int iloscMiast;
	private LinkedList<Polaczenie> spisPolaczen[];
	private static String[] nazwyMiast;

	public Graph() {
		this.iloscMiast = 10;
		this.setIloscMiast(iloscMiast);
		nazwyMiast = new String[iloscMiast];
		spisPolaczen = new LinkedList[iloscMiast];

		for (int i = 0; i < iloscMiast; i++)
			spisPolaczen[i] = new LinkedList();
		for (int i = 0; i < nazwyMiast.length; i++) {
			nazwyMiast[i] = String.valueOf(i);
		}
	}

	public void zmienNazweMiasta(String nazwa, int jakieMiasto) {

		nazwyMiast[jakieMiasto] = nazwa;

		// System.out.println("Nazwa zostala zmieniona");

	}

	public void dodajPolaczenie(int jakieMiasto, int gdzie, int dlugosc) {
		// int jakieInt = poszukIndeksaMiasta(jakieMiasto);
		// int gdzieInt = poszukIndeksaMiasta(gdzie);

		for (int i = 0; i < spisPolaczen[jakieMiasto].size(); i++) {
			if (spisPolaczen[jakieMiasto].get(i).gdzie == gdzie
					&& spisPolaczen[jakieMiasto].get(i).dlugosc == dlugosc) {
				System.out.println("Juz jest takie polaczenie");
				return;
			}
		}

		spisPolaczen[jakieMiasto].add(new Polaczenie(gdzie, dlugosc));
		System.out.println("Polaczenie jest dodane");
	}

	public void dodajPolaczenie(String jakieMiasto, String gdzie, int dlugosc) {
		int jakieInt = poszukIndeksaMiasta(jakieMiasto);
		int gdzieInt = poszukIndeksaMiasta(gdzie);

		dodajPolaczenie(jakieInt, gdzieInt, dlugosc);

	}

	public void usunacPolaczenie(int jakieMiasto, int gdzie, int dlugosc) {

		boolean flag = false;

		for (int i = 0; i < spisPolaczen[jakieMiasto].size(); i++) {
			if (spisPolaczen[jakieMiasto].get(i).gdzie == gdzie
					&& spisPolaczen[jakieMiasto].get(i).dlugosc == dlugosc) {
				spisPolaczen[jakieMiasto].remove(i);
				flag = true;
			}
		}

		if (flag)
			System.out.println("Polaczenie zostalo usuniete");
		else
			System.out.println("Nie ma takiego polaczenie");

	}

	public ArrayList<String> wszystkieDrogi(String miasto) {

		int miastoInt = poszukIndeksaMiasta(miasto);
		int gdzieIndeks = 0;

		ArrayList<String> arr = new ArrayList<>();

		for (int i = 0; i < spisPolaczen[miastoInt].size(); i++) {
			gdzieIndeks = spisPolaczen[miastoInt].get(i).gdzie;
			arr.add(nazwyMiast[gdzieIndeks]);
		}

		Set<String> miastaSet = new HashSet<String>();
		miastaSet.addAll(arr);
		arr.clear();
		arr.addAll(miastaSet);

		System.out.println("Miasto " + miasto + " ma drogi do miast: " + arr);
		return arr;

	}

	public ArrayList<String> wszystkieDrogi(int miasto) {

		String nazwa = nazwyMiast[miasto];

		return wszystkieDrogi(nazwa);

	}

	public int poszukIndeksaMiasta(String nazwa) {
		int indeks = -1;

		for (int i = 0; i < nazwyMiast.length; i++) {
			if (nazwyMiast[i] == nazwa)
				indeks = i;
		}
		return indeks;
	}

	public boolean czyPolaczone(String wyjsciowe, String docelowe) {
		int wyjscInt = poszukIndeksaMiasta(wyjsciowe);
		int docelInt = poszukIndeksaMiasta(docelowe);

		for (Polaczenie i : spisPolaczen[wyjscInt])
			if (i.gdzie == docelInt)
				return true;
		return false;

	}

	public void najszybszaDroga(String wyjsciowe, String docelowe) {
		int wyjscInt = poszukIndeksaMiasta(wyjsciowe);
		int docelInt = poszukIndeksaMiasta(docelowe);
		int tmpInt = -1;
		String tmpNazwa = null;

		if (!(czyPolaczone(wyjsciowe, docelowe))) {

			int sumaDlugosci = -1;
			int tmpSumaDlugosci = -1;
			int sumaPierwszejDlugosci = -1;
			int tmpSumaPierwszejDlugosci = -1;
			for (int i = 0; i < spisPolaczen[wyjscInt].size(); i++) {

				tmpNazwa = nazwyMiast[spisPolaczen[wyjscInt].get(i).gdzie];
				tmpInt = poszukIndeksaMiasta(tmpNazwa);
				tmpSumaPierwszejDlugosci = spisPolaczen[wyjscInt].get(i).dlugosc;

				if (tmpSumaPierwszejDlugosci < sumaPierwszejDlugosci || sumaPierwszejDlugosci == -1)
					sumaPierwszejDlugosci = tmpSumaPierwszejDlugosci;

				for (int j = 0; j < spisPolaczen[tmpInt].size(); j++) {

					if (czyPolaczone(tmpNazwa, docelowe)) {
						tmpSumaDlugosci = sumaPierwszejDlugosci + spisPolaczen[tmpInt].get(j).dlugosc;
						if (tmpSumaDlugosci < sumaDlugosci || sumaDlugosci == -1)
							sumaDlugosci = tmpSumaDlugosci;

					}

				}

			}
			if (sumaDlugosci != -1)
				System.out.println("Najszybsza droga z miasta " + wyjsciowe + " do miasta " + docelowe
						+ " jest o dlugosci: " + sumaDlugosci);
			else
				System.out.println("Nie ma laczenie w ciagu 2 miast");
			return;
		}

		int flag = -1;

		for (Polaczenie i : spisPolaczen[wyjscInt])
			if (i.gdzie == docelInt) {
				if (flag > i.dlugosc || flag == -1)
					flag = i.dlugosc;
			}

		System.out.println(
				"Najszybsza droga z miasta " + wyjsciowe + " do miasta " + docelowe + " jest o dlugosci: " + flag);

	}

	@Override
	public String toString() {
		String result = "";
		for (int i = 0; i < nazwyMiast.length; i++) {
			result += nazwyMiast[i] + "=>" + spisPolaczen[i] + "\n";
		}
		return result;
	}

	public void wszystkieMiastaZDrogami() {
		String result = "";
		for (int i = 0; i < nazwyMiast.length; i++) {
			if (!spisPolaczen[i].isEmpty())
				result += nazwyMiast[i] + "=>" + spisPolaczen[i] + "\n";
		}
		System.out.println(result);
	}

	public void wszystkieMiasta() {

		String result = "";
		for (int i = 0; i < nazwyMiast.length; i++)
			result += nazwyMiast[i] + "=>" + spisPolaczen[i] + "\n";

		System.out.println(result);

	}

	public int getIloscMiast() {
		return iloscMiast;
	}

	public void setIloscMiast(int iloscMiast) {
		this.iloscMiast = iloscMiast;
	}

	private class Polaczenie {

		int gdzie, dlugosc;

		private Polaczenie(int gdzie, int dlugosc) {
			super();
			this.gdzie = gdzie;
			this.dlugosc = dlugosc;
		}

		@Override
		public String toString() {
			return "Do miasta " + Graph.nazwyMiast[gdzie] + " ma " + dlugosc + " km";
		}

	}

	public void menu() {

		Scanner scan = new Scanner(System.in);

		int key = -1;
		System.out.println("Mamy 10 niepolaczonych miedzy soba miast. Co chcesz zrobic?");
		System.out.println("1. Zmienic nazwe miasta\n" + "2. Dodac polaczenie z jednego do drugiego miasta\n"
				+ "3. Wyswietlic wszystkie polaczenia z miasta\n" + "4. Znalezc najszybsza droge\n"
				+ "5. Usunac polaczenie\n" + "6. Wyswietlic miasta\n" + "0. Koniec programu\n");

		key = scan.nextInt();
		switch (key) {
		case 1:
			System.out.println("Wprowadz indeks miasta");
			int jakieMiasto = scan.nextInt();
			System.out.println("Wprowadz nowa nazwe miasta");
			String nazwa = scan.next();
			zmienNazweMiasta(nazwa, jakieMiasto);

			menu();
			break;
		case 2:
			System.out.println("Wprowadz indeks miasta poczatkowego: ");
			jakieMiasto = scan.nextInt();
			System.out.println("Wprowadz indeks miasta drugiego: ");
			int gdzie = scan.nextInt();
			System.out.println("Wprowadz dlugosc drogi: ");
			int dlugosc = scan.nextInt();
			dodajPolaczenie(jakieMiasto, gdzie, dlugosc);

			menu();
			break;
		case 3:
			System.out.println("Wprowadz indeks miasta: ");
			jakieMiasto = scan.nextInt();
			wszystkieDrogi(jakieMiasto);

			menu();
			break;
		case 4:
			System.out.println("Wprowadz wyjsciowe miasto: ");
			String wyjsciowe = scan.next();
			System.out.println("Wprowadz docelowe miasto: ");
			String docelowe = scan.next();
			najszybszaDroga(wyjsciowe, docelowe);

			menu();
			break;
		case 5:
			System.out.println("Wprowadz indeks miasta poczatkowego: ");
			jakieMiasto = scan.nextInt();
			System.out.println("Wprowadz indeks miasta drugiego: ");
			gdzie = scan.nextInt();
			System.out.println("Wprowadz dlugosc drogi: ");
			dlugosc = scan.nextInt();
			usunacPolaczenie(jakieMiasto, gdzie, dlugosc);

			menu();
			break;
		case 6:
			System.out.println("Wyswietlic tylko polaczone miedzy soba miasta (1) lub wszystkie (2)?");
			key = scan.nextInt();
			switch (key) {
			case 1:

				wszystkieMiastaZDrogami();
				break;
			case 2:
				wszystkieMiasta();
				break;
			default:
				System.out.println("Cos zle poszlo");
			}

			menu();
			break;
		case 0:
			System.out.println("Koniec programu...");
			break;

		default:
			System.out.println("Nieprawidlowa odpowiedz, sprobuj ponownie");
			menu();
			break;
		}

	}

	public static void main(String args[]) {
		Graph g = new Graph();

		g.dodajPolaczenie(0, 1, 3);
		g.dodajPolaczenie(0, 1, 10);
		g.dodajPolaczenie(0, 3, 1);
		g.dodajPolaczenie(1, 2, 2);
		g.dodajPolaczenie(1, 2, 20);
		g.dodajPolaczenie(3, 2, 1);
		g.dodajPolaczenie(2, 4, 1);

		g.zmienNazweMiasta("A", 0);
		g.zmienNazweMiasta("B", 1);
		g.zmienNazweMiasta("D", 3);
		g.zmienNazweMiasta("C", 2);
		g.zmienNazweMiasta("E", 4);

		g.menu();

	}

}