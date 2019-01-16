package ufrpe.lc.lab1;

import java.util.ArrayList;

public class Solver {

	private int[][] grafo;
	private ArrayList<int[][]> filhos;
	private ArrayList<Integer> antecessor;
	private int indexAntecessor;

	public Solver(int[][] grafo) {
		this.grafo = grafo;
		filhos = new ArrayList<>();
		antecessor = new ArrayList<>();
		indexAntecessor = -1;
	}

	public int[][] getGrafo() {

		return grafo;
	}

	// SOLVER
	public int[][] gerarJogadaIA() {
		int caminho = 0;
		int check = checarMatriz();
		int[][] temp = getGrafo();

		int index = 1;

		if (check == 0) {
			// linha1 correta apenas
			temp = turn3x2(getGrafo());

			for (index = 0; !posiçãocoluna1(temp); index++) {

				gerarfilhos(temp);
				temp = filhos.get(index);

				// mostra a matriz que esta sendo checada no momento
				System.out.println(index);
				for (int i = 0; i < 2; i++) {
					for (int j = 0; j < 3; j++) {
						System.out.print(temp[i][j] + " ");
					}
					System.out.println();
				}
				System.out.println();

			}

		} else if (check == 1) {
			// linha1 e coluna1 corretas

			temp = turn2x2(getGrafo());
			for (index = 0; !fimjogo2x2(temp); index++) {

				gerarfilhos(temp);
				temp = filhos.get(index);
				// mostra a matriz que esta sendo checada no momento
				System.out.println(index);
				for (int i = 0; i < 2; i++) {
					for (int j = 0; j < 2; j++) {
						System.out.print(temp[i][j] + " ");
					}
					System.out.println();
				}
				System.out.println();

			}
		} else if (check == 2) {

			for (index = 0; !posiçãolinha1(temp); index++) {
				gerarfilhos(temp);
				temp = filhos.get(index);
				// mostra a matriz que esta sendo checada no momento
				System.out.println(index);
				for (int i = 0; i < 3; i++) {
					for (int j = 0; j < 3; j++) {
						System.out.print(temp[i][j] + " ");
					}
					System.out.println();
				}
				System.out.println();

			}

		}

		index--;
		caminho = gerarcaminho(antecessor, index);
		if (check == 0) {
			temp = turn3x2para3x3(filhos.get(caminho));
		} else if (check == 1) {
			temp = turn2x2para3x3(filhos.get(caminho));
		} else if (check == 2) {
			temp = filhos.get(caminho);
		}
		return temp;
	}

	// checa a condição da matriz
	private int checarMatriz() {
		int checker = 0;
		/*
		 * checker == 0 -> linha1 feita
		 * 
		 * checker == 1 -> linha1 e colula1 feita
		 * 
		 * checker == 2 -> linha1 n feita
		 */

		for (int i = 1; i <= 3; i++) {
			if (grafo[0][i - 1] != i) {
				// linha1 incorreta, necessario resolver
				checker = 2;
				break;
			}
		}

		if (checker == 0) {
			// linha1 correta, verificar colula1
			if (grafo[1][0] == 4 && grafo[2][0] == 7) {
				// coluna1 e linha1 correta
				checker = 1;
			}
		}

		return checker;

	}

	// transforma a matriz 3x2 dada em uma 3x3 com a linha1 feita
	private int[][] turn3x2para3x3(int[][] matriz) {
		int temp[][] = new int[3][3];

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (i == 0) {
					temp[i][j] = j + 1;
				} else {
					temp[i][j] = matriz[i - 1][j];
				}

			}
		}

		return temp;
	}

	// transforma a matriz 2x2 dada em uma 3x3 com a linha1 e coluna1 feita
	private int[][] turn2x2para3x3(int[][] matriz) {
		int temp[][] = new int[3][3];

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {

				if (i == 0) {
					temp[i][j] = j + 1;
				} else if (j == 0) {
					if (i == 1) {
						temp[i][j] = 4;
					} else if (i == 2) {
						temp[i][j] = 7;
					}
				} else {
					temp[i][j] = matriz[i - 1][j - 1];
				}

			}
		}

		return temp;
	}

	// verifica se a matriz 2x2 dada é a o fim de jogo quando transformada em 3x3
	private boolean fimjogo2x2(int[][] matriz) {
		int gabarito = 5;
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {
				if (matriz[i][j] != gabarito) {
					return false;
				}
				gabarito++;
			}
			gabarito++;
		}

		return true;
	}

	// verifica se as posições da coluna1 estão corretas
	private boolean posiçãocoluna1(int[][] matriz) {

		if (matriz[0][0] != 4) {
			return false;
		}
		if (matriz[1][0] != 7) {
			return false;
		}
		return true;
	}

	// verifica se as posições da linha1 estão corretas
	private boolean posiçãolinha1(int[][] matriz) {

		for (int i = 0; i < matriz.length; i++) {
			if (matriz[0][i] != i + 1) {
				return false;
			}
		}
		return true;
	}

	// gera as possibilidades de jogadas a partir de uma matriz dada
	private void gerarfilhos(int[][] matriz) {
		ControlePuzzle8 control = new ControlePuzzle8();

		int temp[][] = clonarmatriz(matriz);

		for (int i = 0; i < matriz.length; i++) {
			for (int j = 0; j < matriz[0].length; j++) {
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

	// gera com base no antecessor o caminho para a matriz que gerou o gabarito
	private int gerarcaminho(ArrayList<Integer> antecessor, int index) {
		while (antecessor.get(index) != -1) {

			index = antecessor.get(index);

		}

		return index;

	}

	// copia uma matriz para outra
	private int[][] clonarmatriz(int[][] matriz) {
		int[][] matrizclone = new int[matriz.length][matriz[0].length];

		for (int i = 0; i < matriz.length; i++) {
			for (int j = 0; j < matriz[0].length; j++) {
				matrizclone[i][j] = matriz[i][j];
			}
		}

		return matrizclone;
	}

	// transforma a matriz dada em uma matriz 3x2 ignorando a linha1 da matriz
	public int[][] turn3x2(int[][] matriz) {

		int[][] temp = new int[2][3];

		for (int i = 1; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				temp[i - 1][j] = matriz[i][j];
			}
		}

		return temp;

	}

	// transforma a matriz dada em uma matriz 2x2 ignorando a linha1 e coluna1
	public int[][] turn2x2(int[][] matriz) {
		int[][] temp = new int[2][2];

		for (int i = 1; i < 3; i++) {
			for (int j = 1; j < 3; j++) {
				temp[i - 1][j - 1] = matriz[i][j];
			}
		}

		return temp;
	}

	/*
	 * remake dos metodos da ArrayList para comparar as matrizes
	 * 
	 */

	private int ListindexOf(int[][] e) {
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

	private boolean Listcontains(int[][] e) {
		return ListindexOf(e) >= 0;
	}

}
