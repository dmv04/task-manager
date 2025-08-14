package io.github.dmv04.service;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import io.github.dmv04.dto.TaskCreateDTO;
import io.github.dmv04.dto.TaskDTO;
import io.github.dmv04.dto.TaskParamsDTO;
import io.github.dmv04.dto.TaskUpdateDTO;
import io.github.dmv04.exception.ResourceNotFoundException;
import io.github.dmv04.mapper.TaskMapper;
import io.github.dmv04.model.Task;
import io.github.dmv04.repository.TaskRepository;
import io.github.dmv04.specification.TaskSpecification;

import java.util.List;

@Service
@AllArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final TaskSpecification specBuilder;

    public List<TaskDTO> getAll(TaskParamsDTO params) {
        Specification<Task> spec = specBuilder.build(params);

        return taskRepository.findAll(spec).stream()
                .map(taskMapper::map)
                .toList();
    }

    public TaskDTO findById(Long id) {
        var task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task with id " + id + " not found"));

        return taskMapper.map(task);
    }

    public TaskDTO create(TaskCreateDTO taskData) {
        var task = taskMapper.map(taskData);
        taskRepository.save(task);

        return taskMapper.map(task);
    }

    public TaskDTO update(TaskUpdateDTO taskData, Long id) {
        var task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task with id " + id + " not found"));
        taskMapper.update(taskData, task);
        taskRepository.save(task);

        return taskMapper.map(task);
    }

    public void delete(Long id) {
        taskRepository.deleteById(id);
    }
}
