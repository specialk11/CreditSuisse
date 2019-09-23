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
            dao.createEventsTable();

            logger.debug("Parsing file <" + file + "> for events.");
            Parser parser = new Parser();
            parser.parseLogs(reader, dao);

            dao.selectAll();
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
