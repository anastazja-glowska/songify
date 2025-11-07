//package com.songify.infrastructure.security.jwt;
//
//
//import jakarta.servlet.http.Cookie;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.AllArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.security.NoSuchAlgorithmException;
//
//@RestController
//@AllArgsConstructor
//class JwtTokenController {
//
//    private final JwtTokenGenerator jwtTokenGenerator;
//
//    @PostMapping("/token")
//    public ResponseEntity<JtwResponseDto> authenticateAndGenerateToken(@RequestBody TokenRequestDto tokenRequestDto,
//                                                                       HttpServletResponse httpServletResponse) throws NoSuchAlgorithmException {
//
//        String token = jwtTokenGenerator.authenticateAndGenerateToken(tokenRequestDto.username(), tokenRequestDto.password());
//
//        Cookie cookie = new Cookie("access_token", token);
//        cookie.setHttpOnly(true);
//        cookie.setSecure(false);
//        cookie.setPath("/");
//        cookie.setMaxAge(3600);
//        httpServletResponse.addCookie(cookie);
//
//        return ResponseEntity.ok(JtwResponseDto.builder().
//                token(token).build());
//    }
//}
