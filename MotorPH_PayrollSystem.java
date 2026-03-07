import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * MotorPH Basic Payroll System
 * MotorPH_PayrollSystem.java
 *
 * How it works:
 * 1. Reads employee data (ID, Name, Birthday, Hourly Rate) from employee_data.csv
 * 2. Reads attendance records (login/logout per day) from attendance_data.csv
 * 3. Calculates total hours worked per semi-monthly cutoff (June to December 2024)
 *    - Only counts hours from 8:00 AM to 5:00 PM (no overtime)
 *    - Grace period: login at or before 8:10 AM is treated as exactly 8:00 AM
 *    - Subtracts 1 hour lunch break from each day's hours
 * 4. Computes gross pay per cutoff (hours worked x hourly rate)
 * 5. For deduction computation: adds 1st + 2nd cutoff gross to get monthly gross
 *    - Uses monthly gross to look up correct deduction bracket amounts
 *    - Deductions are only subtracted from the 2nd cutoff pay
 * 6. 1st cutoff net = 1st cutoff gross (no deductions)
 * 7. 2nd cutoff net = 2nd cutoff gross - total deductions
 * 8. Displays a complete payroll summary per employee per month
 */

public class MotorPH_PayrollSystem {

    // ── CREATE DATA FILES IF THEY DON'T EXIST ─────────────────────────────────
    static void createDataFiles() {
        createEmployeeFile();
        createAttendanceFile();
    }

    static void createEmployeeFile() {
        try {
            java.io.File f = new java.io.File("employee_data.csv");
            if (f.exists()) return;
            FileWriter w = new FileWriter("employee_data.csv");
            w.write("EmployeeID,LastName,FirstName,Birthday,BasicSalary,HourlyRate\n");
            w.write("10001,Garcia,Manuel III,10/11/1983,90000.0,535.7142857142857\n");
            w.write("10002,Lim,Antonio,06/19/1988,60000.0,357.14285714285717\n");
            w.write("10003,Aquino,Bianca Sofia,08/04/1989,60000.0,357.14285714285717\n");
            w.write("10004,Reyes,Isabella,06/16/1994,60000.0,357.14285714285717\n");
            w.write("10005,Hernandez,Eduard,09/23/1989,52670.0,313.51190476190476\n");
            w.write("10006,Villanueva,Andrea Mae,02/14/1988,52670.0,313.51190476190476\n");
            w.write("10007,San Jose,Brad,03/15/1996,42975.0,255.80357142857142\n");
            w.write("10008,Romualdez,Alice,05/14/1992,22500.0,133.92857142857142\n");
            w.write("10009,Atienza,Rosie,09/24/1948,22500.0,133.92857142857142\n");
            w.write("10010,Alvaro,Roderick,03/30/1988,52670.0,313.51190476190476\n");
            w.write("10011,Salcedo,Anthony,09/14/1993,50825.0,302.5297619047619\n");
            w.write("10012,Lopez,Josie,01/14/1987,38475.0,229.01785714285714\n");
            w.write("10013,Farala,Martha,01/11/1942,24000.0,142.85714285714286\n");
            w.write("10014,Martinez,Leila,07/11/1970,24000.0,142.85714285714286\n");
            w.write("10015,Romualdez,Fredrick,03/10/1985,53500.0,318.45238095238096\n");
            w.write("10016,Mata,Christian,10/21/1987,42975.0,255.80357142857142\n");
            w.write("10017,De Leon,Selena,02/20/1975,41850.0,249.10714285714286\n");
            w.write("10018,San Jose,Allison,06/24/1986,22500.0,133.92857142857142\n");
            w.write("10019,Rosario,Cydney,10/06/1996,22500.0,133.92857142857142\n");
            w.write("10020,Bautista,Mark,02/12/1991,23250.0,138.39285714285714\n");
            w.write("10021,Lazaro,Darlene,11/25/1985,23250.0,138.39285714285714\n");
            w.write("10022,Delos Santos,Kolby,02/26/1980,24000.0,142.85714285714286\n");
            w.write("10023,Santos,Vella,12/31/1983,22500.0,133.92857142857142\n");
            w.write("10024,Del Rosario,Tomas,12/18/1978,22500.0,133.92857142857142\n");
            w.write("10025,Tolentino,Jacklyn,05/19/1984,24000.0,142.85714285714286\n");
            w.write("10026,Gutierrez,Percival,12/18/1970,24750.0,147.32142857142858\n");
            w.write("10027,Manalaysay,Garfield,08/28/1986,24750.0,147.32142857142858\n");
            w.write("10028,Villegas,Lizeth,12/12/1981,24000.0,142.85714285714286\n");
            w.write("10029,Ramos,Carol,08/20/1978,22500.0,133.92857142857142\n");
            w.write("10030,Maceda,Emelia,04/14/1973,22500.0,133.92857142857142\n");
            w.write("10031,Aguilar,Delia,01/27/1989,22500.0,133.92857142857142\n");
            w.write("10032,Castro,John Rafael,02/09/1992,52670.0,313.51190476190476\n");
            w.write("10033,Martinez,Carlos Ian,11/16/1990,52670.0,313.51190476190476\n");
            w.write("10034,Santos,Beatriz,08/07/1990,52670.0,313.51190476190476\n");
            w.close();
            System.out.println("Created employee_data.csv");
        } catch (IOException e) {
            System.out.println("Error creating employee_data.csv: " + e.getMessage());
        }
    }

