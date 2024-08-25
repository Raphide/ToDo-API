package io.nology.todo.task;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.nology.todo.common.exceptions.NotFoundException;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;




@RestController
@RequestMapping("todos")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @PostMapping
    public ResponseEntity<Todo> createTodo(@Valid @RequestBody CreateTodoDTO data) throws Exception {
        Todo createdTodo = this.todoService.createTodo(data);
        System.out.println(createdTodo);
        return new ResponseEntity<Todo>(createdTodo, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Todo>> findAllTodos(){
        List<Todo> todos =  this.todoService.findAll();
        return new ResponseEntity<List<Todo>>(todos, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Todo> findTodoById(@PathVariable Long id) throws Exception{
        Optional<Todo> result = this.todoService.findById(id);
        Todo foundTodo = result.orElseThrow(()-> new NotFoundException("Could not find task with id "+ id));
        return new ResponseEntity<>(foundTodo, HttpStatus.OK);
    }

    @GetMapping("/priority={priority}")
    public ResponseEntity<List<Todo>> findTodosByPriority(@PathVariable String priority) throws Exception{
       Optional <List<Todo>> result = Optional.of(this.todoService.findByPriority(priority));
        List<Todo> foundTodo = result.orElseThrow(()-> new NotFoundException("Could not find task with priority "+ priority));
        return new ResponseEntity<List<Todo>>(foundTodo, HttpStatus.OK);
    }
    
    
    

    @PatchMapping("/{id}")
    public ResponseEntity<Todo> updateTodoById(@PathVariable Long id, @Valid @RequestBody UpdateTodoDTO data) throws NotFoundException{
        Optional<Todo> result = this.todoService.updateById(id, data);
        Todo foundTodo = result.orElseThrow(()-> new NotFoundException("Could not find task with id "+ id));
        return new ResponseEntity<>(foundTodo, HttpStatus.OK);
    }

    @PatchMapping("/complete/{id}")
    public ResponseEntity<Todo> completeTodo(@PathVariable Long id) throws NotFoundException{
        Optional<Todo> result = this.todoService.completeById(id);
        Todo foundTodo = result.orElseThrow(()-> new NotFoundException("Could not find task with id "+ id));
        return new ResponseEntity<>(foundTodo, HttpStatus.OK);
    }

    // @DeleteMapping("/{id}")
    // public ResponseEntity<Todo> deleteTodoById(@PathVariable Long id) throws NotFoundException{
    //     var result = this.todoService.deleteById(id);
    //     Todo foundTodo = result.orElseThrow(()-> new NotFoundException("Could not find task with id "+ id));
    //     return new ResponseEntity<>(foundTodo, HttpStatus.OK);
    // }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodoById(@PathVariable Long id) throws NotFoundException{
       boolean deleteSuccessful = this.todoService.deleteById(id);
       if(deleteSuccessful == false){
          throw new NotFoundException("Could not find task with id "+ id);
       }
      return new ResponseEntity<>(HttpStatus.NO_CONTENT); 
    }

}
