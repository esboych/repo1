package my2.web.com;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

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
	
	static int sense2intDec;
	int sensorSampleCount;
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	String formattedDate;

	@RequestMapping(value = "/local")
	public String webHome(Locale locale, Model model) {
		System.out.println("webHome is called");
		return "home";

	}
	
	@RequestMapping(value = "/SSE")
	public @ResponseBody void noKeys(Locale locale, Model model, HttpServletRequest req, HttpServletResponse resp) throws Exception {
		System.out.println("noKeys is called");
		while(true){
			Thread.sleep(500);
			
			Date date = new Date();
			DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);		
			formattedDate = dateFormat.format(date);
			
			resp.setContentType("text/event-stream");
			resp.setCharacterEncoding("UTF-8");
			PrintWriter out = resp.getWriter();
			String data = "data:" + sense2intDec + " uV , count= " + sensorSampleCount + " ,time is " + formattedDate + "\n\n";
			out.write(data);
			out.flush();
		}
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(@RequestParam ("value") String value, @RequestParam ("id") String id, Locale locale, Model model) {
		System.out.println("home is called");
		sensorSampleCount++;

		model.addAttribute("serverTime", formattedDate );
		System.out.println("server time " + formattedDate);
		System.out.println("server acccepted value=" + value);
		System.out.println("server acccepted id=" + id);
		String sense2str = value.substring(12, 16) + "0";
		System.out.println("sensor2 in HEX=" + sense2str);
		sense2intDec = Integer.parseInt(sense2str,16);
		System.out.println("sensor2 in mV=" + sense2intDec/1000 + " mV"); //per 100 because the last 4 bites are truncated from wifly
		return "home";
	}
	

	
}
