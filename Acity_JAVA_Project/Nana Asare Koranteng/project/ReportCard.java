import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ReportCard {
    private JFrame frame;
    private JTextField nameField, rollField;
    private JComboBox<String> courseComboBox;
    private JTextArea resultArea;
    private JButton submitButton, deleteButton, updateButton;
    private JTable recordsTable;
    private DefaultTableModel tableModel;
    private static final int MAX_SUBJECTS = 8;

    private DBConnect dbConnect;

    public ReportCard() {
        dbConnect = new DBConnect();

        frame = new JFrame("Report Card System");
        frame.setSize(1000, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        ((JComponent) frame.getContentPane()).setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Input Panel
        ImagePanel inputPanel = new ImagePanel("background.jpg");
        inputPanel.setLayout(null);
        inputPanel.setPreferredSize(new Dimension(350, 600));
        inputPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JLabel titleLabel = new JLabel("STUDENT DETAILS");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(20, 10, 200, 25);

        JLabel nameLabel = new JLabel("NAME:");
        nameLabel.setBounds(20, 50, 100, 25);
        nameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        nameLabel.setForeground(Color.WHITE);
        nameField = new JTextField(20);
        nameField.setBounds(20, 75, 300, 30);

        JLabel rollLabel = new JLabel("ID:");
        rollLabel.setBounds(20, 115, 100, 25);
        rollLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        rollLabel.setForeground(Color.WHITE);
        rollField = new JTextField(20);
        rollField.setBounds(20, 140, 300, 30);

        JLabel courseLabel = new JLabel("COURSE:");
        courseLabel.setBounds(20, 180, 100, 25);
        courseLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        courseLabel.setForeground(Color.WHITE);
        String[] courses = {"Computer Science", "Information Technology", "Business Administration"};
        courseComboBox = new JComboBox<>(courses);
        courseComboBox.setBounds(20, 205, 300, 30);
        courseComboBox.setBackground(Color.WHITE);

        submitButton = new JButton("GENERATE REPORT");
        submitButton.setBounds(20, 260, 300, 35);
        submitButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        submitButton.setForeground(Color.WHITE);
        submitButton.setBackground(new Color(52, 152, 219));
        submitButton.setFocusPainted(false);

        deleteButton = new JButton("DELETE SELECTED");
        deleteButton.setBounds(20, 310, 300, 35);
        deleteButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setBackground(new Color(231, 76, 60));
        deleteButton.setFocusPainted(false);

        updateButton = new JButton("UPDATE SELECTED");
        updateButton.setBounds(20, 360, 300, 35);
        updateButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        updateButton.setForeground(Color.WHITE);
        updateButton.setBackground(new Color(46, 204, 113));
        updateButton.setFocusPainted(false);

        inputPanel.add(titleLabel);
        inputPanel.add(nameLabel);
        inputPanel.add(nameField);
        inputPanel.add(rollLabel);
        inputPanel.add(rollField);
        inputPanel.add(courseLabel);
        inputPanel.add(courseComboBox);
        inputPanel.add(submitButton);
        inputPanel.add(deleteButton);
        inputPanel.add(updateButton);

        // Result Panel
        JPanel resultPanel = new JPanel(new BorderLayout());
        resultPanel.setBorder(BorderFactory.createTitledBorder("Student Report"));

        // Increased size of JTextArea for larger report view
        resultArea = new JTextArea(20, 30);  // Increased height from 10 to 20 rows
        resultArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        resultArea.setEditable(false);
        resultArea.setBackground(Color.WHITE);
        resultPanel.add(new JScrollPane(resultArea), BorderLayout.NORTH);

        // Table Panel
        String[] columnNames = {"Name", "ID", "Average", "Course", "Remarks"};
        tableModel = new DefaultTableModel(columnNames, 0);
        recordsTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(recordsTable);
        resultPanel.add(tableScrollPane, BorderLayout.CENTER);

        frame.add(inputPanel, BorderLayout.WEST);
        frame.add(resultPanel, BorderLayout.CENTER);

        submitButton.addActionListener(e -> generateReport());
        deleteButton.addActionListener(e -> deleteSelectedRecord());
        updateButton.addActionListener(e -> updateRecord());

        loadRecordsFromDatabase();

        frame.setVisible(true);
    }

    private void generateReport() {
        try {
            String name = nameField.getText();
            int roll = Integer.parseInt(rollField.getText());
            String course = (String) courseComboBox.getSelectedItem();
            float total = 0;
            String[] subjects = new String[MAX_SUBJECTS];

            if (course.equals("Computer Science")) {
                subjects = new String[]{"Programming in C++\t", "Data Structures\t\t", "Database Systems\t", "Operations Research\t",
                        "Calculus & Matlab\t", "Leadership Seminar\t", "Logic and Design\t", "F.I.E\t\t\t"};
            } else if (course.equals("Information Technology")) {
                subjects = new String[]{"Business in Africa\t", "Web Development\t\t", "System Administration\t", "Database Systems\t",
                        "Cybersecurity\t\t", "Leadership Seminar\t", "Data Structures\t\t", "F.I.E\t\t\t"};
            } else {
                subjects = new String[]{"Management Principles\t", "Marketing\t\t", "Accounting\t\t", "Business Law\t\t",
                        "Economics\t\t", "Leadership Seminar\t", "I.P. Law\t\t", "F.I.E\t\t\t"};
            }

            StringBuilder report = new StringBuilder();
            report.append("\n--------- Report Card ---------\n");
            report.append("Name       : ").append(name).append("\n");
            report.append("Roll Number: ").append(roll).append("\n");
            report.append("Course     : ").append(course).append("\n\n");

            for (int i = 0; i < MAX_SUBJECTS; i++) {
                float classScore = Float.parseFloat(JOptionPane.showInputDialog("Enter class score for " + subjects[i] + " (out of 40): "));
                while (classScore < 0 || classScore > 40) {
                    classScore = Float.parseFloat(JOptionPane.showInputDialog("Invalid score! Please enter a value between 0 and 40: "));
                }

                float examScore = Float.parseFloat(JOptionPane.showInputDialog("Enter exam score for " + subjects[i] + " (out of 60): "));
                while (examScore < 0 || examScore > 60) {
                    examScore = Float.parseFloat(JOptionPane.showInputDialog("Invalid score! Please enter a value between 0 and 60: "));
                }

                float subjectTotal = classScore + examScore;
                total += subjectTotal;
                char grade = calculateGrade(subjectTotal);

                report.append(subjects[i]).append(" : ").append(subjectTotal).append(" | Grade: ").append(grade).append("\n");
            }

            float average = total / MAX_SUBJECTS;
            String remarks = getRemarks(average);

            report.append("\nTotal Marks: ").append(total).append("\n");
            report.append("CWA        : ").append(average).append("\n");
            report.append("Remarks    : ").append(remarks).append("\n");

            resultArea.setText(report.toString());
            dbConnect.insertData(name, roll, average, course, remarks);
            loadRecordsFromDatabase();
            JOptionPane.showMessageDialog(frame, "Report Card Generated & Saved to Database Successfully!");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadRecordsFromDatabase() {
        tableModel.setRowCount(0);
        try (ResultSet rs = dbConnect.fetchAllRecords()) {
            while (rs.next()) {
                Object[] row = {
                        rs.getString("name"),
                        rs.getInt("rollnum"),
                        rs.getFloat("average"),
                        rs.getString("course"),
                        rs.getString("remarks")
                };
                tableModel.addRow(row);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Error loading records: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteSelectedRecord() {
        int selectedRow = recordsTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Please select a record to delete.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure you want to delete this record?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            int rollnum = (int) tableModel.getValueAt(selectedRow, 1);
            dbConnect.deleteRecordByRollNum(rollnum);
            tableModel.removeRow(selectedRow);
            JOptionPane.showMessageDialog(frame, "Record deleted successfully.");
        }
    }

    private void updateRecord() {
        int selectedRow = recordsTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Please select a record to update.");
            return;
        }

        int rollnum = (int) tableModel.getValueAt(selectedRow, 1); // Get current roll number
        String name = JOptionPane.showInputDialog(frame, "Enter new name:", tableModel.getValueAt(selectedRow, 0));
        String course = JOptionPane.showInputDialog(frame, "Enter new course:", tableModel.getValueAt(selectedRow, 3));

        // Only allow changing the ID
        int newRollnum = Integer.parseInt(JOptionPane.showInputDialog(frame, "Enter new ID:", rollnum)); // Allow user to update ID

        String remarks = JOptionPane.showInputDialog(frame, "Enter new remarks:", tableModel.getValueAt(selectedRow, 4));

        // Update record with new ID, keeping the average, course, and remarks same
        dbConnect.updateRecord(newRollnum, name, course, remarks);

        // Update table with new values
        tableModel.setValueAt(name, selectedRow, 0);
        tableModel.setValueAt(course, selectedRow, 3);
        tableModel.setValueAt(newRollnum, selectedRow, 1);  // Update ID in the table
        tableModel.setValueAt(remarks, selectedRow, 4);

        JOptionPane.showMessageDialog(frame, "Record updated successfully.");
    }

    private char calculateGrade(float subjectTotal) {
        if (subjectTotal >= 90) return 'A';
        else if (subjectTotal >= 75) return 'B';
        else if (subjectTotal >= 50) return 'C';
        else return 'D';
    }

    private String getRemarks(float average) {
        if (average >= 90) return "Excellent performance. More vim.";
        else if (average >= 75) return "Good job. You force.";
        else if (average >= 50) return "You try. There's room for improvement.";
        else return "Needs improvement. Don't give up.";
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ReportCard());
    }
}

// Custom JPanel to paint background image
class ImagePanel extends JPanel {
    private Image backgroundImage;

    public ImagePanel(String imagePath) {
        try {
            backgroundImage = new ImageIcon(imagePath).getImage();
        } catch (Exception e) {
            System.out.println("Image not found: " + imagePath);
        }
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
        super.paintComponent(g);
    }
}
