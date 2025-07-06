package com.example.rojomelbackend.interceptor;

import org.hibernate.resource.jdbc.spi.StatementInspector;
import org.springframework.stereotype.Component;

import java.util.List;


@Component("delegatingStatementInspector")
public class DelegatingStatementInspector implements StatementInspector {

    private final List<StatementInspector> inspectors;

    public DelegatingStatementInspector(List<StatementInspector> inspectors) {
        this.inspectors = inspectors;
    }

    @Override
    public String inspect(String sql) {
        for (StatementInspector inspector : inspectors) {
            String modified = inspector.inspect(sql);
            if (modified != null) {
                sql = modified;
            }
        }
        return sql;
    }
}