package space.bielsolososdev.rdl.domain.users.repository.specification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import space.bielsolososdev.rdl.domain.users.model.User;

@AllArgsConstructor
public class UserSpecification implements Specification<User> {

    private static final long serialVersionUID = 1L;

    private String filter;
    private Boolean isActive;
    private LocalDateTime createdAfter;
    private LocalDateTime createdBefore;

    @Override
    public Predicate toPredicate(Root<User> root, @Nullable CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<>();

        if (filter != null && !filter.trim().isEmpty()) {
            String[] commonSearchParams = { "username", "email" };

            List<Predicate> searchPredicates = new ArrayList<>();

            for (String param : commonSearchParams) {
                searchPredicates.add(cb.like(cb.lower(root.get(param)), "%" + filter.toLowerCase().trim() + "%"));
            }

            predicates.add(cb.or(searchPredicates.toArray(new Predicate[0])));
        }

        if (isActive != null) {
            predicates.add(cb.equal(root.get("isActive"), isActive));
        }

        if (createdAfter != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("createdAt"), createdAfter));
        }

        if (createdBefore != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("createdAt"), createdBefore));
        }

        return cb.and(predicates.toArray(new Predicate[0]));
    }
}