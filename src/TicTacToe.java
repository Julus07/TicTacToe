import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToe extends JFrame implements ActionListener {
    private JRadioButton startingPlayerCOM, startingPlayerPlayer, easy, normal, hard, oneVsOne, oneVsCOM;
    private ButtonGroup startingPlayerButtonGroup, difficultyButtonGroup, modusButtonGroup;
    private JLabel anleitung, zuege, startspieler, difficultyJLabel, spielmodusJLabel, X, O;
    private JPanel board, infos, settings;
    private JButton start;
    private JButton[][] cells;

    // Globale Variablen
    private boolean gameStarted = false; // true -> board actions can be performed
    private String nextSymbol = "X"; // symbol, that would be set next
    private int zug = 0; // 0 -> "-" | 1 ... 9
    private int modus = 0; // 0 -> 1 vs. 1 | 1 -> 1 vs. COM
    private boolean nextPlayer = true; // true -> Player ist dran | false -> COM startet
    private int difficulty = 1; // 0 -> easy | 1 -> normal | 2 -> hard
    private String sieger = "-"; // - -> platzhalter für sieger

    public TicTacToe() {
        Gui();
    }

    public void Gui() {
        setTitle("TicTacToe");
        setIconImage(new ImageIcon("src/images/icon.png").getImage());
        setContentPane(new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(Toolkit.getDefaultToolkit().getImage("src/images/background.png"), 0, 0, 1920, 1080, null);
            }
        });
        setLayout(null);
        setSize(1920, 1080);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Infos-Panel oben links
        infos = new JPanel(null);
        infos.setBounds(25, 25, 500, 500);
        infos.setBackground(new Color(0xCECECE));
        // Label mit Spielanleitung
        anleitung = new JLabel(
                "<html><body>1. Spieler X fängt an<br>2. Man spielt abwechselnd<br>3. Gewonnen hat, wer eine Reihe seiner Symbole voll hat</body></html>",
                new ImageIcon("src/images/tictactoe.png"), JLabel.LEFT);
        anleitung.setBounds(0, -10, 500, 250);
        anleitung.setVerticalTextPosition(JLabel.CENTER);
        anleitung.setFont(new Font("MV Boli", Font.PLAIN, 20));
        anleitung.setBorder(BorderFactory.createLineBorder(new Color(0xCECECE), 15));
        infos.add(anleitung);

        // Label für X (ob dran oder nicht)
        X = new JLabel(new ImageIcon("src/images/nextX.png"));
        X.setBounds(50, 300, 150, 150);
        infos.add(X);
        // Label für O (ob dran oder nicht)
        O = new JLabel(new ImageIcon("src/images/lastO.png"));
        O.setBounds(300, 300, 150, 150);
        infos.add(O);

        // Settings-Panel oben rechts
        settings = new JPanel(null);
        settings.setBounds(1520, 0, 400, 200);
        settings.setBackground(Color.white);
        // Start-Button
        start = new JButton(new ImageIcon("src/images/start.png"));
        start.setBounds(0, 0, 200, 200);
        start.addActionListener(this);
        settings.add(start);
        // Modus
        spielmodusJLabel = new JLabel("Modus");
        spielmodusJLabel.setHorizontalAlignment(JLabel.CENTER);
        spielmodusJLabel.setBounds(200, 0, 200, 34);
        settings.add(spielmodusJLabel);
        modusButtonGroup = new ButtonGroup();
        oneVsOne = new JRadioButton("1 vs. 1", true);
        oneVsOne.setBounds(200, 34, 100, 33);
        oneVsOne.setFocusable(false);
        oneVsOne.addActionListener(this);
        modusButtonGroup.add(oneVsOne);
        settings.add(oneVsOne);
        oneVsCOM = new JRadioButton("1 vs. COM");
        oneVsCOM.setBounds(300, 34, 100, 33);
        oneVsCOM.setFocusable(false);
        oneVsCOM.addActionListener(this);
        modusButtonGroup.add(oneVsCOM);
        settings.add(oneVsCOM);
        // Difficulty
        difficultyJLabel = new JLabel("Difficulty", JLabel.CENTER);
        difficultyJLabel.setBounds(200, 67, 200, 34);
        settings.add(difficultyJLabel);
        difficultyButtonGroup = new ButtonGroup();
        easy = new JRadioButton("easy");
        easy.setBounds(200, 101, 67, 33);
        easy.setEnabled(false);
        easy.setFocusable(false);
        easy.addActionListener(this);
        settings.add(easy);
        difficultyButtonGroup.add(easy);
        normal = new JRadioButton("normal", true);
        normal.setBounds(267, 101, 66, 33);
        normal.setEnabled(false);
        normal.setFocusable(false);
        normal.addActionListener(this);
        settings.add(normal);
        difficultyButtonGroup.add(normal);
        hard = new JRadioButton("hard");
        hard.setBounds(333, 101, 67, 33);
        hard.setEnabled(false);
        hard.setFocusable(false);
        hard.addActionListener(this);
        settings.add(hard);
        difficultyButtonGroup.add(hard);
        // Startspieler
        startspieler = new JLabel("Startspieler", JLabel.CENTER);
        startspieler.setBounds(200, 134, 200, 33);
        settings.add(startspieler);
        startingPlayerButtonGroup = new ButtonGroup();
        startingPlayerCOM = new JRadioButton("COM");
        startingPlayerCOM.setBounds(200, 167, 100, 33);
        startingPlayerCOM.setEnabled(false);
        startingPlayerCOM.setFocusable(false);
        startingPlayerCOM.addActionListener(this);
        settings.add(startingPlayerCOM);
        startingPlayerButtonGroup.add(startingPlayerCOM);
        startingPlayerPlayer = new JRadioButton("Spieler", true);
        startingPlayerPlayer.setBounds(300, 167, 100, 33);
        startingPlayerPlayer.setEnabled(false);
        startingPlayerPlayer.setFocusable(false);
        startingPlayerPlayer.addActionListener(this);
        settings.add(startingPlayerPlayer);
        startingPlayerButtonGroup.add(startingPlayerPlayer);

        zuege = new JLabel("Zug -", JLabel.CENTER);
        zuege.setBounds(660, 25, 600, 200);
        zuege.setFont(new Font("Arial", Font.BOLD, 100));
        zuege.setForeground(Color.white);

        // Spielbrett
        board = new JPanel(null);
        board.setBounds(660, 240, 600, 600);
        board.setBackground(Color.gray);

        // Buttons auf Spielbrett
        cells = new JButton[3][3];
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                cells[row][col] = new JButton("");
                cells[row][col].setBounds(5 + row * 200, 5 + col * 200, 190, 190);
                cells[row][col].setBackground(Color.white);
                cells[row][col].setEnabled(false);
                cells[row][col].setFocusable(false);
                cells[row][col].addActionListener(this);
                board.add(cells[row][col]);
            }
        }
        add(infos);
        add(settings);
        add(zuege);
        add(board);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // modus verändern
        if (e.getSource() == oneVsOne) {
            modus = 0;
            easy.setEnabled(false);
            normal.setEnabled(false);
            hard.setEnabled(false);
            startingPlayerCOM.setEnabled(false);
            startingPlayerPlayer.setEnabled(false);
        }
        if (e.getSource() == oneVsCOM) {
            modus = 1;
            easy.setEnabled(true);
            normal.setEnabled(true);
            hard.setEnabled(true);
            startingPlayerCOM.setEnabled(true);
            startingPlayerPlayer.setEnabled(true);
        }

        // currentPlayerverändern
        if (e.getSource() == startingPlayerPlayer)
            nextPlayer = true;
        if (e.getSource() == startingPlayerCOM)
            nextPlayer = false;

        // difficulty verändern
        if (e.getSource() == easy)
            difficulty = 0;
        if (e.getSource() == normal)
            difficulty = 1;
        if (e.getSource() == hard)
            difficulty = 2;

        if (e.getSource() == start) {
            // stopping game
            if (gameStarted) {
                if (JOptionPane.showConfirmDialog(this, "Are you sure you want to quit the game?", "WARNING",
                        JOptionPane.WARNING_MESSAGE, JOptionPane.WARNING_MESSAGE) == 0)
                    System.exit(0);
            }
            // starting game
            else {
                gameStarted = true;
                zuege.setText("Zug " + ++zug);
                start.setIcon(new ImageIcon("src/images/stop.png"));
                oneVsOne.setEnabled(false);
                oneVsCOM.setEnabled(false);
                easy.setEnabled(false);
                normal.setEnabled(false);
                hard.setEnabled(false);
                startingPlayerCOM.setEnabled(false);
                startingPlayerPlayer.setEnabled(false);
                for (int row = 0; row < 3; row++)
                    for (int col = 0; col < 3; col++)
                        cells[row][col].setEnabled(true);
                if (nextPlayer == false)
                    zugCOM();
            }
        }

        // board action
        // 1 vs. 1
        for (int row = 0; row < 3; row++)
            for (int col = 0; col < 3; col++)
                if (e.getSource() == cells[row][col]) {
                    zuege.setText("Zug " + ++zug);
                    cells[row][col].setText(nextSymbol);
                    cells[row][col].setIcon(new ImageIcon("src/images/" + nextSymbol + ".png"));
                    cells[row][col].setEnabled(false);
                    nextPlayer = false;
                    if (nextSymbol == "X") {
                        X.setIcon(new ImageIcon("src/images/lastX.png"));
                        O.setIcon(new ImageIcon("src/images/nextO.png"));
                        nextSymbol = "O";
                    } else if (nextSymbol == "O") {
                        O.setIcon(new ImageIcon("src/images/lastO.png"));
                        X.setIcon(new ImageIcon("src/images/nextX.png"));
                        nextSymbol = "X";
                    }
                    winTester();
                    // 1 vs. COM
                    if (modus == 1)
                        zugCOM();
                }
    }

    public void zugCOM() {
        while (nextPlayer == false) {
            // difficulty easy
            if (difficulty == 0) {
                int x = (int) Math.round(Math.random() * (2 - 0));
                int y = (int) Math.round(Math.random() * (2 - 0));
                if (cells[x][y].getText() == "") {
                    zuege.setText("Zug " + ++zug);
                    cells[x][y].setText(nextSymbol);
                    cells[x][y].setIcon(new ImageIcon("src/images/" + nextSymbol + ".png"));
                    cells[x][y].setEnabled(false);
                    if (nextSymbol == "X") {
                        X.setIcon(new ImageIcon("src/images/lastX.png"));
                        O.setIcon(new ImageIcon("src/images/nextO.png"));
                        nextSymbol = "O";
                    } else if (nextSymbol == "O") {
                        O.setIcon(new ImageIcon("src/images/lastO.png"));
                        X.setIcon(new ImageIcon("src/images/nextX.png"));
                        nextSymbol = "X";
                    }
                    nextPlayer = true;
                }
            }
            // difficulty normal
            if (difficulty == 1) {
            }

            // difficulty hard
            if (difficulty == 2) {
            }
        }
        winTester();
    }

    public void winTester() {
        String lastSymbol = "";

        if (nextSymbol == "X")
            lastSymbol = "O";
        else if (nextSymbol == "O")
            lastSymbol = "X";

        // auf Sieg testen
        for (int row = 0; row < 3; row++)
            if (cells[row][0].getText() == lastSymbol && cells[row][1].getText() == lastSymbol
                    && cells[row][2].getText() == lastSymbol)
                sieger = lastSymbol;
        for (int col = 0; col < 3; col++)
            if (cells[0][col].getText() == lastSymbol && cells[1][col].getText() == lastSymbol
                    && cells[2][col].getText() == lastSymbol)
                sieger = lastSymbol;
        if ((cells[0][0].getText() == lastSymbol && cells[1][1].getText() == lastSymbol
                && cells[2][2].getText() == lastSymbol)
                || (cells[2][0].getText() == lastSymbol && cells[1][1].getText() == lastSymbol
                        && cells[0][2].getText() == lastSymbol))
            sieger = lastSymbol;

        // draw
        if (zug == 10)
            if (JOptionPane.showConfirmDialog(this, "It's a draw!", "Play again?",
                    JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == 0) {
                dispose();
                new TicTacToe();
            } else
                System.exit(0);

        // sieger küren
        if (sieger == "X" || sieger == "O")
            if (JOptionPane.showConfirmDialog(this, sieger + " WINS!!", "Play again?",
                    JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == 0) {
                dispose();
                new TicTacToe();
            } else
                System.exit(0);
    }

    public static void main(String[] args) {
        new TicTacToe();
    }
}