package ufrpe.lc.lab1;

import java.util.Random;

public class ControlePuzzle8 {

	public int[][] iniciarNovoJogo() {

		int[][] retorno = { { 1, 2, 3 }, { 4, 5, 6 }, {7, 8, 9 } };
		Random rand = new Random();
		for (int i = 0; i < 2000; i++) {
			retorno = tratarJogada(retorno, rand.nextInt(3), rand.nextInt(3));
		}
		return retorno;

	}

	public int[][] tratarJogada(int[][] matrizJogo, int posicaoX, int posicaoY) {
		if (posicaoX < 0 || posicaoX >= matrizJogo.length) {
			return matrizJogo;
		}
		if (posicaoY < 0 || posicaoY >= matrizJogo[0].length) {
			return matrizJogo;
		}
		for (int i = posicaoX - 1; i <= posicaoX + 1; i++) {
			if (i < 0 || i >= matrizJogo.length)
				continue;
			for (int j = posicaoY - 1; j <= posicaoY + 1; j++) {
				if ((i == posicaoX - 1 && j == posicaoY - 1) || (i == posicaoX + 1 && j == posicaoY - 1) || (i == posicaoX - 1 && j == posicaoY + 1) || (i == posicaoX + 1 && j == posicaoY + 1)) {
					continue;
				}
				if (j < 0 || j >= matrizJogo[i].length)
					continue;
				if (i == posicaoX && j == posicaoY)
					continue;
				if (matrizJogo[i][j] == Puzzle8.NUMERO_POS_VAZIA) {
					matrizJogo[i][j] = matrizJogo[posicaoX][posicaoY];
					matrizJogo[posicaoX][posicaoY] = Puzzle8.NUMERO_POS_VAZIA;
				}
			}
		}
		return matrizJogo;
	}

	public int verificarFimJogo(int[][] matrizJogo) {

		if (compararArrays(matrizJogo, new int[][] { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 } })) {
			return 0;
		} else {
			return -1;
		}
	}

	/**
	 * Gera uma jogada escolhida pelo computador.
	 * 
	 * @param matrizJogo Matriz que representa as posições do jogo A posição da
	 *                   coluna selecionada
	 * @return A matriz após a jogada ser feita
	 */
	public int[][] gerarJogada(int[][] matrizJogo) {

		Solver MatrizCPU = new Solver(matrizJogo);
		matrizJogo = MatrizCPU.gerarJogadaIA();
		return matrizJogo;
	}

	public boolean compararArrays(int[][] matrizJogo, int[][] matrizNovaJogada) {
		boolean match = true;

		if (matrizJogo.length != matrizNovaJogada.length && matrizJogo[0].length != matrizNovaJogada[0].length) {
			return false;
		}
		for (int i = 0; i < matrizJogo.length; i++) {
			for (int j = 0; j < matrizJogo[0].length; j++) {
				if (matrizJogo[i][j] != matrizNovaJogada[i][j]) {
					match = false;
					break;
				}

			}
			if (!match) {
				break;
			}
		}

		return match;

	}
}
