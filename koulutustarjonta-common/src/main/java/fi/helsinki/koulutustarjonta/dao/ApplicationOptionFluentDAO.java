package fi.helsinki.koulutustarjonta.dao;

import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;

/**
 * @author Hannu Lyytikainen
 */
public class ApplicationOptionFluentDAO {


    private final Handle handle;

    public ApplicationOptionFluentDAO(DBI dbi) {
        this.handle = dbi.open();
    }



}

