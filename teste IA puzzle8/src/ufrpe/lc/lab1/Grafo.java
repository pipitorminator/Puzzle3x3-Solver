package ufrpe.lc.lab1;

import java.util.ArrayList;

public class Grafo {

	private int[][] grafo;
	private ArrayList<int[][]> filhos;
	private ArrayList<Integer> antecessor;
	private int indexAntecessor;

	public Grafo(int[][] grafo) {
		this.grafo = grafo;
		filhos = new ArrayList<>();
		antecessor = new ArrayList<>();
		indexAntecessor = -1;
	}

	public int[][] getGrafo() {

		return grafo;
	}

	public int[][] buscaGrafo() {
		ControlePuzzle8 control = new ControlePuzzle8();
		int[][] temp = clonarmatriz(getGrafo());
		int index;
		int caminho = 0;

		
		//gera todas as possibilidades de jogadas ate o caso da jogara gerada for a jogada gabarito
		for (index = 0; control.verificarFimJogo(temp) == -1; index++) {

			gerarfilhos(temp);

			temp = filhos.get(index);

			System.out.println(antecessor.get(index));
			System.out.println(index);
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					System.out.print(filhos.get(index)[i][j] + " ");
				}
				System.out.println();
			}
			System.out.println();

		}
		index--;

		caminho = gerarcaminho(antecessor, index);

		return filhos.get(caminho);

	}
	
	//gera as possibilidades de jogadas a partir de uma matriz dada
	private void gerarfilhos(int[][] matriz) {
		ControlePuzzle8 control = new ControlePuzzle8();

		int temp[][] = clonarmatriz(matriz);

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (matriz[i][j] == Puzzle8.NUMERO_POS_VAZIA) {

					temp = clonarmatriz(matriz);
					control.tratarJogada(temp, i + 1, j);
					if (!control.compararArrays(matriz, temp) && !Listcontains(temp)) {
						filhos.add(temp);
						antecessor.add(indexAntecessor);

					}

					temp = clonarmatriz(matriz);
					control.tratarJogada(temp, i - 1, j);
					if (!control.compararArrays(matriz, temp) && !Listcontains(temp)) {
						filhos.add(temp);
						antecessor.add(indexAntecessor);
					}

					temp = clonarmatriz(matriz);
					control.tratarJogada(temp, i, j + 1);
					if (!control.compararArrays(matriz, temp) && !Listcontains(temp)) {
						filhos.add(temp);
						antecessor.add(indexAntecessor);
					}

					temp = clonarmatriz(matriz);
					control.tratarJogada(temp, i, j - 1);
					if (!control.compararArrays(matriz, temp) && !Listcontains(temp)) {
						filhos.add(temp);
						antecessor.add(indexAntecessor);
					}
				}
			}

		}

		indexAntecessor++;

	}

	// gera com base no antecessor o caminho para a matriz que gerou a matriz gabarito
	private int gerarcaminho(ArrayList<Integer> antecessor, int index) {

		while (antecessor.get(index) != -1) {

			index = antecessor.get(index);

		}

		return index;

	}
	
	//copia uma matriz para outra
	private int[][] clonarmatriz(int[][] matriz) {
		int[][] matrizclone = new int[matriz.length][matriz[0].length];

		for (int i = 0; i < matriz.length; i++) {
			for (int j = 0; j < matriz[0].length; j++) {
				matrizclone[i][j] = matriz[i][j];
			}
		}

		return matrizclone;
	}

	/*
	 * remake dos metodos da ArrayList para comparar as matrizes
	 * 
	 */

	public int ListindexOf(int[][] e) {
		ControlePuzzle8 control = new ControlePuzzle8();
		if (e == null) {
			for (int i = 0; i < filhos.size(); i++)
				if (filhos.get(i) == null)
					return i;
		} else {
			for (int i = 0; i < filhos.size(); i++)
				if (control.compararArrays(e, filhos.get(i)))
					return i;
		}
		return -1;
	}

	public boolean Listcontains(int[][] e) {
		return ListindexOf(e) >= 0;
	}

}
