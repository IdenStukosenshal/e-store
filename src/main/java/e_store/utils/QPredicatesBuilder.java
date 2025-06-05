package e_store.utils;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class QPredicatesBuilder {

    private List<Predicate> predicatesLst = new ArrayList<>();

    public static QPredicatesBuilder of() {
        return new QPredicatesBuilder();
    }

    public <T> QPredicatesBuilder add(T fieldValue, Function<T, Predicate> function) {
        if (fieldValue != null) {
            predicatesLst.add(function.apply(fieldValue));
        }
        return this;
    }

    public Predicate buildAnd() {
        return predicatesLst.isEmpty()
                ? Expressions.TRUE.isTrue()
                : ExpressionUtils.allOf(predicatesLst);
    }

    private QPredicatesBuilder() {
    }
}
