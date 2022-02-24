package board.api.modules.articles;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/articles")
@RestController
public class ArticleController {

}

//modules ->
//            account
//                    service
//                    controller
//                    repository
//                    ...
//            article
//                    service
//                    controller
//                    repository
//                    ...
//            articleComment
//                    service
//                    controller
//                    repository
//                    ...
