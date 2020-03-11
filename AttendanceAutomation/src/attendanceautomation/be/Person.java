/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.be;

import java.time.LocalDate;

/**
 *
 * @author ander
 */
public abstract class Person {
    
    private int id;
    private Roles role;
    private String name;
    private String email;
    private LocalDate lastAccess;

    public Person(String name, String email, Roles role) {
        this.name = name;
        this.email = email;
        this.role = role;
    }
    
    public Person(Roles role)
    {
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getLastAccess() {
        return lastAccess;
    }

    public void setLastAccess(LocalDate lastAccess) {
        this.lastAccess = lastAccess;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }
 
        public boolean equals(Student obj)
    {
        if (obj.getId() == this.getId())
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
}