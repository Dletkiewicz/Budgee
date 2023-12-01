package pl.budgee.adapter.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class BudgetController {

  @GetMapping("/hello")
  public String hello() {
    return "Hello";
  }
}
