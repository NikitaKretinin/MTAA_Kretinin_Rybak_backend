package fiit.mtaa.mtaa_backend.services;

import fiit.mtaa.mtaa_backend.models.Meal;
import fiit.mtaa.mtaa_backend.repositories.MealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MealService {
    @Autowired
    private MealRepository mealRepository;

    public List<Meal> getAllMeals() {
        return mealRepository.findAll();
    }

    public Optional<Meal> getMealById(Long id) {
        return mealRepository.findById(id);
    }

    public Meal saveMeal(Meal meal) {
        return mealRepository.save(meal);
    }

    public Meal deleteMeal(Long id) {
        Meal meal = mealRepository.findById(id);
        return mealRepository.delete(meal);
    }
}