    static void createAttendanceFile() {
        try {
            java.io.File f = new java.io.File("attendance_data.csv");
            if (!f.exists()) {
                System.out.println("Note: attendance_data.csv not found. Creating sample file...");
                FileWriter w = new FileWriter("attendance_data.csv");
                w.write("EmployeeID,Date,Login,Logout\n");
                w.write("10001,2024-06-03,08:59,18:31\n");
                w.write("10001,2024-06-04,09:47,19:07\n");
                w.write("10001,2024-06-10,08:30,17:30\n");
                w.write("10001,2024-06-14,08:05,17:00\n");
                w.write("10001,2024-06-17,09:00,18:00\n");
                w.write("10001,2024-06-20,08:00,17:00\n");
                w.write("10001,2024-07-01,08:00,17:00\n");
                w.write("10001,2024-07-16,09:00,18:00\n");
                w.write("10001,2024-08-01,08:10,17:00\n");
                w.write("10001,2024-08-16,08:00,17:00\n");
                w.write("10001,2024-09-02,08:00,17:00\n");
                w.write("10001,2024-09-16,08:00,17:00\n");
                w.write("10001,2024-10-01,08:00,17:00\n");
                w.write("10001,2024-10-16,08:00,17:00\n");
                w.write("10001,2024-11-04,08:00,17:00\n");
                w.write("10001,2024-11-18,08:00,17:00\n");
                w.write("10001,2024-12-02,08:00,17:00\n");
                w.write("10001,2024-12-16,08:00,17:00\n");
                w.close();
                System.out.println("Created sample attendance_data.csv");
            }
        } catch (IOException e) {
            System.out.println("Error creating attendance_data.csv: " + e.getMessage());
        }
    }

    // ── CALCULATE HOURS WORKED FOR ONE DAY ────────────────────────────────────
    // Rules:
    // - Only count hours between 8:00 AM and 5:00 PM (no overtime)
    // - Grace period: login at or before 8:10 AM is treated as exactly 8:00 AM
    // - Subtract 1 hour for lunch break
    static double calculateHours(int loginHour, int loginMin,
                                  int logoutHour, int logoutMin) {
        double loginDecimal  = loginHour  + loginMin  / 60.0;
        double logoutDecimal = logoutHour + logoutMin / 60.0;

        // Grace period: if login is at or before 8:10, treat as 8:00
        double effectiveStart;
        if (loginDecimal <= 8.0 + 10.0 / 60.0) {
            effectiveStart = 8.0;
        } else {
            effectiveStart = loginDecimal;  // Late employee - use actual time
        }

        // Cap logout at 5:00 PM - no overtime counted
        double effectiveEnd = Math.min(logoutDecimal, 17.0);

        double rawHours = effectiveEnd - effectiveStart;
        if (rawHours <= 0) return 0;

        // Subtract 1 hour lunch break
        return Math.max(0, rawHours - 1.0);
    }

