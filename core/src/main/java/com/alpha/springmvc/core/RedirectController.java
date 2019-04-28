package com.alpha.springmvc.core;

import com.alpha.springmvc.domain.Address;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Map;

@Controller
@RequestMapping("redirect")
public class RedirectController {

    private static final Logger log = LogManager.getLogger(RedirectController.class);

    /**
     * by default,attributes in model which are primitive type or collection or array of primitive type are automatically appended to redirect url as query parameters.
     */
    @GetMapping("queryParameter")
    public String throughQueryParameter(Model model) {
        Address address = new Address();
        address.setStreet("baoguang");
        address.setNumber(44);
        model.addAttribute("age", "40");
        String[] hobbies = new String[]{"reading", "coding", "sport"};
        model.addAttribute("hobby", hobbies);
        model.addAttribute("address", address);
        return "redirect:redirect/test";
    }

    /**
     * besides through query parameter,by the way of path-variable is also feasible
     */
    @GetMapping("pathVariable/{age}")
    public View throughPathVariable() {
        return new RedirectView("/redirect/test/{age}");
    }

    /**
     * RedirectAttributes is special type aiming for storing data being used in redirect.
     * you can designate a method argument of RedirectAttributes type,given so,this argument would be act as model.
     */
    @GetMapping("redirectAttributes")
    public String throughRedirectAttributes(RedirectAttributes redirectAttributes, Model model) {
        Address address = new Address();
        address.setStreet("baoguang");
        address.setNumber(44);
        redirectAttributes.addAttribute("name", "alpha");
        redirectAttributes.addAttribute("address", address);

        model.addAttribute("age", 40);
        return "redirect:redirect/test";
    }

    /**
     * flash attribute provide a way for a request store data that are intended for use in another.
     * redirect is it's main battle filed.after redirect happened,flash attribute would gone to dead.
     * note intrinsically,flash attribute is special session attribute which life is very temporary.
     */

    @GetMapping("flashAttribute")
    public String throughFlashAttribute(RedirectAttributes redirectAttributes) {
        Address address = new Address();
        address.setStreet("baoguang");
        address.setNumber(44);
        redirectAttributes.addFlashAttribute("name", "alpha");
        redirectAttributes.addFlashAttribute("address", address);
        return "redirect:redirect/test";
    }


    @GetMapping("test")
    public void test(Model model) {
        Map<String, Object> map = model.asMap();
        String name = (String) map.get("name");
        Address address = (Address) map.get("address");
        log.info("name: " + name);
        log.info("address: " + address.getStreet() + ";" + address.getNumber());
    }

    @GetMapping("test/{age}")
    public void test(@PathVariable int age) {
        log.info("age: " + age);
    }
}
