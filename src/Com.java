import javax.swing.ImageIcon;

public class Com {
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
}
