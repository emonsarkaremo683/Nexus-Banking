import dao.UserDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import model.User;
import service.AuthService;


public class Main {
	
	public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException {
		/*
		UserDAO userDAO = new UserDAO();

        User user = userDAO.findByEmail("emon@gmail.com");

		if (user != null) {
			System.out.println(user.getFullName());
			System.out.println(user.getEmail());
			System.out.println(user.getPassword());
		} else {
			System.out.println("User not found");
		}
		
		*/
		DBUtils.ensureDBFolder();
		
		AuthService auth = new AuthService();
        auth.start();
		
    }
    
}
