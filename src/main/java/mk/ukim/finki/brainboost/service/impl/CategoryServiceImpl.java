package mk.ukim.finki.brainboost.service.impl;

import mk.ukim.finki.brainboost.domain.Category;
import mk.ukim.finki.brainboost.repository.CategoryRepository;
import mk.ukim.finki.brainboost.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> listAll() {
        return this.categoryRepository.findAll();
    }
}
