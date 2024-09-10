package io.nology.todo.category;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
import org.springframework.test.util.ReflectionTestUtils;

import io.nology.todo.common.exceptions.ServiceValidationException;

public class CategoryServiceUnitTest {
    @Mock
    private CategoryRepository repo;

    @Mock
    private ModelMapper mapper;

    // @Mock
    // private Category Category;

    @InjectMocks
    @Spy
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void findAll() {
        categoryService.findAll();
        verify(repo).findAll();
    }

    @Test
    public void findById() {
        Long categoryId = 1L;
        categoryService.findById(categoryId);
        verify(repo).findById(categoryId);
    }

    @Test
    public void create_success() throws Exception {
        // given
        CreateCategoryDTO mockDTO = new CreateCategoryDTO();
        mockDTO.setName("test");
        Category mockCategory = new Category();
        // when
        when(mapper.map(mockDTO, Category.class)).thenReturn(mockCategory);
        when(repo.existsByName(mockDTO.getName())).thenReturn(false);
        when(repo.save(any(Category.class))).thenReturn(mockCategory);
        // then
        Category result = categoryService.createCategory(mockDTO);
        assertNotNull(result);
        assertEquals(mockCategory, result);
        verify(repo).save(mockCategory);
    }

    @Test
    public void create_existingCategory_failure() {
        // given
        CreateCategoryDTO mockDTO = new CreateCategoryDTO();
        mockDTO.setName("test");
        Category mockCategory = new Category();
        // when
        when(mapper.map(mockDTO, Category.class)).thenReturn(mockCategory);
        when(repo.existsByName(mockDTO.getName())).thenReturn(true);
        // then
        assertThrows(ServiceValidationException.class, () -> categoryService.createCategory(mockDTO));
        verify(repo, never()).save(any());
    }

    @Test
    public void deleteById_success() {
    Long categoryId = 1L;
    Category mockCategory = new Category();
     // ReflectionTestUtils.setField(mockCategory, "id", categoryId);
    Optional<Category> result = Optional.of(mockCategory);
    when(repo.findById(categoryId)).thenReturn(result);
    Boolean isDeleted = categoryService.deleteById(categoryId);
    assertEquals(true, isDeleted);
    verify(repo).delete(mockCategory);    
    }

    @Test
    public void deleteById_failure() {
        Long categoryId = 20L;
        Optional<Category> result = Optional.empty();
        when(repo.findById(categoryId)).thenReturn(result);
        Boolean isDeleted = categoryService.deleteById(categoryId);
        assertEquals(false, isDeleted);
        verify(repo, never()).delete(any());
    }

}