package com.example.memderadmin.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;
import javax.persistence.metamodel.EntityType;
import java.util.List;
import java.util.stream.Collectors;


@Component
@ActiveProfiles("test")
public class H2DatabaseCleanUp implements InitializingBean {
    @PersistenceContext
    private EntityManager entityManager;

    private List<String> tableNames;

    @Override
    public void afterPropertiesSet() {
        tableNames = entityManager.getMetamodel().getEntities().stream()
                .filter(e -> e.getJavaType().getAnnotation(Table.class) != null)
                .map(e -> getTableName(e))
                .collect(Collectors.toList());
    }

    private String getTableName(EntityType<?> e) {
        Table annotation = e.getJavaType().getAnnotation(Table.class);
        return annotation.name();
    }

    @Transactional
    public void execute() {
        entityManager.flush();
        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS=FALSE").executeUpdate();

        for (String tableName : tableNames) {
            entityManager.createNativeQuery("TRUNCATE TABLE moim." + tableName).executeUpdate();
            //entityManager.createNativeQuery("ALTER TABLE " + tableName + " ALTER COLUMN stu_id RESTART WITH 1").executeUpdate();
            //entityManager.createNativeQuery("ALTER TABLE " + tableName + " AUTO_INCREMENT=1").executeUpdate();
        }

        //entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS=TRUE").executeUpdate();
        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate();
    }
}
