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

	private int i = 0;

	private static final Object[] FILE_HEADER = { "company", "agentNumber", "status", "firstName", "lastName",
			"companyId", "statusId" };

	private static final String NEW_LINE_SEPARATOR = "\n";

	@Override
	public void run(String... strings) throws Exception {

		// Split up the array of whole names into an array of first/last names
		List<AgentIn> agentsIn = new ArrayList<>();

		Reader in = new FileReader("./thefile.csv");
		Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(in);

		for (CSVRecord record : records) {
			AgentIn agentIn = new AgentIn(record.get(0), record.get(1), record.get(2));
			agentsIn.add(agentIn);
			System.out.println("read agent no: " + agentIn.getAgentNumber());
		}

		String sql = "select agent_number, search_first_name, search_last_name, company_id, status_id "
				+ "from cyberlife.agent " + "inner join client.party p on cyberlife.agent.client_id=p.client_id "
				+ "where agent_number= ?";

		List<AgentOut> agentsOut = new ArrayList<>();

		System.out.println("Now querying database!");

		for (AgentIn agentIn : agentsIn) {

			Agent agent = new Agent();
			
			List<Agent> agentsQuery = jdbcTemplate.query(sql, new Object[] { agentIn.getAgentNumber() },
					new BeanPropertyRowMapper<Agent>(Agent.class));

			if (agentsQuery.size()>0) {
				agent = agentsQuery.get(0);
			}
			
			AgentOut agentOut = mergeAgents(agentIn, agent);

			agentsOut.add(agentOut);

			i++;
			System.out.println("Added agent " + i + "/" + agentsIn.size());

		}

		System.out.println("Now writing file!");

		FileWriter fileWriter = null;

		CSVPrinter csvFilePrinter = null;

		// Create the CSVFormat object with "\n" as a record delimiter
		CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator(NEW_LINE_SEPARATOR);

		try {

			// initialize FileWriter object
			fileWriter = new FileWriter("agentOut.csv");

			// initialize CSVPrinter object
			csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat);

			// Create CSV file header
			csvFilePrinter.printRecord(FILE_HEADER);

			// "company", "agentNumber", "status", "firstName", "lastName",
			// "companyId", "statusId"

			// Write a new agent object list to the CSV file
			for (AgentOut agentOut : agentsOut) {
				List<String> agentDataRecord = new ArrayList<>();
				agentDataRecord.add(agentOut.getCompanyCode());
				agentDataRecord.add(agentOut.getAgentNumber());
				agentDataRecord.add(agentOut.getStatus());
				agentDataRecord.add(agentOut.getSearchFirstName());
				agentDataRecord.add(agentOut.getSearchLastName());
				agentDataRecord.add(agentOut.getCompanyId());
				agentDataRecord.add(String.valueOf(agentOut.getStatusId()));
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

	private AgentOut mergeAgents(AgentIn agentIn, Agent agent) {
		return new AgentOut(agentIn.getCompanyCode(), agentIn.getStatus(), agentIn.getAgentNumber(),
				agent.getSearchFirstName(), agent.getSearchLastName(), agent.getCompanyId(), agent.getStatusId());
	}
}