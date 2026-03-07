/**
 * MotorPH Payroll System
 * MotorPH_ApplyDeductions.java
 *
 * Program Plan:
 * 1. Declare employee information and salary variables
 * 2. Compute gross semi-monthly salary from hours worked and hourly rate
 * 3. Call individual deduction methods (SSS, PhilHealth, Pag-IBIG, Income Tax)
 *    - Each method uses the ACTUAL contribution tables provided by MotorPH
 * 4. Compute net pay by subtracting total deductions from gross salary
 * 5. Display a complete and formatted payroll summary
 *
 * Contribution Table Sources:
 * - SSS     : SSS Contribution Schedule (bracket-based, monthly)
 * - PhilHealth: 3% of monthly basic salary, employee pays 50%
 * - Pag-IBIG: 2% of monthly salary if over 1,500; max 100/month (50 per cutoff)
 * - Tax     : BIR Withholding Tax Table (applied on taxable income after deductions)
 */

public class MotorPH_ApplyDeductions {

    // -- METHOD: Compute SSS Deduction ----------------------------------------
    // Uses the actual SSS Contribution Schedule (bracket lookup on MONTHLY salary)
    // Returns the semi-monthly SSS contribution (monthly divided by 2)
    static double computeSSS(double monthlySalary) {
        double sssContribution;

        if      (monthlySalary < 3250)  { sssContribution = 135.00;  }
        else if (monthlySalary < 3750)  { sssContribution = 157.50;  }
        else if (monthlySalary < 4250)  { sssContribution = 180.00;  }
        else if (monthlySalary < 4750)  { sssContribution = 202.50;  }
        else if (monthlySalary < 5250)  { sssContribution = 225.00;  }
        else if (monthlySalary < 5750)  { sssContribution = 247.50;  }
        else if (monthlySalary < 6250)  { sssContribution = 270.00;  }
        else if (monthlySalary < 6750)  { sssContribution = 292.50;  }
        else if (monthlySalary < 7250)  { sssContribution = 315.00;  }
        else if (monthlySalary < 7750)  { sssContribution = 337.50;  }
        else if (monthlySalary < 8250)  { sssContribution = 360.00;  }
        else if (monthlySalary < 8750)  { sssContribution = 382.50;  }
        else if (monthlySalary < 9250)  { sssContribution = 405.00;  }
        else if (monthlySalary < 9750)  { sssContribution = 427.50;  }
        else if (monthlySalary < 10250) { sssContribution = 450.00;  }
        else if (monthlySalary < 10750) { sssContribution = 472.50;  }
        else if (monthlySalary < 11250) { sssContribution = 495.00;  }
        else if (monthlySalary < 11750) { sssContribution = 517.50;  }
        else if (monthlySalary < 12250) { sssContribution = 540.00;  }
        else if (monthlySalary < 12750) { sssContribution = 562.50;  }
        else if (monthlySalary < 13250) { sssContribution = 585.00;  }
        else if (monthlySalary < 13750) { sssContribution = 607.50;  }
        else if (monthlySalary < 14250) { sssContribution = 630.00;  }
        else if (monthlySalary < 14750) { sssContribution = 652.50;  }
        else if (monthlySalary < 15250) { sssContribution = 675.00;  }
        else if (monthlySalary < 15750) { sssContribution = 697.50;  }
        else if (monthlySalary < 16250) { sssContribution = 720.00;  }
        else if (monthlySalary < 16750) { sssContribution = 742.50;  }
        else if (monthlySalary < 17250) { sssContribution = 765.00;  }
        else if (monthlySalary < 17750) { sssContribution = 787.50;  }
        else if (monthlySalary < 18250) { sssContribution = 810.00;  }
        else if (monthlySalary < 18750) { sssContribution = 832.50;  }
        else if (monthlySalary < 19250) { sssContribution = 855.00;  }
        else if (monthlySalary < 19750) { sssContribution = 877.50;  }
        else if (monthlySalary < 20250) { sssContribution = 900.00;  }
        else if (monthlySalary < 20750) { sssContribution = 922.50;  }
        else if (monthlySalary < 21250) { sssContribution = 945.00;  }
        else if (monthlySalary < 21750) { sssContribution = 967.50;  }
        else if (monthlySalary < 22250) { sssContribution = 990.00;  }
        else if (monthlySalary < 22750) { sssContribution = 1012.50; }
        else if (monthlySalary < 23250) { sssContribution = 1035.00; }
        else if (monthlySalary < 23750) { sssContribution = 1057.50; }
        else if (monthlySalary < 24250) { sssContribution = 1080.00; }
        else if (monthlySalary < 24750) { sssContribution = 1102.50; }
        else                            { sssContribution = 1125.00; } // Maximum

        return sssContribution / 2;  // Return semi-monthly share
    }

