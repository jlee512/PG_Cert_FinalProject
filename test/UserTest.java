/**
 * Created by jlee512 on 2/06/2017.
 */

import login.system.dao.User;
import login.system.dao.UserDAO;
import login.system.db.MySQL;
import login.system.passwords.Passwords;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static junit.framework.TestCase.*;
import static org.junit.Assert.assertArrayEquals;

/*Fix test order*/
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserTest {

    MySQL DB = new MySQL();
    /*Tests have been designed to run sequentially (i.e. add a user and check their login)*/
    static User testUser = UserGenerator.generateRandomUser();
    static User testUser2 = UserGenerator.generateRandomUser();
    User knownExistingUser;

    @Before
    public void setUp() {
        knownExistingUser = UserGenerator.getKnownUser();
        
    }

    @Test
    public void test1UserAdditionToDB(){

        /*Check for successful database addition code of "1"*/
        assertEquals(1, UserDAO.addUserToDB(DB, testUser.getUsername(), testUser.getIterations(), testUser.getSalt(), testUser.getHash(), testUser.getEmail(), testUser.getPhone(), testUser.getOccupation(), testUser.getCity(), testUser.getProfile_description(), testUser.getProfile_picture(), testUser.getFirstname(), testUser.getLastname()));

    }

    @Test
    public void test2UserRejectionFromDBDuplicateUsername() {

        assertEquals(2, UserDAO.addUserToDB(DB, knownExistingUser.getUsername(), knownExistingUser.getIterations(), knownExistingUser.getSalt(), knownExistingUser.getHash(), knownExistingUser.getEmail(), knownExistingUser.getPhone(), knownExistingUser.getOccupation(), knownExistingUser.getCity(), knownExistingUser.getProfile_description(), knownExistingUser.getProfile_picture(), knownExistingUser.getFirstname(), knownExistingUser.getLastname()));

    }

    @Test
    public void test3UserAccessFromDB(){

        /*Pull the user from the database by their username*/
        testUser = UserDAO.getUser(DB, knownExistingUser.getUsername());

        //Lookup user by username and access the parameters returned by the MySQL database and confirm they are not null
        assertEquals(knownExistingUser.getUsername(), testUser.getUsername());
        assertArrayEquals(knownExistingUser.getHash(), testUser.getHash());
        assertArrayEquals(knownExistingUser.getSalt(), testUser.getSalt());
        assertEquals(knownExistingUser.getIterations(), testUser.getIterations());
        assertEquals(knownExistingUser.getEmail(), testUser.getEmail());
        assertEquals(knownExistingUser.getPhone(), testUser.getPhone());
        assertEquals(knownExistingUser.getOccupation(), testUser.getOccupation());
        assertEquals(knownExistingUser.getCity(), testUser.getCity());
        assertEquals(knownExistingUser.getProfile_description(), testUser.getProfile_description());
        assertEquals(knownExistingUser.getProfile_picture(), testUser.getProfile_picture());
        assertEquals(knownExistingUser.getFirstname(), testUser.getFirstname());
        assertEquals(knownExistingUser.getLastname(), testUser.getLastname());

    }

    @Test
    public void test4UserLoginAttemptSuccess() {

        assertTrue(Passwords.isExpectedPassword("test".toCharArray(), knownExistingUser.getSalt(), knownExistingUser.getIterations(), knownExistingUser.getHash()));

    }
    
    @Test
    public void test5UserLoginAttemptFailure() {
        
        assertFalse(Passwords.isExpectedPassword("test1".toCharArray(), knownExistingUser.getSalt(), knownExistingUser.getIterations(), knownExistingUser.getHash()));
        
    }

    @Test
    public void test6UpdateUserPassword() {

        assertEquals(1, UserDAO.updateUserPassword(DB, testUser, "test1"));

    }

    @Test
    public void test7CheckLoginAfterPasswordChange() {

        /*Change password in accordance with test 6 and reattempt login*/
        UserDAO.updateUserPassword(DB, testUser, "test1");

        /*Update the test user*/
        testUser = UserDAO.getUser(DB, testUser.getUsername());

        assertTrue(Passwords.isExpectedPassword("test1".toCharArray(), testUser.getSalt(), testUser.getIterations(), testUser.getHash()));

    }

    @Test
    public void test8UserAdditionToDBPartialInput(){

        /*Check for successful database addition code of "1"*/
        assertEquals(1, UserDAO.addUserToDB(DB, testUser2.getUsername(), testUser2.getIterations(), testUser2.getSalt(), testUser2.getHash(), testUser2.getEmail(), "", testUser2.getOccupation(), testUser2.getCity(), "", testUser2.getProfile_picture(), testUser2.getFirstname(), testUser2.getLastname()));

    }

}
