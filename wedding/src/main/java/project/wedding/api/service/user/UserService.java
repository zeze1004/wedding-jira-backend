package project.wedding.api.service.user;

import org.springframework.stereotype.Service;

import project.wedding.domain.CardBoard;
import project.wedding.dto.user.request.UserCreateRequest;
import project.wedding.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private CardBoard cardBoardId;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void saveUser(UserCreateRequest request) {
        if (!userRepository.isNotExistId(request.getUserId())) {
            throw new IllegalArgumentException("이미 존재하는 ID입니다.");
        }
        userRepository.save(request.getUserId()); // todo: loverId를 받아서 커플 도메인에 저장
    }
}
