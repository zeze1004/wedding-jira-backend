package org.wedding.domain;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Couple {

    private int coupleId;
    private int senderUserId;   // 연동 요청한 사용자
    private int receiverUserId; // 연동 요청 수락한 사용자
    private LocalDate createAt;

    @Override
    public String toString() {
        return """
            Couple{
                coupleId='%s',
                senderUserId='%s',
                receiverUserId='%s',
                createAt='%s'
            }
            """.formatted(coupleId, senderUserId, receiverUserId, createAt);
    }
}
