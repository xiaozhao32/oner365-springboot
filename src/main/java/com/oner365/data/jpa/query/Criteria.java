package com.oner365.data.jpa.query;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

/**
 * 定义一个查询条件容器
 *
 * @author zhaoyong
 */
public class Criteria<T> implements Specification<T> {

    private static final long serialVersionUID = 1L;

    private final List<Criterion> criterionList = new ArrayList<>();

    @Override
    public Predicate toPredicate(@NonNull Root<T> root, CriteriaQuery<?> query, @NonNull CriteriaBuilder builder) {
        if (!criterionList.isEmpty()) {
            List<Predicate> predicates = new ArrayList<>();
            criterionList.forEach(c -> predicates.add(c.toPredicate(root, query, builder)));
            // 将所有条件用 and 联合起来
            if (!predicates.isEmpty()) {
                return builder.and(predicates.toArray(new Predicate[0]));
            }
        }
        return builder.conjunction();
    }

    /***
     * 增加简单条件表达式
     * @param criterion 表达式
     */
    public void add(Criterion criterion) {
        if (criterion != null) {
            criterionList.add(criterion);
        }
    }

}
