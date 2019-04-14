package com.alpha.springmvc.core;

import com.alpha.springmvc.domain.Address;
import com.alpha.springmvc.domain.Backlog;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("argument")
public class HandlerArgumentController {
    private static final Logger log = LogManager.getLogger(RequestMappingController.class);

    /**
     * if request parameter name is identical with method parameter name,no intervention.data binding automatically
     * if you choice a different method parameter name,it must to be done that using @RequestParam annotation to explicitly indicate data binding between request parameter and method parameter.
     * similar with intention mentioned in the getBacklogByDueTime method,data type conversion is still ignored by design.
     */
    @GetMapping(value = "get")
    public void getBacklogByTitleAndDueTime(String title, @RequestParam("dueTime") String maturity) {
        log.info("title: " + title + "; dueTime: " + maturity);
    }


    /**
     * do you remember getParameters in Servlet API? using @RequestParam annotation,request parameters with same name could also be retrieved.
     */
    @PostMapping("addCooperators")
    public void getCooperators(@RequestParam String[] cooperators) {
        for (String cooperator : cooperators) {
            log.info(cooperator);
        }
    }

    /**
     * parameter dueTime should be Date type,but now let's put this problem aside
     * temporarily,we use String type which can avoid data conversion and so make us focusing on request mapping
     */
    @GetMapping(value = "get/{dueTime}")
    public String getBacklogByDueTime(@PathVariable String dueTime) {
        log.info("request is mapped to ------ getBacklogByDueTime  " + dueTime);
        return "mapping_success";
    }

    /**
     * you can use ${...} placeholder in url pattern,before request mapping,
     * a bean which type is PropertySourcesPlaceholderConfigurer,actual a BeanPostProcessor can take effect,
     * which will look up all placeholders and replace with real values.these values may be from certain external data source,such as properties file and so on.
     */
    @GetMapping(value = "get/${db}")
    public String getBacklogFromDb(@PathVariable String db) {
        log.info("request is mapped to ------ getBacklog  " + db);
        return "mapping_success";
    }

    /**
     * this demo shows how to retrieve information carried with request header.
     * note that if some header is a serial of strings split by comma,you can use string array for binding
     */
    @GetMapping(value = "getHeader")
    public void handleWithRequestHeader(@RequestHeader("Accept-Encoding") String encoding, @RequestHeader("Accept") String[] accept) {
        log.info(encoding + "  " + accept[0]);
    }

    /**
     * if you want to get cookie value,@CookieValue annotation can help you
     */
    @GetMapping(value = "getCookieValue")
    public void getCookieValue(@CookieValue("JSESSIONID") String sessionId) {
        log.info(sessionId);
    }

    /**
     * matrix-variable:shapes like /get/backlog;id=1;title=test
     * you can use matrix variable to carry request information
     */
    @GetMapping("matrix/backlog")
    public void getMatrixVariable(@MatrixVariable String id, @MatrixVariable("title") String backlog) {
        log.info("id: " + id + "  title: " + backlog);
    }

    @PostMapping("attachment")
    public void getMultiPart(@RequestParam MultipartFile attachment) throws IOException {
        log.info(attachment.getName());
        byte[] bytes = attachment.getBytes();
        log.info(new String(bytes));
    }


    /**
     * if the method parameter is a domain object,there is a easy way for binding request parameters
     * the binding might be automatic,provided request parameters have identical names with domain object property.
     * indeed,the setter method actually is critical role for properly binding.you can modify some setter method name which differs from corresponding property
     * and then observe whether binding succeeds or not.
     */
    @PostMapping(value = "addAddress")
    public void addAddress(Address address) {
        log.info(address.getStreet() + "  " + address.getNumber());
    }

    /**
     * similar with the case mentioned above,provided keeping identical between request parameter and domain object property,automatic binding succeed.
     * note that if some property is an other object,you should use hierarchical name,which format like address.street
     */
    @PostMapping(value = "addBacklogWithAddress")
    public void addBacklogWithAddress(Backlog backlog) {
        log.info(backlog.getTitle() + " " + backlog.getAddress().getStreet() + " " + backlog.getAddress().getNumber());
    }

    /**
     * if method parameter is a domain object and includes property typed collection,how to achieve binding?
     * note that you should still use hierarchical name,which shapes like cooperators[0].name
     */
    @PostMapping(value = "addBacklogWithCooperators")
    public void addBacklogWithCooperators(Backlog backlog) {
        log.info(backlog.getCooperators().get(0).getName() + " " + backlog.getCooperators().get(0).getEmail());
    }

}
