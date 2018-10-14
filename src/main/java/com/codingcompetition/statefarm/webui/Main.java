package com.codingcompetition.statefarm.webui;

import com.codingcompetition.statefarm.Category;
import com.codingcompetition.statefarm.SearchCriteria;
import com.codingcompetition.statefarm.StreetMapDataInterpreter;
import com.codingcompetition.statefarm.model.PointOfInterest;
import com.codingcompetition.statefarm.utility.PointOfInterestParser;
import com.google.gson.Gson;
import org.xml.sax.SAXException;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.get;
public class Main {
    public static void main(String[] args) throws IOException, SAXException {
        Gson gson = new Gson();
        StreetMapDataInterpreter data = new StreetMapDataInterpreter("/large-metro.xml");
        System.out.println("Running on: http://localhost:4567");

        get("/", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "webui/map.vm");
        }, new VelocityTemplateEngine());

        get("/points", "application/json", (req, res) -> {
            return data.interpret();
        }, gson::toJson);

        get("/filter", (req, res) -> {
            String category = req.queryParams("category");
            String value = req.queryParams("value");

            return data.interpret(new SearchCriteria(Category.valueOf(category), value));
        }, gson::toJson);
    }

}
