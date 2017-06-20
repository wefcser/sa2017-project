package scis.batchfile;

import scis.excel.poi.PoiItemReader;
import scis.model.SC;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import scis.repository.SCRepository;
import scis.repository.jpa.JpaSCRepositoryImpl;
import scis.service.SCService;

import java.net.MalformedURLException;
import java.util.List;

@Service
@Configuration
@EnableAutoConfiguration
@EnableBatchProcessing
public class SCConvert {
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Bean
    ItemReader<SC> reader() {
        PoiItemReader<SC> reader = new PoiItemReader<>();
        reader.setLinesToSkip(1);
        try {
            reader.setResource(new UrlResource("file:Storage/students.xlsx"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
            System.out.println("Can't find the resource.");
        }
        reader.setRowMapper(new SCExcelRowMapper());
        return reader;
    }

    @Bean
    ItemProcessor<SC, SC> processor() {
        return new ItemProcessor<SC, SC>() {
            @Override
            public SC process(SC sc) throws Exception {
                return sc;
            }
        };
    }

    @Bean
    ItemWriter<SC> writer() {
        return new ItemWriter<SC>() {
            @Autowired
            private SCService scService;
            @Override
            public void write(List<? extends SC> scs) throws Exception {
                Integer size = scs.size();
                for (Integer i = 0; i < size; i++) {
                    //System.out.println(scs.get(i).getNo()+"/");
                    SC sc = new SC();
                    sc.setNo(scs.get(i).getNo());
                    sc.setName(scs.get(i).getName());
                    sc.setDepart(scs.get(i).getDepart());
                    sc.setCourse(scs.get(i).getCourse());
                    this.scService.saveSC(sc);
                }
            }
        };
    }

    @Bean
    Step step() {
        return stepBuilderFactory.get("step")
                .<SC, SC>chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .faultTolerant()
                .skipLimit(Integer.MAX_VALUE)
                .skip(Exception.class)
                .build();
    }

    @Bean
    Job job() {
        return jobBuilderFactory.get("job")
                .incrementer(new RunIdIncrementer())
                .flow(step())
                .end()
                .build();
    }
}