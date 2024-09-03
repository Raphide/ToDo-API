package io.nology.todo.task;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class CreateTodoDTO {

    @NotBlank
    @Length(min = 3)
    private String task;
    @NotBlank
    private String description;
    @NotNull
    private Long categoryId;
    @NotNull
    // @Length(min = 1)
    // @Pattern(regexp = ".*\\S.*", message = "Must choose a priority")
    private String priority;

    public String getTask() {
        return task;
    }

    public String getDescription() {
        return description;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public String getPriority() {
        return priority;
    }

}
