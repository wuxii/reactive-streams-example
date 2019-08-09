package com.example;

import io.vertx.core.CompositeFuture;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.SQLConnection;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import lombok.extern.log4j.Log4j2;
import org.h2.jdbcx.JdbcConnectionPool;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author wuxii
 */
@Log4j2
@RunWith(VertxUnitRunner.class)
public class VertxTest {

    private static JDBCClient jdbcClient;

    @Before
    public void before(TestContext context) {
        Vertx vertx = Vertx.vertx();
        JdbcConnectionPool pool = JdbcConnectionPool.create("jdbc:h2:~/.h2/vertx", "sa", "");
        jdbcClient = JDBCClient.create(vertx, pool);

        Async async = context.async();

        jdbcClient.getConnection(ar0 -> {
            // ignore async result failed
            SQLConnection conn = ar0.result();
            conn.execute("drop table if exists test_vertx",
                    ar1 -> {
                        log.info("drop table if exists. {}", ar1.succeeded());
                        conn.execute("create table test_vertx(id int, name varchar(20))",
                                ar2 -> {
                                    log.info("create new table, test_vertx {}!", ar1.succeeded());
                                    conn.close();
                                    // wait complete
                                    async.complete();
                                });
                    });
        });
    }

    @Test
    public void testInsertData(TestContext context) {

        Async async = context.async();

        jdbcClient.getConnection(ar0 -> {
            SQLConnection conn = ar0.result();
            CompositeFuture future = insertData(conn);
            future.setHandler(ar -> {
                // ar.result().resultAt(0);
                log.info("data inserted! {}", ar.succeeded(), ar.cause());
                conn.close();

                async.complete();
            });
        });

    }


    CompositeFuture insertData(SQLConnection conn) {

        Promise<Void> ins1 = Promise.promise();
        Promise<Void> ins2 = Promise.promise();

        conn.execute("insert into test_vertx(id, name) values(1, 'one')", ar0 -> {
            if (ar0.succeeded()) {
                ins1.complete(ar0.result());
            } else {
                ins1.fail(ar0.cause());
            }
        });

        conn.execute("insert into test_vertx(id, name) values(2, 'two')", ar1 -> {
            if (ar1.succeeded()) {
                ins2.complete(ar1.result());
            } else {
                ins2.fail(ar1.cause());
            }
        });

        return CompositeFuture.all(ins1.future(), ins2.future());

    }

}
