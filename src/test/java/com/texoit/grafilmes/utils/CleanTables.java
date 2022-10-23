package com.texoit.grafilmes.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

/**
 * @author Carlos Lima on 23/10/2022
 */
@Service
public class CleanTables {

    @Autowired
    EntityManager entityManager;

    @Transactional
    public void clean(final String tableName) {
        entityManager.createNativeQuery("DELETE FROM " + tableName)
            .executeUpdate();
    }
}
