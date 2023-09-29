import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToeLogik implements ActionListener {
    public TicTacToeLogik() {

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
                new TicTacToeLogik();
            } else
                System.exit(0);

        // sieger küren
        if (sieger == "X" || sieger == "O")
            if (JOptionPane.showConfirmDialog(this, sieger + " WINS!!", "Play again?",
                    JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == 0) {
                dispose();
                new TicTacToeLogik();
            } else
                System.exit(0);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }

}