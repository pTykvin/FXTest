package ch.makery.adress.view;

import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ch.makery.adress.model.Person;
import ch.makery.adress.util.DateUtil;
import ch.makery.adress.util.StringUtil;

public class PersonEditDialogController {

	@FXML
	private TextField firstNameField;
	@FXML
	private TextField lastNameField;
	@FXML
	private TextField streetField;
	@FXML
	private TextField postalCodeField;
	@FXML
	private TextField cityField;	
	@FXML
	private TextField birthdayField;
	
	private Stage dialogStage;
	private Person person;
	
	private boolean okClicked = false;
	
	@FXML
	private void initiallize() {		
	}
	
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	
	public void setPerson(Person person) {
		this.person = person;
		
		firstNameField.setText(person.getFirstName());
		lastNameField.setText(person.getLastName());
		streetField.setText(person.getStreet());
		postalCodeField.setText(Integer.toString(person.getPostalCode()));
		cityField.setText(person.getStreet());
		birthdayField.setText(DateUtil.format(person.getBirthday()));
		birthdayField.setPromptText("dd.mm.yyyy");
	}
	
	public boolean isOkClicked() {
		return okClicked;
	}
	
	@FXML
	private void handleOk() {
		if (isInputValid()) {
			person.setFirstName(firstNameField.getText());
			person.setLastName(lastNameField.getText());
			person.setStreet(streetField.getText());
			person.setPostalCode(Integer.parseInt(postalCodeField.getText()));
			person.setCity(cityField.getText());
			person.setBirthday(DateUtil.parse(birthdayField.getText()));
			
			okClicked = true;
			dialogStage.close();
		}
	}
	
	@FXML
	private void handleCancel() {
		dialogStage.close();
	}

	private boolean isInputValid() {
		StringBuilder errorMessage = new StringBuilder();		
		if (StringUtil.isEmpty(firstNameField.getText())) {
			errorMessage.append("No valid first name!\n");
		}
		if (StringUtil.isEmpty(lastNameField.getText())) {
			errorMessage.append("No valid last name!\n");
		}
		if (StringUtil.isEmpty(streetField.getText())) {
			errorMessage.append("No valid street!\n");
		}
		if (StringUtil.isEmpty(postalCodeField.getText())) {
			errorMessage.append("No valid postal code!\n");
		} else {
			try {
				Integer.parseInt(postalCodeField.getText());
			} catch (NumberFormatException e) {
				errorMessage.append("No valid postal code (must be an integer)!\n");
			}
		}
		
		if (StringUtil.isEmpty(cityField.getText())) {
			errorMessage.append("No valid city!\n");
		}
		if (StringUtil.isEmpty(birthdayField.getText())) {
			errorMessage.append("No valid birthday");			
		} else {
			if (!DateUtil.validDate(birthdayField.getText())) {
				errorMessage.append("No valid birthday. Use the format dd.mm.yyy!\n");
			}
		}
		if (StringUtil.isEmpty(errorMessage.toString())) {
			return true;
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Invalid Fields");
			alert.setHeaderText("Please correct invalid fields");
			alert.setContentText(errorMessage.toString());
			alert.showAndWait();
			return false;
		}
	}
	
}
