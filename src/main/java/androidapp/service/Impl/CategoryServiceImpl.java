package androidapp.service.Impl;

import androidapp.entity.CategoryEntity;
import androidapp.repository.CategoryRepository;
import androidapp.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    List<CategoryEntity> categoryList;
    @Override
    public List<CategoryEntity> getAllCategories() {
        return categoryRepository.findAll();
    }
}
