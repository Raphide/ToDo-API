package io.nology.todo.task;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Pattern;

public class UpdateTodoDTO {
    
    @Pattern(regexp = ".*\\S.*", message = "Task Name cannot be empty")
    @Length(min = 5)
    private String task;
  
    @Pattern(regexp = ".*\\S.*", message = "Description cannot be empty")
    private String description;
  
    @Pattern(regexp = ".*\\S.*", message = "Category cannot be empty")
    private String category;
   
    @Pattern(regexp = ".*\\S.*", message = "Category cannot be empty")
    private String priority;

    @Override
    public String toString() {
        return "UpdateTodoDTO [task=" + task + ", description=" + description + ", category=" + category + ", priority="
                + priority + "]";
    }

    

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

    



}
