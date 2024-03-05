package project.wedding.common.logging;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.MDC;

class MDCCopyTaskDecoratorTest {

    private MDCCopyTaskDecorator taskDecorator;

    @BeforeEach
    void setUp() {
        taskDecorator = new MDCCopyTaskDecorator();
    }

    @DisplayName("MDC에 저장된 데이터를 복제하고 MDC clear 한다.")
    @Test
    void decorate_CopyMDCContextAndClear() {
        // given
        String key = "testKey";
        String value = "testValue";
        MDC.put(key, value);

        // when
        Runnable mockRunnable = Mockito.mock(Runnable.class);
        Runnable decoratedRunnable = taskDecorator.decorate(mockRunnable);
        decoratedRunnable.run();

        // then
        verify(mockRunnable, times(1)).run();
        assertNull(MDC.getCopyOfContextMap());

    }
}
