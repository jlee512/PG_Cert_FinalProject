package login.system.dao;

import java.io.Serializable;

/**
 * Created by jlee512 on 29/05/2017.
 */
public class User implements Serializable {

    private int user_id;
    private String username;
    private byte[] hash;
    private byte[] salt;
    private int iterations;
    private String email;
    private String phone;
    private String occupation;
    private String city;
    private String profile_description;
    private String profile_picture;
    private String firstname;
    private String lastname;

    public User(String username, byte[] pwHash, byte[] salt, int iterations, String email, String phone, String occupation, String city, String profile_description, String profile_picture, String firstname, String lastname) {
        this.username = username;
        this.hash = pwHash;
        this.salt = salt;
        this.iterations = iterations;
        this.email = email;
        this.phone = phone;
        this.occupation = occupation;
        this.city = city;
        this.profile_description = profile_description;
        this.profile_picture = profile_picture;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public User(String username, String email, String phone, String occupation, String city, String profile_description, String firstname, String lastname) {
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.occupation = occupation;
        this.city = city;
        this.profile_description = profile_description;
        this.firstname = firstname;
        this.lastname = lastname;
    }


    public String getUsername() {
        return username;
    }

    public byte[] getHash() {
        return hash;
    }

    public byte[] getSalt() {
        return salt;
    }

    public int getIterations() {
        return iterations;
    }

    public String getEmail() {
        return email;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getPhone() {
        return phone;
    }

    public String getOccupation() {
        return occupation;
    }

    public String getCity() {
        return city;
    }

    public String getProfile_description() {
        return profile_description;
    }

    public String getProfile_picture() {
        return profile_picture;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setHash(byte[] hash) {
        this.hash = hash;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    public void setIterations(int iterations) {
        this.iterations = iterations;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setProfile_description(String profile_description) {
        this.profile_description = profile_description;
    }

    public void setProfile_picture(String profile_picture) {
        this.profile_picture = profile_picture;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setUserParameters(int user_id, String username, byte[] hash, byte[] salt, int iterations, String email, String phone, String occupation, String city, String profile_description, String profile_picture, String firstname, String lastname) {
        this.user_id = user_id;
        this.username = username;
        this.hash = hash;
        this.salt = salt;
        this.iterations = iterations;
        this.email = email;
        this.phone = phone;
        this.occupation = occupation;
        this.city = city;
        this.profile_description = profile_description;
        this.profile_picture = profile_picture;
        this.firstname = firstname;
        this.lastname = lastname;

    }
}
