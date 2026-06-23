package com.oner365.data.redis.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.annotation.AliasFor;

import com.oner365.data.commons.constants.PublicConstants;

/**
 * Generator Cache
 *
 * @author zhaoyong
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
@Cacheable(PublicConstants.NAME)
public @interface GeneratorCache {

    @AliasFor(annotation = Cacheable.class, attribute = "value")
    String[] value() default {};

    @AliasFor(annotation = Cacheable.class, attribute = "cacheNames")
    String[] cacheNames() default {};

    @AliasFor(annotation = Cacheable.class, attribute = "key")
    String key() default PublicConstants.EMPTY;

    @AliasFor(annotation = Cacheable.class, attribute = "keyGenerator")
    String keyGenerator() default PublicConstants.KEY_GENERATOR;

}
