package io.github.dmv04.mapper;

import jakarta.persistence.EntityManager;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.TargetType;
import org.springframework.beans.factory.annotation.Autowired;

import io.github.dmv04.exception.ResourceNotFoundException;
import io.github.dmv04.model.BaseEntity;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING
)
public abstract class ReferenceMapper {
    @Autowired
    private EntityManager entityManager;

    public <T extends BaseEntity> T toEntity(Long id, @TargetType Class<T> entityClass) {
        if (id != null && entityManager.find(entityClass, id) == null) {
            throw new ResourceNotFoundException(entityClass.getSimpleName() + " with id '" + id + "' not found");
        }
        return id != null ? entityManager.find(entityClass, id) : null;
    }

    public <T extends BaseEntity> Long toId(T entity) {
        return entity != null ? entity.getId() : null;
    }
}
