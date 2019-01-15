package ufrpe.lc.lab1;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.InputStream;
import java.security.AccessController;
import java.security.PrivilegedAction;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class Puzzle8 extends JFrame {

	private static final long serialVersionUID = 1L;

	private JButton[][] botoes = new JButton[3][3];

	private JButton novoJogo = new JButton("Começar novo jogo");

	private JButton gerarJogada = new JButton("Gerar jogada da CPU");

	private ControlePuzzle8 controle = new ControlePuzzle8();

	public static final int NUMERO_POS_VAZIA = 9;

	/**
	 * Construtor vazio
	 */
	public Puzzle8(){
		super();
	}

	/**
	 * Construtor da classe
	 * @param titulo Tírulo da tela
	 */
	public Puzzle8(String titulo) {
		super(titulo);
		setIconImage(loadImage("logo.png"));
	}

	/**
	 * Cria uma posição inicial para cada número no jogo
	 *
	 */
	public void iniciarNovoJogo() {
		int[][] matrizJogo = controle.iniciarNovoJogo();
		converterMatriz2Botoes(botoes, matrizJogo);
		repaint();
		habilitarBotoes(true);
	}

	/**
	 * Cria uma posição inicial para cada número no jogo
	 *
	 */
	public void gerarJogada() {
		habilitarBotoes(false);
		int[][] matrizJogo = new int[3][3];
		converterBotoes2Matriz(botoes, matrizJogo);
		int[][] matrizNovaJogada = matrizJogo.clone();
		matrizNovaJogada = controle.gerarJogada(matrizJogo);
		converterMatriz2Botoes(botoes, matrizNovaJogada);
		habilitarBotoes(true);
		repaint();
		if (controle.verificarFimJogo(matrizNovaJogada) == 0){
			//o jogo terminou - exibir mensagem e desabilitar controles
			gameOver();
		}
	}
	
	/**
	 * Trata o clique do jogador em alguma posição.
	 *
	 * @param botao O botão que foi clicado na tela.
	 */
	public void tratarCliqueBotao(Object botao) {
		// TODO: implementar o que ocorre quando clica em uma posição
		int[][] matriz = new int[3][3];
		int[][] matrizAposJogada = null;
		converterBotoes2Matriz(botoes, matriz);
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (botoes[i][j].getText().equals(((JButton)botao).getText())) {
					matrizAposJogada = controle.tratarJogada(matriz, i, j);
					break;
				}
			}
		}
		converterMatriz2Botoes(botoes, matrizAposJogada);
		repaint();

		if (controle.verificarFimJogo(matrizAposJogada) == 0){
			//o jogo terminou - exibir mensagem e desabilitar controles
			gameOver();
		}
	}
	
	/**
	 * Habilita ou desabilita os botões
	 *
	 * @param habilitar Indicativo se habilita ou não.
	 */
	public void habilitarBotoes(boolean habilitar){
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				botoes[i][j].setEnabled(habilitar);
			}
		}
	}
	
	/**
	 * Trata o clique do jogador em alguma posição.
	 *
	 * @param botao O botão que foi clicado na tela.
	 */
	public void gameOver() {
		habilitarBotoes(false);
		if (JOptionPane.showConfirmDialog(this, "Parabéns! Deseja jogar de novo?") == JOptionPane.YES_OPTION){
			iniciarNovoJogo();
		}
	}

	/**
	 * Converte a representação de matriz de inteiros para a tela.
	 * 
	 * @param botoes
	 *            Representação na tela
	 * @param matriz
	 *            Matriiz de inteiros
	 */
	public void converterMatriz2Botoes(JButton[][] botoes, int[][] matriz) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (matriz[i][j] == NUMERO_POS_VAZIA) {
					botoes[i][j].setText("");
				} else {
					botoes[i][j].setText("" + matriz[i][j]);
				}
			}
		}
	}

	/**
	 * Converte a representação da tela em uma matriz de inteiros.
	 * 
	 * @param botoes
	 *            Representação na tela
	 * @param matriz
	 *            Matriiz de inteiros
	 */
	public void converterBotoes2Matriz(JButton[][] botoes, int[][] matriz) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (botoes[i][j].getText().equals("")) {
					matriz[i][j] = NUMERO_POS_VAZIA;
				} else {
					matriz[i][j] = Integer.parseInt(botoes[i][j].getText());
				}
			}
		}
	}

	/**
	 * Faz os comandos iniciais necessários para exibição na tela
	 * 
	 */
	public void iniciar() {
		JPanel panelSup = new JPanel();
		JPanel panelInf = new JPanel();
		setLayout(new BorderLayout());
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			System.out.println("Erro ao alterar LAF: " + e);
		}

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dispose();
				System.exit(0);
			}
		});

		novoJogo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				iniciarNovoJogo();
			}
		});
		panelSup.add(novoJogo);
		panelSup.add(gerarJogada);
		
		gerarJogada.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				gerarJogada();
			}
		});

		ActionListener alBotoes = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tratarCliqueBotao(e.getSource());
			}
		};

		panelInf.setLayout(new GridLayout(3, 3));
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				botoes[i][j] = new JButton("" + (j + i * 3 + 1));
				botoes[i][j].addActionListener(alBotoes);
				if (i == 2 && j == 2) {
					botoes[i][j].setText("");
				}
				panelInf.add(botoes[i][j]);
				botoes[i][j].setFont(new Font("Arial", Font.BOLD, 36));
			}
		}
		add(BorderLayout.NORTH, panelSup);
		add(BorderLayout.CENTER, panelInf);
		habilitarBotoes(false);
		setSize(336, 336);
		setLocation(50, 50);
		setVisible(true);
	}

	/**
	 * Método de entrada da aplicação
	 * 
	 * @param args
	 *            Atributos de linha de comando - é ignorado pela aplicação.
	 */
	public static void main(String[] args) {
		Puzzle8 f = new Puzzle8("UFRPE - Lab. Programação I");
		f.iniciar();
	}
	
	/**
	 * Retorna uma imagem, considerando a possibilidade de estar empacotado em
	 * um arquivo JAR ou não.
	 * 
	 * @param imageName
	 *            Nome da imagem
	 * @return Objeto representando a imagem
	 */
	public static Image loadImage(final String imageName) {
		final ClassLoader loader = Puzzle8.class.getClassLoader();
		Image image = null;
		InputStream is = (InputStream) AccessController
				.doPrivileged(new PrivilegedAction<InputStream>() {
					public InputStream run() {
						if (loader != null) {
							return loader.getResourceAsStream(imageName);
						} else {
							return ClassLoader
									.getSystemResourceAsStream(imageName);
						}
					}
				});
		if (is != null) {
			try {
				final int BlockLen = 256;
				int offset = 0;
				int len;
				byte imageData[] = new byte[BlockLen];
				while ((len = is.read(imageData, offset, imageData.length
						- offset)) > 0) {
					if (len == (imageData.length - offset)) {
						byte newData[] = new byte[imageData.length * 2];
						System.arraycopy(imageData, 0, newData, 0,
								imageData.length);
						imageData = newData;
						newData = null;
					}
					offset += len;
				}
				image = java.awt.Toolkit.getDefaultToolkit().createImage(
						imageData);
			} catch (java.io.IOException ex) {
			}
		}

		if (image == null) {
			image = new ImageIcon(imageName).getImage();
		}
		return image;
	}

}
