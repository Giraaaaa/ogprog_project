/*
@author Sieben Veldeman
 */

package databank.database_algemeen;

import java.sql.Connection;
import java.sql.SQLException;

public interface DataAccessProvider {

    DataAccessContext getDataAccessContext() throws SQLException;

    void editURL(String filepath);

    void createDataBase() throws SQLException;



}
