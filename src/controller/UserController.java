package controller;

import model.MyBatisUtil;
import model.User;
import model.UserMapper;
import org.apache.ibatis.session.SqlSession;
import view.UserView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class UserController {
    private UserView view;
    private UserMapper mapper;

    public UserController(UserView view, UserMapper mapper) {
        this.view = view;
        this.mapper = mapper;

        this.view.addAddUserListener(new AddUserListener());
        this.view.addRefreshListener(new RefreshListener());
    }

    class AddUserListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = view.getNameInput();
            String email = view.getEmailInput();

            if (!name.isEmpty() && !email.isEmpty()) {
                User user = new User();
                user.setName(name);
                user.setEmail(email);

                // Membuka sesi MyBatis untuk operasi database
                SqlSession session = MyBatisUtil.getSqlSession();
                try {
                    // Mendapatkan mapper dari sesi
                    UserMapper mapper = session.getMapper(UserMapper.class);

                    // Menyisipkan data ke dalam database
                    mapper.insertUser(user);

                    // Melakukan commit transaksi untuk memastikan data disimpan
                    session.commit();

                    // Menampilkan pesan sukses
                    JOptionPane.showMessageDialog(view, "User added successfully!");
                } catch (Exception ex) {
                    // Jika terjadi kesalahan, rollback transaksi
                    session.rollback();
                    System.out.println(ex.getMessage());
                    JOptionPane.showMessageDialog(view, "Error while adding user!");
                } finally {
                    // Menutup sesi setelah operasi selesai
                    session.close();
                }
            } else {
                JOptionPane.showMessageDialog(view, "Please fill in all fields.");
            }
        }
    }

    class RefreshListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            List<User> users = mapper.getAllUsers();
            String[] userArray = users.stream()
                    .map(u -> u.getName() + " (" + u.getEmail() + ")")
                    .toArray(String[]::new);

            view.setUserList(userArray);
        }
    }
}