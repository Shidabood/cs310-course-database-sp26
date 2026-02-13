package edu.jsu.mcis.cs310.tas_sp26;

import edu.jsu.mcis.cs310.tas_sp26.dao.*;

public class Main {

    public static void main(String[] args) {
        
        // test database connectivity; get DAOs

        DAOFactory daoFactory = new DAOFactory("tas.jdbc");
        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();
        
        // find badge

        Badge b = badgeDAO.find("ADD650A8");
        
        // output should be "Test Badge: #ADD650A8 (Taylor, Jennifer T)"
        
        System.err.println("Test Badge: " + b.toString());

    }

}
