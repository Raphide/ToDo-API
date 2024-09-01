package io.nology.todo.task;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;

public class UpdateTodoDTO {
    
    @Pattern(regexp = ".*\\S.*", message = "Task Name cannot be empty")
    @Length(min = 5)
    private String task;
  
    @Pattern(regexp = ".*\\S.*", message = "Description cannot be empty")
    private String description;
  
    @Pattern(regexp = ".*\\S.*", message = "Must choose a category")
    private Long categoryId;
   
    // @Pattern(regexp = ".*\\S.*", message = "Priority cannot be empty")
    @Pattern(regexp = ".*\\S.*", message = "Must choose a priority")
    private String priority;

    @Override
    public String toString() {
        return "UpdateTodoDTO [task=" + task + ", description=" + description + ", category=" + categoryId + ", priority="
                + priority + "]";
    }

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
