package my2.web.com;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static int sense0intDec, sense1intDec, sense2intDec, sense3intDec, sense4intDec, sense5intDec, sense6intDec;
	private static int sensorSampleCount;
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	private static String formattedDate;
	private static boolean isUpdated;

	@RequestMapping(value = "/local")
	public String webHome(Locale locale, Model model) {
		System.out.println("webHome is called");
		return "home";

	}
	
	@RequestMapping(value = "/SSE")
	public @ResponseBody void noKeys(Locale locale, Model model, HttpServletRequest req, HttpServletResponse resp) throws Exception {
		System.out.println("noKeys is called");

		while(true){
			Thread.sleep(1200);
			
			Date date = new Date();
			DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);		
			formattedDate = dateFormat.format(date);
			isUpdated=false;
			resp.setContentType("text/event-stream");
			resp.setCharacterEncoding("UTF-8");  
			PrintWriter out = resp.getWriter();
			Random random = new Random();
//			String data = "data:" + sense2intDec + " uV , count= " + sensorSampleCount + " ,time is " + formattedDate + "\n\n";
//			String data = "data:" + sense2intDec + "\n\n";
			String data = "data:{" + 
					    "\"count\":" + sensorSampleCount +"," +
					    "\"sens0\":" + sense0intDec +"," +
					    "\"sens1\":" + sense1intDec +"," +
					    "\"sens2\":" + sense2intDec +"," +
					    "\"sens3\":" + sense3intDec +"," +
					    "\"sens4\":" + sense4intDec +"," +
					    "\"sens5\":" + sense5intDec +"," +
						"\"sens6\":" + sense6intDec  +
						"}\n\n";
			out.write(data);  
			out.flush();

		}
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(@RequestParam ("value") String value, Locale locale, Model model) {
		System.out.println("home is called");
		sensorSampleCount++;
		System.out.println("form home: isUpdated is true");
		model.addAttribute("serverTime", formattedDate );
		System.out.println("server time " + formattedDate);
		System.out.println("server acccepted value=" + value);
//		System.out.println("server acccepted id=" + id);
		String sense0str = value.substring(4, 8) + "0";
		String sense1str = value.substring(8, 12) + "0";
		String sense2str = value.substring(12, 16) + "0";  //initial
		String sense3str = value.substring(16, 20) + "0";
		String sense4str = value.substring(20, 24) + "0";
		String sense5str = value.substring(24, 28) + "0";
		String sense6str = value.substring(28, 32) + "0";	
//		sense0intDec = 100;
//		sense1intDec = 2000;
//		System.out.println("sensor2 in HEX=" + sense2str);
		sense0intDec = Integer.parseInt(sense0str,16);
		sense1intDec = Integer.parseInt(sense1str,16);
		sense2intDec = Integer.parseInt(sense2str,16);
		sense3intDec = Integer.parseInt(sense3str,16);
		sense4intDec = Integer.parseInt(sense4str,16);
		sense5intDec = Integer.parseInt(sense5str,16);
		sense6intDec = Integer.parseInt(sense6str,16);
		System.out.println("sensor2 in mV=" + sense2intDec/1000 + " mV"); //per 100 because the last 4 bites are truncated from wifly
		return "home";
	}
	

	
}
