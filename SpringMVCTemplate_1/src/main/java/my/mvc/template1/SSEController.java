package my.mvc.template1;

import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SSEController {
	int i;

	
	@RequestMapping("SSE_1")
	public @ResponseBody void answerSSE(HttpServletResponse response, HttpServletRequest request) throws Exception{
//		System.out.println("SSE activated");	
		new Thread(){
			public void run(){		
				while(true){
					System.out.println("SSE_1 !!!: sending deg =" + HomeController.getDeg());
					response.setContentType("text/event-stream");
					response.setCharacterEncoding("UTF-8");
					Writer writer = response.getWriter();
					writer.write("data:"+ HomeController.getDeg() +"\n\n");
			//		writer.write("data:"+ i++ +"\n\n");
					writer.flush();
					Thread.sleep(900);
			    }
			}
		}.start();	
	}
}
