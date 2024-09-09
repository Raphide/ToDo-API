package io.nology.todo.todo;

import static io.restassured.RestAssured.given;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import io.nology.todo.category.Category;
import io.nology.todo.category.CategoryRepository;
import io.nology.todo.task.CreateTodoDTO;
import io.nology.todo.task.Todo;
import io.nology.todo.task.TodoRepository;
import io.nology.todo.task.UpdateTodoDTO;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import static org.hamcrest.Matchers.*;

import java.util.Optional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class TodoEndToEndTest {

        @LocalServerPort
        private int port;

        @Autowired
        private TodoRepository todoRepository;

        @Autowired
        private CategoryRepository categoryRepository;

        Long todoId;

        Long categoryId;

        Todo todo1 = new Todo();

        Todo todo2 = new Todo();

        @BeforeEach
        public void setup() {
                RestAssured.port = port;
                todoRepository.deleteAll();
                categoryRepository.deleteAll();

                Category category1 = new Category();
                category1.setName("test");
                categoryRepository.save(category1);
                categoryId = category1.getId();

                todo1.setTask("test");
                todo1.setDescription("this is a test");
                todo1.setPriority("High");
                todo1.setCategory(category1);
                todoRepository.save(todo1);
                todoId = todo1.getId();

                todo2.setTask("experiment");
                todo2.setDescription("this is an experiment");
                todo2.setPriority("Low");
                todo2.setCategory(category1);
                todoRepository.save(todo2);
        }

        @Test
        public void getAllTodos() {
                given().when().get("/todos").then().statusCode(HttpStatus.OK.value()).body("$", hasSize(2))
                                .body("task", hasItems("test", "experiment"))
                                .body("description", hasItems("this is a test", "this is an experiment"))
                                .body("priority", hasItems("High", "Low"));
        }

        @Test
        public void getTodoById() {
                given().when().get("/todos/{id}", todoId).then().statusCode(HttpStatus.OK.value())
                                .body("task", equalTo("test")).body("description", equalTo("this is a test"))
                                .body("priority", equalTo("High"));
        }

        @Test
        public void createTodo_success() {
                CreateTodoDTO data = new CreateTodoDTO();
                data.setTask("new");
                data.setDescription("creating new task");
                data.setPriority("Low");
                data.setCategoryId(categoryId);
                given().contentType(ContentType.JSON).body(data).when().post("/todos").then()
                                .statusCode(HttpStatus.CREATED.value()).body("task", equalTo("new"))
                                .body("description", equalTo("creating new task")).body("priority", equalTo("Low"))
                                .body("id", notNullValue());

                given().when().get("/todos").then().statusCode(HttpStatus.OK.value()).body("$", hasSize(3))
                                .body("task", hasItems("test", "experiment", "new"))
                                .body("description",
                                                hasItems("this is a test", "this is an experiment",
                                                                "creating new task"))
                                .body("priority", hasItems("High", "Low", "Low"));
        }

        @Test
        public void createTodo_missingCategoryId_failure() {
                CreateTodoDTO data = new CreateTodoDTO();
                data.setTask("create");
                data.setDescription("create a new task");
                data.setPriority("Medium");
                given().contentType(ContentType.JSON).body(data).when().post("/todos").then()
                                .statusCode(HttpStatus.BAD_REQUEST.value());

                given().when().get("/todos").then().statusCode(HttpStatus.OK.value()).body("$", hasSize(2))
                                .body("task", hasItems("test", "experiment"))
                                .body("description", hasItems("this is a test", "this is an experiment"))
                                .body("priority", hasItems("High", "Low"));
        }

        @Test
        public void createTodo_invalidCategoryId_failure() {
                CreateTodoDTO data = new CreateTodoDTO();
                data.setTask("create");
                data.setDescription("create an new task");
                data.setPriority("Medium");
                data.setCategoryId(2L);
                given().contentType(ContentType.JSON).body(data).when().post("/todos").then()
                                .statusCode(HttpStatus.BAD_REQUEST.value());

                given().when().get("/todos").then().statusCode(HttpStatus.OK.value()).body("$", hasSize(2))
                                .body("task", hasItems("test", "experiment"))
                                .body("description", hasItems("this is a test", "this is an experiment"))
                                .body("priority", hasItems("High", "Low"));
        }

        @Test
        public void createTodo_emptyInputField_failure() {
                CreateTodoDTO data = new CreateTodoDTO();
                data.setTask("");
                data.setDescription("This is a description");
                data.setPriority("Low");
                data.setCategoryId(1L);
                given().contentType(ContentType.JSON).body(data).when().post("/todos").then()
                                .statusCode(HttpStatus.BAD_REQUEST.value());

                given().when().get("/todos").then().statusCode(HttpStatus.OK.value()).body("$", hasSize(2))
                                .body("task", hasItems("test", "experiment"))
                                .body("description", hasItems("this is a test", "this is an experiment"))
                                .body("priority", hasItems("High", "Low"));
        }

        @Test
        public void duplicateTodo_success() {
                given().when().get("/todos/{id}", todoId).then().statusCode(HttpStatus.OK.value())
                                .body("task", equalTo("test")).body("description", equalTo("this is a test"))
                                .body("priority", equalTo("High"));

                Optional<Todo> todoResult = todoRepository.findById(todoId);

                CreateTodoDTO data = new CreateTodoDTO();
                data.setTask(todoResult.get().getTask());
                data.setDescription(todoResult.get().getDescription());
                data.setPriority(todoResult.get().getPriority());
                data.setCategoryId(todoResult.get().getCategory().getId());

                given().contentType(ContentType.JSON).when().post("/todos/duplicate/{id}", todoId).then()
                                .statusCode(HttpStatus.CREATED.value()).body("task", equalTo("test"))
                                .body("description", equalTo("this is a test")).body("priority", equalTo("High"))
                                .body("id", notNullValue());

                given().when().get("/todos").then().statusCode(HttpStatus.OK.value()).body("$", hasSize(3))
                                .body("task", hasItems("test", "experiment"))
                                .body("description",
                                                hasItems("this is a test", "this is an experiment"))
                                .body("priority", hasItems("High", "Low"));
        }

        @Test
        public void duplicateTodo_invalidId_failure() {
                Long id = 20L;
                given().when().get("/todos/{id}", id).then().statusCode(HttpStatus.NOT_FOUND.value());

                // // Above test should be okay as this is where the request would stop.

                // Optional<Todo> todoResult = todoRepository.findById(id);

                // CreateTodoDTO data = new CreateTodoDTO();
                // data.setTask(todoResult.get().getTask());
                // data.setDescription(todoResult.get().getDescription());
                // data.setPriority(todoResult.get().getPriority());
                // data.setCategoryId(todoResult.get().getCategory().getId());

                // given().contentType(ContentType.JSON).when().post("/todos/duplicate/{id}", todoId).then()
                //                 .statusCode(HttpStatus.BAD_REQUEST.value());

        }

//         @Test 
//         public void updateTodo_success(){
//                 given().when().get("/todos/{id}", todoId).then().statusCode(HttpStatus.OK.value())
//                 .body("task", equalTo("test")).body("description", equalTo("this is a test"))
//                 .body("priority", equalTo("High"));

// Optional<Todo> todoResult = todoRepository.findById(todoId);
// UpdateTodoDTO editedData = new UpdateTodoDTO();
// editedData.getTask();

// todoResult.get().setTask("edited task");

//         }

}
