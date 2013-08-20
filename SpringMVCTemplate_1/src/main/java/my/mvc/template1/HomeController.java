package my.mvc.template1;

import java.io.Writer;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	int i; //synchronous counter for GEt requests
	int j; //asynch counter for SSE requests
	static volatile float deg; // degrees value from XHR client HTML page, sending via SSe to another page	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	
	public static float getDeg(){
		return deg;
	}
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)  // "" works the same as "/"
	public String wuahahahome(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		System.out.println("date is: " + date);
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, Locale.CANADA); // w/o Locale to prevent mess with Cyrillic symbols
		
		String formattedDate = dateFormat.format(date);
		System.out.println("formatteddate is: " + formattedDate + " hello man!!");
		
		model.addAttribute("giveMeServerTime", formattedDate + " hello man!!");
		model.addAttribute("anotherAtt", "   Hi man!!");
		
		return "home";
	}
	
	@RequestMapping("/hello")
	public String answerHello(Model model){
		
		model.addAttribute("answerHello", "You've pressed this, young bastard!! " + i++ + " times");
		return "home";
	}
	
	@RequestMapping("SSE")
	public @ResponseBody void answerSSE(HttpServletResponse response, HttpServletRequest request) throws Exception{
//		System.out.println("SSE activated");	
		while(true){
		System.out.println("SSE: sending deg =" + deg);
		response.setContentType("text/event-stream");
		response.setCharacterEncoding("UTF-8");
		Writer writer = response.getWriter();
		writer.write("data:"+ deg +"\n\n");
		writer.flush();
		Thread.sleep(900);
		}
		
	}
	
	@RequestMapping("XHR")
	public @ResponseBody void answerXHR(HttpServletResponse response, HttpServletRequest request) throws Exception{
//	public String answerXHR(HttpServletResponse response, HttpServletRequest request) throws Exception{
		deg = Float.parseFloat(request.getParameter("deg"));
		System.out.println("XHR activated , degrees is: " + deg);
//		response.setHeader("Connection","close");
		Writer writer = response.getWriter();
		writer.write("hi from server! the server time is: " + i++);
		writer.flush();
//		Thread.sleep(3000);
//		return "hi";
	}
	
	@RequestMapping("getRemoteBar")
	public String giveHi(HttpServletResponse response, HttpServletRequest request) throws Exception{

		return "remoteBar";
	}
	
}