    // -- METHOD: Compute PhilHealth Deduction ---------------------------------
    // 3% of monthly basic salary; employee pays 50%; split for semi-monthly cutoff
    static double computePhilHealth(double monthlySalary) {
        double monthlyPremium = monthlySalary * 0.03;  // 3% premium rate

        // Apply PhilHealth floor and ceiling
        if (monthlyPremium < 300.00)  { monthlyPremium = 300.00;  }  // Minimum
        if (monthlyPremium > 1800.00) { monthlyPremium = 1800.00; }  // Maximum

        double employeeShare = monthlyPremium / 2;  // Employee pays 50%
        return employeeShare / 2;                   // Return semi-monthly share
    }

    // -- METHOD: Compute Pag-IBIG Deduction -----------------------------------
    // 1% if salary 1,000-1,500; 2% if over 1,500; max 100/month (50 per cutoff)
    static double computePagIbig(double monthlySalary) {
        double rate = (monthlySalary <= 1500) ? 0.01 : 0.02;  // Choose rate by bracket
        double contribution = monthlySalary * rate;

        if (contribution > 100.00) { contribution = 100.00; }  // Cap at maximum

        return contribution / 2;  // Return semi-monthly share
    }

    // -- METHOD: Compute Income Tax (Withholding Tax) -------------------------
    // BIR Tax Table: computed on taxable income = monthly salary - monthly deductions
    // Returns semi-monthly tax (monthly tax divided by 2)
    static double computeIncomeTax(double monthlySalary,
                                   double monthlySSS,
                                   double monthlyPhilHealth,
                                   double monthlyPagIbig) {

        // Taxable income = monthly salary minus total monthly government deductions
        double taxableIncome = monthlySalary - monthlySSS - monthlyPhilHealth - monthlyPagIbig;

        double monthlyTax;

        // BIR Withholding Tax brackets (TRAIN Law)
        if (taxableIncome <= 20832) {
            monthlyTax = 0;  // Exempt from withholding tax
        } else if (taxableIncome < 33333) {
            monthlyTax = (taxableIncome - 20833) * 0.20;
        } else if (taxableIncome < 66667) {
            monthlyTax = 2500 + (taxableIncome - 33333) * 0.25;
        } else if (taxableIncome < 166667) {
            monthlyTax = 10833 + (taxableIncome - 66667) * 0.30;
        } else if (taxableIncome < 666667) {
            monthlyTax = 40833.33 + (taxableIncome - 166667) * 0.32;
        } else {
            monthlyTax = 200833.33 + (taxableIncome - 666667) * 0.35;
        }

        return monthlyTax / 2;  // Return semi-monthly withholding tax
    }

    // -- METHOD: Compute Net Pay ----------------------------------------------
    // Calls all deduction methods and subtracts total from gross semi-monthly salary
    static double computeNetPay(double grossSemiMonthly) {
        double monthlySalary = grossSemiMonthly * 2;  // Estimate monthly for table lookups

        double sss        = computeSSS(monthlySalary);
        double philHealth = computePhilHealth(monthlySalary);
        double pagIbig    = computePagIbig(monthlySalary);
        double incomeTax  = computeIncomeTax(
                                monthlySalary,
                                sss * 2,        // Pass monthly equivalents
                                philHealth * 2,
                                pagIbig * 2
                            );

        double totalDeductions = sss + philHealth + pagIbig + incomeTax;
        return grossSemiMonthly - totalDeductions;  // Net pay = gross minus deductions
    }

