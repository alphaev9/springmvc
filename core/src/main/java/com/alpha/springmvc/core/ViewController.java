package com.alpha.springmvc.core;

import com.alpha.springmvc.core.view.ExcelView;
import com.alpha.springmvc.domain.Address;
import com.alpha.springmvc.domain.Backlog;
import com.alpha.springmvc.domain.BacklogState;
import com.alpha.springmvc.domain.Cooperator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Date;

@Controller
@RequestMapping("view")
public class ViewController {

    @GetMapping("thymeleaf")
    public String thymeleaf(Model model) {
        Address address = new Address();
        address.setStreet("baoguang");
        address.setNumber(44);
        model.addAttribute("address", address);
        model.addAttribute("name", "alpha");
        return "thymeleaf/test";
    }

    @GetMapping("freemarker")
    public String freemarker(Model model) {
        Address address = new Address();
        address.setStreet("baoguang");
        address.setNumber(44);
        model.addAttribute("address", address);
        model.addAttribute("name", "alpha");
        return "freemarker/test";
    }

    @GetMapping("excel")
    public ModelAndView excel() {
        ModelAndView mv = new ModelAndView();
        ExcelView excelView = new ExcelView();
        mv.setView(excelView);
        Backlog backlog = createBacklog();
        mv.addObject("backlog", backlog);
        return mv;
    }

    @GetMapping("json")
    public String json(ModelMap model) {
        Backlog backlog = createBacklog();
        model.put("backlog", backlog);
        return "backlogJson";
    }


    private Backlog createBacklog() {
        Backlog backlog = new Backlog();
        backlog.setState(BacklogState.pending);
        backlog.setTitle("test");
        backlog.setDescription("it's test");
        backlog.setDueTime(new Date());
        backlog.setAddress(new Address("XiBao", 44));
        Cooperator alpha = new Cooperator("alpha", "alpha@qq.com");
        Cooperator laskin = new Cooperator("laskin", "laskin@qq.com");
        ArrayList<Cooperator> cooperators = new ArrayList<>();
        cooperators.add(alpha);
        cooperators.add(laskin);
        backlog.setCooperators(cooperators);
        return backlog;
    }
}
