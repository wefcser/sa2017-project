//package scis.bean;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.jdbc.datasource.SimpleDriverDataSource;
//import org.springframework.jdbc.datasource.init.DatabasePopulator;
//import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
//import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
//
//import javax.sql.DataSource;
//
//@Configuration
//public class DataSourceBean {
//    @Bean
//    public DataSource dataSource() {
//        DataSource dataSource = simpleDriverDataSourceDataSource();
//        DatabasePopulatorUtils.execute(databasePopulator(), dataSource);
//        return dataSource;
//    }
//
//    private DatabasePopulator databasePopulator() {
//        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
//        databasePopulator.setContinueOnError(true);
//        databasePopulator.addScript(new ClassPathResource("create-students-table.sql"));
//        return databasePopulator;
//    }
//
//    private SimpleDriverDataSource simpleDriverDataSourceDataSource() {
//        SimpleDriverDataSource simpleDriverDataSource = new SimpleDriverDataSource();
//        simpleDriverDataSource.setDriverClass(org.hsqldb.Driver.class);
//        simpleDriverDataSource.setUrl("jdbc:h2:~/database");
//        simpleDriverDataSource.setUsername("");
//        simpleDriverDataSource.setPassword("");
//        return simpleDriverDataSource;
//    }
//}