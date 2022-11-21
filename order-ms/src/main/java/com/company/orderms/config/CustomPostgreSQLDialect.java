package com.company.orderms.config;

import org.hibernate.dialect.PostgreSQL10Dialect;

import java.sql.Types;
import java.util.UUID;

public class CustomPostgreSQLDialect extends PostgreSQL10Dialect {

    public CustomPostgreSQLDialect() {
        super();
        this.registerHibernateType(Types.OTHER, UUID.class.getName());
    }

}
