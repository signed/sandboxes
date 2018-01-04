package app;

import app.utils.SparkUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Spark;

import static spark.Spark.get;

public class ApplicationMain {

    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(ApplicationMain.class);
		Spark.port(8899);
        SparkUtils.createServerWithRequestLog(logger);

        get("/hello", (request, response) -> "world");
    }

}
