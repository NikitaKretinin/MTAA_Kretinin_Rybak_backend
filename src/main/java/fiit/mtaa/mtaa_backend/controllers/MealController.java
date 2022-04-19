package fiit.mtaa.mtaa_backend.controllers;

import fiit.mtaa.mtaa_backend.models.Meal;
import fiit.mtaa.mtaa_backend.services.MealService;
import fiit.mtaa.mtaa_backend.artifacts_data.TokenManager;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
public class MealController {
    @Autowired
    private MealService mealService;

    @GetMapping("/getMeals")
    public Object getAllMeals() {
        List<Meal> result = mealService.getAllMeals();
        List<JSONObject> resJson = new ArrayList<>();
        for (Meal meal : result) {
            JSONObject tmp = new JSONObject();
            tmp.put("id", meal.getId());
            tmp.put("price", meal.getPrice());
            tmp.put("description", meal.getDescription());
            tmp.put("name", meal.getName());
            tmp.put("photo", meal.getPhoto());
            resJson.add(tmp);
        }
        return new ResponseEntity<>(resJson, HttpStatus.OK);
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
                JSONObject jo = new JSONObject();
                jo.put("id", result.getId());
                jo.put("price", result.getPrice());
                jo.put("description", result.getDescription());
                jo.put("name", result.getName());
                jo.put("photo", result.getPhoto());
                return new ResponseEntity<>(jo, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getMeal/{id}")
    public ResponseEntity<JSONObject> getMealById(@PathVariable(value = "id") Long mealID)
            throws ResourceNotFoundException {
        try {
            Meal meal = mealService.getMealById(mealID);
            JSONObject jo = new JSONObject();
            jo.put("id", meal.getId());
            jo.put("price", meal.getPrice());
            jo.put("description", meal.getDescription());
            jo.put("name", meal.getName());
            jo.put("photo", meal.getPhoto());
            return new ResponseEntity<>(jo, HttpStatus.OK);
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
    public Object editMeal(@ModelAttribute Meal meal,
                           @RequestPart(name="file", required = false) byte[] file,
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
                if (file != null) {
                    edit_meal.setPhoto(file);
                }
                edit_meal = mealService.saveMeal(edit_meal);

                JSONObject jo = new JSONObject();
                jo.put("id", edit_meal.getId());
                jo.put("price", edit_meal.getPrice());
                jo.put("description", edit_meal.getDescription());
                jo.put("name", edit_meal.getName());
                jo.put("photo", edit_meal.getPhoto());

                return new ResponseEntity<>(jo, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
