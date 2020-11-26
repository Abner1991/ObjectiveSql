package com.github.braisdom.objsql;

import com.github.braisdom.objsql.annotations.DomainModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.github.braisdom.objsql.DatabaseType.*;

public class DefaultQueryTest {

    @Test
    public void testSimpleSQL() {
        Query query = new DefaultQuery(Domain.class);

        Assertions.assertEquals(query.getQuerySQL(MySQL.getDatabaseProductName()), "SELECT * FROM `domains`");
        Assertions.assertEquals(query.getQuerySQL(PostgreSQL.getDatabaseProductName()), "SELECT * FROM \"domains\"");
        Assertions.assertEquals(query.getQuerySQL(Oracle.getDatabaseProductName()), "SELECT * FROM \"DOMAINS\"");
    }

    @Test
    public void testWhereSQL() {
        Query query = new DefaultQuery(Domain.class);
        query.where("name = ?", "12");

        Assertions.assertEquals(query.getQuerySQL(MySQL.getDatabaseProductName()), "SELECT * FROM `domains` WHERE name = ?");
        Assertions.assertEquals(query.getQuerySQL(PostgreSQL.getDatabaseProductName()), "SELECT * FROM \"domains\" WHERE name = ?");
        Assertions.assertEquals(query.getQuerySQL(Oracle.getDatabaseProductName()), "SELECT * FROM \"DOMAINS\" WHERE name = ?");
    }

    @Test
    public void testPageSQL() {
        Query query = new DefaultQuery(Domain.class);
        query.offset(10).fetch(10);

        Assertions.assertEquals(query.getQuerySQL(MySQL.getDatabaseProductName()), "SELECT * FROM `domains` OFFSET 10 ROWS  FETCH  NEXT 10 ROWS ONLY ");
        Assertions.assertEquals(query.getQuerySQL(PostgreSQL.getDatabaseProductName()), "SELECT * FROM \"domains\" OFFSET 10 ROWS  FETCH  NEXT 10 ROWS ONLY ");
        Assertions.assertEquals(query.getQuerySQL(Oracle.getDatabaseProductName()), "SELECT * FROM \"DOMAINS\" OFFSET 10 ROWS  FETCH  NEXT 10 ROWS ONLY ");
    }

    @Test
    public void testGroupByAndOrderBySQL() {
        Query query = new DefaultQuery(Domain.class);
        query.groupBy("name").having("len(name) > 10").orderBy("name DESC");

        Assertions.assertEquals(query.getQuerySQL(MySQL.getDatabaseProductName()), "SELECT * FROM `domains` GROUP BY name HAVING len(name) > 10 ORDER BY name DESC");
        Assertions.assertEquals(query.getQuerySQL(PostgreSQL.getDatabaseProductName()), "SELECT * FROM \"domains\" GROUP BY name HAVING len(name) > 10 ORDER BY name DESC");
        Assertions.assertEquals(query.getQuerySQL(Oracle.getDatabaseProductName()), "SELECT * FROM \"DOMAINS\" GROUP BY name HAVING len(name) > 10 ORDER BY name DESC");
    }

    @DomainModel
    public static class Domain {
        private String name;
    }
}