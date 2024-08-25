package io.nology.todo.task;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findByPriority(String priority);

}
