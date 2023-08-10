package dev.camilo.demo.aspects;

import com.nimbusds.jose.proc.SecurityContext;
import dev.camilo.demo.domain.entities.documents.AppUserDocument;
import dev.camilo.demo.domain.repositories.mongo.AppUserRepository;
import dev.camilo.demo.infraestructure.helpers.BlackListHelper;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
@RequiredArgsConstructor
public class BlackListCheckAspect {

  //dependencias inyectadas
  private final BlackListHelper blackListHelper;

  @Before(value = "@annotation(dev.camilo.demo.util.annotations.BlackListCheck)")
  public void checkBlackList() {
    blackListHelper.isInBlackListCustomer();
  }
}
