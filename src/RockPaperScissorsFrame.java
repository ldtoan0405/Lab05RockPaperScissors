import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class RockPaperScissorsFrame extends JFrame implements Strategy {
    private JButton rockButton, paperButton, scissorsButton, quitButton;
    private JTextArea resultTextArea;
    private JTextField playerWinsField, computerWinsField, tiesField;

    private int playerWins, computerWins, ties;

    public RockPaperScissorsFrame() {
        setTitle("Rock Paper Scissors Game");
        setLayout(new BorderLayout());

        // Create buttons
        rockButton = new JButton(new ImageIcon("src/rock.png"));
        paperButton = new JButton(new ImageIcon("src/paper.png"));
        scissorsButton = new JButton(new ImageIcon("src/scissors.png"));
        quitButton = new JButton("Quit");

        // Create button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBorder(BorderFactory.createTitledBorder("Choose"));
        buttonPanel.add(rockButton);
        buttonPanel.add(paperButton);
        buttonPanel.add(scissorsButton);
        buttonPanel.add(quitButton);

        // Create stats panel
        JPanel statsPanel = new JPanel(new GridLayout(3, 2));
        statsPanel.setBorder(BorderFactory.createTitledBorder("Stats"));
        statsPanel.add(new JLabel("Player Wins:"));
        playerWinsField = new JTextField("0");
        playerWinsField.setEditable(false);
        statsPanel.add(playerWinsField);
        statsPanel.add(new JLabel("Computer Wins:"));
        computerWinsField = new JTextField("0");
        computerWinsField.setEditable(false);
        statsPanel.add(computerWinsField);
        statsPanel.add(new JLabel("Ties:"));
        tiesField = new JTextField("0");
        tiesField.setEditable(false);
        statsPanel.add(tiesField);

        // Create result panel
        resultTextArea = new JTextArea(10, 20);
        resultTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultTextArea);

        // Add components to the frame
        add(buttonPanel, BorderLayout.NORTH);
        add(statsPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        // Add action listeners
        rockButton.addActionListener(new ButtonClickListener("Rock"));
        paperButton.addActionListener(new ButtonClickListener("Paper"));
        scissorsButton.addActionListener(new ButtonClickListener("Scissors"));
        quitButton.addActionListener(e -> System.exit(0));

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    @Override
    public String determineMove() {
        Random random = new Random();
        int choice = random.nextInt(3);
        switch (choice) {
            case 0: return "Rock";
            case 1: return "Paper";
            case 2: return "Scissors";
            default: return null; // This should never happen
        }
    }

    private class ButtonClickListener implements ActionListener {
        private String playerChoice;

        public ButtonClickListener(String choice) {
            playerChoice = choice;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String computerChoice = determineMove();
            String result = determineResult(playerChoice, computerChoice);

            // Update stats
            if (result.equals("Player wins")) playerWins++;
            else if (result.equals("Computer wins")) computerWins++;
            else ties++;

            // Update fields
            playerWinsField.setText(String.valueOf(playerWins));
            computerWinsField.setText(String.valueOf(computerWins));
            tiesField.setText(String.valueOf(ties));

            // Update text area
            resultTextArea.append(result + " (" + playerChoice + " vs " + computerChoice + ")\n");
        }
    }

    private String determineResult(String playerChoice, String computerChoice) {
        if (playerChoice.equals(computerChoice)) return "Tie";
        if ((playerChoice.equals("Rock") && computerChoice.equals("Scissors")) ||
                (playerChoice.equals("Paper") && computerChoice.equals("Rock")) ||
                (playerChoice.equals("Scissors") && computerChoice.equals("Paper"))) {
            return "Player wins";
        }
        return "Computer wins";
    }
}
