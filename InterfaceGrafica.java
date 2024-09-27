import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.Font;
import java.util.Locale;

public class InterfaceGrafica extends JFrame {
    // Declaração dos componentes
    private JPanel painelInicial;
    private JPanel painelJogo;
    private JPanel painelFinal;
    private JLabel letrasAdivinhadas;
    private JLabel dica;
    private JLabel letrasEscolhidas;
    private JLabel tentativasRestantes;
    private JLabel vetorLetras;
    private Jogo jogo;



    // Construtor da classe
    public InterfaceGrafica(Jogo jogo) {
        this.jogo = jogo;
        // Configura o JFrame
        setTitle("Jogo da forca");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centraliza a janela na tela

        // Inicializa os componentes
        mostrarTelaInicial();

        setVisible(true);
    }

    public void mostrarTelaInicial() {
        painelInicial = new JPanel();
        painelInicial.setLayout(null);

        JLabel titulo = new JLabel("Bem vindo ao Jogo da Forca!");
        titulo.setFont(new Font("Tahoma", Font.BOLD, 40));
        titulo.setBounds(170, 100, 800, 100);

        // Painel intermediário para o botão
        JButton botaoComeco = new JButton("Começar jogo");
        botaoComeco.setFont(new Font("Tahoma", Font.BOLD, 15));
        botaoComeco.setBounds(400, 200, 180, 50);

        botaoComeco.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Botao começo");
                mostrarTelaJogo();
            }
        });

        painelInicial.add(titulo);
        painelInicial.add(botaoComeco);

        setContentPane(painelInicial);
        painelInicial.setVisible(true);
        revalidate();
        repaint();
    }

    public void mostrarTelaJogo(){
        painelJogo = new JPanel();
        painelJogo.setLayout(null);

        // Load the image
        ImageIcon imageIcon = new ImageIcon("/home/yasmin/Documentos/24.1/POO/Trabalhos/POOTP2/forca.png"); // Adjust the path to your image
        JLabel imageLabel = new JLabel(imageIcon);
        imageLabel.setBounds(100, 100, imageIcon.getIconWidth(), imageIcon.getIconHeight()); // Adjust the position and size as needed
        painelJogo.add(imageLabel);

        JPanel painelQuadrado = new JPanel();
        painelQuadrado.setLayout(new GridLayout(5, 1));
        painelQuadrado.setBorder(BorderFactory.createLoweredBevelBorder());
        painelQuadrado.setBounds(20,480,350,200);

        letrasAdivinhadas = new JLabel("Palavra: " + jogo.getPalavra().getLetrasAdvinhadas().toString(), JLabel.CENTER);
        letrasAdivinhadas.setFont(new Font("Tahoma", Font.BOLD,  18));

        dica = new JLabel("Dica: " + jogo.getPalavra().getTema(), JLabel.CENTER);
        dica.setFont(new Font("Tahoma", Font.ITALIC,  15));


        letrasEscolhidas = new JLabel("Letras já escolhidas:", JLabel.CENTER);
        letrasEscolhidas.setFont(new Font("Tahoma", Font.BOLD,  15));

        vetorLetras = new JLabel(jogo.getLetrasEscolhidasFormatadas(), JLabel.CENTER);
        vetorLetras.setFont(new Font("Tahoma", Font.PLAIN,  15));

        tentativasRestantes = new JLabel("Tentativas restantes: " + jogo.getNumTentativas(), JLabel.CENTER);
        tentativasRestantes.setFont(new Font("Tahoma", Font.BOLD,  15));

        painelQuadrado.add(letrasAdivinhadas);
        painelQuadrado.add(dica);
        painelQuadrado.add(letrasEscolhidas);
        painelQuadrado.add(vetorLetras);
        painelQuadrado.add(tentativasRestantes);

        painelJogo.add(painelQuadrado);

        JPanel painelBotoes = criarPainelBotoes();
        painelBotoes.setBounds(440,500,555,180);
        painelJogo.add(painelBotoes);

        setContentPane(painelJogo);
        painelJogo.setVisible(true);
        revalidate();



    }

    public JPanel criarPainelBotoes() {
        JPanel painelQuadrado = new JPanel();
        painelQuadrado.setLayout(null); // Use null layout for custom positioning
        painelQuadrado.setBorder(BorderFactory.createLoweredBevelBorder());
        painelQuadrado.setBounds(0, 240, 500, 300); // Adjust the size as needed

        JLabel letra = new JLabel("Escolha uma letra: ", JLabel.CENTER);
        letra.setFont(new Font("Tahoma", Font.BOLD, 18));
        letra.setBounds(0, 0, 500, 30); // Adjust the size as needed
        painelQuadrado.add(letra);

        Dimension buttonSize = new Dimension(50, 30);
        int x = 4, y = 40; // Start y position below the label

        for (char letraChar = 'A'; letraChar <= 'Z'; letraChar++) {
            JButton botaoLetra = new JButton(String.valueOf(letraChar));
            botaoLetra.setBounds(x, y, buttonSize.width, buttonSize.height);

            // Customize button appearance
            botaoLetra.setBackground(Color.LIGHT_GRAY);
            botaoLetra.setForeground(Color.BLACK);
            botaoLetra.setFont(new Font("Arial", Font.BOLD, 14));
            botaoLetra.setBorder(BorderFactory.createLineBorder(Color.BLUE));

            botaoLetra.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Letra escolhida: " + botaoLetra.getText());
                    capturarEntradaLetra(botaoLetra);
                }
            });

            painelQuadrado.add(botaoLetra);

            x += buttonSize.width + 5;
            if (x >= 500) { // Adjust the width as needed
                x = 4;
                y += buttonSize.height + 5;
            }
        }

        painelQuadrado.setPreferredSize(new Dimension(500, y + buttonSize.height));
        return painelQuadrado;
    }

    public void capturarEntradaLetra(JButton botao) {
        boolean acertou;
        char letra = botao.getText().charAt(0);
        botao.setEnabled(false);

        jogo.getLetrasEscolhidas().add(letra);
        vetorLetras.setText(jogo.getLetrasEscolhidasFormatadas());

        acertou = jogo.adivinharLetra(letra);
        if(!acertou){
            jogo.setNumTentativas(jogo.getNumTentativas() - 1);
        }

        tentativasRestantes.setText("Tentativas restantes: " + jogo.getNumTentativas());

        letrasAdivinhadas.setText("Palavra: " + jogo.getPalavra().getLetrasAdvinhadas().toString());

        if(jogo.getNumTentativas() == 0 || jogo.getNumAcertos() == jogo.getPalavra().getPalavra().length())
            mostrarTelaFinal();
    }

    public void mostrarTelaFinal(){
        painelFinal = new JPanel();
        painelFinal.setLayout(null);

        JLabel resultado = new JLabel();
        resultado.setFont(new Font("Tahoma", Font.BOLD,  30));
        resultado.setBounds(300, 100, 800, 100);

        if(jogo.getNumTentativas() > 0){
            resultado.setText("Parabéns, você venceu!");
            jogo.setNumVitorias(jogo.getNumVitorias() + 1);
        } else {
            resultado.setText("Que pena, você perdeu!");
            jogo.setNumDerrotas(jogo.getNumDerrotas() + 1);
        }

        painelFinal.add(resultado);

        JButton recomecar = new JButton("Jogar Novamente");
        recomecar.setFont(new Font("Tahoma", Font.BOLD, 15));
        recomecar.setBounds(400, 200, 180, 50);

        recomecar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println("Botão recomeçar");
                jogo.iniciarNovoJogo();
                mostrarTelaInicial();
            }
        });

        painelFinal.add(recomecar);

        JButton sair = new JButton("Sair");
        sair.setFont(new Font("Tahoma", Font.BOLD, 15));
        sair.setBounds(400, 300, 180, 50);

        sair.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println("Botão sair");
                System.exit(0);
            }
        });

        painelFinal.add(sair);

        setContentPane(painelFinal);
        painelFinal.setVisible(true);
        revalidate();


    }



    // Método principal para executar a aplicação
    public static void main(String[] args) {
        Jogo jogo = new Jogo();
        jogo.iniciarNovoJogo();
        // Garante que a GUI será criada no event-dispatching thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new InterfaceGrafica(jogo).setVisible(true);
            }
        });
    }
}