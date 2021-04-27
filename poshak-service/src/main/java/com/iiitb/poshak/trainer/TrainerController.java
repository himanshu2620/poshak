package com.iiitb.poshak.trainer;

import com.iiitb.poshak.util.ErrorDto;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

@RestController
public class TrainerController {

    @Resource
    private TrainerService trainerService;

    @ExceptionHandler(Exception.class)
    public ErrorDto handleException(Exception e) {
        e.printStackTrace();
        ErrorDto errorDto = new ErrorDto();
        errorDto.getError().add(e.getMessage());
        errorDto.setExceptionId("IE-" + System.currentTimeMillis());
        return errorDto;
    }

    @PutMapping(value = "/trainer")
    public List<TrainerGoal> setTrainerGoals(@RequestBody TrainerExcelRequest request) throws Exception {
        return trainerService.setTrainerGoals(request);
    }

    @GetMapping(value = "/trainer")
    public Set<TrainerGoal> getTrainerGoals(@RequestBody List<String> emails) throws Exception {
        //0 element is trainerEmail, 1st element is userEmail
        return trainerService.getTrainerGoals(emails);
    }


}
