package com.example.socialplatform.controller;

import com.example.socialplatform.datatransferobject.AuthenticationResponse;
import com.example.socialplatform.datatransferobject.LoginRequest;
import com.example.socialplatform.datatransferobject.RefreshTokenRequest;
import com.example.socialplatform.datatransferobject.RegisterRequest;
import com.example.socialplatform.service.AuthService;
import com.example.socialplatform.service.RefreshTokenService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/autoryzacja")
@AllArgsConstructor
public class AuthorizationCntrl {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;

    @GetMapping("/siema")
    public String siema(){
        return "siema";
    }


    @GetMapping("zweryfikujkonto/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token) {
        authService.verifyAccount(token);
        return new ResponseEntity<>("Konto zostalo aktywowane!", OK);
    }

    @PostMapping("/rejestracja")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {
        authService.register(registerRequest);
        return new ResponseEntity<>("Użytkownik zostal zarejestrowany.", OK);
    }

    @PostMapping("/zaloguj")
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/odswiez/token")
    public AuthenticationResponse refreshTokens(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        return authService.refreshToken(refreshTokenRequest);
    }

    @PostMapping("/wyloguj")
    public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
        return ResponseEntity.status(OK).body("Token zostal usuniety.");
    }
}
