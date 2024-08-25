package io.nology.todo.task;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.nology.todo.category.Category;
import io.nology.todo.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "todos")
public class Todo extends BaseEntity {

    public Todo() {

    }

    @Column
    private String task;

    @Column(columnDefinition = "TEXT")
    private String description;

    // @ManyToOne
    // @JoinColumn(name = "category_id")
    // @JsonIgnoreProperties("todos")
    // private Category category;

    @Column
    private String category;

    @Column
    private String priority;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column
    private boolean completed;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date completedAt;
    
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


    public String getDescription() {
        return description;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setDescription(String description) {
        this.description = description;
    }

     public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }


    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    // public Category getCategory() {
    // return category;
    // }

    // public void setCategory(Category category) {
    // this.category = category;
    // }

    public Date getCompletedAt() {
        return completedAt;
    }

    
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setCompletedAt(Date completedAt) {
        this.completedAt = completedAt;
    }

    @PreUpdate
    public void onComplete() {
        completedAt = new Date();
    }

    @Override
    public String toString() {
        return "Todo [task=" + task + ", description=" + description + ", category=" + category + ", completedAt="
                + completedAt + ", getId()=" + getId() + ", getCreatedAt()=" + getCreatedAt() + "]";
    }

}
