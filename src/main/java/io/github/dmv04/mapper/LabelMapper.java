package io.github.dmv04.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import io.github.dmv04.dto.LabelCreateDTO;
import io.github.dmv04.dto.LabelDTO;
import io.github.dmv04.dto.LabelUpdateDTO;
import io.github.dmv04.model.Label;

@Mapper(
        uses = {JsonNullableMapper.class, ReferenceMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class LabelMapper {
    @Mapping(source = "taskIds", target = "tasks")
    public abstract Label map(LabelCreateDTO dto);

    @Mapping(source = "tasks", target = "taskIds")
    public abstract LabelDTO map(Label model);

    @Mapping(source = "taskIds", target = "tasks")
    public abstract Label map(LabelDTO dto);

    @Mapping(source = "taskIds", target = "tasks")
    public abstract void update(LabelUpdateDTO dto, @MappingTarget Label model);
}
