package org.wedding.adapter.out.persistence.mybatis;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.wedding.application.port.out.repository.VerificationCodeRepository;
import org.wedding.domain.VerificationCode;

@Repository
@Mapper
public interface MybatisVerificationCodeRepositoryImpl extends VerificationCodeRepository {

    @Override
    void insertVerificationCode(VerificationCode verificationCode);

    @Override
    String selectVerificationCode(String email);
}
