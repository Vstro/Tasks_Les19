package by.company.library.main;

import java.util.Scanner;

import by.company.library.controller.Controller;

public class Main {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		Controller control = new Controller();
		String role = "JUNIOR"; // Доступ текущего пользователя
		// Выводим подсказку для пользователя
		System.out.println("Wellcome to the ELibrary!\n"
				+ "Allowed commands ([] - for optional with default values):\n"
				+ "ADDBOOK [title = Unknown] [price = 0] [role = JUNIOR]\n"
				+ "LOGIN login password\n"
				+ "SIGNUP login password\n"
				+ "CHANGEROLE target_user_login [new_role = ADULT]\n"
				+ "SHOWLIB\n");
		
		String line = "";	
		System.out.print("Your command: ");
		
		if (scanner.hasNextLine()) {
			line = scanner.nextLine();
		} else {
			System.out.println("Error occured, please try again after a while");
		}
		
		while (!line.equals("EXIT") && !line.equals("Exit") && !line.equals("exit")) {
			// Передаём первым параметром запроса доступ текущего пользователя
			String[] response = control.doAction(role + " " + line).split("\\s+");
			// Первый параметр ответа - доступ пользователя после выполнения команды
			role = response[0];
			
			// Выводим ответ
			for (int i = 1; i < response.length; i++){
				System.out.print(response[i] + " ");
			}
			System.out.println();
			
			System.out.print("Your command: ");
			
			if (scanner.hasNextLine()) {
				line = scanner.nextLine();
			} else {
				System.out.println("Error occured, please try again after a while");
			}
		}
		
		scanner.close();
	}

}
