package com.alpha.springmvc.core;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("mapping")
public class RequestMappingController {
    private static final Logger log = LogManager.getLogger(RequestMappingController.class);

    /**
     * request mapping mainly relies on url pattern,but there are other information existing in request which are helpful
     * for narrowing request mapping,such as http method,parameters,headers etc.
     */
    @RequestMapping(value = "get", method = RequestMethod.GET, params = {"id"})
    public void getBacklogById() {
        log.info("request is mapped to ------ getBacklogById");
    }

    /**
     * single asterisk just only matches within a path segment.
     * for example,"/get/id/2" includes 3 segments
     */
    @GetMapping(value = "get/*")
    public void getBacklogWithSingleAsterisk() {
        log.info("request is mapped to ------ getBacklogWithSingleAsterisk");
    }

    /**
     * double asterisks can suffices multiple path segments matching.
     */
    @GetMapping(value = "get/**")
    public void getBacklogWithDoubleAsterisk() {
        log.info("request is mapped to ------ getBacklogWithDoubleAsterisk");
    }

    /**
     * path segment can be represented uri variable which is placeholder replaced by real path segment when request arrived
     * */
    @RequestMapping(value = "get/backlog/{id}")
    public void getBacklogWithPathVariable() {
        log.info("request is mapped to ------ getBacklogWithPathVariable");
    }

    /**
     * how to test the post http method and meanwhile specify the request content type?
     * obviously,writing javascript code is viable,but it's luxury,you just want a test.
     * spring provides all things you need.
     */
    @PostMapping(value = "add", consumes = "application/json")
    public void addBacklog() {
        log.info("request is mapped to ------- addBacklog");
    }

    /**
     * similar the case represented above,produce attribute can also narrowed the request mapping
     * */
    @GetMapping(value = "get/backlog/{id}", produces = "application/json")
    public void getBacklogByJson(@PathVariable String id) {
        log.info("request is mapped to ------- getBacklogByJson  " + id);
    }

    /**
     * by default,the suffix would be ignored when request url mapping occurring.
     * if you want to consider suffix in mapping process,PathMatchConfigurer should be re-config.
     * */
    @GetMapping("suffix/backlog")
    public void suffixMatch(){
        log.info("request is mapped to ------- suffixMatch");
    }
}
