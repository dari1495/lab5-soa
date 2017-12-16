package soa.web;

import org.apache.camel.Headers;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class SearchController {

    @Autowired
    private ProducerTemplate producerTemplate;

    @RequestMapping("/")
    public String index() {
        return "index";
    }


    @RequestMapping(value="/search")
    @ResponseBody
    public Object search(@RequestParam("q") String q) {
        Map<String, Object> headers = new HashMap<String, Object>();
        List<String> h = new ArrayList<>();
        if(q.contains("max:")){
            int n = Integer.parseInt(q.substring(q.length()-1));
            System.out.println("n: " + n);
            List<String> h2 = new ArrayList<>();
            h2.add(q.substring(q.length()-1));
            headers.put("CamelTwitterCount", h2);
            q = q.substring(0, q.indexOf("max"));
        }
        h.add(q);
        headers.put("CamelTwitterKeywords", h);
        System.out.println(headers.toString());

        return producerTemplate.requestBodyAndHeaders("direct:search", "", headers);
    }
}