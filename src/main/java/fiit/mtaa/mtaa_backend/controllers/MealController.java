package fiit.mtaa.mtaa_backend.controllers;

import fiit.mtaa.mtaa_backend.models.Contact;
import fiit.mtaa.mtaa_backend.models.Meal;
import fiit.mtaa.mtaa_backend.models.User;
import fiit.mtaa.mtaa_backend.repositories.MealRepository;
import fiit.mtaa.mtaa_backend.services.MealService;
import fiit.mtaa.mtaa_backend.services.UserService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public ResponseEntity<Meal> addMeal(@ModelAttribute Meal meal, @RequestPart(name="file", required = false) MultipartFile file) {
        try {

            if (file != null) {
                byte[] bytearr = file.getBytes();
                meal.setPhoto(bytearr);
            }
            Meal result = mealService.saveMeal(meal);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getMeal/{id}")
    public ResponseEntity<Meal> getMealById(@PathVariable(value = "id") Long mealID)
            throws ResourceNotFoundException {
        try {
            Meal meal = mealService.getMealById(mealID);
            return new ResponseEntity<>(meal, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delMeal/{id}")
    public ResponseEntity<String> deleteMeal(@PathVariable(value = "id") Long mealID)
            throws ResourceNotFoundException {
        try {
            mealService.deleteMeal(mealID);
            return new ResponseEntity<>("Deleted", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/editMeal/{id}")
    public ResponseEntity<Meal> editMeal(@RequestBody (required=false) Meal meal, @PathVariable(value = "id") Long mealID) {
        try {
            if (meal == null) { // if json body of request is empty
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
            Meal edit_meal = mealService.getMealById(mealID);
            if (meal.getName() != null) {
                edit_meal.setName(meal.getName());
            }
            if (meal.getDescription() != null) {
                edit_meal.setDescription(meal.getDescription());
            }
            if (meal.getPrice() != null) {
                edit_meal.setPrice(meal.getPrice());
            }
            if (meal.getPhoto() != null) {
                edit_meal.setPhoto(meal.getPhoto());
            }
            edit_meal = mealService.saveMeal(edit_meal);
            return new ResponseEntity<>(edit_meal, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
