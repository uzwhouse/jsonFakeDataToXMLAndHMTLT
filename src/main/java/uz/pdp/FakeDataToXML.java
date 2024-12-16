package uz.pdp;

import java.util.EnumSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public
class FakeDataToXML implements Collector<Person, StringBuffer, String> {
    final String xml = """
            \n\t<person id='%s'>
            \t\t<name>%s</name>
            \t\t<gender>%s</gender>
            \t\t<age>%s</age>
            \t</person>
            """;

    @Override
    public Supplier<StringBuffer> supplier() {
        return StringBuffer::new;
    }

    @Override
    public BiConsumer<StringBuffer, Person> accumulator() {
        return (sb, person) -> {
            sb.append(String.format(xml,
                    person.getId(),
                    person.getName(),
                    person.getGender(),
                    person.getAge()));
        };
    }

    @Override
    public BinaryOperator<StringBuffer> combiner() {
        return (stringBuffer, stringBuffer2) -> stringBuffer.append(stringBuffer2.toString());
    }

    @Override
    public Function<StringBuffer, String> finisher() {
        return stringBuffer -> String.format("<people>%s</people>", stringBuffer.toString());
    }

    @Override
    public Set<Characteristics> characteristics() {
        return EnumSet.of(Characteristics.CONCURRENT);
    }
}