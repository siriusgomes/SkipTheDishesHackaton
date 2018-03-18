package br.hackathon.skipthedishes.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import br.hackathon.skipthedishes.model.User;

@Service("userService")
public class UserServiceImpl implements UserService {

	static Connection conn;

	static {

		try {
			Class.forName("org.h2.Driver");
			conn = DriverManager.getConnection("jdbc:h2:~/test", "sa", "");

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<User> findAllUsers() {
		List<User> listUsers = new ArrayList<User>();
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("select * from users");
			while (rs.next()) {

				int id_user = rs.getInt("id_user");
				String name = rs.getString("name");
				String login = rs.getString("login");
				String password = rs.getString("password");
				listUsers.add(new User(id_user, name, login, password));

			}
			rs.close();
			st.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listUsers;
	}

	public User findById(long id_user_query) {
		User user = null;
		try {
			PreparedStatement st = conn.prepareStatement("select * from users where id_user = ?");
			st.setLong(1, id_user_query);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {

				int id_user = rs.getInt("id_user");
				String name = rs.getString("name");
				String login = rs.getString("login");
				String password = rs.getString("password");
				user = new User(id_user, name, login, password);

			}
			rs.close();
			st.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}

	

	public User findByName(String name_query) {
		User user = null;
		try {
			PreparedStatement st = conn.prepareStatement("select * from users where name = ?");
			st.setString(1, name_query);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {

				int id_user = rs.getInt("id_user");
				String name = rs.getString("name");
				String login = rs.getString("login");
				String password = rs.getString("password");
				user = new User(id_user, name, login, password);

			}
			rs.close();
			st.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}
	
	public void saveUser(User user) { 
		try {
			PreparedStatement st = conn.prepareStatement("insert into users (name, login, password) values  (?,?,?)");
			st.setString(1, user.getName());
			st.setString(2, user.getLogin());
			st.setString(3, user.getPassword());
			st.execute();
			st.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void updateUser(User user) {
		try {
			PreparedStatement st = conn.prepareStatement("update users set name = ?, login = ?, password = ? where id_user = ?");
			st.setString(1, user.getName());
			st.setString(2, user.getLogin());
			st.setString(3, user.getPassword());
			st.setLong(4, user.getId_user());
			st.execute();
			st.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void deleteUserById(long id) {
		try {
			PreparedStatement st = conn.prepareStatement("delete from users where id_user = ?");
			st.setLong(1, id);
			st.execute();
			st.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean isUserExist(User user) {
		return findByName(user.getName()) != null;
	}

	@Override
	public void deleteAllUsers() {
		try {
			PreparedStatement st = conn.prepareStatement("delete from users");
			st.execute();
			st = conn.prepareStatement("ALTER TABLE users ALTER COLUMN id_user RESTART WITH 1");
			st.execute();
			st.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
