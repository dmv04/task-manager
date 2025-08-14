package io.github.dmv04.controller.api;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.github.dmv04.dto.LabelCreateDTO;
import io.github.dmv04.dto.LabelDTO;
import io.github.dmv04.dto.LabelUpdateDTO;
import io.github.dmv04.service.LabelService;

import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class LabelController {
    private final LabelService labelService;

    @GetMapping("/labels")
    public ResponseEntity<List<LabelDTO>> index() {
        var labels = labelService.getAll();

        return ResponseEntity.status(HttpStatus.OK)
                .header("X-Total-Count", String.valueOf(labels.size()))
                .body(labels);
    }

    @GetMapping("/labels/{id}")
    public LabelDTO show(@PathVariable long id) {

        return labelService.findById(id);
    }

    @PostMapping("/labels")
    @ResponseStatus(HttpStatus.CREATED)
    public LabelDTO create(@RequestBody @Valid LabelCreateDTO labelData) {

        return labelService.create(labelData);
    }

    @PutMapping("/labels/{id}")
    public LabelDTO update(@RequestBody @Valid LabelUpdateDTO labelData, @PathVariable long id) {

        return labelService.update(labelData, id);
    }

    @DeleteMapping("/labels/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroy(@PathVariable long id) {

        labelService.delete(id);
    }
}
