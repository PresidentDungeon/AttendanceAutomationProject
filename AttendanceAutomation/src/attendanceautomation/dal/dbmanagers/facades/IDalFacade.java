/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.dal.dbmanagers.facades;

import attendanceautomation.be.Person;

/**
 *
 * @author ander
 */
public interface IDalFacade {
    
     public Person login(String username, String password);
    
}
