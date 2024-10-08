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
    @NotNull
    private Long categoryId;

    @NotNull
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

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}