    // ── PARSE TIME STRING "HH:MM" ─────────────────────────────────────────────
    static int[] parseTime(String timeStr) {
        String[] parts = timeStr.trim().split(":");
        return new int[]{ Integer.parseInt(parts[0]), Integer.parseInt(parts[1]) };
    }

    // ── EXTRACT MONTH FROM DATE STRING "YYYY-MM-DD" ───────────────────────────
    static int getMonth(String dateStr) {
        return Integer.parseInt(dateStr.substring(5, 7));
    }

    // ── EXTRACT DAY FROM DATE STRING "YYYY-MM-DD" ────────────────────────────
    static int getDay(String dateStr) {
        return Integer.parseInt(dateStr.substring(8, 10));
    }

    // ── COMPUTE SSS DEDUCTION (Monthly) ──────────────────────────────────────
    // Bracket lookup based on actual SSS Contribution Schedule
    static double computeSSS(double monthlySalary) {
        double sss;
        if      (monthlySalary < 3250)  { sss = 135.00;  }
        else if (monthlySalary < 3750)  { sss = 157.50;  }
        else if (monthlySalary < 4250)  { sss = 180.00;  }
        else if (monthlySalary < 4750)  { sss = 202.50;  }
        else if (monthlySalary < 5250)  { sss = 225.00;  }
        else if (monthlySalary < 5750)  { sss = 247.50;  }
        else if (monthlySalary < 6250)  { sss = 270.00;  }
        else if (monthlySalary < 6750)  { sss = 292.50;  }
        else if (monthlySalary < 7250)  { sss = 315.00;  }
        else if (monthlySalary < 7750)  { sss = 337.50;  }
        else if (monthlySalary < 8250)  { sss = 360.00;  }
        else if (monthlySalary < 8750)  { sss = 382.50;  }
        else if (monthlySalary < 9250)  { sss = 405.00;  }
        else if (monthlySalary < 9750)  { sss = 427.50;  }
        else if (monthlySalary < 10250) { sss = 450.00;  }
        else if (monthlySalary < 10750) { sss = 472.50;  }
        else if (monthlySalary < 11250) { sss = 495.00;  }
        else if (monthlySalary < 11750) { sss = 517.50;  }
        else if (monthlySalary < 12250) { sss = 540.00;  }
        else if (monthlySalary < 12750) { sss = 562.50;  }
        else if (monthlySalary < 13250) { sss = 585.00;  }
        else if (monthlySalary < 13750) { sss = 607.50;  }
        else if (monthlySalary < 14250) { sss = 630.00;  }
        else if (monthlySalary < 14750) { sss = 652.50;  }
        else if (monthlySalary < 15250) { sss = 675.00;  }
        else if (monthlySalary < 15750) { sss = 697.50;  }
        else if (monthlySalary < 16250) { sss = 720.00;  }
        else if (monthlySalary < 16750) { sss = 742.50;  }
        else if (monthlySalary < 17250) { sss = 765.00;  }
        else if (monthlySalary < 17750) { sss = 787.50;  }
        else if (monthlySalary < 18250) { sss = 810.00;  }
        else if (monthlySalary < 18750) { sss = 832.50;  }
        else if (monthlySalary < 19250) { sss = 855.00;  }
        else if (monthlySalary < 19750) { sss = 877.50;  }
        else if (monthlySalary < 20250) { sss = 900.00;  }
        else if (monthlySalary < 20750) { sss = 922.50;  }
        else if (monthlySalary < 21250) { sss = 945.00;  }
        else if (monthlySalary < 21750) { sss = 967.50;  }
        else if (monthlySalary < 22250) { sss = 990.00;  }
        else if (monthlySalary < 22750) { sss = 1012.50; }
        else if (monthlySalary < 23250) { sss = 1035.00; }
        else if (monthlySalary < 23750) { sss = 1057.50; }
        else if (monthlySalary < 24250) { sss = 1080.00; }
        else if (monthlySalary < 24750) { sss = 1102.50; }
        else                            { sss = 1125.00; }
        return sss;
    }

