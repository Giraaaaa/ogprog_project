/*
@author Sieben Veldeman
 */

package databank.database_algemeen;

import databank.DataAccessException;

import java.sql.SQLException;

public interface DataAccessProvider {

    DataAccessContext getDataAccessContext() throws DataAccessException;

    void editURL(String filepath);

    void createDataBase() throws DataAccessException;



}
