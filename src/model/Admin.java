package model;



public class Admin extends User {
    public Admin(int id, String fullName, String email, String password) {
        super(id, fullName, email, password, "ADMIN","ACTIVE");
    }
}
