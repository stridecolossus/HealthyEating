package uk.co.mayden.shopping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ShoppingController {
	@GetMapping("/")
	public String home() { // @RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
System.err.println("ROOT");
		return "home";
	}

	@GetMapping("/hello")
	public String hello() { // @RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
System.err.println("HELLO");
//		model.addAttribute("name", name);
		return "hello";
	}
////	@GetMapping("/greeting")
////	public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
////		model.addAttribute("name", name);
////		return "greeting";
////	}
}
