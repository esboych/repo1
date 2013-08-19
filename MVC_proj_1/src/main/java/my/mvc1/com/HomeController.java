package my.mvc1.com;

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
import org.springframework.context.annotation.Scope;
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
	int i;	
	static int externalPushCounter;
	static boolean externalPushCounterIncremented = true;
	static int instanceId = 0;
	public HomeController(){ instanceId++; System.out.println("HomeController instance # " + instanceId + "created");}
	
	/**
	 * Simply selects the home view to render by returning its name.
	 * @throws IOException 
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model, HttpServletRequest request, 
					HttpServletResponse response) throws IOException {
		System.out.println("home page called!!");
		
		return "home";
	}
	
	@RequestMapping(value = "/remoteIncrement", method = RequestMethod.GET)
	public String remoteIncrement(Locale locale, Model model, HttpServletRequest request, 
					HttpServletResponse response) throws IOException {
		System.out.println("home page called!!");
		
		return "remoteIncrement";
	}
	
	@RequestMapping(value = "/SSE", method = RequestMethod.GET)
	public @ResponseBody void getSSE(Locale locale, Model model, HttpServletRequest request, 
			HttpServletResponse response) throws IOException {
		System.out.println("SSE called!!");  
		
		while(true){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			response.setContentType("text/event-stream");
			response.setCharacterEncoding("UTF-8");
			PrintWriter writer = response.getWriter();
			String responseData = "data: " + i++ + " Thead no# " + Thread.currentThread().getId() 
					+ " , instanceId= " + instanceId + "\n\n";
			writer.write(responseData);
			writer.flush();
//			System.out.println("SSE called " + i + " times");
		}
}

	@RequestMapping(value = "/complexSSE", method = RequestMethod.GET)
	public @ResponseBody void getComplexSSE(Locale locale, Model model, HttpServletRequest request, 
			HttpServletResponse response) throws IOException {
		System.out.println("complexSSE called!!");
		
		while(true){
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
//			if(!externalPushCounterIncremented) return; 
			response.setContentType("text/event-stream");
			response.setCharacterEncoding("UTF-8");
			PrintWriter writer = response.getWriter();
			String responseData = "data: {" + 
				"\"msg\":\"hello world\"," + 
				"\"id\":" + externalPushCounter +
				"}\n\n";
			writer.write(responseData);
			writer.flush();
			externalPushCounterIncremented = false;
//			System.out.println("SSE called " + i + " times");
		}
}

	@RequestMapping(value="/increment")
	public String handlePost(){

		externalPushCounter++;
		externalPushCounterIncremented = true;
		System.out.println("handlePost invoked" + externalPushCounter + " times");
		return "remoteIncrement";
	}
	

	
}