    // -- MAIN METHOD ----------------------------------------------------------
    public static void main(String[] args) {

        // Employee Information
        String employeeName = "Juan Dela Cruz";
        String employeeID   = "EMP-001";
        String position     = "Sales Associate";
        String cutoffPeriod = "March 1-15, 2026";

        // Work Hours (from Task 7 logic)
        double timeIn    = 8.0;   // Clock-in at 8:00 AM
        double timeOut   = 17.0;  // Clock-out at 5:00 PM
        double breakTime = 1.0;   // 1 hour unpaid break
        int    workDays  = 11;    // Working days in this cutoff

        double hourlyRate = 95.50;  // Hourly rate in Philippine Peso

        // Input Validation: ensure values are positive and logical
        if (hourlyRate <= 0 || workDays <= 0 || timeOut <= timeIn) {
            System.out.println("ERROR: Invalid input values. Please check your data.");
            return;
        }

        // Compute Gross Semi-Monthly Salary
        double hoursPerDay      = (timeOut - timeIn) - breakTime;  // Net hours per day
        double totalHours       = hoursPerDay * workDays;          // Total hours this cutoff
        double grossSemiMonthly = totalHours * hourlyRate;         // Gross semi-monthly pay
        double monthlySalary    = grossSemiMonthly * 2;            // Estimated monthly salary

        // Compute Each Deduction (semi-monthly amounts)
        double sssDeduction        = computeSSS(monthlySalary);
        double philHealthDeduction = computePhilHealth(monthlySalary);
        double pagIbigDeduction    = computePagIbig(monthlySalary);
        double incomeTaxDeduction  = computeIncomeTax(
                                        monthlySalary,
                                        sssDeduction * 2,
                                        philHealthDeduction * 2,
                                        pagIbigDeduction * 2
                                     );
        double totalDeductions = sssDeduction + philHealthDeduction
                               + pagIbigDeduction + incomeTaxDeduction;

        // Compute Net Pay using method
        double netPay = computeNetPay(grossSemiMonthly);

        // Display Payroll Summary
        System.out.println("==============================================");
        System.out.println("        MotorPH Payroll System                ");
        System.out.println("        Deductions & Net Pay Report           ");
        System.out.println("==============================================");
        System.out.println("Employee Name     : " + employeeName);
        System.out.println("Employee ID       : " + employeeID);
        System.out.println("Position          : " + position);
        System.out.println("Cutoff Period     : " + cutoffPeriod);
        System.out.println("----------------------------------------------");
        System.out.println("Hours Per Day     : " + hoursPerDay + " hrs");
        System.out.println("Work Days         : " + workDays + " days");
        System.out.println("Total Hours Worked: " + totalHours + " hrs");
        System.out.printf( "Hourly Rate       : P%.2f%n", hourlyRate);
        System.out.println("----------------------------------------------");
        System.out.printf( "Gross Salary      : P%.2f%n", grossSemiMonthly);
        System.out.printf( "(Est. Monthly     : P%.2f)%n", monthlySalary);
        System.out.println("----------------------------------------------");
        System.out.printf( "SSS Deduction     : P%.2f%n", sssDeduction);
        System.out.printf( "PhilHealth        : P%.2f%n", philHealthDeduction);
        System.out.printf( "Pag-IBIG          : P%.2f%n", pagIbigDeduction);
        System.out.printf( "Income Tax        : P%.2f%n", incomeTaxDeduction);
        System.out.println("----------------------------------------------");
        System.out.printf( "Total Deductions  : P%.2f%n", totalDeductions);
        System.out.println("----------------------------------------------");
        System.out.printf( "NET PAY           : P%.2f%n", netPay);
        System.out.println("==============================================");

        // Verification message
        if (netPay > 0) {
            System.out.println("Computation verified successfully!");
        } else {
            System.out.println("Warning: Net pay is zero or negative. Please review.");
        }

        System.out.println("==============================================");
    }
}