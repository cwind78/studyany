package com.example.studyany.ctrl.user;

import com.example.studyany.map.user.UserMapper;
import com.example.studyany.vo.common.Rslt;
import com.example.studyany.vo.user.IdChkVo;
import com.example.studyany.vo.user.LoginVo;
import com.example.studyany.vo.user.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
//import jakarta.security.auth.message.AuthException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//import java.util.Base64;
//import static jakarta.security.auth.message.AuthException.*;

@Tag(name = "사용자 API", description = "사용자 관련 프로세스를 처리한다")
@RequestMapping("/user")
@Slf4j
@RestController
@RequiredArgsConstructor
public class UserCtrl {
//    private static final String SESSION_KEY = "SESSION";
//    private static final String REDIS_SESSION_KEY = ":sessions:";

//    @Value("${spring.session.redis.namespace}")
//    private String namespace;
//
//    private final StringRedisTemplate redisTemplate;

    private final UserMapper mapper;

    @Operation(summary = "로그인 여부 확인 API", description = "아이디로 이미 로그인한 사용자인지 체크하고 응답한다")
    @Parameter(name = "id", description = "계정 아이디")
    @GetMapping("/chk/id")
    public Rslt idChk(@Valid IdChkVo v, HttpServletRequest request, HttpServletResponse response) throws Exception {//@Valid가 없으면 자동으로 유효성 검사를 해주지 않는다.
        /*
        Spring Session을 사용하기 전처럼 HttpSession을 사용하면 됩니다.
        Spring Session을 사용하면 HttpSession에 우리가 설정한 RedisSession이 구현체로 지정되기 때문에 그대로 사용하면 됩니다.
        */
//        final String sessionIdByCookie = getSessionIdByCookie(request);
//        final String decodedSessionId = new String(Base64.getDecoder().decode(sessionIdByCookie.getBytes()));
//        if (!redisTemplate.hasKey(namespace + REDIS_SESSION_KEY + decodedSessionId)) {
//            log.warn("Session Cookie exist, but Session in Storage is not exist");
//            throw new Exception();
//        }
        final HttpSession session = request.getSession();
        String id = session.getAttribute("member").toString();
        log.debug("login id : {}", v.getId());
        if (id == null || !v.getId().equals(id)) {
            throw new Exception();
        }

        return Rslt.builder()
                .cd("S")
                .msg(v.getId())
                .build();
    }

    @Operation(summary = "로그인 API", description = "로그인 요청을 받아 아이디 비밀번호 검사를 하고 로그인 여부를 응답한다")
    @Parameter(name = "id", description = "계정 아이디")
    @Parameter(name = "pwd", description = "계정 비밀번호")
    @GetMapping("/login")
    public Rslt login(@Valid LoginVo v, HttpServletRequest request) {
        User u = mapper.transLoginVoToUser(v);
        final HttpSession session = request.getSession();
        session.setAttribute("member", u.getUserId());
        session.setMaxInactiveInterval(3600);

        return Rslt.builder()
                .cd("S")
                .msg("Completed login")
                .build();
    }
}
