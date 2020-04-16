package by.kudko.classgenerator;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Getter
@Component
@PropertySource("classpath:newEntity.properties")
public class Properties {

    @Value("${textTemplateForClass}")
    private String textTemplateForClass;
    @Value("${columnsNameAndType}")
    private String columnsNameAndType;
    @Value("${dbName}")
    private String dbName;
    @Value("${tableName}")
    private String tableName;
    @Setter
    private String[] columnNames;
    @Setter
    private List<String> classFields = new ArrayList<>();
    @Value("${pairSplit}")
    private String pairSplit;
    @Value("${split}")
    private String split;

}
