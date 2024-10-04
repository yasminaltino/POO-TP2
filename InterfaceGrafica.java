import javax.swing.*;
import javax.sound.sampled.*;
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
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

//classe InterfaceGrafica que herda de JFrame
public class InterfaceGrafica extends JFrame {
    // Declaração dos atributos de InterfaceGrafica
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

    // Construtor da classe
    public InterfaceGrafica(Jogo jogo, Jogo jogador2) {
        this.jogo = jogo;
        this.jogadorUmDados = jogador2;
        this.maxTentativas = jogo.getNumTentativas();
        jogo.setNivelDificuldade(1);
        // Configura o JFrame
        setTitle("Jogo da forca");
        setSize(1000, 760);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centraliza a janela na tela
        setResizable(false);

        // Adiciona um WindowListener para oferecer a opcao de salvar o jogo caso a janela seja fechada
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int response = JOptionPane.showConfirmDialog(InterfaceGrafica.this, "Deseja salvar o jogo antes de sair?", "Confirmar", JOptionPane.YES_NO_CANCEL_OPTION);
                if (response == JOptionPane.YES_OPTION) {
                    jogo.salvarJogo("savegame.dat");
                }
                dispose(); // Fecha a aplicacao
            }
        });

        // Somos levados para a tela inicial
        try { // try-catch para excecoes que envolvam o audio do jogo
            mostrarTelaInicial();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedAudioFileException e) {
            throw new RuntimeException(e);
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }

    // Faz com que nossa interface seja visivel
        setVisible(true);
    }

    // Tela inicial do jogo 
    public void mostrarTelaInicial() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        painelInicial = new JPanel();
        painelInicial.setLayout(null);

        // Configuracao do audio presente enquanto o jogo esta aberto
        File file = new File("Toy Shopping - Jeremy Korpas.wav");
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
        Clip clip = AudioSystem.getClip();
        clip.open(audioStream);

        //Metodo que inicializa o audio
        clip.start();

        //Titulo do jogo
        JLabel titulo = new JLabel("Bem vindo ao Jogo da Forca!");
        titulo.setFont(new Font("Tahoma", Font.BOLD, 40));
        titulo.setBounds(170, 100, 800, 100);

        // Botao para opcao de jogar com 1 jogador
        JButton botao1jogador = new JButton("1 jogador");
        botao1jogador.setFont(new Font("Tahoma", Font.BOLD, 15));
        botao1jogador.setBounds(400, 200, 180, 50);

        botao1jogador.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Fazemos as configuracoes necessarias para o modo 1 jogador
                System.out.println("1 jogador");
                jogo.setVersus(false);
                jogo.setStatusVersus(1);
                jogo.iniciarNovoJogo();
                mostrarTelaEntradaNome(); // O codigo parte para a secao onde o jogador ira inserir seu nome
            }
        });

        // Botao para opcao de jogar com 2 jogadores
        JButton botao2jogadores = new JButton("2 jogadores");
        botao2jogadores.setFont(new Font("Tahoma", Font.BOLD, 15));
        botao2jogadores.setBounds(400, 300, 180, 50);

        botao2jogadores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Fazemos as configuracoes necessarias para o modo 2 jogadores
                System.out.println("2 jogadores");
                jogo.setVersus(true);
                jogadorUmDados.setVersus(true);
                jogo.setStatusVersus(1);
                jogadorUmDados.setStatusVersus(1);
                jogo.setNumTentativas(7);
                jogadorUmDados.setNumTentativas(7);
                mostrarTelaEntradaNome(); // O codigo parte para a secao onde o jogador ira inserir seu nome
                jogo.setNivelDificuldade(4);
                jogadorUmDados.setNivelDificuldade(4);
            }
        });

        // Botao para continuar um jogo que ja estava salvo
        JButton botaoContinuar = new JButton("Continuar jogo");
        botaoContinuar.setFont(new Font("Tahoma", Font.BOLD, 15));
        botaoContinuar.setBounds(400, 400, 180, 50);

        botaoContinuar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Continuar jogo");
                try{ //try-catch para verificar se existe um jogo salvo
                    jogo.carregarJogo("savegame.dat");
                } catch (IOException ex){
                    System.out.println("Nenhum jogo salvo encontrado.");
                    jogo = new Jogo();
                    jogo.iniciarNovoJogo();
                }
                jogo.setJogoSalvo(true);
                mostrarTelaJogo(); // O codigo vai direto para o jogo, sem antes precisar passar pela tela onde o nome sera pedido, pelo fato de ja estar presente no arquivo
            }
        });

        //RadioButtons para escolher a dificuldade do jogo
        JRadioButton botaoFacil = new JRadioButton("Fácil: ");
        JRadioButton botaoMedio = new JRadioButton("Médio: ");
        JRadioButton botaoDificil = new JRadioButton("Difícil: ");
        botaoFacil.setFont(new Font("Tahoma", Font.BOLD, 15));
        botaoMedio.setFont(new Font("Tahoma", Font.BOLD, 15));
        botaoDificil.setFont(new Font("Tahoma", Font.BOLD, 15));
        ButtonGroup botoesDificuldade = new ButtonGroup();
