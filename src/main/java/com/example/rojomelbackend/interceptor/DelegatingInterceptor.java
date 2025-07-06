package com.example.rojomelbackend.interceptor;

import java.io.Serializable;
import java.util.*;
import org.hibernate.CallbackException;
import org.hibernate.EmptyInterceptor;
import org.hibernate.Interceptor;
import org.hibernate.Transaction;
import org.hibernate.resource.jdbc.spi.StatementInspector;
import org.hibernate.type.Type;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

@Component("delegatingInterceptor")
public class DelegatingInterceptor extends EmptyInterceptor
        implements BeanFactoryAware, SmartInitializingSingleton, StatementInspector {

    private ConfigurableListableBeanFactory configurableListableBeanFactory;
    private final List<Interceptor> implementedInterceptors = new LinkedList<>();
    private ConfigurableListableBeanFactory beanFactory;
    private final List<StatementInspector> inspectors = new ArrayList<>();


    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        if (beanFactory instanceof ConfigurableListableBeanFactory clbf) {
            this.beanFactory = clbf;
        } else {
            throw new IllegalArgumentException("BeanFactory must be ConfigurableListableBeanFactory");
        }
    }

    @Override
    public void afterSingletonsInstantiated() {
        Map<String, HibernateOrderedInterceptor> interceptorBeans =
                beanFactory.getBeansOfType(HibernateOrderedInterceptor.class);
        List<HibernateOrderedInterceptor> allInterceptorList = new ArrayList<>(interceptorBeans.values());
        allInterceptorList.sort(Comparator.comparingInt(HibernateOrderedInterceptor::getOrder));
        implementedInterceptors.addAll(allInterceptorList);

        Map<String, StatementInspector> inspectorBeans =
                beanFactory.getBeansOfType(StatementInspector.class);
        for (Map.Entry<String, StatementInspector> entry : inspectorBeans.entrySet()) {
            if (!entry.getValue().getClass().equals(this.getClass())) {
                inspectors.add(entry.getValue());
            }
        }
    }

    @Override
    public boolean onLoad(Object entity, Serializable id, Object[] state,
                          String[] propertyNames, Type[] types) throws CallbackException {
        boolean changed = false;
        for (Interceptor interceptor : implementedInterceptors) {
            changed |= interceptor.onLoad(entity, id, state, propertyNames, types);
        }
        return changed;
    }

    @Override
    public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState,
                                Object[] previousState, String[] propertyNames, Type[] types)
            throws CallbackException {
        boolean changed = false;
        for (Interceptor interceptor : implementedInterceptors) {
            changed |= interceptor.onFlushDirty(entity, id, currentState, previousState, propertyNames, types);
        }
        return changed;
    }

    @Override
    public boolean onSave(Object entity, Serializable id, Object[] state,
                          String[] propertyNames, Type[] types) throws CallbackException {
        boolean changed = false;
        for (Interceptor interceptor : implementedInterceptors) {
            changed |= interceptor.onSave(entity, id, state, propertyNames, types);
        }
        return changed;
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

    @Override
    public void onDelete(Object entity, Serializable id, Object[] state,
                         String[] propertyNames, Type[] types) throws CallbackException {
        for (Interceptor interceptor : implementedInterceptors) {
            interceptor.onDelete(entity, id, state, propertyNames, types);
        }
    }

    @Override
    public void onCollectionRecreate(Object collection, Serializable key) throws CallbackException {
        for (Interceptor interceptor : implementedInterceptors) {
            interceptor.onCollectionRecreate(collection, key);
        }
    }

    @Override
    public void onCollectionRemove(Object collection, Serializable key) throws CallbackException {
        for (Interceptor interceptor : implementedInterceptors) {
            interceptor.onCollectionRemove(collection, key);
        }
    }

    @Override
    public void onCollectionUpdate(Object collection, Serializable key) throws CallbackException {
        for (Interceptor interceptor : implementedInterceptors) {
            interceptor.onCollectionUpdate(collection, key);
        }
    }

    @Override
    public void preFlush(Iterator entities) throws CallbackException {
        for (Interceptor interceptor : implementedInterceptors) {
            interceptor.preFlush(entities);
        }
    }

    @Override
    public void postFlush(Iterator entities) throws CallbackException {
        for (Interceptor interceptor : implementedInterceptors) {
            interceptor.postFlush(entities);
        }
    }

    @Override
    public Boolean isTransient(Object entity) {
        for (Interceptor interceptor : implementedInterceptors) {
            Boolean result = interceptor.isTransient(entity);
            if (result != null) return result;
        }
        return null;
    }

    @Override
    public int[] findDirty(Object entity, Serializable id, Object[] currentState,
                           Object[] previousState, String[] propertyNames, Type[] types) {
        for (Interceptor interceptor : implementedInterceptors) {
            int[] result = interceptor.findDirty(entity, id, currentState, previousState, propertyNames, types);
            if (result != null) return result;
        }
        return null;
    }

    @Override
    public String getEntityName(Object object) throws CallbackException {
        for (Interceptor interceptor : implementedInterceptors) {
            String result = interceptor.getEntityName(object);
            if (result != null) return result;
        }
        return null;
    }

    @Override
    public Object getEntity(String entityName, Serializable id) throws CallbackException {
        for (Interceptor interceptor : implementedInterceptors) {
            Object result = interceptor.getEntity(entityName, id);
            if (result != null) return result;
        }
        return null;
    }

    @Override
    public void afterTransactionBegin(Transaction tx) {
        for (Interceptor interceptor : implementedInterceptors) {
            interceptor.afterTransactionBegin(tx);
        }
    }

    @Override
    public void beforeTransactionCompletion(Transaction tx) {
        for (Interceptor interceptor : implementedInterceptors) {
            interceptor.beforeTransactionCompletion(tx);
        }
    }

    @Override
    public void afterTransactionCompletion(Transaction tx) {
        for (Interceptor interceptor : implementedInterceptors) {
            interceptor.afterTransactionCompletion(tx);
        }
    }
}
