package com.csc.fsg.bpo.readcsv;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@SpringBootApplication
public class ReadcsvApplication implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(ReadcsvApplication.class);

    public static void main(String args[]) {
        SpringApplication.run(ReadcsvApplication.class, args);
    }

    @Autowired
    JdbcTemplate jdbcTemplate;
    
	private int i=0;
	
	private static final Object [] FILE_HEADER = {"agentNumber","firstName","lastName","companyId","statusId"};

	private static final String NEW_LINE_SEPARATOR = "\n";	
	
    @Override
    public void run(String... strings) throws Exception {
      
        // Split up the array of whole names into an array of first/last names
        List<String> agentNumbers = new ArrayList<>();
        
        Reader in = new FileReader("X:/thefile.csv");
        Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(in);             
        
        records.forEach(record->{
        	String agentNumber = record.get(0);
        	agentNumbers.add(agentNumber);
        	System.out.println("read agent no: "+agentNumber);
        });

        String sql = "select agent_number, search_first_name, search_last_name, company_id, status_id "
        		+ "from cyberlife.agent "
        		+ "inner join client.party p on cyberlife.agent.client_id=p.client_id "
        		+ "where agent_number= ?";
        
		List<Agent> agentList=new ArrayList<Agent>();
		
    	System.out.println("Now querying database!");
    
		agentNumbers.forEach(agentNumber->{
			 Agent agent = jdbcTemplate.queryForObject(
	    			sql,  new Object[] {agentNumbers},
	    			new BeanPropertyRowMapper<Agent>(Agent.class));
			
			agentList.add(agent);
			i++;
        	System.out.println("Added agent "+i+"/"+agentNumbers.size());

		});
		
		agentList.forEach(agent->{
			System.out.println("Agent info: "+agent.toString());
		});
		
    	System.out.println("Now writing file!");

    	
		FileWriter fileWriter = null;
		
		CSVPrinter csvFilePrinter = null;
		
		//Create the CSVFormat object with "\n" as a record delimiter
        CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator(NEW_LINE_SEPARATOR);
				
		try {
			
			//initialize FileWriter object
			fileWriter = new FileWriter("agentOut.csv");
			
			//initialize CSVPrinter object 
	        csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat);
	        
	        //Create CSV file header
	        csvFilePrinter.printRecord(FILE_HEADER);
			
			//Write a new agent object list to the CSV file
			for (Agent agent : agentList) {
				List<String> agentDataRecord = new ArrayList<>();
	            agentDataRecord.add(agent.getAgentNumber());
	            agentDataRecord.add(agent.getSearchFirstName());
	            agentDataRecord.add(agent.getSearchLastName());
	            agentDataRecord.add(agent.getCompanyId());
	            agentDataRecord.add(String.valueOf(agent.getStatusId()));
	            csvFilePrinter.printRecord(agentDataRecord);
			}

			System.out.println("CSV file was created successfully !!!");
			
		} catch (Exception e) {
			System.out.println("Error in CsvFileWriter !!!");
			e.printStackTrace();
		} finally {
			try {
				fileWriter.flush();
				fileWriter.close();
				csvFilePrinter.close();
			} catch (IOException e) {
				System.out.println("Error while flushing/closing fileWriter/csvPrinter !!!");
                e.printStackTrace();
			}
		}

    }
}