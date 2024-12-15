package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class UserView extends JFrame {
    private final JTextField txtName = new JTextField(20);
    private final JTextField txtEmail = new JTextField(20);
    private final JButton btnAdd = new JButton("Add User");
    private final JButton btnRefresh = new JButton("Refresh");
    private final JList<String> userList = new JList<>();
    private final DefaultListModel<String> listModel = new DefaultListModel<>();


    public UserView() {
        setTitle("User Management");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(5, 1));
        panel.add(new JLabel("Name:"));
        panel.add(txtName);
        panel.add(new JLabel("Email:"));
        panel.add(txtEmail);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnRefresh);
        panel.add(buttonPanel);

        // Setup JList with DefaultListModel
        userList.setModel(listModel);
        userList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(userList);

        add(panel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    public String getNameInput() {
        return txtName.getText();
    }

    public String getEmailInput() {
        return txtEmail.getText();
    }

    // Method to update the JList with the users array
    public void setUserList(String[] users) {
        listModel.clear();  // Clear the current list before adding new data
        for (String user : users) {
            listModel.addElement(user);  // Add each user to the JList
        }
    }

    public void addAddUserListener(ActionListener listener) {
        btnAdd.addActionListener(listener);
    }

    public void addRefreshListener(ActionListener listener) {
        btnRefresh.addActionListener(listener);
    }
}