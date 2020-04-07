/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.dal.dbmanagers.dbdao;

import attendanceautomation.be.Roles;

/**
 *
 * @author ander
 */
public interface IPersonDBDAO {
    
    /**
     * Returns a boolean based in whether the login information given is valid or not.
     * @param userName The username of the account
     * @param password The password of the account
     * @return boolean value representing whether or not the account is active
     */
    public boolean isLoginCorrect(String userName, String password);
    
    /**
     * Returns the ID of the person found with the given login information.
     * @param userName The username of the account
     * @param password The password of the account
     * @return ID of the person based on the entered login
     */
    public int getPersonId(String userName, String password);
    
    /**
     * Returns the role of the person containing a specific ID.
     * @param id The ID of the person being searched for
     * @return The role of the person entered
     */
    public Roles getRoleById(int id);
    
    /**
     * Updates the last login on the person entered.
     * @param personID The ID of the person logging in.
     */
    public void updateLastLogin(int personID);
    
}
