package com.github.zmilad97.sodukusolverapp.controller;

import com.github.zmilad97.sodukusolverapp.service.SudokuSolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class ApiController {
    private final SudokuSolver sudokuSolver;

    @Autowired
    public ApiController(SudokuSolver sudokuSolver) {
        this.sudokuSolver = sudokuSolver;
    }


    @RequestMapping(value = "/solve", method = RequestMethod.POST)
    public List<Integer[]> solve(@RequestBody HashMap<String, String> data) {
        if (sudokuSolver.beginSolve(data.get("board")))
            return sudokuSolver.getSolved();
        else
            return new ArrayList<>();
    }
}