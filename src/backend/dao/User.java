package backend.dao;

import java.io.Serializable;

/**
 * Created by jlee512 on 29/05/2017.
 */

/**
 * The User class stores the backend representation of an user object.
 *
 * User can register/login/be-deleted/comment/post-multimedia/post-articles and are generally the core around which the application is built.
 *
 */

public class User implements Serializable {

    /*User instance variables (extended user profile)*/
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

    /*---------------------------------------------
            User Constructors
------------------------------------------------*/

    /*Constructor used when creating new users or looking up users*/
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

    /*Constructor used when updating an existing user's details*/
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
    /*----------------------------------------------------------------*/

    /*Used in the UserDAO to set all the parameters for a User instance which has been sucessfully looked up in the database */

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

    /**
     * @param username represents the unique text used to identify a given registered user
     */

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }


    /**
     * @param hash represents the unique hashed password for a given user
     */

    public void setHash(byte[] hash) {
        this.hash = hash;
    }

    public byte[] getHash() {
        return hash;
    }


    /**
     * @param salt represents the salt used in generating the hashed password for a given user
     */

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    public byte[] getSalt() {
        return salt;
    }

    /**
     * @param iterations represents the number of iterations used in generating the hashed password for a given user
     */

    public void setIterations(int iterations) {
        this.iterations = iterations;
    }

    public int getIterations() {
        return iterations;
    }

    /**
     * @param email represents a registered user's email address
     */

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }


    /**
     * @param user_id represents the auto-increment id assigned in the database to a given user
     */

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getUser_id() {
        return user_id;
    }


    /**
     * @param phone represents a regisetered user's phone number
     */

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }


    /**
     * @param occupation represents a registered user's occupation
     */

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getOccupation() {
        return occupation;
    }


    /**
     * @param city represents a registered user's city
     */

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }


    /**
     * @param profile_description represents the description entered by a user to describe themselves
     */

    public void setProfile_description(String profile_description) {
        this.profile_description = profile_description;
    }

    public String getProfile_description() {
        return profile_description;
    }


    /**
     * @param profile_picture represents a filepath to from the root directory to a given user's profile picture
     */

    public void setProfile_picture(String profile_picture) {
        this.profile_picture = profile_picture;
    }

    public String getProfile_picture() {
        return profile_picture;
    }


    /**
     * @param firstname represents a given user's firstname
     */

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getFirstname() {
        return firstname;
    }

    /**
     * @param lastname represents a given user's lastname
     */

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getLastname() {
        return lastname;
    }

    /*------------------------------*/
    /*End of Class*/
    /*------------------------------*/

}
