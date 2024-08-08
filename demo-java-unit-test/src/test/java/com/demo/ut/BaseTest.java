package com.demo.ut;

import ch.vorburger.exec.ManagedProcessException;
import ch.vorburger.mariadb4j.DB;
import ch.vorburger.mariadb4j.DBConfigurationBuilder;
import com.demo.ut.util.RestClient;
import org.junit.jupiter.api.BeforeAll;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.event.annotation.AfterTestExecution;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@AutoConfigureMockMvc
@ActiveProfiles("test")
@SpringBootTest(classes = DemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(BaseTest.class);

    /*
     * mockito external interface
     */
    @MockBean protected RestClient restClient;

    /**
     * mockito rest request
     */
    @Autowired
    protected MockMvc mockMvc;

    /**
     * mariaDB
     */
    private static DB db;

    /**
     * test Statement
     * used for execute sql
     */
    protected static Statement dbStatement;

    private static boolean initial = false;

    @BeforeAll
    public static void setup() throws ManagedProcessException, SQLException, IOException {
        if (!initial) {
            // Use mariaDB-embedded as test database
            DBConfigurationBuilder builder = DBConfigurationBuilder.newBuilder();
            // Start db as root user in Linux
            if (!builder._getArgs().contains("--user=root")) {
                builder.addArg("--user=root");
            }
            builder.setPort(13306);
            db = DB.newEmbeddedDB(builder.build());
            db.start();
            db.createDB("test");
            Connection dbConnection = DriverManager.getConnection("jdbc:mysql://localhost:13306/test", "root", "");
            dbStatement = dbConnection.createStatement();
            initial = true;
        }
    }

    @AfterTestExecution
    public void tearDown() {
        System.out.println("stop database");
        try {
            db.stop();
        } catch (ManagedProcessException e) {
            logger.error("Fail to stop database", e);
        }
    }

    /**
     * Wrapper the sleep method of Thread class
     * used for asynchronous process test
     *
     * @param millis the length of time to sleep in milliseconds
     */
    protected void sleepAWhile(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
