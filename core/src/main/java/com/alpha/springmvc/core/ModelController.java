package com.alpha.springmvc.core;

import com.alpha.springmvc.core.conversion.AddressEditor;
import com.alpha.springmvc.domain.Address;
import com.alpha.springmvc.domain.Backlog;
import com.alpha.springmvc.domain.Cooperator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("model")
@SessionAttributes({"alpha", "lisa"})
public class ModelController {
    private static final Logger log = LogManager.getLogger(ModelController.class);

    @InitBinder
    public void config(WebDataBinder webDataBinder) {
        webDataBinder.registerCustomEditor(Address.class, new AddressEditor());
//        webDataBinder.addCustomFormatter(new AddressFormatter());
    }

    /**
     * notice Model responsibility is delivering data whatever it is to view.
     * remember model object is just over the request life.request over,it's over also.
     */
    @RequestMapping("why")
    public void modelWhy(Model model) {
        Address address = new Address();
        address.setStreet("baoguang");
        model.addAttribute("test", address);
    }

    /**
     * considering the following use case:
     * 1 user input through form field
     * 2 usually,inputs are converted and composed to a domain object
     * 3 in common,after business logic execution,domain object would be delivered to view to render
     *
     * @ModelAtribute can suffice.
     * notice if attribute is present,it would be update using request parameters,otherwise,new attribute is created and populated with request parameters.
     */
    @RequestMapping("formObject")
    public void modelForFormObject(@ModelAttribute("address") Address address) {
        log.info(address.getStreet() + "  " + address.getNumber());
    }

    /**
     * because binding between request parameters and object field is achieved on BeanWrapper,so the rules defined in BeanWrapper must be followed
     * given some field is a embedded object,for successful binding,the corresponding request parameter name must like format of "address.street"
     */
    @RequestMapping("embedded")
    public void embeddedObject(@ModelAttribute("backlog") Backlog backlog) {
        log.info(backlog.getAddress().getStreet());
    }

    /**
     * there is more complex case,in which object has collection type field such as List.
     * BeanWrapper defined access rules for this case and named this rule "indexed property".
     * for example,Backlog has a field cooperators typed List<Cooperator>,the rule demands request parameters named like"cooperators[index].name".
     */

    @RequestMapping("collection")
    public void collectionFieldBinding(@ModelAttribute Backlog backlog) {
        List<Cooperator> cooperators = backlog.getCooperators();
        log.info(cooperators.get(0).getName() + " " + cooperators.get(0).getEmail());
        log.info(cooperators.get(1).getName() + " " + cooperators.get(1).getEmail());

    }

    /**
     * @SessionAtributes create a store accessed in controller scope,which means that attributes in this store are just shared between methods in same controller.
     * notice if you want to access attributes created from @SessionAttribute in other controller,HttpSession is correct choice.
     */
    @RequestMapping("session")
    public void sessionAttributes(ModelMap model) {
        Address address = new Address();
        address.setStreet("baoguang");
        model.put("alpha", address);
    }

    @RequestMapping("sessionRemove")
    public void sessionAttributesRemove(SessionStatus status) {
        status.setComplete();
    }

    /**
     * if method annotated by a @ModelAttribute but without @RequestMapping,this method would be called before  execution of any other methods.
     * this provide a chance to initialize the model used by controller method.
     * the aim of the following example is make preparation for demonstrating key characteristic of @SessionAttribute.
     * in detail,an attribute named "lisa" is populated in http session through @SessionAttributes annotation,but the other named "surprise" is through HttpSession.
     * the former is treated in scope of controller,not used outside,but the latter is global,it can be accessed anywhere.
     * who can take the responsibility of accessing global attribute in session?
     * spring is so nice,which give you a shortcut named @SessionAttribute to serve you.
     */
    @ModelAttribute
    public void preModelAttribute(Model model, HttpSession session) {
        Address address = new Address();
        address.setStreet("gaoxin");
        model.addAttribute("lisa", address);
        session.setAttribute("surprise", "why?");
    }

    /**
     * notice attribute named "lisa" is from model which had been populated in execution of a special method named preModelAttribute.
     * here you can access it directly,but remember,after corresponding value object of key "lisa" is got from model,it would be updated on request parameters given which is present.
     */
    @RequestMapping("preModelAttribute")
    public void preModelAttribute(@ModelAttribute("lisa") Address address) {
        log.info(address.getStreet());
    }

    /**
     * notice there are a global session attribute named "surprise" and a controller scope session attribute named "lisa".
     * recall the description of @SessionAttribute,only global session attribute is its client,so it's a problem how to avoid binding failure of "lisa".
     * "required" attribute in @SessionAttribute annotation is we need.
     */
    @RequestMapping("sessionAttribute")
    public void sessionAttribute(@SessionAttribute(value = "lisa", required = false) Address address,
                                 @SessionAttribute(value = "surprise", required = false) String surprise) {
        log.info(address);
        log.info(surprise);
    }


    @RequestMapping("test")
    public void test(@SessionAttribute("alpha") Address address) {
        log.info(address.getStreet());
    }


}
