package io.github.dmv04.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import io.github.dmv04.dto.LabelCreateDTO;
import io.github.dmv04.dto.LabelDTO;
import io.github.dmv04.dto.LabelUpdateDTO;
import io.github.dmv04.exception.ResourceNotFoundException;
import io.github.dmv04.mapper.LabelMapper;
import io.github.dmv04.repository.LabelRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class LabelService {
    private final LabelRepository labelRepository;
    private final LabelMapper labelMapper;

    public List<LabelDTO> getAll() {
        return labelRepository.findAll().stream()
                .map(labelMapper::map)
                .toList();
    }

    public LabelDTO findById(Long id) {
        var label = labelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Label with id " + id + " not found"));

        return labelMapper.map(label);
    }

    public LabelDTO create(LabelCreateDTO labelData) {
        var label = labelMapper.map(labelData);
        labelRepository.save(label);

        return labelMapper.map(label);
    }

    public LabelDTO update(LabelUpdateDTO labelData, Long id) {
        var label = labelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Label with id " + id + " not found"));

        if (labelData.getTaskIds() != null) {
            label.removeTaskAssociations();
        }
        labelMapper.update(labelData, label);

        if (labelData.getTaskIds() != null) {
            label.createTaskAssociations();
        }
        labelRepository.save(label);

        return labelMapper.map(label);
    }

    public void delete(Long id) {
        labelRepository.deleteById(id);
    }
}
