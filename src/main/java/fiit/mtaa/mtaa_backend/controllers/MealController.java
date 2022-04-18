package fiit.mtaa.mtaa_backend.controllers;

import fiit.mtaa.mtaa_backend.models.Meal;
import fiit.mtaa.mtaa_backend.services.MealService;
import fiit.mtaa.mtaa_backend.artifacts_data.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class MealController {
    @Autowired
    private MealService mealService;

    @GetMapping("/getMeals")
    public List<Meal> getAllMeals() {
        return mealService.getAllMeals();
    }

    @PostMapping("/addMeal")
    public Object addMeal(@ModelAttribute Meal meal,
                          @RequestPart(name="file", required = false) byte[] file,
                          @RequestHeader(value="Authorization") String token) {
        try {
            if (TokenManager.validToken(token, "manager")) {
                if (file != null) {
                    //byte[] bytearr = file.getBytes();
                    meal.setPhoto(file);
                }
                Meal result = mealService.saveMeal(meal);
                return new ResponseEntity<>(result, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getMeal/{id}")
    public ResponseEntity<Meal> getMealById(@PathVariable(value = "id") Long mealID)
            throws ResourceNotFoundException {
        try {
            Meal meal = mealService.getMealById(mealID);
            return new ResponseEntity<>(meal, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delMeal/{id}")
    public ResponseEntity<String> deleteMeal(@PathVariable(value = "id") Long mealID,
                                             @RequestHeader(value="Authorization") String token)
            throws ResourceNotFoundException {
        try {
            if (TokenManager.validToken(token, "manager")) {
                mealService.deleteMeal(mealID);
                return new ResponseEntity<>("Deleted", HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/editMeal/{id}")
    public Object editMeal(@RequestBody (required=false) Meal meal,
                           @PathVariable(value = "id") Long mealID,
                           @RequestHeader(value="Authorization") String token) {
        try {
            if (TokenManager.validToken(token, "manager")) {
                if (meal == null) { // if json body of request is empty
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
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
            } else {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
