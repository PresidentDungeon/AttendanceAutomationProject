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
    
    public boolean isLoginCorrect(String userName, String password);
    public int getPersonId(String userName, String password);
    public Roles getRoleById(int id);
    
}
