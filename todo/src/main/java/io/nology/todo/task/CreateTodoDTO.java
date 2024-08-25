package io.nology.todo.task;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.NotNull;

public class CreateTodoDTO {

    @NotBlank
    @Length(min = 3)
    private String task;
    @NotBlank
    private String description;
    @NotBlank
    private String category;
    @NotNull
    private String priority;

    public String getTask() {
        return task;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public String getPriority() {
        return priority;
    }

    @Override
    public String toString() {
        return "CreateTodoDTO [task=" + task + ", description=" + description + ", category=" + category + ", priority="
                + priority + "]";
    }

}
