package tr.yildiz.ozeralperen17011069;

public class User {
    private Integer id;
    private String name;
    private String surname;
    private String mail;
    private String phone;
    private String bDay;
    private String password;
    private String profilePic;

    public User(String name, String surname, String mail, String phone, String bDay, String password) {
        this.name = name;
        this.surname = surname;
        this.mail = mail;
        this.phone = phone;
        this.bDay = bDay;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMail() {
        return mail;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPhone() {
        return phone;
    }

    public String getbDay() {
        return bDay;
    }

    public String getPassword() {
        return password;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }
}
