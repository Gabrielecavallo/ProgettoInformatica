import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class CalisthenicsTracker extends JFrame {

    String[] esercizi = {"Pull Up", "Dip", "Push Up", "Muscle Up"};
    JTextField[] repFields = new JTextField[4];
    JCheckBox[] checkBoxes = new JCheckBox[4];
    String fileName = "allenamento.csv";

    public CalisthenicsTracker() {
        setTitle("Calisthenics Workout Tracker");
        setSize(400, 300);
        setLayout(new GridLayout(6, 1)); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        for (int i = 0; i < esercizi.length; i++) {
            JPanel panel = new JPanel(new FlowLayout());
            JLabel label = new JLabel(esercizi[i] + ": ");
            repFields[i] = new JTextField(5);
            checkBoxes[i] = new JCheckBox("Completato");

            panel.add(label);
            panel.add(repFields[i]);
            panel.add(checkBoxes[i]);
            add(panel);
        }

        JButton saveButton = new JButton("Salva Progressi");
        saveButton.addActionListener(e -> salvaDati());
        add(saveButton);

        caricaDati();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                salvaDati();
            }
        });

        setVisible(true);
    }

    private void salvaDati() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (int i = 0; i < esercizi.length; i++) {
                String reps = repFields[i].getText().isEmpty() ? "0" : repFields[i].getText();
                boolean isDone = checkBoxes[i].isSelected();
                bw.write(esercizi[i] + "," + reps + "," + isDone);
                bw.newLine();
            }
            System.out.println("Dati salvati correttamente!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void caricaDati() {
        File file = new File(fileName);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            int i = 0;
            while ((line = br.readLine()) != null && i < esercizi.length) {
                String[] data = line.split(",");
                if (data.length == 3) {
                    repFields[i].setText(data[1]);
                    checkBoxes[i].setSelected(Boolean.parseBoolean(data[2]));
                }
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new CalisthenicsTracker();
    }
}
