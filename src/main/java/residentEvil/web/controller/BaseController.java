package residentEvil.web.controller;

import org.springframework.web.servlet.ModelAndView;

public abstract class BaseController {

    protected ModelAndView view(String view, ModelAndView modelAndView) {
        modelAndView.setViewName(view);

        return modelAndView;
    }

    protected ModelAndView view(String view) {
        return view(view, new ModelAndView());
    }

    protected ModelAndView redirect(String url) {
        return view("redirect:" + url);
    }
}
