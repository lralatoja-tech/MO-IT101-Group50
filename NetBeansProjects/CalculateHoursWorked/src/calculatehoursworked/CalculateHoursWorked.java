/**
 * MotorPH Payroll System
 * CalculateHoursWorked.java
 *
 * This program calculates the total hours worked by an employee
 * based on their time in, time out, and break time.
 */

public class CalculateHoursWorked {

    public static void main(String[] args) {

        // ── Employee Information ──────────────────────────────────────────────

        String employeeName = "Juan dela Cruz";  // Employee's full name
        String employeeID   = "EMP-001";         // Employee ID number
        String workDate     = "2024-01-15";      // Date of work

        // ── Time Variables (in decimal hours) ────────────────────────────────
        // Time is represented in 24-hour decimal format
        // Example: 8.5 means 8:30 AM, 17.5 means 5:30 PM

        double timeIn    = 8.0;   // Employee clocked in at 8:00 AM
        double timeOut   = 17.0;  // Employee clocked out at 5:00 PM
        double breakTime = 1.0;   // 1-hour lunch break (unpaid)

        // ── Calculate Total Hours Worked ──────────────────────────────────────
        // Formula: Total Hours = (Time Out - Time In) - Break Time

        double grossHours      = timeOut - timeIn;          // Hours between clock-in and clock-out
        double totalHoursWorked = grossHours - breakTime;   // Subtract break time for net hours

        // ── Display Employee Details ──────────────────────────────────────────

        System.out.println("=========================================");
        System.out.println("       MotorPH Payroll System            ");
        System.out.println("       Hours Worked Summary              ");
        System.out.println("=========================================");
        System.out.println("Employee Name   : " + employeeName);
        System.out.println("Employee ID     : " + employeeID);
        System.out.println("Date            : " + workDate);
        System.out.println("-----------------------------------------");
        System.out.println("Time In         : " + timeIn + ":00");
        System.out.println("Time Out        : " + timeOut + ":00");
        System.out.println("Break Time      : " + breakTime + " hour(s)");
        System.out.println("-----------------------------------------");
        System.out.println("Gross Hours     : " + grossHours + " hour(s)");
        System.out.println("Total Hours     : " + totalHoursWorked + " hour(s)");
        System.out.println("=========================================");

        // ── Verify Correctness ────────────────────────────────────────────────
        // Expected: (17.0 - 8.0) - 1.0 = 8.0 hours

        double expectedHours = 8.0;  // What we manually calculated

        if (totalHoursWorked == expectedHours) {
            System.out.println("Test passed: Computation is correct");
        } else {
            System.out.println("Test failed: Please review the calculation.");
        }

        System.out.println("=========================================");
    }
}