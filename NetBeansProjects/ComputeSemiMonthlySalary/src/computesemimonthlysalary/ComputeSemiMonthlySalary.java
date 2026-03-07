/**
 * MotorPH Payroll System
 * ComputeSemiMonthlySalary.java
 *
 * Program Plan:
 * 1. Declare employee information variables
 * 2. Declare work hours and hourly rate variables
 * 3. Validate inputs using conditional statements
 * 4. Compute total hours worked for the cutoff period
 * 5. Compute semi-monthly salary based on hours and rate
 * 6. Display a formatted payroll summary report
 */

public class ComputeSemiMonthlySalary {

    public static void main(String[] args) {

        // ── Employee Information ──────────────────────────────────────────────
        String employeeName   = "Angeline Latoja";   // Employee full name
        String employeeID     = "EMP-001";            // Employee ID number
        String position       = "Sales Associate";    // Job position
        String cutoffPeriod   = "March 1–15, 2026";  // Semi-monthly cutoff period

        // ── Work Hours Variables (reused from Task 7 logic) ───────────────────
        double timeIn         = 8.0;    // Clock-in time (8:00 AM)
        double timeOut        = 17.0;   // Clock-out time (5:00 PM)
        double breakTime      = 1.0;    // Unpaid break time in hours
        int    workDays       = 11;     // Number of working days in this cutoff

        // ── Rate Variable ─────────────────────────────────────────────────────
        double hourlyRate     = 95.50;  // Hourly rate in Philippine Peso

        // ── Input Validation: Check for invalid values ────────────────────────
        // Conditional statement to catch zero or negative inputs
        if (hourlyRate <= 0) {
            System.out.println("ERROR: Hourly rate must be greater than zero.");
            return;  // Stop the program if rate is invalid
        }

        if (workDays <= 0) {
            System.out.println("ERROR: Number of work days must be greater than zero.");
            return;  // Stop the program if work days is invalid
        }

        if (timeOut <= timeIn) {
            System.out.println("ERROR: Time out must be later than time in.");
            return;  // Stop the program if time values are invalid
        }

        // ── Compute Total Hours Worked Per Day ────────────────────────────────
        double hoursPerDay       = (timeOut - timeIn) - breakTime;  // Daily hours minus break

        // ── Compute Total Hours for Cutoff Period ─────────────────────────────
        double totalHoursWorked  = hoursPerDay * workDays;  // Total hours across all work days

        // ── Compute Semi-Monthly Salary ───────────────────────────────────────
        double semiMonthlySalary = totalHoursWorked * hourlyRate;  // Hours x rate = salary

        // ── Compute Deductions (SSS, PhilHealth, Pag-IBIG) ───────────────────
        double sssDeduction       = 500.00;   // Fixed SSS contribution
        double philHealthDeduction = 300.00;  // Fixed PhilHealth contribution
        double pagIbigDeduction   = 100.00;   // Fixed Pag-IBIG contribution
        double totalDeductions    = sssDeduction + philHealthDeduction + pagIbigDeduction;

        // ── Compute Net Pay ───────────────────────────────────────────────────
        double netPay = semiMonthlySalary - totalDeductions;  // Gross minus deductions

        // ── Display Payroll Summary ───────────────────────────────────────────
        System.out.println("=============================================");
        System.out.println("         MotorPH Payroll System             ");
        System.out.println("         Semi-Monthly Salary Report         ");
        System.out.println("=============================================");
        System.out.println("Employee Name     : " + employeeName);
        System.out.println("Employee ID       : " + employeeID);
        System.out.println("Position          : " + position);
        System.out.println("Cutoff Period     : " + cutoffPeriod);
        System.out.println("---------------------------------------------");
        System.out.println("Time In           : " + timeIn + ":00");
        System.out.println("Time Out          : " + timeOut + ":00");
        System.out.println("Break Time        : " + breakTime + " hour(s)");
        System.out.println("Hours Per Day     : " + hoursPerDay + " hour(s)");
        System.out.println("Work Days         : " + workDays + " day(s)");
        System.out.println("Total Hours Worked: " + totalHoursWorked + " hour(s)");
        System.out.println("Hourly Rate       : ₱" + hourlyRate);
        System.out.println("---------------------------------------------");
        System.out.println("Gross Salary      : ₱" + semiMonthlySalary);
        System.out.println("---------------------------------------------");
        System.out.println("SSS Deduction     : ₱" + sssDeduction);
        System.out.println("PhilHealth        : ₱" + philHealthDeduction);
        System.out.println("Pag-IBIG          : ₱" + pagIbigDeduction);
        System.out.println("Total Deductions  : ₱" + totalDeductions);
        System.out.println("---------------------------------------------");
        System.out.println("NET PAY           : ₱" + netPay);
        System.out.println("=============================================");

        // ── Conditional: Low salary warning ──────────────────────────────────
        if (netPay < 5000) {
            System.out.println("⚠ Notice: Net pay is below ₱5,000 this cutoff.");
        } else {
            System.out.println("✔ Salary computation verified successfully!");
        }

        System.out.println("=============================================");
    }
}