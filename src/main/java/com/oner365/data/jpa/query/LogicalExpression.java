package com.oner365.data.jpa.query;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

/**
 * 逻辑条件表达式 用于复杂条件时使用，如但属性多对应值的OR查询等
 *
 * @author zhaoyong
 */
public class LogicalExpression implements Criterion {

  /**
  * 
  */
  private static final long serialVersionUID = 1L;
  /**
   * 逻辑表达式中包含的表达式
   */
  private final Criterion[] criterion;
  /**
   * 计算符
   */
  private final Operator operator;

  public LogicalExpression(Criterion[] criterion, Operator operator) {
    this.criterion = criterion;
    this.operator = operator;
  }

  @Override
  public Predicate toPredicate(Root<?> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
    List<Predicate> predicates = Arrays.stream(this.criterion).map(value -> value.toPredicate(root, query, builder))
        .collect(Collectors.toList());
    if (Operator.OR.equals(operator)) {
      return builder.or(predicates.toArray(new Predicate[0]));
    }
    return null;
  }

}
