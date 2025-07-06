package com.example.rojomelbackend.interceptor;

import org.hibernate.Interceptor;
import org.springframework.core.Ordered;

public interface HibernateOrderedInterceptor extends Interceptor, Ordered {
}
