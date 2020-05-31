package client;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class allows to configure ServerLog 
 * @author Quentin Beeckmans - Mathieu Roux
 * @version 1.0
 * @since 2020-05-30
 */
public class ServerLog {
	
    private String myLog = "D://HES//2019//BAC2//SEM02//06 Programmation distribu�e//Projet//DossierPartage//my.log";


    public ServerLog(){


        printList();

    }
    public void printList (){

        Logger log = Logger.getLogger("myLogger");
        try {
            FileHandler f = new FileHandler(myLog, true);
            // SimpleFormatter formatter = new SimpleFormatter();
            CustomLogFormatter formatter = new CustomLogFormatter();

            log.addHandler(f);
            f.setFormatter(formatter);
            log.setLevel(Level.INFO);
            log.info("\n-------------this is the info level------");
            log.warning("attention hacker");
            log.severe("exception");

            log.setLevel(Level.WARNING);
            log.warning("\n-------------this is the warning level------");
            log.warning("attention hacker");
            log.severe("exception");

            log.setLevel(Level.SEVERE);
            log.severe("\n-------------this is severe level------");
            log.warning("attention hacker");
            log.severe("exception");

        } catch (IOException e) {
            e.printStackTrace();
        }




    }

}
