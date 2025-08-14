package io.github.dmv04.component;

import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import io.github.dmv04.model.Label;
import io.github.dmv04.model.TaskStatus;
import io.github.dmv04.model.User;
import io.github.dmv04.repository.LabelRepository;
import io.github.dmv04.repository.TaskStatusRepository;
import io.github.dmv04.repository.UserRepository;
import io.github.dmv04.service.CustomUserDetailsService;
import io.github.dmv04.util.LabelKit;
import io.github.dmv04.util.TaskStatusKit;

@Component
@AllArgsConstructor
public class DataInitializer implements ApplicationRunner {

    private final UserRepository userRepository;
    private final CustomUserDetailsService customUserDetailsService;
    private final TaskStatusRepository taskStatusRepository;
    private final LabelRepository labelRepository;
    private final TaskStatusKit taskStatusKit;
    private final LabelKit labelKit;

    public void run(ApplicationArguments args) {

        if (userRepository.findByEmail("dmv04@example.com").isEmpty()) {
            initializeAdmin();
        }

        if (taskStatusRepository.findAll().isEmpty()) {
            initializeTaskStatuses();
        }

        if (labelRepository.findAll().isEmpty()) {
            initializeLabels();
        }
    }

    public void initializeAdmin() {
        var userData = new User();
        userData.setEmail("dmv04@example.com");
        userData.setPasswordDigest("qwerty");
        customUserDetailsService.createUser(userData);
    }

    public void initializeTaskStatuses() {
        for (var slug : taskStatusKit.getSlugs()) {
            var taskStatus = new TaskStatus();
            taskStatus.setSlug(slug);
            taskStatus.setName(taskStatusKit.getName(slug));
            taskStatusRepository.save(taskStatus);
        }
    }

    public void initializeLabels() {
        for (var labelName : labelKit.getLabelNames()) {
            var label = new Label();
            label.setName(labelName);
            labelRepository.save(label);
        }
    }
}
