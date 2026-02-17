/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.jsu.mcis.cs310.tas_sp26;

/**
 *
 * @author cthra
 */


public class Department {

    private int id;
    private String description;
    private int terminalid;

    public Department(int id, String description, int terminalid) {
        this.id = id;
        this.description = description;
        this.terminalid = terminalid;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public int getTerminalid() {
        return terminalid;
    }

    @Override
    public String toString() {
        return String.format(
            "#%d (%s), Terminal ID: %d",
            id, description, terminalid
        );
    }
}
