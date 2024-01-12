package project.wedding.api.controller.user;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import project.wedding.api.service.cardboard.CardBoardService;
import project.wedding.api.service.user.UserService;
import project.wedding.dto.user.request.UserCreateRequest;

@RestController
@Tag(name = "User", description = "User API")
public class UserApiController {

    private final UserService userService;
    private final CardBoardService cardBoardService;

    public UserApiController(UserService userService, CardBoardService cardBoardService) {
        this.userService = userService;
        this.cardBoardService = cardBoardService;
    }

    @PostMapping("/api/v1/user")
    @Operation(summary = "create user", description = "유저 생성")
    public void saveUser(@RequestBody UserCreateRequest request) {
        userService.saveUser(request);
        cardBoardService.createCardBoard(request.getUserId());
    }

}
