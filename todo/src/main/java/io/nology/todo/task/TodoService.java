package io.nology.todo.task;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.nology.todo.category.Category;
import io.nology.todo.category.CategoryService;
import io.nology.todo.common.ValidationErrors;
import jakarta.validation.Valid;

@Service
public class TodoService {

    @Autowired
    private TodoRepository repo;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ModelMapper mapper;

    public Todo createTodo(@Valid CreateTodoDTO data) throws Exception {
        ValidationErrors errors = new ValidationErrors();
        Todo newTodo = mapper.map(data, Todo.class);
        Optional<Category> categoryResult = this.categoryService.findById(data.getCategoryId());
        if (categoryResult.isEmpty()) {
            errors.addError("category", String.format("Category with is %s does not exist", data.getCategoryId()));
        }
        newTodo.setCategory(categoryResult.get());
        // newTodo.setUpdatedAt(new Date());
        newTodo.setCreatedAt(new Date());
        newTodo.setCompleted(false);
        newTodo.setIsArchived(false);
        return this.repo.save(newTodo);
    }

    public Todo duplicateTodo(Long id) throws Exception {
        Optional<Todo> result = this.findById(id);
        if (result.isEmpty()) {
            throw new Exception("Todo does not exist what id " + id);
        } 
        Todo foundTodo = result.get();
        Todo newTodo = new Todo();
        newTodo.setTask(foundTodo.getTask());
        newTodo.setDescription(foundTodo.getDescription());
        newTodo.setPriority(foundTodo.getPriority());        
        newTodo.setCategory(foundTodo.getCategory());
        newTodo.setCreatedAt(new Date());
        newTodo.setCompleted(false);
        newTodo.setIsArchived(false);
        return this.repo.save(newTodo);
    }

    public List<Todo> findAll() {
        return this.repo.findAll();
    }

    public Optional<Todo> findById(Long id) {
        return this.repo.findById(id);
    }

    public Optional<Todo> updateById(Long id, @Valid UpdateTodoDTO data) throws Exception {
        Optional<Todo> result = this.findById(id);
        if (result.isEmpty()) {
            return result;
        }
        Todo foundTodo = result.get();
        mapper.map(data, foundTodo);
        if (data.getCategoryId() != null) {
            Optional<Category> categoryResult = this.categoryService.findById(data.getCategoryId());
            if (categoryResult.isEmpty()) {
                throw new Exception("Category does not exist");
            }
            foundTodo.setCategory(categoryResult.get());
        }
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
        foundTodo.setIsArchived(true);
        foundTodo.setCompletedAt(new Date());
        Todo updatedTodo = this.repo.save(foundTodo);
        return Optional.of(updatedTodo);

    }

    public List<Todo> findByPriority(String priority) {
        return this.repo.findByPriority(priority);
    }

    public Optional<Todo> archiveById(Long id) {
        Optional<Todo> result = this.findById(id);
        if (result.isEmpty()) {
            return result;
        }
        Todo foundTodo = result.get();
        foundTodo.setIsArchived(!foundTodo.getIsArchived());
        Todo updatedTodo = this.repo.save(foundTodo);
        return Optional.of(updatedTodo);
    }

}
