package service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Service {

	public boolean getPerson() {
		try {
			Document d = Jsoup.connect("https://www.invertexto.com/gerador-de-pessoas").timeout(6000).get();
			Elements e = d.select("div.col-md-18");
			int i = 2;
			String name = "";
			for (Element element : e.select("div.form-group")) {
				String cpf = element.select("div.form-group input").attr("value");
				if (i == 2) {
					name = cpf;
				}
				if (i == 1) {
					if (cpf.contains(".")) {
						saveCSV(name, cpf);
						return true;
					} else {
						return false;
					}
				}
				i--;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public void saveCSV(String name, String cpf) {
		int grade[] = new int[24];
		try {
			File file = new File("grades.csv");
			if (!file.exists()) {
				file.createNewFile();
			}
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = reader.readLine();
			String newLine = "";
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			if (line != null) {
				newLine = line + ";" + name + "\n";
				line = reader.readLine();
				newLine = newLine + line + ";" + cpf + "\n";
				for (int i = 0; i < 12; i++) {
					line = reader.readLine();
					grade[i] = getGrade(i);
					newLine = newLine + line + ";" + grade[i] + "\n";
				}
				line = reader.readLine();
				newLine = newLine + line + "\n";
				for (int i = 12; i < 24; i++) {
					line = reader.readLine();
					grade[i] = getGrade(i);
					newLine = newLine + line + ";" + grade[i] + "\n";
				}
			} else {
				newLine = "NOME;" +name + "\n";
				newLine = newLine +"CPF;"+ cpf + "\n";
				for (int i = 0; i < 12; i++) {
					grade[i] = getGrade(i);
					newLine = newLine +"NOTA;"+ grade[i] + "\n";
				}
				newLine = newLine + "Dilema do Médico" + "\n";
				for (int i = 12; i < 24; i++) {
					line = reader.readLine();
					grade[i] = getGrade(i);
					newLine = newLine +"NOTA;"+ grade[i] + "\n";
				}
			}
			writer.write(newLine);
			writer.close();
			reader.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int getGrade(int pos) {
		int grade = 0;
		if (pos == 0 || pos == 12) {
			grade = (int) (0 + Math.random() * 5) * negativeRandom();
		} else {
			grade = (int) (0 + Math.random() * 4) * negativeRandom();
		}
		return grade;
	}

	public int negativeRandom() {
		int negativeCount = 0;
		for (int i = 0; i < 5; i++) {
			if ((int) (0 + Math.random() * 5) % 2 == 0) {
				negativeCount++;
			}
		}
		if (negativeCount > 3) {
			return -1;
		} else {
			return 1;
		}

	}

	public void generateEverything() {
		boolean person = false;
		while (!person) {
			person = getPerson();
		}
	}
}