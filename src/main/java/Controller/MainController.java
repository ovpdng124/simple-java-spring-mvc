package Controller;

import Model.Content;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {
    public static List<Content> contents = new ArrayList<Content>(); //Tạo danh sách contents
    public static Integer autoId = 0;

    //    @RequestMapping(value = "/contentList", method = RequestMethod.GET)
    @GetMapping("/contentList")
    public String contentList(Model listModel) {
        listModel.addAttribute("contents", contents); //Gán giá trị contents trong danh sách vào model(content)
        return "contentList";

    }

    //    @RequestMapping(value = "/addContent", method = RequestMethod.GET)
    @GetMapping("/addContent")
    public String showAddContentPage(Model model) {
        return "addContent";
    }

    //    @RequestMapping(value = "/addContent", method = RequestMethod.POST)
    @PostMapping("/addContent")
    public String savedContent(@RequestParam("content") String str, Model model) {
        if (str == null || str.isEmpty()) {
            return "/addContent";
        } else {
            if (str != null && str.length() > 0) {

                Content newContent = new Content(++autoId, str);
                contents.add(newContent);//Gắn giá trị có được vào ArrayList

            }

        }
        return "redirect:/contentList";
    }


    @GetMapping("/editContent/{id}")
    public String showEditPage(@PathVariable("id") Integer id, Model model) {
        return "editContent";
    }

    @PostMapping("/editContent/{id}")
    public String editContent(@RequestParam("contentEdit") String strEdit, @PathVariable("id") Integer idEdit, Model model) {
        if (strEdit == null || strEdit.isEmpty()) {
            return "/editContent";
        } else {
            for (Content content : contents) {
                if (content.getId().equals(idEdit)) {
                    content.setContent(strEdit);
                }
            }
        }

        return "redirect:/contentList";

    }

    @GetMapping("removeContent/{id}")
    public String removeContent(@PathVariable("id") Integer id) {
        return "removeContent";
    }

    @PostMapping("removeContent/{id}")
    public String removeContent(@PathVariable("id") Integer id, Model model) {
        List<Content> toRemove = new ArrayList<Content>();
        for (Content content : contents) {
            if (content.getId().equals(id)) {
                toRemove.add(content);
            }
        }
        contents.removeAll(toRemove);

        return "redirect:/contentList";
    }
}
