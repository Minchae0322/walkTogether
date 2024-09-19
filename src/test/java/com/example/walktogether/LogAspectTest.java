package com.example.walktogether;



import com.example.walktogether.service.TestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.*;

@SpringBootTest
@EnableAspectJAutoProxy // AOP 자동 설정 활성화
public class LogAspectTest {

    private static final Logger logger = LoggerFactory.getLogger(LogAspectTest.class);

    @Autowired
    private TestService testService; // AOP가 적용된 테스트 서비스

    @SpyBean
    private LogAspect logAspect; // AOP를 스파이 처리하여 내부 동작을 감시

    @Test
    @DisplayName("로그 Aspect 작동테스트")
    public void testLogAspect() throws Throwable {
        // 테스트 메소드 호출
        String result = testService.testMethod();

        // 결과 검증
        assert(result.equals("Test Result"));

        // 로그 메시지가 정상적으로 호출되었는지 검증
        verify(logAspect).logExecutionStartAndEndTime(any());
    }
}