    // ── COMPUTE PHILHEALTH DEDUCTION (Monthly Employee Share) ─────────────────
    // 3% of monthly salary; employee pays 50%; min 300, max 1800
    static double computePhilHealth(double monthlySalary) {
        double premium = monthlySalary * 0.03;
        if (premium < 300.00)  premium = 300.00;
        if (premium > 1800.00) premium = 1800.00;
        return premium / 2;  // Employee pays 50%
    }

    // ── COMPUTE PAG-IBIG DEDUCTION (Monthly) ──────────────────────────────────
    // 1% if salary <= 1,500; 2% if over 1,500; max P100/month
    static double computePagIbig(double monthlySalary) {
        double rate = (monthlySalary <= 1500) ? 0.01 : 0.02;
        double contribution = monthlySalary * rate;
        if (contribution > 100.00) contribution = 100.00;
        return contribution;
    }

    // ── COMPUTE WITHHOLDING TAX (Monthly) ────────────────────────────────────
    // BIR TRAIN Law - applied on taxable income after SSS, PhilHealth, Pag-IBIG
    static double computeWithholdingTax(double monthlySalary,
                                         double sss,
                                         double philHealth,
                                         double pagIbig) {
        double taxable = monthlySalary - sss - philHealth - pagIbig;
        double tax;
        if      (taxable <= 20832)  { tax = 0; }
        else if (taxable < 33333)   { tax = (taxable - 20833) * 0.20; }
        else if (taxable < 66667)   { tax = 2500 + (taxable - 33333) * 0.25; }
        else if (taxable < 166667)  { tax = 10833 + (taxable - 66667) * 0.30; }
        else if (taxable < 666667)  { tax = 40833.33 + (taxable - 166667) * 0.32; }
        else                        { tax = 200833.33 + (taxable - 666667) * 0.35; }
        return tax;
    }

    // ── MAIN METHOD ───────────────────────────────────────────────────────────
    public static void main(String[] args) {

        // Step 1: Create CSV files if they don't exist
        createDataFiles();

        // ── STEP 2: READ EMPLOYEE DATA ────────────────────────────────────────
        int[]    empIDs      = new int[50];
        String[] lastNames   = new String[50];
        String[] firstNames  = new String[50];
        String[] birthdays   = new String[50];
        double[] basicSals   = new double[50];
        double[] hourlyRates = new double[50];
        int      empCount    = 0;

        try (BufferedReader br = new BufferedReader(new FileReader("employee_data.csv"))) {
            String line;
            br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                String[] parts = line.split(",", 6);
                if (parts.length < 6) continue;
                empIDs[empCount]      = Integer.parseInt(parts[0].trim());
                lastNames[empCount]   = parts[1].trim();
                firstNames[empCount]  = parts[2].trim();
                birthdays[empCount]   = parts[3].trim();
                basicSals[empCount]   = Double.parseDouble(parts[4].trim());
                hourlyRates[empCount] = Double.parseDouble(parts[5].trim());
                empCount++;
            }
        } catch (IOException e) {
            System.out.println("ERROR reading employee_data.csv: " + e.getMessage());
            return;
        }

        System.out.println("Loaded " + empCount + " employees.");

        // ── STEP 3: READ ATTENDANCE DATA ──────────────────────────────────────
        int[]    attEmpID  = new int[10000];
        String[] attDate   = new String[10000];
        String[] attLogin  = new String[10000];
        String[] attLogout = new String[10000];
        int      attCount  = 0;

