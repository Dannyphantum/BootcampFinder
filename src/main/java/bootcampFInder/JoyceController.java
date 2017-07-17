package bootcampFInder;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class JoyceController{

@RequestMapping("/search")
    public String gosearch(){
    return "base"
}

}

