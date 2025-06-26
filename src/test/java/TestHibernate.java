import com.dao.RoleDAO;
import com.service.AuthService;

public class TestHibernate {
    
    public static void main(String[] args) {
        try {
            // Test Hibernate connection
            System.out.println("Testing Hibernate setup...");
            
            // Initialize default roles
            RoleDAO roleDAO = new com.dao.RoleDAO();
            roleDAO.getOrCreateRole("USER", "Regular user role");
            roleDAO.getOrCreateRole("ADMIN", "Administrator role");
            
            // Test user registration
            AuthService authService = new AuthService();
            String token = authService.registerUser(
                "John", 
                "Doe", 
                "johndoe", 
                "john@example.com", 
                "password123", 
                true
            );
            
            System.out.println("User registered successfully!");
            System.out.println("JWT Token: " + token);
            
            // Test user authentication
            String loginToken = authService.authenticateUser("john@example.com", "password123");
            System.out.println("User authenticated successfully!");
            System.out.println("Login Token: " + loginToken);
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            HibernateUtil.shutdown();
        }
    }
}
