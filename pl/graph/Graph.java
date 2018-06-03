package pl.graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
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
		spisPolaczen[jakieMiasto].add(new Polaczenie(gdzie, dlugosc));
	}

	public void dodajPolaczenie(String jakieMiasto, String gdzie, int dlugosc) {
		int jakieInt = poszukIndeksaMiasta(jakieMiasto);
		int gdzieInt = poszukIndeksaMiasta(gdzie);
		spisPolaczen[jakieInt].add(new Polaczenie(gdzieInt, dlugosc));
	}

	public void dodajPolaczenie(String jakieMiasto, int gdzie, int dlugosc) {
		int jakieInt = poszukIndeksaMiasta(jakieMiasto);
		// int gdzieInt = poszukIndeksaMiasta(gdzie);
		spisPolaczen[jakieInt].add(new Polaczenie(gdzie, dlugosc));
	}

	public void dodajPolaczenie(int jakieMiasto, String gdzie, int dlugosc) {
		// int jakieInt = poszukIndeksaMiasta(jakieMiasto);
		int gdzieInt = poszukIndeksaMiasta(gdzie);
		spisPolaczen[jakieMiasto].add(new Polaczenie(gdzieInt, dlugosc));
	}

	public ArrayList<String> wszystkieDrogi(String miasto) {

		int miastoInt = poszukIndeksaMiasta(miasto);
		int gdzieIndeks = 0;

		ArrayList<String> arr = new ArrayList<>();

		for (int i = 0; i < spisPolaczen[miastoInt].size(); i++) {
			gdzieIndeks = spisPolaczen[miastoInt].get(i).gdzie;
			arr.add(nazwyMiast[gdzieIndeks]);
		}

//		Set<String> miastaSet = new HashSet<String>();
//		miastaSet.addAll(arr);
//		arr.clear();
//		arr.addAll(miastaSet);

		// System.out.println("Miasto " + miasto + " ma drogi do miast: " + arr);
		return arr;

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
			int sizeWszystkieDrogi = -1;
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
			System.out.println(sumaDlugosci);

			System.out.println("Nie ma laczenie");
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

	public String wszystkieMiastaZDrogami() {
		String result = "";
		for (int i = 0; i < nazwyMiast.length; i++) {
			if (!spisPolaczen[i].isEmpty())
				result += nazwyMiast[i] + "=>" + spisPolaczen[i] + "\n";
		}
		return result;
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

	public static void main(String args[]) {
		Graph g = new Graph();

		// g.dodajPolaczenie(0, 1, 4);
		// g.dodajPolaczenie(0, 1, 100500);
		// g.dodajPolaczenie(0, 2, 1);
		// g.dodajPolaczenie(0, 3, 2);
		// g.dodajPolaczenie(0, 3, 1);
		// g.dodajPolaczenie(3, 4, 20);
		// g.dodajPolaczenie(3, 4, 10);
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

		// g.wszystkieDrogi("A");
		// g.najszybszaDroga("A", "B");
		g.najszybszaDroga("A", "C");

		// System.out.println(g.wszystkieMiastaZDrogami());
	}

}