package io.quarkus.opentelemetry.deployment.traces;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;

import io.quarkus.test.QuarkusUnitTest;

public class ClassMismatchTest {

    @RegisterExtension
    static final QuarkusUnitTest config = new QuarkusUnitTest()
            .withApplicationRoot((jar) -> jar
                    .addClass(MyEnum.class));



    @ParameterizedTest
    @MethodSource("provideEnumValues")
    void test(MyEnum myEnum) {
        assertThat(myEnum).isNotNull();
    }


    @ParameterizedTest
    @EnumSource(MyEnum.class)
    void test2(MyEnum myEnum) {
        assertThat(myEnum).isNotNull();
    }

    private static enum MyEnum {
        VALUE1, VALUE2
    }

    public static Stream<Arguments> provideEnumValues() {
        return Stream.of(Arguments.of(MyEnum.VALUE1), Arguments.of(MyEnum.VALUE2));
    }

}
