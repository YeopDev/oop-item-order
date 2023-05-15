package order.parser;

import order.Order;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

interface Parser {
    List<Order> execute() throws IOException;

    class CsvParser implements Parser {
        private static final String DEFAULT_PATH = "./src/main/resources";
        private static final String REGEX = ",(?=([^\"]*\"[^\"]*\")*[^\"]*$)";

        private final Path filePath;

        CsvParser(String filename) {
            this.filePath = Paths.get(DEFAULT_PATH + "/" + filename);
        }

        @Override
        public List<Order> execute() throws IOException {
            return Files.readAllLines(filePath)
                    .subList(1, Files.readAllLines(filePath).size())
                    .stream()
                    .map(raw -> byRaw(raw.split(REGEX)))
                    .collect(Collectors.toList());
        }
        private Order byRaw(String... raws) {
            return new Order(
                    Long.valueOf(raws[0]),
                    raws[1],
                    new BigDecimal(raws[2]),
                    Integer.valueOf(raws[3])
            );
        }
    }
}
