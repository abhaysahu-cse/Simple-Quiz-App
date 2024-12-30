import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SimpleQuizApp extends JFrame {

    private static final int TIMER_DURATION = 10;  // Time limit for each question in seconds
    private int currentQuestionIndex = 0;
    private int score = 0;
    private int timeLeft = TIMER_DURATION;
    private Timer timer;

    // Question Data (question, options, correct answer)
    String[] questions = {
        "What is the capital of France?",
        "Which planet is known as the Red Planet?"
    };
    String[][] options = {
        {"Berlin", "Madrid", "Paris", "Rome"},
        {"Earth", "Mars", "Venus", "Jupiter"}
    };
    String[] correctAnswers = {
        "Paris", 
        "Mars"
    };

    // GUI Components
    private JLabel questionLabel;
    private JButton[] optionButtons = new JButton[4];
    private JLabel timerLabel;

    public SimpleQuizApp() {
        // Basic JFrame setup
        setTitle("Simple Quiz Application");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Question Label
        questionLabel = new JLabel("", SwingConstants.CENTER);
        questionLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        add(questionLabel, BorderLayout.NORTH);

        // Option Buttons
        JPanel optionsPanel = new JPanel(new GridLayout(4, 1));
        for (int i = 0; i < 4; i++) {
            optionButtons[i] = new JButton();
            optionButtons[i].addActionListener(new OptionButtonListener());
            optionsPanel.add(optionButtons[i]);
        }
        add(optionsPanel, BorderLayout.CENTER);

        // Timer Label
        timerLabel = new JLabel("Time left: " + timeLeft, SwingConstants.CENTER);
        timerLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        add(timerLabel, BorderLayout.SOUTH);

        // Show first question
        showNextQuestion();
    }

    private void showNextQuestion() {
        if (currentQuestionIndex < questions.length) {
            // Update question text and options
            questionLabel.setText(questions[currentQuestionIndex]);
            for (int i = 0; i < 4; i++) {
                optionButtons[i].setText(options[currentQuestionIndex][i]);
            }

            // Reset timer
            timeLeft = TIMER_DURATION;
            timerLabel.setText("Time left: " + timeLeft);

            // Start countdown timer
            startTimer();
        } else {
            showResult();
        }
    }

    private void startTimer() {
        // Timer to decrease the time each second
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeLeft--;
                timerLabel.setText("Time left: " + timeLeft);
                if (timeLeft == 0) {
                    timer.stop();
                    submitAnswer();
                }
            }
        });
        timer.start();
    }

    private void submitAnswer() {
        // Get the selected answer and check if it's correct
        for (JButton button : optionButtons) {
            if (button.getBackground() == Color.YELLOW) {
                if (button.getText().equals(correctAnswers[currentQuestionIndex])) {
                    score++;
                }
                break;
            }
        }

        // Move to the next question
        currentQuestionIndex++;
        showNextQuestion();
    }

    private void showResult() {
        // Show the final score when quiz ends
        JOptionPane.showMessageDialog(this, "Your score: " + score + " out of " + questions.length);
        System.exit(0);
    }

    // Listen for option selection (highlight selected option)
    private class OptionButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton selectedButton = (JButton) e.getSource();
            // Highlight the selected answer
            for (JButton button : optionButtons) {
                button.setBackground(null);
            }
            selectedButton.setBackground(Color.YELLOW);
        }
    }

    public static void main(String[] args) {
        SimpleQuizApp app = new SimpleQuizApp();
        app.setVisible(true);
    }
}