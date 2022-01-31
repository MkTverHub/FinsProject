package com.sweetcard.basic.registration.token;

import com.sweetcard.basic.appuser.AppUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Entity
public class ConfirmationToken {
    @SequenceGenerator(
            name = "confirmation_token_sequence",
            sequenceName = "confirmation_token_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "confirmation_token_sequence"
    )
    private Long id;

    @Column(nullable = false)
    private String token;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    private LocalDateTime confirmedAt;

    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "app_user_id"
    )
    private AppUser appUser;

    public ConfirmationToken(String token,
                             LocalDateTime createdAt,
                             LocalDateTime expiredAt,
                             AppUser appUser) {
        this.token = token;
        this.createdAt = createdAt;
        this.expiresAt = expiredAt;
        this.appUser = appUser;
    }

    //Это не по гайду но помогает избежать ошибки
    //org.hibernate.InstantiationException: No default constructor for entity:  : com.example.demo.registration.token.ConfirmationToken
    public ConfirmationToken(){
        super();
    }

}