//        botoesDificuldade.setSelected(botaoFacil.getModel(), true);
        botaoFacil.setSelected(true);
        botaoFacil.setBounds(620, 200, 180, 50);
        botaoMedio.setBounds(620, 250, 180, 50);
        botaoDificil.setBounds(620, 300, 180, 50);
        botoesDificuldade.add(botaoFacil);
        botoesDificuldade.add(botaoMedio);
        botoesDificuldade.add(botaoDificil);
        painelInicial.add(botaoFacil);
        painelInicial.add(botaoMedio);
        painelInicial.add(botaoDificil);

        // Com base no RadioButton escolhido, a dificuldade do jogo eh setada
        botaoFacil.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Fácil");
                jogo.setNumTentativas(8);
                maxTentativas = 8;
                jogo.setNivelDificuldade(1);
            }
        });

        botaoMedio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Médio");
                jogo.setNumTentativas(7);
                maxTentativas = 7;
                jogo.setNivelDificuldade(2);
            }
        });

        botaoDificil.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Difícil");
                jogo.setNumTentativas(6);
                maxTentativas = 6;
                jogo.setNivelDificuldade(3);
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

    // Tela em que o nome do jogador eh pedido
    public void mostrarTelaEntradaNome(){
        painelJogo = new JPanel();
        painelJogo.setLayout(null);

        // Painel para os elementos
        JPanel painelEntrada = new JPanel();
        painelEntrada.setLayout(null);
        painelEntrada.setBounds(300, 300, 400, 300);

        // label para o texto
        JLabel labelEntrada = new JLabel();
        if (!jogo.isVersus()) { // Se for 1 jogador
            labelEntrada.setText("Digite o seu nome: ");
        }else{ // Se for 2 jogadores
            labelEntrada.setText("<html><div style='text-align: center;'> Digite o nome de quem irá <br> descobrir a palavra: </html>");
        }
        labelEntrada.setFont(new Font("Tahoma", Font.BOLD, 18));
        labelEntrada.setBounds(0, 0, 400, 60);
        labelEntrada.setHorizontalAlignment(SwingConstants.CENTER);
        painelEntrada.add(labelEntrada);

        // Campo para escrever o texto a ser entrado
        JTextField campoEntrada = new JTextField();
        campoEntrada.setFont(new Font("Tahoma", Font.PLAIN, 18));
        campoEntrada.setBounds(0, 70, 400, 30);
        painelEntrada.add(campoEntrada);

        // Botao para confirmar o nome
        JButton botaoConfirmar = new JButton("Confirmar nome");
        botaoConfirmar.setFont(new Font("Tahoma", Font.BOLD, 15));
        botaoConfirmar.setBounds(100, 120, 180, 50);
        botaoConfirmar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { // Define a próxima ação de acordo com o tipo de jogo
                System.out.printf("Botão confirmar nome %d\n", jogo.getStatusVersus());

                jogo.setNomeJogador(campoEntrada.getText());

                if(jogo.isVersus() && jogo.getStatusVersus() == 1 || jogo.getStatusVersus() == 2){
                    mostrarTelaEscolhaPalavra();
                } else if (!jogo.isVersus()) {
                    mostrarTelaJogo();
                }
            }
        });

        // Adiciona elementos ao painel e ao frame
        painelEntrada.add(botaoConfirmar);
        painelJogo.add(painelEntrada);

        setContentPane(painelJogo);
        painelJogo.setVisible(true);
        revalidate();
    }

    public void mostrarTelaJogo(){
        painelJogo = new JPanel();
        painelJogo.setLayout(null);

        // Painel para a imagem da forca
        JPanel imagePanel = new JPanel();
        imagePanel.setBounds(100, 100, 300, 300);
        imagePanel.setBackground(Color.LIGHT_GRAY);
        imagePanel.setOpaque(true);

        // Tratamento da imagem
        imageLabel = new JLabel();
        atualizarImagem();
        imagePanel.add(imageLabel);
        painelJogo.add(imagePanel);

        // Painel para as estatísticas do jogo
        JPanel painelQuadrado = new JPanel();
        painelQuadrado.setLayout(new GridLayout(5, 1));
        painelQuadrado.setBorder(BorderFactory.createRaisedBevelBorder());
        painelQuadrado.setBounds(20,480,410,200);

        // Label para as letras adivinhadas
        letrasAdivinhadas = new JLabel("Palavra: " + jogo.getPalavra().toString(), JLabel.CENTER);
        letrasAdivinhadas.setFont(new Font("Tahoma", Font.BOLD,  18));

        // Label para a dica
        dica = new JLabel("Dica: " + jogo.getPalavra().getTema(), JLabel.CENTER);
        dica.setFont(new Font("Tahoma", Font.ITALIC,  15));

        // Label para as letras escolhidas
        letrasEscolhidas = new JLabel("Letras escolhidas:", JLabel.CENTER);
        letrasEscolhidas.setFont(new Font("Tahoma", Font.BOLD,  15));

        // Label para o vetor de letras escolhidas
        vetorLetras = new JLabel(jogo.getLetrasEscolhidasFormatadas(), JLabel.CENTER);
        vetorLetras.setFont(new Font("Tahoma", Font.PLAIN,  15));

        tentativasRestantes = new JLabel("Tentativas restantes: " + jogo.getNumTentativas(), JLabel.CENTER);
        tentativasRestantes.setFont(new Font("Tahoma", Font.BOLD,  15));

        // RadioButtons para níveis de dificuldade
        JLabel nivelDificuldade = new JLabel("Dificuldade: " + jogo.getNivelDificuldade(), JLabel.CENTER);
        nivelDificuldade.setFont(new Font("Tahoma", Font.BOLD,  15));
        nivelDificuldade.setBounds(130, 440, 200, 30);
        painelJogo.add(nivelDificuldade);

        // Botão para dica
        JButton botaoDica = new JButton("Dica?");
        botaoDica.setFont(new Font("Tahoma", Font.BOLD, 15));
        botaoDica.setBounds(20, 440, 80, 30);
        botaoDica.setBackground(Color.LIGHT_GRAY);
        botaoDica.setForeground(Color.BLACK);
        botaoDica.setFont(new Font("Arial", Font.BOLD, 14));
        botaoDica.setBorder(BorderFactory.createEtchedBorder(Color.BLACK, Color.LIGHT_GRAY));
        painelJogo.add(botaoDica);

        botaoDica.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jogo.mostrarDica(); // A dica eh mostrada ao jogador
                botaoDica.setEnabled(false);
                letrasAdivinhadas.setText("Palavra: " + jogo.getPalavra().toString());
            }
        });
        if(jogo.getNivelDificuldade() == Jogo.NivelDificuldade.DIFICIL){
            botaoDica.setVisible(false);
        }

        // Adiciona todos os elementos ao painel
        painelQuadrado.add(letrasAdivinhadas);
        painelQuadrado.add(dica);
        painelQuadrado.add(letrasEscolhidas);
        painelQuadrado.add(vetorLetras);
        painelQuadrado.add(tentativasRestantes);

        // Adiciona o painel ao frame
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
                
                boolean hasInvalidCharacter = false;
                
             // Percorre a palavra em busca de algum caractere inválido
                for (int i = 0; i < palavra.length(); i++) 
                	if ((palavra.charAt(i) < 'A' || palavra.charAt(i) > 'Z') && palavra.charAt(i) != ' ' && palavra.charAt(i) != '-')
                		hasInvalidCharacter = true;
                
             // Verifica se a palavra tem um comprimento válido ( >= 8)
                if(palavra.length() < 8){
                    JOptionPane.showMessageDialog(null, "A palavra deve ter no mínimo 8 letras!", "", JOptionPane.ERROR_MESSAGE);
                    campoEntrada.setText("");
                    mostrarTelaEscolhaPalavra();
                }
                
             // Verifica se a palavra apresentou algum caractere inválido
                else if(hasInvalidCharacter) {
                	JOptionPane.showMessageDialog(null, "A palavra deve conter somente letras, espaços ou hífens!", "", JOptionPane.ERROR_MESSAGE);
                    campoEntrada.setText("");
                    mostrarTelaEscolhaPalavra();
                }
                
             // Se nenhuma irregularidade for detectada, prossegue com o jogo
                else {
                    jogo.getPalavra().setPalavra(palavra);
                    jogo.getPalavra().setTema("Customizado");
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

    // Metodo responsavel por criar botoes utilizados na interface
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

        // Sao criados os botoes com todas as letras do alfabeto
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

            // Customizar a aparencia dos botoes
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

    // Metodo responsavel por alterar o jogo quando o jogador faz uma jogada (escolhe uma letra)
    public void capturarEntradaLetra(JButton botao) {
        boolean acertou;
        char letra = botao.getText().charAt(0);
        botao.setEnabled(false);

        jogo.getLetrasEscolhidas().add(letra);
        vetorLetras.setText(jogo.getLetrasEscolhidasFormatadas());

        acertou = jogo.adivinharLetra(letra);

        // Caso o jogador erre, a quantidade de tentativas que o mesmo possui, diminui
        if (!acertou) {
            jogo.setNumTentativas(jogo.getNumTentativas() - 1);
        }

        // Atualizamos o Label com a quantidade atual de tentativas e a imagem da forca, com base nessa nova quantidade de tentativas
        tentativasRestantes.setText("Tentativas restantes: " + jogo.getNumTentativas());
        atualizarImagem();

        // Atualizamos o Label que contem o vetor de letras corretamente adivinhadas
        letrasAdivinhadas.setText("Palavra: " + jogo.getPalavra().toString());

        if (jogo.getNumTentativas() == 0 || jogo.getNumAcertos() == (jogo.getPalavra().getPalavra().length() - jogo.getPalavra().getNEspacos()) && !jogo.isVersus()) {
            // O jogo modo 1 jogador terminou
            //salvar dados jogador 1

            mostrarTelaFinal();
        } else if (jogo.isVersus() && jogo.getNumAcertos() == (jogo.getPalavra().getPalavra().length() - jogo.getPalavra().getNEspacos()) && jogo.getStatusVersus() == 1) {
            // O jogo modo 2 jogadores terminou
            Jogo tmp = jogo;
            jogo = jogadorUmDados;
            jogadorUmDados = tmp;
            jogo.setStatusVersus(jogo.getStatusVersus() + 1);
            jogo.resetarDados();
            mostrarTelaEntradaNome();
        }else if (jogo.getNumTentativas() == 0 && jogo.isVersus() && jogo.getStatusVersus() > 1 || jogo.getNumAcertos() == jogo.getPalavra().getPalavra().length()){
            // salvar dados do jogador 2 aq
            mostrarTelaFinal();
        }
    }

    // Metodo responsavel por apresentar a tela final, onde teremos as estatisticas do jogador
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

        // Label que contara se o jogador venceu ou nao
        JLabel resultado = new JLabel();
        resultado.setFont(new Font("Tahoma", Font.BOLD,  30));
        resultado.setBounds(300, 10, 800, 100);

        if(jogo.getNumTentativas() > 0){
            resultado.setText("Parabéns, você venceu!");
            jogo.setNumVitorias(jogo.getNumVitorias() + 1);
            jogo.adicionarPalavraCorreta(jogo.getPalavra().getPalavra()); // Adiciona a palavra correta

        } else {
            resultado.setText("Que pena, você perdeu!");
            jogo.setNumDerrotas(jogo.getNumDerrotas() + 1);
        }

        salvarDadosJogador();/////////////////////////////////////////////

        //Resultado somente caso seja um jogo individual
        if(!jogo.isVersus()){
            painelFinal.add(resultado);
        }

        // Botao que oferece a opcao de jogar novamente
        JButton recomecar = new JButton("Jogar Novamente");
        recomecar.setFont(new Font("Tahoma", Font.BOLD, 15));
        recomecar.setBounds(420, 450, 180, 50);

        recomecar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println("Botão recomeçar");
                jogo.iniciarNovoJogo(); // Inicia o jogo novamente
                try { // try-catch para excecoes possiveis vindas da utilizacao de audio
                    mostrarTelaInicial();
                } catch (UnsupportedAudioFileException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (LineUnavailableException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        painelFinal.add(recomecar);

        // Botao que oferece a opcao de sair do jogo apos concluido
        JButton sair = new JButton("Sair");
        sair.setFont(new Font("Tahoma", Font.BOLD, 15));
        sair.setBounds(420, 550, 180, 50);

        sair.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println("Botão sair");
                System.exit(0); // A aplicacao eh finalizada
            }
        });

        painelFinal.add(sair);

        // Panel com as estatisticas da partida
        JPanel painelEstatisticas = new JPanel();
        painelEstatisticas.setLayout(null);
        painelEstatisticas.setBounds(100, 450, 850, 200);
        JLabel labelEstatisticas = new JLabel("", JLabel.CENTER);

        // Definindo bordas para o compoundBorder
        Border bevelBorder = new BevelBorder(BevelBorder.LOWERED);
        Border paddingBorder = new EmptyBorder(10, 10, 10, 10);

        // Caso tenhamos somente um jogador, os dados de somente um jogador entao, serao impressos
        if(!jogo.isVersus()){
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

        // Caso tenhamos somente dois jogadores, os dados de ambos serao impressos
        if(jogo.isVersus()){
            // Jogador 1
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

            // Jogador 2
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

    // Metodo responsavel por atualizar a imagem da forca com base na quantidade de tentativas restantes
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





    private void salvarDadosJogador() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("dados_jogadores.txt", true))) {
            writer.println("Nome: " + jogo.getNomeJogador());
            writer.println("Vitórias: " + jogo.getNumVitorias());
            writer.println("Palavras corretas: " + String.join(", ", jogo.getPalavrasCorretas()));
            writer.println(); // linha em branco para separação
        } catch (IOException e) {
            e.printStackTrace();
        }
    }










    // Método principal para executar a aplicação
    public static void main(String[] args) {
        // Inicializacao dos objetos do tipo Jogo
        Jogo jogo = new Jogo();
        Jogo jogador2 = new Jogo();
        // Garante que a GUI será criada no event-dispatching thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new InterfaceGrafica(jogo, jogador2).setVisible(true);
            }
        });
    }
}
