import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class TicTacToeGame {
    Position position = new Position();
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("Java TicTacToe");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLayout(new GridLayout(3,3));
                final TicTacToeGame game = new TicTacToeGame();
                final JButton[] buttons = new JButton[9];

                //Creates the buttons and set their functionality
                for (int i = 0; i < 9; i++) {
                    final int idx = i;
                    final JButton button = new JButton();
                    buttons[i] = button;
                    button.setPreferredSize(new Dimension(100,100));
                    button.setBackground(Color.WHITE);
                    button.setOpaque(true);
                    button.setFont(new Font(null, Font.PLAIN, 100));
                    button.addMouseListener(new MouseListener() {
                        // These are unnecessary but needed for the MouseListener to function
                        public void mousePressed(MouseEvent e) {}
                        public void mouseReleased(MouseEvent e) {}
                        public void mouseEntered(MouseEvent e) {}
                        public void mouseExited(MouseEvent e) {}

                        @Override
                        public void mouseClicked(MouseEvent e) {
                            button.setText("" + game.position.turn);
                            game.move(idx);
                            if (!game.position.gameEnd()){
                                int best = game.position.bestMove();
                                buttons[best].setText("" + game.position.turn);
                                game.move(best);
                            }
                            if (game.position.gameEnd()) {
                                String message = "";
                                if (game.position.win('x')) {
                                    message = "You won!";
                                } else if (game.position.win('o')){
                                    message = "Computer won!";
                                } else {
                                    message = "Draw";
                                }
                                JOptionPane.showMessageDialog(null, message);
                            }
                        }
                    });
                    frame.add(button);
                }
                frame.pack();
                frame.setVisible(true);
            }
        });
    }

    // Go around for the move function
    private void move(int idx) {
        position = position.move(idx);
    }
}
