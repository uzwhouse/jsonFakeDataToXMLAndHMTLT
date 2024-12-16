package uz.pdp;

import java.util.EnumSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public
class FakeDataToHTMLTable implements Collector<Person, StringBuffer, String> {
    final String htmlTableHeader = """
            \n\t<tr>
            \t\t<th>ID</th>
            \t\t<th>name</th>
            \t\t<th>gender</th>
            \t\t<th>age</th>
            \t</tr>
            """;

    final String htmlTableBody = """
            \t<tr>
            \t\t<td>%s</td>
            \t\t<td>%s</td>
            \t\t<td>%s</td>
            \t\t<td>%s</td>
            \t</tr>
            """;

    @Override
    public Supplier<StringBuffer> supplier() {
        return () -> new StringBuffer().append(htmlTableHeader);
    }

    @Override
    public BiConsumer<StringBuffer, Person> accumulator() {
        return (sb, person) -> {
            sb.append(String.format(htmlTableBody,
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
        return stringBuffer -> String.format("<table>%s</table>", stringBuffer.toString());
    }

    @Override
    public Set<Characteristics> characteristics() {
        return EnumSet.of(Characteristics.CONCURRENT);
    }
}