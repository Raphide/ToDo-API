package io.nology.todo.todo;

import static io.restassured.RestAssured.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;

import io.nology.todo.category.Category;
import io.nology.todo.category.CategoryService;
import io.nology.todo.common.exceptions.ServiceValidationException;
import io.nology.todo.task.CreateTodoDTO;
import io.nology.todo.task.Todo;
import io.nology.todo.task.TodoRepository;
import io.nology.todo.task.TodoService;

public class TodoServiceUnitTest {

    @Mock
    private TodoRepository repo;

    @Mock
    private CategoryService categoryService;

    @Mock
    private ModelMapper mapper;

    @Spy
    @InjectMocks
    private TodoService service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

    }

    @Test
    public void findAll() {
        service.findAll();
        verify(repo).findAll();
    }

    @Test
    public void findById() {
        Long todoId = 1L;
        service.findById(todoId);
        verify(repo).findById(todoId);
    }

    @Test
    public void createTodo_success() throws Exception {
        CreateTodoDTO MockDTO = new CreateTodoDTO();
        MockDTO.setCategoryId(1L);
        Category newCat = new Category();
        newCat.setName("testCat");
        Optional<Category> result = Optional.of(newCat);
        Todo newTodo = new Todo();
        MockDTO.setTask("test");
        MockDTO.setDescription("testing a test");
        MockDTO.setPriority("low");
        when(mapper.map(MockDTO, Todo.class)).thenReturn(newTodo);
        when(categoryService.findById(1L)).thenReturn(result);
        when(repo.save(any(Todo.class))).thenReturn(newTodo);
        Todo todoResult = service.createTodo(MockDTO);
        assertNotNull(todoResult);
        assertEquals(newTodo, todoResult);
        verify(repo).save(newTodo);
    }

    @Test
    void createTodo_failure() throws Exception {
        CreateTodoDTO mockDTO = new CreateTodoDTO();
        mockDTO.setCategoryId(20L);
        mockDTO.setTask("a");
        mockDTO.setDescription("");
        mockDTO.setPriority("");
        Todo newTodo = new Todo();
        when(mapper.map(mockDTO, Todo.class)).thenReturn(newTodo);
        when(categoryService.findById(20L)).thenReturn(null);
        assertThrows(NullPointerException.class, () -> service.createTodo(mockDTO));
        verify(repo, never()).save(any());
    }

    @Test
    public void deleteById_success() {
        Long todoId = 1L;
        Todo mockTodo = new Todo();
        Optional<Todo> result = Optional.of(mockTodo);
        when(repo.findById(todoId)).thenReturn(result);
        Boolean isDeleted = service.deleteById(todoId);
        assertEquals(true, isDeleted);
        verify(repo).delete(mockTodo);
    }

    @Test
    public void deleteById_failure(){
        Long todoId =  20L;
        Optional<Todo> result = Optional.empty();
        when(repo.findById(todoId)).thenReturn(result);
        Boolean isDeleted = service.deleteById(todoId);
        assertEquals(false, isDeleted);
        verify(repo, never()).delete(any());
    }

    // @Test
    // void duplicateTodo_success() throws Exception {
    //     Long todoId = 1L;
    //     Optional<Todo> result = service.findById(todoId);
    //     Todo foundTodo = result.get();
    //     CreateTodoDTO mockDTO = new CreateTodoDTO();
    //     mockDTO.setCategoryId(foundTodo.getCategory().getId());
    //     mockDTO.setTask(foundTodo.getTask());
    //     mockDTO.setDescription(foundTodo.getDescription());
    //     mockDTO.setPriority(foundTodo.getPriority());
    //     Todo newTodo = new Todo();
    //     when(mapper.map(mockDTO, Todo.class)).thenReturn(newTodo);
    //     // when(categoryService.findById(1L)).thenReturn(result);
    //     when(repo.save(any(Todo.class))).thenReturn(newTodo);
    //     Todo todoResult = service.createTodo(mockDTO);
    //     assertNotNull(todoResult);
    //     assertEquals(newTodo, todoResult);
    //     verify(repo).save(newTodo);
    // }

}
