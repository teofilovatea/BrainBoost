package mk.ukim.finki.brainboost;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import testUI.elements.UIElement;


import static testUI.UIOpen.open;
import static testUI.elements.TestUI.E;
import static testUI.Utils.By.*;
import static testUI.Utils.AppiumHelps.sleep;

@SpringBootTest
 class BrainBoostApplicationTests {

	@Test
	 void contextLoads() {

		open("http://localhost:8080/register"); // opens the following link automatically.

		E(byId("name")).sendKeys("TestUserName"); // auto-insert Name in this case "TestUserName".
		E(byId("surname")).sendKeys("TestUserSurname"); // auto-insert Surname in this case "TestUserSurname".
		E(byId("username")).sendKeys("TestUserUsername"); // auto-insert Username in this case "TestUserUsername".
		E(byId("email")).sendKeys("TestUserEmail@gmail.com"); // auto-insert Email in this case "TestUserEmail@gmail.com".
		E(byId("password")).sendKeys("TestUserPassword"); // auto-insert Password in this case "TestUserPassword".
		E(byId("repeatedPassword")).sendKeys("TestUserPassword"); // auto-insert repeatedPassword in this case "TestUserPassword".

		E(byXpath("//button[text()=\"Create Account\"]")).click(); // clicks the button "Create Account" and opens the URL: "http://localhost:8080/login".

		E(byXpath("//button[text()=\"Log in\"]")).click(); // Clicks the button "Log in" and enters the main page.


		sleep(60*1000); // the last page is opened for 1 minute..



	}
}
