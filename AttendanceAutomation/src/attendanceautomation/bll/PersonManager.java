/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.bll;

import attendanceautomation.be.Person;
import attendanceautomation.dal.dbmanagers.facades.DalFacade;
import attendanceautomation.dal.dbmanagers.facades.IDalFacade;

/**
 *
 * @author ander
 */
public class PersonManager {

    private IDalFacade dalFacade;

    public PersonManager() {
        dalFacade = new DalFacade();
    }

    public Person validateUser(String username, String password)
    {
       return dalFacade.login(username, password);
    }
    
}
