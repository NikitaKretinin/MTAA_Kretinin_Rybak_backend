package fiit.mtaa.mtaa_backend.controllers;

import fiit.mtaa.mtaa_backend.models.Meal;
import fiit.mtaa.mtaa_backend.models.User;
import fiit.mtaa.mtaa_backend.repositories.MealRepository;
import fiit.mtaa.mtaa_backend.services.MealService;
import fiit.mtaa.mtaa_backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class MealController {
    @Autowired
    private MealService mealService;

    @GetMapping("/getMeals")
    public List<Meal> getAllMeals() {
        return mealService.getAllMeals();
    }

    @PostMapping("/addMeal")
    public Meal addProduct(@RequestBody Meal meal) {
        return mealService.saveMeal(meal);
    }

    @DeleteMapping("/deleteMeal/{id}")
    public Map<String, Boolean> deleteEmployee(@PathVariable(value = "id") Long mealID)
            throws ResourceNotFoundException {

        MealRepository mealRepository;

        Meal meal = mealRepository.findById(mealID);
                .orElseThrow(() -> new ResourceNotFoundException("Meal not found for this id :: " + mealID));

        mealService.deleteMeal(meal);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
