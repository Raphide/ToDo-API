package io.nology.todo.category;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repo;

    @Autowired
    private ModelMapper mapper;

    public Category createCategory(@Valid CreateCategoryDTO data) throws Exception {
  
        if(repo.existsByName(data.getName().trim())){
            throw new Exception("category already exists");
        }
        Category newCategory = mapper.map(data, Category.class);
        return this.repo.save(newCategory);

    }

    public List<Category> findAll() {
        return this.repo.findAll();
    }

    public Optional<Category> findById(Long categoryId) {
        return this.repo.findById(categoryId);
    }

    public boolean deleteById(Long id) {
        Optional<Category> result = this.findById(id);
        if (result.isEmpty()) {
            return false;
        }
        this.repo.delete(result.get());
        return true;
    }



}
