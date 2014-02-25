package com.companyname.dirtylibs.restapi;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Properties;

@Controller
@RequestMapping("rest/" + TestHelperController.uriExtension)
@Transactional
public class TestHelperController {
    public static final String uriExtension = "testhelper";

    @PersistenceContext
    private EntityManager entityManager;

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    @Resource(name = "properties")
    private Properties properties;

    @RequestMapping("resetDatabase")
    public ResponseEntity<String> resetDatabase() {
        String DATABASE_NAME = properties.getProperty("database.name");
        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS  = 0;").executeUpdate();

        @SuppressWarnings("unchecked")
        List<String> results = entityManager.createNativeQuery("SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = '" + DATABASE_NAME + "';").getResultList();
        for (String tableName : results) {
            entityManager.createNativeQuery("TRUNCATE TABLE " + DATABASE_NAME + "." + tableName).executeUpdate();
        }
        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS  = 1;").executeUpdate();
        return new ResponseEntity<String>("Database reset successfully", HttpStatus.OK);
    }

}