        try (BufferedReader br = new BufferedReader(new FileReader("attendance_data.csv"))) {
            String line;
            br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                String[] parts = line.split(",");
                if (parts.length < 4) continue;
                attEmpID[attCount]  = Integer.parseInt(parts[0].trim());
                attDate[attCount]   = parts[1].trim();
                attLogin[attCount]  = parts[2].trim();
                attLogout[attCount] = parts[3].trim();
                attCount++;
            }
        } catch (IOException e) {
            System.out.println("ERROR reading attendance_data.csv: " + e.getMessage());
            return;
        }

        System.out.println("Loaded " + attCount + " attendance records.");
        System.out.println();

        // ── STEP 4: COMPUTE PAYROLL PER EMPLOYEE PER MONTH ───────────────────
        int[]    months     = {6, 7, 8, 9, 10, 11, 12};
        String[] monthNames = {"June", "July", "August", "September",
                               "October", "November", "December"};

        System.out.println("=".repeat(110));
        System.out.println("                    MOTORPH BASIC PAYROLL SYSTEM - 2024");
        System.out.println("=".repeat(110));

        for (int e = 0; e < empCount; e++) {
            int    empID    = empIDs[e];
            String fullName = lastNames[e] + ", " + firstNames[e];
            double hourRate = hourlyRates[e];

            System.out.println("\n" + "=".repeat(110));
            System.out.println("  Employee #    : " + empID);
            System.out.println("  Name          : " + fullName);
            System.out.println("  Birthday      : " + birthdays[e]);
            System.out.println("  Basic Salary  : " + basicSals[e]);
            System.out.println("  Hourly Rate   : " + hourRate);
            System.out.println("=".repeat(110));

            // Column headers
            System.out.printf("  %-11s | %-9s | %-9s | %-12s | %-12s | %-9s | %-10s | %-10s | %-9s | %-12s | %-12s%n",
                "Month",
                "Cut1 Hrs", "Cut2 Hrs",
                "1st Gross", "2nd Gross",
                "SSS", "PhilHealth", "Pag-IBIG",
                "Tax",
                "2nd Net Pay", "Total Net");
            System.out.println("  " + "-".repeat(106));

            for (int m = 0; m < months.length; m++) {
                int month = months[m];

                // Accumulate hours per cutoff for this employee and month
                double hours1st = 0;  // 1st cutoff: days 1-15
                double hours2nd = 0;  // 2nd cutoff: days 16-end

                for (int a = 0; a < attCount; a++) {
                    if (attEmpID[a] != empID) continue;
                    if (getMonth(attDate[a]) != month) continue;

                    int[]  login  = parseTime(attLogin[a]);
                    int[]  logout = parseTime(attLogout[a]);
                    double daily  = calculateHours(login[0], login[1],
                                                   logout[0], logout[1]);
                    int day = getDay(attDate[a]);

                    if (day <= 15) {
                        hours1st += daily;  // Add to 1st cutoff
                    } else {
                        hours2nd += daily;  // Add to 2nd cutoff
                    }
                }

                // Gross pay per cutoff
                double gross1st = hours1st * hourRate;
                double gross2nd = hours2nd * hourRate;

                // IMPORTANT: Add both cutoffs to get monthly gross for deduction lookup
                double monthlyGross = gross1st + gross2nd;

                // Compute deductions based on monthly gross (as instructed)
                double sss        = computeSSS(monthlyGross);
                double philHealth = computePhilHealth(monthlyGross);
                double pagIbig    = computePagIbig(monthlyGross);
                double tax        = computeWithholdingTax(
                                        monthlyGross, sss, philHealth, pagIbig);
                double totalDeductions = sss + philHealth + pagIbig + tax;

                // Deductions are only subtracted from 2nd cutoff pay
                double net1st    = gross1st;                       // 1st cutoff: no deductions
                double net2nd    = gross2nd - totalDeductions;     // 2nd cutoff: after deductions
                double totalNet  = net1st + net2nd;                // Total net for the month

                System.out.printf("  %-11s | %-9.4f | %-9.4f | %-12.4f | %-12.4f | %-9.4f | %-10.4f | %-10.4f | %-9.4f | %-12.4f | %-12.4f%n",
                    monthNames[m],
                    hours1st, hours2nd,
                    gross1st, gross2nd,
                    sss, philHealth, pagIbig, tax,
                    net2nd, totalNet
                );
            }

            System.out.println("  " + "-".repeat(106));
        }

        System.out.println("\n" + "=".repeat(110));
        System.out.println("  Payroll computation complete.");
        System.out.println("  Employees processed : " + empCount);
        System.out.println("  Period              : June - December 2024");
        System.out.println("  Note: Deductions applied to 2nd cutoff only.");
        System.out.println("        Monthly gross (1st + 2nd cutoff) used for deduction bracket lookup.");
        System.out.println("=".repeat(110));
    }
}
