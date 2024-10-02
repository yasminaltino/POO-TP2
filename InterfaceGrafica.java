import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import java.awt.Font;
import javax.swing.border.EmptyBorder;
import javax.swing.border.BevelBorder;
import java.io.IOException;

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
    private JLabel imageLabel;
    private Jogo jogo, jogadorUmDados;
    private int maxTentativas;
    private boolean versus;
    private int statusVersus;

    // Construtor da classee
    public InterfaceGrafica(Jogo jogo, Jogo jogador2) {
        this.jogo = jogo;
        this.jogadorUmDados = jogador2;
        this.maxTentativas = jogo.getNumTentativas();
        // Configura o JFrame
        setTitle("Jogo da forca");
        setSize(1000, 760);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centraliza a janela na tela
        setResizable(false);

        // Add a WindowListener to handle the close event
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int response = JOptionPane.showConfirmDialog(InterfaceGrafica.this, "Deseja salvar o jogo antes de sair?", "Confirmar", JOptionPane.YES_NO_CANCEL_OPTION);
                if (response == JOptionPane.YES_OPTION) {
                    jogo.salvarJogo("savegame.dat"); // Specify your save path
                }
                // Optionally, handle NO_OPTION and CANCEL_OPTION here
                dispose(); // Close the application
            }
        });

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

        JButton botao1jogador = new JButton("1 jogador");
        botao1jogador.setFont(new Font("Tahoma", Font.BOLD, 15));
        botao1jogador.setBounds(400, 200, 180, 50);

        botao1jogador.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("1 jogador");
                setVersus(false);
                setStatusVersus(1);
                mostrarTelaEntradaNome();
            }
        });

        JButton botao2jogadores = new JButton("2 jogadores");
        botao2jogadores.setFont(new Font("Tahoma", Font.BOLD, 15));
        botao2jogadores.setBounds(400, 300, 180, 50);

        botao2jogadores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("2 jogadores");
                setVersus(true);
                setStatusVersus(1);
                mostrarTelaEntradaNome();
            }
        });

        JButton botaoContinuar = new JButton("Continuar jogo");
        botaoContinuar.setFont(new Font("Tahoma", Font.BOLD, 15));
        botaoContinuar.setBounds(400, 400, 180, 50);

        botaoContinuar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Continuar jogo");
                try{
                    jogo.carregarJogo("savegame.dat");
                } catch (IOException ex){
                    System.out.println("Nenhum jogo salvo encontrado.");
                    jogo = new Jogo();
                    jogo.iniciarNovoJogo();
                }
                jogo.setJogoSalvo(true);
                mostrarTelaJogo(); //Vai direto para o jogo
            }
        });

        // Adiciona os componentes ao painel
        painelInicial.add(titulo);
        painelInicial.add(botao1jogador);
        painelInicial.add(botao2jogadores);
        painelInicial.add(botaoContinuar);

        setContentPane(painelInicial);
        painelInicial.setVisible(true);
        revalidate();
        repaint();
    }

    public void mostrarTelaEntradaNome(){
        painelJogo = new JPanel();
        painelJogo.setLayout(null);

        JPanel painelEntrada = new JPanel();
        painelEntrada.setLayout(null);
        painelEntrada.setBounds(300, 300, 400, 300);

        JLabel labelEntrada = new JLabel();
        if (!getVersus()) {
            labelEntrada.setText("Digite o seu nome: ");
        }else{
            labelEntrada.setText("<html><div style='text-align: center;'> Digite o nome de quem irá <br> descobrir a palavra: </html>");
        }
        labelEntrada.setFont(new Font("Tahoma", Font.BOLD, 18));
        labelEntrada.setBounds(0, 0, 400, 60);
        labelEntrada.setHorizontalAlignment(SwingConstants.CENTER);
        painelEntrada.add(labelEntrada);

        JTextField campoEntrada = new JTextField();
        campoEntrada.setFont(new Font("Tahoma", Font.PLAIN, 18));
        campoEntrada.setBounds(0, 70, 400, 30);
        painelEntrada.add(campoEntrada);

        JButton botaoConfirmar = new JButton("Confirmar nome");
        botaoConfirmar.setFont(new Font("Tahoma", Font.BOLD, 15));
        botaoConfirmar.setBounds(100, 120, 180, 50);
        botaoConfirmar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.printf("Botão confirmar nome %d\n", getStatusVersus());

                jogo.setNomeJogador(campoEntrada.getText());

                if(getVersus() && getStatusVersus() == 1 || getStatusVersus() == 2){
                    mostrarTelaEscolhaPalavra();
                } else if (!getVersus()) {
                    mostrarTelaJogo();
                }
            }
        });

        painelEntrada.add(botaoConfirmar);

        painelJogo.add(painelEntrada);

        setContentPane(painelJogo);
        painelJogo.setVisible(true);
        revalidate();
    }

    public void mostrarTelaJogo(){
        painelJogo = new JPanel();
        painelJogo.setLayout(null);

        // Load the image
        // Painel para a imagem da forca
        JPanel imagePanel = new JPanel();
        imagePanel.setBounds(100, 100, 300, 300);
        imagePanel.setBackground(Color.LIGHT_GRAY);
        imagePanel.setOpaque(true);

        imageLabel = new JLabel();
        atualizarImagem();
        imagePanel.add(imageLabel);
        painelJogo.add(imagePanel);

        JPanel painelQuadrado = new JPanel();
        painelQuadrado.setLayout(new GridLayout(5, 1));
        painelQuadrado.setBorder(BorderFactory.createRaisedBevelBorder());
        painelQuadrado.setBounds(20,480,410,200);

        letrasAdivinhadas = new JLabel("Palavra: " + jogo.getPalavra().toString(), JLabel.CENTER);
        letrasAdivinhadas.setFont(new Font("Tahoma", Font.BOLD,  18));

        dica = new JLabel("Dica: " + jogo.getPalavra().getTema(), JLabel.CENTER);
        dica.setFont(new Font("Tahoma", Font.ITALIC,  15));

        letrasEscolhidas = new JLabel("Letras escolhidas:", JLabel.CENTER);
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
        if(jogo.getJogoSalvo()){
            for(int i = 0; i < jogo.getLetrasEscolhidas().size(); i++){
/////////////////////////////////////////////////////////////////////////////////
            }
        }
        painelBotoes.setBounds(550,472,410,210);
        painelJogo.add(painelBotoes);

        setContentPane(painelJogo);
        painelJogo.setVisible(true);
        revalidate();
    }

    public void mostrarTelaEscolhaPalavra() {
        painelJogo = new JPanel();
        painelJogo.setLayout(null);

        JPanel painelEntrada = new JPanel();
        painelEntrada.setLayout(null);
        painelEntrada.setBounds(300, 300, 400, 200);

        JLabel labelEntrada = new JLabel("Digite a palavra secreta: ");
        labelEntrada.setFont(new Font("Tahoma", Font.BOLD, 18));
        labelEntrada.setBounds(0, 0, 400, 30);
        painelEntrada.add(labelEntrada);

        JTextField campoEntrada = new JTextField();
        campoEntrada.setFont(new Font("Tahoma", Font.PLAIN, 18));
        campoEntrada.setBounds(0, 40, 400, 30);
        painelEntrada.add(campoEntrada);

        JButton botaoConfirmar = new JButton("Confirmar");
        botaoConfirmar.setFont(new Font("Tahoma", Font.BOLD, 15));
        botaoConfirmar.setBounds(100, 90, 180, 50);
        botaoConfirmar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Botão confirmar");

                String palavra;
                palavra = campoEntrada.getText().toUpperCase();
                if(palavra.length() < 8){
                    JOptionPane.showMessageDialog(null, "A palavra deve ter no mínimo 8 letras!", "", JOptionPane.ERROR_MESSAGE);
                    campoEntrada.setText("");
                    mostrarTelaEscolhaPalavra();
                }else{
                    jogo.getPalavra().setPalavra(palavra);
                    jogo.getPalavra().atualizaLabelLetrasAdvinhadas();
                    mostrarTelaJogo();
                }

            }
        });


        painelEntrada.add(botaoConfirmar);
        painelJogo.add(painelEntrada);

        setContentPane(painelJogo);
        painelJogo.setVisible(true);

        revalidate();
    }

    public JPanel criarPainelBotoes() {
        JPanel painelQuadrado = new JPanel();
        painelQuadrado.setLayout(null); // Use null layout for custom positioning
        painelQuadrado.setBounds(0, 240, 500, 300); // Adjust the size as needed

        Border border = BorderFactory.createRaisedBevelBorder();
        TitledBorder titledBorder = BorderFactory.createTitledBorder(border, "Escolha uma Letra");
        titledBorder.setTitleJustification(TitledBorder.CENTER); // Centraliza o título
        titledBorder.setTitleFont(new Font("Tahoma", Font.BOLD, 18));
        titledBorder.setTitleColor(Color.ORANGE);
        titledBorder.setTitlePosition(TitledBorder.TOP); // Coloca o título no topo
        painelQuadrado.setBorder(titledBorder);

        Dimension buttonSize = new Dimension(45, 25);
        int x = 7, y = 40; // Start y position below the label

        for (char letraChar = 'A'; letraChar <= 'Z'; letraChar++) {
            JButton botaoLetra = new JButton(String.valueOf(letraChar));
            botaoLetra.setBounds(x, y, buttonSize.width, buttonSize.height);

            if(jogo.getJogoSalvo()){
                for(Character c : jogo.getLetrasEscolhidas()){
                    if(c == letraChar){
                        botaoLetra.setEnabled(false);
                    }
                }
            }

            // Customize button appearance
            botaoLetra.setBackground(Color.LIGHT_GRAY);
            botaoLetra.setForeground(Color.BLACK);
            botaoLetra.setFont(new Font("Arial", Font.BOLD, 14));
            botaoLetra.setBorder(BorderFactory.createEtchedBorder(Color.BLACK, Color.LIGHT_GRAY));

            botaoLetra.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Letra escolhida: " + botaoLetra.getText());
                    capturarEntradaLetra(botaoLetra);
                }
            });

            painelQuadrado.add(botaoLetra);

            x += buttonSize.width + 5;
            if (x >= 400) { // Adjust the width as needed
                x = 7;
                y += buttonSize.height + 15;
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

        if (!acertou) {
            jogo.setNumTentativas(jogo.getNumTentativas() - 1);
        }

        tentativasRestantes.setText("Tentativas restantes: " + jogo.getNumTentativas());
        atualizarImagem();

        letrasAdivinhadas.setText("Palavra: " + jogo.getPalavra().toString());

        if (jogo.getNumTentativas() == 0 || jogo.getNumAcertos() == (jogo.getPalavra().getPalavra().length() - jogo.getPalavra().getNEspacos()) && !getVersus()) {
            // salvar dados do jogador sozinho aqui
            mostrarTelaFinal();
        } else if (getVersus() && jogo.getNumAcertos() == (jogo.getPalavra().getPalavra().length() - jogo.getPalavra().getNEspacos()) && getStatusVersus() == 1) {
            // salvar dados do jogador 1 aq
            Jogo tmp = jogo;
            jogo = jogadorUmDados;
            jogadorUmDados = tmp;
            setStatusVersus(getStatusVersus() + 1);
            jogo.iniciarNovoJogo();
            mostrarTelaEntradaNome();
        }else if(jogo.getNumTentativas() == 0 && getVersus() && getStatusVersus() > 1 || jogo.getNumAcertos() == jogo.getPalavra().getPalavra().length()){
            // salvar dados do jogador 2 aq
            mostrarTelaFinal();
        }
    }



    public void mostrarTelaFinal(){

        painelFinal = new JPanel();
        painelFinal.setLayout(null);

        // Painel para a imagem 1
        JPanel imagePanel = new JPanel();
        imagePanel.setBounds(100, 100, 300, 300);
        imagePanel.setBackground(Color.LIGHT_GRAY);
        imagePanel.setOpaque(true);

        imageLabel = new JLabel();
        imagePanel.add(imageLabel);

        painelFinal.add(imagePanel);
        atualizarImagem();


        JLabel resultado = new JLabel();
        resultado.setFont(new Font("Tahoma", Font.BOLD,  30));
        resultado.setBounds(300, 10, 800, 100);

        if(jogo.getNumTentativas() > 0){
            resultado.setText("Parabéns, você venceu!");
            jogo.setNumVitorias(jogo.getNumVitorias() + 1);
        } else {
            resultado.setText("Que pena, você perdeu!");
            jogo.setNumDerrotas(jogo.getNumDerrotas() + 1);
        }

        if(!getVersus()){
            painelFinal.add(resultado);
        }

        JButton recomecar = new JButton("Jogar Novamente");
        recomecar.setFont(new Font("Tahoma", Font.BOLD, 15));
        recomecar.setBounds(420, 450, 180, 50);

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
        sair.setBounds(420, 550, 180, 50);

        sair.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println("Botão sair");
                System.exit(0);
            }
        });

        painelFinal.add(sair);


        JPanel painelEstatisticas = new JPanel();
        painelEstatisticas.setLayout(null);
        painelEstatisticas.setBounds(100, 450, 850, 200);
        JLabel labelEstatisticas = new JLabel("", JLabel.CENTER);

        // definindo bordas para o compoundBorder
        Border bevelBorder = new BevelBorder(BevelBorder.LOWERED);
        Border paddingBorder = new EmptyBorder(10, 10, 10, 10);
        if(!getVersus()){
            labelEstatisticas = new JLabel("", JLabel.CENTER);
            labelEstatisticas.setFont(new Font("Tahoma", Font.BOLD, 18));
            labelEstatisticas.setBounds(0, 0, 250, 200);
            labelEstatisticas.setBorder(new CompoundBorder(bevelBorder, paddingBorder));
            String venceu = jogo.getNumTentativas() > 0 ? "Venceu" : "Perdeu";
            labelEstatisticas.setText("<html><span style='font-size:18px;'>Estatísticas</span><br>" +
                    "Jogador: " + jogo.getNomeJogador() + "<br>" +
                    "Tentativas restantes: " + (jogo.getNumTentativas()) + "<br>" +
                    "Letras acertadas: " + jogo.getNumAcertos() + "<br>" +
                    "Palavra: " + jogo.getPalavra().getPalavra() + "</html>");
        }


        if(getVersus()){
            JLabel labelEstatisticas2 = new JLabel("", JLabel.CENTER);
            labelEstatisticas2.setFont(new Font("Tahoma", Font.BOLD,  18));
            labelEstatisticas2.setBounds(0,0, 250, 200);
            labelEstatisticas2.setBorder(new CompoundBorder(bevelBorder, paddingBorder));
            String venceu = jogo.getNumTentativas() > 0 ? "Venceu" : "Perdeu";
            labelEstatisticas2.setText("<html><span style='font-size:18px;'> " + venceu +" </span><br>" +
                    "Jogador: " + jogadorUmDados.getNomeJogador() + "<br>" +
                    "Tentativas restantes: " + (jogadorUmDados.getNumTentativas() ) + "<br>" +
                    "Letras acertadas: " + jogadorUmDados.getNumAcertos() + "<br>" +
                    "Palavra: " + jogadorUmDados.getPalavra().getPalavra() + "</html>");
            painelEstatisticas.add(labelEstatisticas2);

            labelEstatisticas = new JLabel("", JLabel.CENTER);
            labelEstatisticas.setFont(new Font("Tahoma", Font.BOLD, 18));
            labelEstatisticas.setBounds(550, 0, 250, 200);
            labelEstatisticas.setBorder(new CompoundBorder(bevelBorder, paddingBorder));
            venceu = jogo.getNumTentativas() > 0 ? "Venceu" : "Perdeu";
            labelEstatisticas.setText("<html><span style='font-size:18px;'> " + venceu +" </span><br>" +
                    "Jogador: " + jogo.getNomeJogador() + "<br>" +
                    "Tentativas restantes: " + (jogo.getNumTentativas()) + "<br>" +
                    "Letras acertadas: " + jogo.getNumAcertos() + "<br>" +
                    "Palavra: " + jogo.getPalavra().getPalavra() + "</html>");
        }

        painelEstatisticas.add(labelEstatisticas);
        painelFinal.add(painelEstatisticas);

        setContentPane(painelFinal);
        painelFinal.setVisible(true);
        revalidate();
    }

    public void atualizarImagem() {
        String imagePath;
        System.out.printf("Tentativas: %d\n MaxTentativas: %d", jogo.getNumTentativas() , maxTentativas);
        imagePath = "media/forca" + (jogo.getNumTentativas() + 1) + ".jpeg";

        ImageIcon originalIcon = new ImageIcon(imagePath);
        Image originalImage = originalIcon.getImage();

        int newWidth = 300;
        int newHeight = (originalIcon.getIconHeight() * newWidth) / originalIcon.getIconWidth();
        Image scaledImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);

        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        imageLabel.setIcon(scaledIcon);
    }


    public void setVersus(boolean versus){
        this.versus = versus;
    }

    public boolean getVersus(){
        return versus;
    }

    public int getStatusVersus() {
        return statusVersus;
    }

    public void setStatusVersus(int statusVersus) {
        this.statusVersus = statusVersus;
    }

    // Método principal para executar a aplicação
    public static void main(String[] args) {
        Jogo jogo = new Jogo();
        Jogo jogador2 = new Jogo();
        jogo.iniciarNovoJogo();
        // Garante que a GUI será criada no event-dispatching thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new InterfaceGrafica(jogo, jogador2).setVisible(true);
            }
        });
    }
}