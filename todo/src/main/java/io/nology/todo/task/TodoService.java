package io.nology.todo.task;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;

@Service
public class TodoService {

    @Autowired
    private TodoRepository repo;

    public Todo createTodo(@Valid CreateTodoDTO data) {
        // System.out.println("From service " + data);
        // return "Got to service";

        Todo newTodo = new Todo();
        newTodo.setTask(data.getTask().trim());
        newTodo.setCategory(data.getCategory().trim().toLowerCase());
        newTodo.setDescription(data.getDescription().trim());
        newTodo.setPriority(data.getPriority());
        Date now = new Date();
        newTodo.setCreatedAt(now);
        newTodo.setUpdatedAt(now);
        newTodo.setCompleted(false);
        return this.repo.save(newTodo);
    }

    public List<Todo> findAll() {
        return this.repo.findAll();
    }

    public Optional<Todo> findById(Long id) {
        return this.repo.findById(id);
    }

    public Optional<Todo> updateById(Long id, @Valid UpdateTodoDTO data) {
        Optional<Todo> result = this.findById(id);
        if (result.isEmpty()) {
            return result;
        }
        Todo foundTodo = result.get();
        if (data.getTask() != null) {
            foundTodo.setTask(data.getTask().trim());
        }
        if (data.getDescription() != null) {
            foundTodo.setDescription(data.getDescription().trim());
        }
        if (data.getCategory() != null) {
            foundTodo.setCategory(data.getCategory().trim());
        }
        if (data.getPriority() != null) {
        foundTodo.setPriority(data.getPriority().trim());
        }
        foundTodo.setUpdatedAt(new Date());
        Todo updatedTodo = this.repo.save(foundTodo);
        return Optional.of(updatedTodo);
    }

    // public Optional<Todo> deleteById(Long id) {
    // Optional<Todo> result = this.findById(id);
    // if(result.isEmpty()){
    // return result;
    // }
    // this.repo.delete(result.get());
    // return result;
    // }

    public boolean deleteById(Long id) {
        Optional<Todo> result = this.findById(id);
        if (result.isEmpty()) {
            return false;
        }
        this.repo.delete(result.get());
        return true;
    }

    public Optional<Todo> completeById(Long id) {
        Optional<Todo> result = this.findById(id);
        if (result.isEmpty()) {
            return result;
        }
        Todo foundTodo = result.get();
        foundTodo.setCompleted(true);
        foundTodo.setCompletedAt(new Date());
        Todo updatedTodo = this.repo.save(foundTodo);
        return Optional.of(updatedTodo);

    }

    public List<Todo> findByPriority(String priority) {
        return this.repo.findByPriority(priority);
    }

}
