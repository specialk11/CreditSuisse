import helpers.DAO;
import helpers.Parser;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;

public class LogHandler {
    private final static Log logger = LogFactory.getLog(Parser.class);


    public static void main(String[] args) {
        String file = args[0];

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));

            DAO dao = new DAO();
            logger.info("Creating Events table");
            dao.createEventsTable();

            Parser parser = new Parser();
            logger.debug("Parsing file <" + file + "> for events.");
            parser.parseLogs(reader, dao);

            // The code below will allow you to see events in the DB as well as clear all items.
            logger.debug("Retrieving all DB entries in Events table.");
            dao.selectAll();

            logger.warn("Deleting all entries in DB.");
            dao.deleteAll();
            dao.closeDatabase();
        } catch (IOException e) {
            logger.error("Error parsing file < " + file + " >");
            e.printStackTrace();
        } catch (SQLException e) {
            logger.error("Error encountered with DB");
            e.printStackTrace();
        }
    }
}
