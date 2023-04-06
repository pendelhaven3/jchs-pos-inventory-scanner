package com.jchs.pos.inventoryscanner.component;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jchs.pos.inventoryscanner.controller.ControllerFactory;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
public class SceneManager {

	private static final double INITIAL_WIDTH = 1200d;
	private static final double INITIAL_HEIGHT = 640d;
	
	@Autowired
	private ControllerFactory controllerFactory;
	
	private Stage stage;
	
	public void loadScene(String name) {
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setControllerFactory(controllerFactory);
		
		double width = (stage.getScene() != null) ? stage.getScene().getWidth() : INITIAL_WIDTH;
		double height= (stage.getScene() != null) ? stage.getScene().getHeight() : INITIAL_HEIGHT;
		
		try {
			stage.setScene(new Scene(fxmlLoader.load(getClass().getResourceAsStream("/fxml/" + name + ".fxml")), width, height));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/*
	private void loadSceneFromFXML(String file, Map<String, Object> model) {
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setControllerFactory(controllerFactory);
		
		Parent root = null;
		try {
			root = fxmlLoader.load(getClass().getResourceAsStream("/fxml/" + file + ".fxml"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		if (stage.getScene() != null) {
			stage.setScene(new Scene(root, stage.getScene().getWidth(), stage.getScene().getHeight()));
		} else {
			stage.setScene(new Scene(root, WIDTH, HEIGHT));
		}
		
		stage.getScene().getStylesheets().add("css/application.css");
	}

	private void mapParameters(AbstractController controller, Map<String, Object> model) {
		Class<? extends AbstractController> clazz = controller.getClass();
		for (String key : model.keySet()) {
			try {
				Field field = clazz.getDeclaredField(key);
				if (field != null && field.getAnnotation(Parameter.class) != null) {
					field.setAccessible(true);
					field.set(controller, model.get(key));
				}
			} catch (Exception e) {
				System.out.println("Error setting parameter " + key);
			}
		}
	}

	public void setTitle(String title) {
		stage.setTitle("HR App v2.13.1 - " + title);
	}

	public void showEmployeeListScreen() {
		loadSceneFromFXML("employeeList");
	}

	public void showAddEmployeeScreen() {
		loadSceneFromFXML("employee");
	}

	public void showUpdateEmployeeScreen(Employee employee, EmployeeSearchCriteria searchCriteria) {
		history.addToLastItemModel("searchCriteria", searchCriteria);
		loadSceneFromFXML("employee", Collections.singletonMap("employee", employee));
	}

	public void showSalaryListScreen() {
		loadSceneFromFXML("salaryList");
	}

	public void showAddSalaryScreen() {
		loadSceneFromFXML("salary");
	}

	public void showUpdateSalaryScreen(Salary salary) {
		loadSceneFromFXML("salary", Collections.singletonMap("salary", salary));
	}

	public void showPayrollListScreen() {
		loadSceneFromFXML("payrollList");
	}

	public void showAddPayrollScreen() {
		loadSceneFromFXML("addEditPayroll");
	}

	public void showPayrollScreen(Payroll payroll) {
		loadSceneFromFXML("payroll", Collections.singletonMap("payroll", payroll));
	}

	public void showUpdatePayrollScreen(Payroll payroll) {
		loadSceneFromFXML("addEditPayroll", Collections.singletonMap("payroll", payroll));
	}

	public void showPayslipScreen(Payslip payslip) {
		loadSceneFromFXML("payslip", Collections.singletonMap("payslip", payslip));
	}

	public void showEditPayslipScreen(Payslip payslip) {
		loadSceneFromFXML("editPayslip", Collections.singletonMap("payslip", payslip));
	}

	public Stage getStage() {
		return stage;
	}

	public void showSSSContributionTableScreen() {
		loadSceneFromFXML("sssContributionTable");
	}

	public void addSSSContributionTableEntryScreen() {
		loadSceneFromFXML("sssContributionTableEntry");
	}

	public void showEditSSSContributionTableEntryScreen(SSSContributionTableEntry entry) {
		loadSceneFromFXML("sssContributionTableEntry", 
				Collections.singletonMap("entry", entry));
	}

    public void showSSSContributionTableForHouseholdScreen() {
        loadSceneFromFXML("sssContributionTableForHousehold");
    }

    public void addSSSContributionTableEntryForHouseholdScreen() {
        loadSceneFromFXML("sssContributionTableEntryForHousehold");
    }

    public void showEditSSSContributionTableEntryForHouseholdScreen(SSSContributionTableEntry entry) {
        loadSceneFromFXML("sssContributionTableEntryForHousehold", 
                Collections.singletonMap("entry", entry));
    }

	public void showPhilHealthContributionTableScreen() {
		loadSceneFromFXML("philHealthContributionTable");
	}

	public void addPhilHealthContributionTableEntryScreen() {
		loadSceneFromFXML("philHealthContributionTableEntry");
	}

	public void showEmployeeLoanListScreen() {
		loadSceneFromFXML("employeeLoanList");
	}

	public void showAddEmployeeLoanScreen() {
		loadSceneFromFXML("addEditEmployeeLoan");
	}

	public void showUpdateEmployeeLoanScreen(EmployeeLoan loan) {
		loadSceneFromFXML("addEditEmployeeLoan", Collections.singletonMap("loan", loan));
	}

	public void showEmployeeLoanScreen(EmployeeLoan loan) {
		loadSceneFromFXML("employeeLoan", Collections.singletonMap("loan", loan));
	}

	public void showEmployeeAttendanceListScreen() {
		loadSceneFromFXML("employeeAttendanceList");
	}

	public void showEditPagibigContributionValueScreen() {
		loadSceneFromFXML("editPagibigContributionValue");
	}

	public void showReportListScreen() {
		loadSceneFromFXML("reportList");
	}

	public void showSSSPhilHealthReportScreen() {
		loadSceneFromFXML("sssPhilHealthReport");
	}

	public void updateSalaryListScreenHistoryItemModel(Map<String, Object> model) {
		history.updateHistoryItemModel("salaryList", model);
	}

	public void showLatesReportScreen() {
		loadSceneFromFXML("latesReport");
	}

	public void showAddEmployeeAttendanceScreen() {
		loadSceneFromFXML("employeeAttendance");
	}

	public void showEditEmployeeAttendanceScreen(EmployeeAttendance employeeAttendance) {
		loadSceneFromFXML("employeeAttendance", Collections.singletonMap("employeeAttendance", employeeAttendance));
	}

	public void showCompanyProfileScreen() {
		loadSceneFromFXML("companyProfile");
	}

	public void showBasicSalaryReportScreen() {
		loadSceneFromFXML("basicSalaryReport");
	}

	public void addCurrentScreenParameter(String name, Object value) {
		history.addToLastItemModel(name, value);
	}

	public void showEmployeeLoanPaymentsReportScreen() {
		loadSceneFromFXML("employeeLoanPaymentsReport");
	}

    public void showSSSReportScreen() {
        loadSceneFromFXML("sssReport");
    }

    public void showPhilHealthReportScreen() {
        loadSceneFromFXML("philHealthReport");
    }

    public void showPagIbigReportScreen() {
        loadSceneFromFXML("pagIbigReport");
    }

    public void showSssLoanPaymentsReportScreen() {
        loadSceneFromFXML("sssLoanPaymentsReport");
    }

    public void showPagIbigLoanPaymentsReportScreen() {
        loadSceneFromFXML("pagIbigLoanPaymentsReport");
    }

	public void showThirteenthMonthReportScreen() {
        loadSceneFromFXML("thirteenthMonthReport");
	}

	public void showEmployeeSavingsListScreen() {
		loadSceneFromFXML("employeeSavingsList");
	}

	public void showAddEmployeeSavingsScreen() {
		loadSceneFromFXML("addEditEmployeeSavings");
	}

	public void showEmployeeSavingsScreen(EmployeeSavings savings) {
		loadSceneFromFXML("employeeSavings", Collections.singletonMap("savings", savings));
	}

	public void showUpdateEmployeeSavingsScreen(EmployeeSavings savings) {
		loadSceneFromFXML("addEditEmployeeSavings", Collections.singletonMap("savings", savings));
	}

	public void showEmployeeLoanTypeListScreen() {
		loadSceneFromFXML("employeeLoanTypeList");
	}

	public void showAddEmployeeLoanTypeScreen() {
		loadSceneFromFXML("addEditEmployeeLoanType");
	}

	public void showUpdateEmployeeLoanTypeScreen(EmployeeLoanType loanType) {
		loadSceneFromFXML("addEditEmployeeLoanType", Collections.singletonMap("loanType", loanType));
	}
	*/

}